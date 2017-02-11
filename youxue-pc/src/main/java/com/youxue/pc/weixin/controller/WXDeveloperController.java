package com.youxue.pc.weixin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lkzlee.pay.wx.controller.WxVerifyController;

@Controller
public class WXDeveloperController extends WxVerifyController
{
	private static Log log = LogFactory.getLog(WXDeveloperController.class);

	@RequestMapping(value = "/wx/verify_signature.do", method = RequestMethod.GET)
	public void verifyDeveloper(HttpServletRequest request, HttpServletResponse response, String signature,
			String echostr, String timestamp, String nonce) throws IOException
	{
		log.info("--微信调用，传参：signature=" + signature + "|timestamp=" + timestamp + "|nonce=" + nonce + "|echostr="
				+ echostr);
		String result = checkSignature(signature, echostr, timestamp, nonce);
		log.info("--微信调返回 result=" + result);
		response.getWriter().write(result);
	}
}
