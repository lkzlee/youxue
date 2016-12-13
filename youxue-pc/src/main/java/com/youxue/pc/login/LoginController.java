package com.youxue.pc.login;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Date;

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

import com.lkzlee.pay.utils.CommonUtil;
import com.netease.is.image.CheckCode_a;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.constant.ImgConstant;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.dao.UserInfoDao;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.service.mobileCode.MobileCodeService;
import com.youxue.core.util.ControllerUtil;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.util.PropertyUtils;
import com.youxue.core.util.RandomUuidFactory;
import com.youxue.core.vo.UserInfoVo;

/**
 * @author Masterwind
 * 2016年11月1日下午2:54:06

 * @Description 登录接口
 */
@Controller
public class LoginController extends BaseController
{
	protected final static Log LOG = LogFactory.getLog(LoginController.class);
	/** SECIMAGE_WIDTH : 图片验证码宽度*/
	private static final String SECIMAGE_WIDTH = "160";
	/** SECIMAGE_HEIGHT : 图片验证码高度*/
	private static final String SECIMAGE_HEIGHT = "50";

	@Autowired
	MobileCodeService mobileCodeService;
	@Autowired
	UserInfoDao userInfoDao;
	@Autowired
	JedisProxy jedisProxy;

	/**
	 * @param request
	 * @param response
	 * @param mobile 手机号
	 * @param phoneCode 手机验证码
	 * @param imgCode 图片验证码
	 * @return 登录接口
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response, String mobile, String phoneCode,
			String imgCode, Integer autoLog)
	{
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(phoneCode) || StringUtils.isBlank(imgCode))
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("登录参数缺失！"));
		}
		if (autoLog == null || autoLog < 0 || autoLog > 1)
		{
			autoLog = 0;
		}
		if (!PropertyUtils.getProperty("ignoreCheckMobile").contains(mobile))
		{
			//校验手机验证码
			if (!mobileCodeService.checkMobileCode(mobile, phoneCode))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("手机验证码错误！"));
			}

			//校验图片验证码
			String imgCodeId = getCodeIdFromCookie(request, ImgConstant.MOBLIE_SECCODE_COOKIE_NAME);
			if (StringUtils.isBlank(imgCodeId))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("登录失败,请刷新重试！"));
			}
			String cachedImgCode = (String) jedisProxy.get(RedisConstant.MOBILE_LOGIN_IMG_SECCODE + imgCodeId);
			if (!imgCode.equalsIgnoreCase(cachedImgCode))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("图片验证码错误！"));
			}
			jedisProxy.del(RedisConstant.MOBILE_LOGIN_IMG_SECCODE + imgCodeId);
		}
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
			userInfoDao.insert(newUser);
			LOG.info("create user:" + mobile);
		}
		jedisProxy.del(RedisConstant.MOBILE_LOGIN_PHONE_SECCODE + mobile);
		ControllerUtil.setCurrentLoginUserName(request, mobile);
		if (autoLog == 1)
		{
			Cookie cookie = new Cookie(CommonConstant.AUTO_LOGIN_COOKIE, request.getSession().getId());
			cookie.setMaxAge(7 * 24 * 3600);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("登录成功"));
	}

	/**
	 * @param request
	 * @param response
	 * 获取手机验证码
	 */
	@RequestMapping("/mobileCode.do")
	@ResponseBody
	public String mobileCode(HttpServletRequest request, HttpServletResponse response, String phone)
	{
		if (StringUtils.isBlank(phone) || !CommonUtil.isValidMobile(phone))
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("请输入合法手机号"));
		}
		return JsonUtil.serialize(mobileCodeService.sendMobileCode(phone));
	}

	/**
	 * @param request
	 * @param response
	 * 获取图片验证码
	 */
	@RequestMapping("/verifyCode.do")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response, String width, String height)
	{
		System.out.println("get verifyCode request");
		if (StringUtils.isBlank(width) || Integer.valueOf(width) <= 0)
		{
			width = SECIMAGE_WIDTH;
		}
		if (StringUtils.isBlank(height) || Integer.valueOf(height) <= 0)
		{
			height = SECIMAGE_HEIGHT;
		}
		CheckCode_a sec = new CheckCode_a();
		sec.setCodeLength(4, 4); //设置字符个数；
		sec.setOutputSize(Integer.valueOf(height), Integer.valueOf(width)); //设置输出图片尺寸
		sec.setCodeAppearance(CheckCode_a.CodeAppearance_SingleUni);
		sec.setEasyLevel(0);
		ByteArrayInputStream imageStream = sec.createCAPTCHA();
		try
		{
			String code = sec.getCheckCode();
			System.out.println("verify code:" + code);
			String preCodeId = getCodeIdFromCookie(request, ImgConstant.MOBLIE_SECCODE_COOKIE_NAME);//获取cookie中的coodeId
			jedisProxy.del(RedisConstant.MOBILE_LOGIN_IMG_SECCODE + preCodeId);//删除已经失效的数据
			String codeId = RandomUuidFactory.getInstance().createUUID();//产生codeId(16位uuid)，用于标示memcached中用户对应的验证码
			Cookie cookie = new Cookie(ImgConstant.MOBLIE_SECCODE_COOKIE_NAME, codeId);
			cookie.setDomain(request.getServerName());
			cookie.setPath("/");
			cookie.setMaxAge(600);//10分钟失效
			response.addCookie(cookie);
			jedisProxy.setex(RedisConstant.MOBILE_LOGIN_IMG_SECCODE + codeId, code, 600);

			response.setContentType("image/png");
			OutputStream out = response.getOutputStream();
			byte[] image = new byte[imageStream.available()];
			imageStream.read(image);
			out.write(image);
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			LOG.error("产生片验证码操作出现异常！", e);
		}
	}

	/**
	* @Description:检测cookie中是否已经包含验证码id
	* @return   
	*/
	private String getCodeIdFromCookie(HttpServletRequest request, String cookieName)
	{
		Cookie[] cookies = request.getCookies();
		String preCodeId = "";
		for (Cookie cookie : cookies)
		{
			if (cookie.getName().equals(cookieName))
			{
				preCodeId = cookie.getValue().trim();
				break;
			}
		}
		return preCodeId;
	}
}
