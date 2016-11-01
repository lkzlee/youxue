package com.youxue.pc.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youxue.core.common.BaseController;

@Controller
public class IndexController extends BaseController
{
	protected final static Log LOG = LogFactory.getLog(IndexController.class);

	/**
	 * @param request
	 * @param response
	 */
	@RequestMapping("/index.html")
	public String index(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("get index request");
		return "index";
	}
}
