package com.youxue.pc.uc.controller;

import java.net.URLEncoder;

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
import com.youxue.pc.uc.dto.EmailUsableDto;
import com.youxue.pc.uc.dto.UserInfoDto;
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
			userInfo.setEmail(userInfo.getEmail());
		boolean ifPop = jedisProxy.exists(RedisConstant.getUserOrderIfPopCreditKey(accountId));
		if (!ifPop)
		{
			jedisProxy.setex(RedisConstant.getUserOrderIfPopCreditKey(accountId), "1", 24 * 60 * 60);
		}
		userInfo.setIfPop(ifPop ? false : true);
		userInfo.setResult(100);
		userInfo.setResultDesc("查询成功");
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
	public String updatePhoto(HttpServletRequest request, HttpServletResponse response, UserInfoDto userInfo)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (userInfo == null)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户修改信息失败"));
		/**
		 * 设置附加属性
		 */
		UserInfoVo dbUserInfo = new UserInfoVo();
		dbUserInfo.setAccountId(accountId);
		if (StringUtils.isNotBlank(userInfo.getBirthTime()))
			dbUserInfo.setBirthTime(DateUtil.formatToDate(userInfo.getBirthTime(), DateUtil.DEFAULT_DATE_FORMAT));
		if (StringUtils.isNotBlank(userInfo.getNickName()))
			dbUserInfo.setNickName(userInfo.getNickName());
		if (null != userInfo.getGender())
			dbUserInfo.setGender(userInfo.getGender());
		if (StringUtils.isNotBlank(userInfo.getLoveCity()))
			dbUserInfo.setLoveCity(userInfo.getLoveCity());
		dbUserInfo.setUpdateTime(DateUtil.getCurrentTimestamp());
		int success = userInfoDao.updateByPrimaryKeySelective(dbUserInfo);
		if (success <= 0)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户不存在，修改信息失败"));
		return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("用户信息修改成功"));
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

	@RequestMapping("/uc/isUsable.do")
	@ResponseBody
	public String isUsableEmail(HttpServletRequest request, HttpServletResponse response, String email)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (StringUtils.isBlank(email) || !CommonUtil.isValidEmail(email))
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("email为空或者格式有误"));
		UserInfoVo userInfo = userInfoDao.selectByEmail(email);
		EmailUsableDto resultDto = new EmailUsableDto();
		resultDto.setResult(100);
		resultDto.setResultDesc("操作成功");
		resultDto.setIsUsed(userInfo == null ? false : true);
		return JsonUtil.serialize(resultDto);
	}

	@RequestMapping("/uc/emailInfo.do")
	@ResponseBody
	public String emailInfo(HttpServletRequest request, HttpServletResponse response, String ifModify)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());

		UserInfoVo userInfo = userInfoDao.selectByPrimaryKey(accountId);
		if (userInfo == null)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户不存在"));
		EmailActiveDto emailDto = getResultEmailDto(userInfo);
		if (StringUtils.isNotBlank(ifModify) && "1".equals(ifModify))
		{
			emailDto.setActiveStatus(EmailActiveStatusConstant.INIT);
		}
		return JsonUtil.serialize(emailDto);

	}

	@RequestMapping("/uc/verifyEmail.do")
	public void emailInfo(HttpServletRequest request, HttpServletResponse response, String accountId, String key)
	{
		try
		{
			LOG.info("--参数：accountId=" + accountId + ",key=" + key);
			if (StringUtils.isBlank(accountId) || StringUtils.isBlank(key))
			{
				response.sendRedirect("/index.html?msg=" + URLEncoder.encode("链接非法", "utf-8"));
				return;
			}
			UserInfoVo userInfo = userInfoDao.selectByPrimaryKey(accountId);
			if (userInfo == null)
			{
				response.sendRedirect("/index.html?msg=" + URLEncoder.encode("用户不存在", "utf-8"));
				return;
			}
			boolean isValid = emailVerifyService.isValidActiveAccountUrl(accountId, key);
			if (!isValid)
			{
				response.sendRedirect("/index.html?msg=" + URLEncoder.encode("链接过期失效，请重新激活", "utf-8"));
				return;
			}
			userInfo.setEmailActiveStatus(EmailActiveStatusConstant.ACTIVED);
			userInfo.setUpdateTime(DateUtil.getCurrentTimestamp());
			userInfoDao.updateByPrimaryKeySelective(userInfo);
			jedisProxy.del(RedisConstant.getEmailVerifyKey(accountId));
			response.sendRedirect("/index.html");
		}
		catch (Exception e)
		{
			LOG.fatal("业务异常，msg:" + e.getMessage(), e);
		}
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
			emailDto.setActiveEmail(userInfo.getEmail());
			emailDto.setActiveEmailUrl(emailUrl);
		}
		else
		{
			emailDto.setActiveEmail(userInfo.getEmail());
		}
		return emailDto;
	}
}
