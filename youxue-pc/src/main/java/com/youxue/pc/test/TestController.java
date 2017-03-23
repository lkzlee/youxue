package com.youxue.pc.test;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.wx.helper.MenuOperationHelper;
import com.youxue.core.common.BaseController;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.JsonUtil;

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
	@Autowired
	JedisProxy jedisProxy;

	@RequestMapping("/removeRedis.do")
	@ResponseBody
	public String removeRedis(HttpServletRequest request, HttpServletResponse response, String accountId)
	{
		try
		{
			jedisProxy.del(RedisConstant.SHOP_CART_KEY + accountId);
		}
		catch (Exception e)
		{
			LOG.info("error", e);
			return "error";
		}
		return "success";
	}

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

	@RequestMapping("/testMenu.do")
	@ResponseBody
	public String testMenu(HttpServletRequest request, HttpServletResponse response, String type)
	{
		try
		{
			if ("create".equals(type))
			{
				boolean result = MenuOperationHelper.createMenuByConfig();
				LOG.info("创建菜单result=" + result);
				return "100";
			}
			else if ("delete".equals(type))
			{
				boolean result = MenuOperationHelper.deleteMenuByConfig();
				LOG.info("删除菜单result=" + result);
				return "100";
			}
			else
			{
				Map<String, Object> result = MenuOperationHelper.getMenuInfoByConfig();
				LOG.info("查询菜单结果：" + result);
				return JsonUtil.serialize(result);
			}

		}
		catch (Exception e)
		{
			LOG.info("错误", e);
			return "-1";
		}
	}
}
