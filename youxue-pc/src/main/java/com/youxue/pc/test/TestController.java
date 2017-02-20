package com.youxue.pc.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseController;
import com.youxue.core.dao.CampsDao;

/**
 * @author Masterwind
 * 2017年2月20日下午4:12:37

 * @Description 测试用
 */
@Controller
public class TestController extends BaseController
{
	protected final static Log LOG = LogFactory.getLog(TestController.class);

	@Autowired
	CampsDao campsDao;
	@Autowired
	TxTestService txTestService;

	@RequestMapping("/test1.do")
	@ResponseBody
	public String test1(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			txTestService.test1();
		}
		catch (Exception e)
		{
			LOG.info("error", e);
			return "error";
		}
		return "success";
	}

	@RequestMapping("/test1WithOutTx.do")
	@ResponseBody
	public String test1WithOutTx(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			txTestService.test1WithOutTx();
		}
		catch (Exception e)
		{
			LOG.info("error", e);
			return "error";
		}
		return "success";
	}
}
