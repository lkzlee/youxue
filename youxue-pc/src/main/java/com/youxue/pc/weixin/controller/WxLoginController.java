package com.youxue.pc.weixin.controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.utils.DateUtil;
import com.lkzlee.pay.wx.helper.WeixinOauthHelper;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.dao.UserInfoDao;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.service.mobileCode.MobileCodeService;
import com.youxue.core.util.ControllerUtil;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.util.PropertyUtils;
import com.youxue.core.vo.UserInfoVo;

@Controller
public class WxLoginController extends BaseController
{
	private static Log log = LogFactory.getLog(WXDeveloperController.class);
	@Resource
	private UserInfoDao userInfoDao;
	@Autowired
	MobileCodeService mobileCodeService;
	@Autowired
	JedisProxy jedisProxy;

	/***
	 * http://open.weixin.qq.com/connect/oauth2/authorize?appid=wx46701bcc055a3dec&redirect_uri=http%3a%2f%2flkzlee.imwork.net%2fwxlogin.do&response_type=code&scope=snsapi_base&state=DXFG#wechat_redirect
	 */
	@RequestMapping(value = "/wxlogin.do")
	public String wxLogin(HttpServletRequest request, HttpServletResponse response, String code) throws IOException
	{
		log.info("微信授权登录code=" + code);
		String openId = WeixinOauthHelper.oauthAndLogin(code);
		log.info("微信授权登录的用户openId=" + openId);
		if (StringUtils.isEmpty(openId))
		{
			return "wx/login"; //跳转微信绑定手机号登录页
		}
		UserInfoVo userInfo = userInfoDao.selectUserInfoByOpenId(openId);
		if (userInfo != null)
		{
			ControllerUtil.setCurrentLoginUserName(request, userInfo.getAccountId());
			return "wx/index"; //跳转微信首页
		}
		ControllerUtil.setWxOpenId(request, openId);
		return "wx/login"; //跳转微信绑定手机号登录页
	}

	/***
	 * 微信绑定手机号页
	 * @param request
	 * @param response
	 * @param mobile
	 * @param phoneCode
	 * @param autoLog
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/wx/bindphone.do")
	@ResponseBody
	public String wxBindPhone(HttpServletRequest request, HttpServletResponse response, String mobile,
			String phoneCode, String autoLog) throws IOException
	{
		log.info("微信绑定手机好登录mobile=" + mobile + ",phoneCode=" + phoneCode + ",autoLog=" + autoLog);
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(phoneCode))
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("登录参数缺失！"));
		}
		if (!PropertyUtils.getProperty("ignoreCheckMobile").contains(mobile))
		{
			//校验手机验证码
			if (!mobileCodeService.checkMobileCode(mobile, phoneCode))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("手机验证码错误！"));
			}
		}
		String openId = ControllerUtil.getWxOpenId(request);
		//校验手机：如果不存在，则生成一条新的数据库记录
		UserInfoVo user = userInfoDao.selectByPrimaryKey(mobile);
		if (user == null)
		{
			UserInfoVo newUser = new UserInfoVo();
			newUser.setAccountId(mobile);
			newUser.setMobile(mobile);
			newUser.setCreateTime(new Date());
			newUser.setUpdateTime(new Date());
			newUser.setCreateIp(getCurrentLoginUserIp(request));
			newUser.setOpenId(openId);
			userInfoDao.insert(newUser);
			LOG.info("create user:" + mobile);
		}
		else
		{
			user.setOpenId(openId);
			user.setUpdateTime(DateUtil.getCurrentTimestamp());
			userInfoDao.updateByPrimaryKeySelective(user);
		}
		jedisProxy.del(RedisConstant.MOBILE_LOGIN_PHONE_SECCODE + mobile);
		ControllerUtil.setCurrentLoginUserName(request, mobile);
		if ("true".equals(autoLog))
		{
			Cookie cookie = new Cookie(CommonConstant.AUTO_LOGIN_COOKIE, request.getSession().getId());
			cookie.setMaxAge(7 * 24 * 3600);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("登录成功"));
	}
}
