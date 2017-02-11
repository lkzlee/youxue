package com.youxue.pc.weixin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.wx.helper.WeixinOauthHelper;

@Controller
public class WxLoginController
{
	private static Log log = LogFactory.getLog(WXDeveloperController.class);

	/***
	 * http://open.weixin.qq.com/connect/oauth2/authorize?appid=wx46701bcc055a3dec&redirect_uri=http%3a%2f%2flkzlee.imwork.net%2fwxlogin.do&response_type=code&scope=snsapi_base&state=DXFG#wechat_redirect
	 */
	@RequestMapping(value = "/wxlogin.do")
	@ResponseBody
	public String wxLogin(HttpServletRequest request, HttpServletResponse response, String code) throws IOException
	{
		log.info("微信授权登录code=" + code);
		String openId = WeixinOauthHelper.oauthAndLogin(code);
		log.info("微信授权登录的用户openId=" + openId);
		return "100";
	}
}
