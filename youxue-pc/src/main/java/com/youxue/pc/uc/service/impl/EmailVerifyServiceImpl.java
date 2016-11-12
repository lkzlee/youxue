package com.youxue.pc.uc.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.lkzlee.pay.utils.MD5Utils;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.constant.UrlConstant;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.MailUtil;
import com.youxue.core.util.PropertyUtils;
import com.youxue.core.vo.UserInfoVo;
import com.youxue.pc.uc.service.EmailVerifyService;

@Service
public class EmailVerifyServiceImpl implements EmailVerifyService
{
	@Resource
	private MailUtil mailUtil;
	@Resource
	private JedisProxy jedisProxy;

	@Override
	public void sendActiveEmailUrl(UserInfoVo userInfo)
	{
		String verifyUrl = calcEmailVerifyUrl(userInfo.getAccountId());
		mailUtil.init(userInfo.getEmail(), null);
		mailUtil.sendEmail("账号激活邮件", "系统激活", "欢迎使用，点击<a href='" + verifyUrl + "'>此处链接</a>激活,过期时间30分钟", "UTF-8");
	}

	private String calcEmailVerifyUrl(String accountId)
	{
		StringBuffer sb = new StringBuffer(UrlConstant.EMAIL_VERIFY_URL);
		String nonceStr = RandomStringUtils.random(12);
		sb.append("?key=" + calcKeyByUserInfoSalt(accountId, nonceStr) + "&accountId=" + accountId);
		/***
		 * 设置超时时间为30分钟
		 */
		jedisProxy.setex(RedisConstant.getEmailVerifyKey(accountId), nonceStr, 30000);
		return sb.toString();
	}

	@Override
	public boolean isValidActiveAccountUrl(String accountId, String key)
	{
		String nonceStr = (String) jedisProxy.get(RedisConstant.getEmailVerifyKey(accountId));
		if (StringUtils.isBlank(nonceStr))
			return false;
		String calcKey = calcKeyByUserInfoSalt(accountId, nonceStr);
		return calcKey.equals(key);
	}

	private static String calcKeyByUserInfoSalt(String accountId, String nonceStr)
	{
		StringBuffer source = new StringBuffer(accountId);
		source.append(PropertyUtils.getProperty("SystemSecretKey", "DA#%DAD#3665@!DV^#@*$%^#$653CS$%&*@XCVD"));
		source.append(nonceStr);
		return MD5Utils.getMD5(source.toString());
	}
}
