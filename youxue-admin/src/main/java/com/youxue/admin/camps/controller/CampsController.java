package com.youxue.admin.camps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youxue.core.dao.CampsDao;

@Controller
public class CampsController
{
	private static final Log logger = LogFactory.getLog(CampsController.class);

	@Autowired
	private CampsDao campsDao;

	/**
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "campsListIndex.do")
	public String campsListIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
	{
		try
		{
		}
		catch (Exception e)
		{
			logger.error("campsListIndex()--error", e);
			return "login";
		}
		return "main";
	}
}
