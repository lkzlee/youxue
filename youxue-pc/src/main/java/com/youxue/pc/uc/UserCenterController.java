package com.youxue.pc.uc;

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
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.UserInfoDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.UserInfoVo;

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
	private final static Log LOG = LogFactory.getLog(UserCenterController.class);

	/***
	 * 用户信息查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/uc/userinfo.html")
	@ResponseBody
	public String userInfo(HttpServletRequest request, HttpServletResponse response)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户未登录"));
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
	 * 用户头像修改
	 * @param request
	 * @param response
	 * @param userPhotoUrl
	 * @return
	 */
	@RequestMapping("/uc/updatePhoto.html")
	@ResponseBody
	public String updatePhoto(HttpServletRequest request, HttpServletResponse response, String userPhotoUrl)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户未登录"));
		if (StringUtils.isBlank(userPhotoUrl))
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("userPhotoUrl参数为空非法"));
		UserInfoVo userInfo = new UserInfoVo();
		userInfo.setAccountId(accountId);
		userInfo.setPhotoUrl(userPhotoUrl);
		int success = userInfoDao.updateByPrimaryKeySelective(userInfo);
		if (success <= 0)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户不存在，头像更新失败"));
		return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("用户头像更新成功"));
	}

	@RequestMapping("/uc/activeEmail.html")
	@ResponseBody
	public String verifyActiveEmail(HttpServletRequest request, HttpServletResponse response, String email)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户未登录"));

		UserInfoVo userInfo = userInfoDao.selectByPrimaryKey(accountId);
		if()
		userInfo.setAccountId(accountId);
		userInfo.setPhotoUrl(userPhotoUrl);
		int success = userInfoDao.updateByPrimaryKeySelective(userInfo);
		if (success <= 0)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("用户不存在，头像更新失败"));
		return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("用户头像更新成功"));
	}
}
