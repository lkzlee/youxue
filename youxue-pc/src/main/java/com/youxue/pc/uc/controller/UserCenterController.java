package com.youxue.pc.uc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.utils.CommonUtil;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.EmailActiveStatusConstant;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.dao.UserInfoDao;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.UserInfoVo;
import com.youxue.pc.uc.dto.EmailActiveDto;
import com.youxue.pc.uc.service.EmailVerifyService;

/***
 * 用户个人页
 * @author lkzlee
 *
 */
@Controller
public class UserCenterController extends BaseController
{
	@Resource
	private UserInfoDao userInfoDao;
	@Resource
	private EmailVerifyService emailVerifyService;
	@Resource
	private JedisProxy jedisProxy;
	private final static Log LOG = LogFactory.getLog(UserCenterController.class);

	/***
	 * 用户信息查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/uc/userinfo.do")
	@ResponseBody
	public String userInfo(HttpServletRequest request, HttpServletResponse response)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		UserInfoVo userInfo = userInfoDao.selectByPrimaryKey(accountId);
		if (userInfo == null)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户不存在"));
		if (StringUtils.isNotBlank(userInfo.getMobile()))
			userInfo.setMobile(CommonUtil.getSecretNumberWithStart(userInfo.getMobile(), 3, 4, 4));
		if (StringUtils.isNotBlank(userInfo.getEmail()))
			userInfo.setEmail(CommonUtil.getEncryptAccountId(userInfo.getEmail(), 1));
		LOG.info("查询用户信息为：userInfo=" + userInfo);
		return JsonUtil.serialize(userInfo);

	}

	/**
	 * 用户信息修改
	 * @param request
	 * @param response
	 * @param userPhotoUrl
	 * @return
	 */
	@RequestMapping("/uc/updateUserInfo.do")
	@ResponseBody
	public String updatePhoto(HttpServletRequest request, HttpServletResponse response, UserInfoVo userInfo)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		/***
		 * 这几个值不能进行设置
		 */

		userInfo.setCreateIp(null);
		userInfo.setCredit(null);
		userInfo.setEmail(null);
		userInfo.setEmailActiveStatus(null);
		userInfo.setPhotoUrl(null);
		/**
		 * 设置附加属性
		 */
		userInfo.setAccountId(accountId);
		userInfo.setUpdateTime(DateUtil.getCurrentTimestamp());
		int success = userInfoDao.updateByPrimaryKeySelective(userInfo);
		if (success <= 0)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户不存在，头像更新失败"));
		return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("用户头像更新成功"));
	}

	/**
	 * 用户头像修改
	 * @param request
	 * @param response
	 * @param userPhotoUrl
	 * @return
	 */
	@RequestMapping("/uc/updatePhoto.do")
	@ResponseBody
	public String updatePhoto(HttpServletRequest request, HttpServletResponse response, String userPhotoUrl)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (StringUtils.isBlank(userPhotoUrl))
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("userPhotoUrl参数为空非法"));
		UserInfoVo userInfo = new UserInfoVo();
		userInfo.setAccountId(accountId);
		userInfo.setPhotoUrl(userPhotoUrl);
		userInfo.setUpdateTime(DateUtil.getCurrentTimestamp());
		int success = userInfoDao.updateByPrimaryKeySelective(userInfo);
		if (success <= 0)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户不存在，头像更新失败"));
		return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("用户头像更新成功"));
	}

	@RequestMapping("/uc/activeEmail.do")
	@ResponseBody
	public String verifyActiveEmail(HttpServletRequest request, HttpServletResponse response, String email)
	{
		if (StringUtils.isBlank(email) || !CommonUtil.isValidEmail(email))
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		UserInfoVo userInfo = userInfoDao.selectByPrimaryKey(accountId);
		if (userInfo == null)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户不存在"));
		if (email.equals(userInfo.getEmail()) && EmailActiveStatusConstant.ACTIVED == userInfo.getEmailActiveStatus())
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户该邮箱已经激活无需再次激活"));

		userInfo.setEmail(email);
		emailVerifyService.sendActiveEmailUrl(userInfo);
		userInfo.setEmailActiveStatus(EmailActiveStatusConstant.SENDED_MAIL);
		userInfo.setUpdateTime(DateUtil.getCurrentTimestamp());
		userInfoDao.updateByPrimaryKeySelective(userInfo);
		EmailActiveDto emailDto = getResultEmailDto(userInfo);
		return JsonUtil.serialize(emailDto.setDesc("激活邮件已发送至您邮箱，点击激活"));

	}

	@RequestMapping("/uc/emailInfo.do")
	@ResponseBody
	public String emailInfo(HttpServletRequest request, HttpServletResponse response)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());

		UserInfoVo userInfo = userInfoDao.selectByPrimaryKey(accountId);
		if (userInfo == null)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户不存在"));
		EmailActiveDto emailDto = getResultEmailDto(userInfo);
		return JsonUtil.serialize(emailDto);

	}

	@RequestMapping("/uc/verifyEmail.do")
	@ResponseBody
	public String emailInfo(HttpServletRequest request, HttpServletResponse response, String accountId, String key)
	{
		if (StringUtils.isBlank(accountId) || StringUtils.isBlank(key))
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("链接非法"));
		UserInfoVo userInfo = userInfoDao.selectByPrimaryKey(accountId);
		if (userInfo == null)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户不存在"));
		boolean isValid = emailVerifyService.isValidActiveAccountUrl(accountId, key);
		if (!isValid)
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("链接过期失效，请重新激活"));
		}
		userInfo.setEmailActiveStatus(EmailActiveStatusConstant.ACTIVED);
		userInfo.setUpdateTime(DateUtil.getCurrentTimestamp());
		userInfoDao.updateByPrimaryKeySelective(userInfo);
		jedisProxy.del(RedisConstant.getEmailVerifyKey(accountId));
		return JsonUtil.serialize(BaseResponseDto.successDto());

	}

	private EmailActiveDto getResultEmailDto(UserInfoVo userInfo)
	{

		EmailActiveDto emailDto = new EmailActiveDto();
		emailDto.setResult(100);
		emailDto.setResultDesc("操作成功");
		emailDto.setActiveStatus(EmailActiveStatusConstant.INIT);
		if (userInfo.getEmailActiveStatus() == null)
			return emailDto;
		emailDto.setActiveStatus(userInfo.getEmailActiveStatus());
		if (EmailActiveStatusConstant.INIT == userInfo.getEmailActiveStatus()
				&& StringUtils.isNotBlank(userInfo.getEmail()))
		{
			emailDto.setActiveEmail(userInfo.getEmail());
		}
		else if (EmailActiveStatusConstant.SENDED_MAIL == userInfo.getEmailActiveStatus()
				&& StringUtils.isNotBlank(userInfo.getEmail()))
		{
			String suffixEmail = userInfo.getEmail().split("@")[1];
			String emailUrl = EmailActiveStatusConstant.emailUrl.get(suffixEmail);
			emailDto.setActiveEmailUrl(emailUrl);
		}
		else
		{
			emailDto.setActiveEmail(userInfo.getEmail());
		}
		return emailDto;
	}
}
