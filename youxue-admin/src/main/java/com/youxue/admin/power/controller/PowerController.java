//package com.youxue.admin.power.controller;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.google.common.collect.Lists;
//import com.youxue.admin.constant.AdminConstant;
//import com.youxue.admin.power.annotation.PowerCheck;
//import com.youxue.admin.power.constant.PowerConstant;
//import com.youxue.core.common.BaseController;
//import com.youxue.core.util.ReflectUtil;
//
//@Controller
//@PowerCheck(powerType = AdminConstant.POWER_MANAGE_POWER)
//public class PowerController extends BaseController
//{
//	//要查询权限的用户名
//	public static final String QUERY_NAME = "queryName";
//	private final static Log LOG = LogFactory.getLog(PowerController.class);
//
//	@RequestMapping(value =
//	{ "/query_manager_power.html" })
//	public String queryPowerOwned()
//	{
//		return "power/powerManager";
//	}
//
//	@RequestMapping(value =
//	{ "/query_manager_power_by_queryName.html" })
//	public String queryPowerOwnedByAdminName(HttpServletRequest request, String queryName)
//	{
//		try
//		{
//			if (StringUtils.isBlank(queryName))
//			{
//				request.setAttribute("msg", "查询失败，参数缺失！");
//				return "power/powerManager";
//			}
//			if (queryName.equals(PowerConstant.GOD_USER))
//			{
//				request.setAttribute("msg", "查询失败，超级管理员权限不可修改！");
//				return "power/powerManager";
//			}
//			UserVo user = userDao.selectUser(queryName);
//			if (user == null)
//			{
//				request.setAttribute("msg", "查询失败，用户不存在！");
//				return "power/powerManager";
//			}
//			LOG.info("query_manager_power_by_queryName queryName:" + queryName);
//			List<PowerVo> powers = powerDao.findAll();
//			List<String> myPowers = powerRelationDao.selectByUsername(queryName);
//			List<PowerEdit> powerEditList = new LinkedList<>();
//			for (PowerVo power : powers)
//			{
//				PowerEdit edit = new PowerEdit();
//				ReflectUtil.setObjectFiledsValue(edit, power);
//				if (CollectionUtils.isNotEmpty(myPowers) && myPowers.contains(power.getPowerId()))
//				{
//					edit.setOwned(1);
//				}
//				else
//				{
//					edit.setOwned(0);
//				}
//				powerEditList.add(edit);
//			}
//			request.setAttribute("menuListForEdit", powerEditList);
//			request.setAttribute(QUERY_NAME, queryName);
//		}
//		catch (Exception e)
//		{
//			LOG.error("query_manager_power_by_queryName exception:" + e);
//		}
//		return "power/powerDistribution";
//	}
//
//	@RequestMapping(value =
//	{ "/update_manager_power.html" })
//	public ModelAndView updatePowerOwned(HttpServletRequest request, String queryName, String[] powerSelected)
//	{
//		LOG.info("updatePowerOwned powerSelected:" + powerSelected + ",queryName:" + queryName);
//		if (powerSelected == null || StringUtils.isBlank(queryName))
//		{
//			request.setAttribute("msg", "修改失败，参数缺失！");
//			return new ModelAndView("redirect:/query_manager_power_by_queryName.html?queryName=" + queryName);
//		}
//		if (queryName.equals(PowerConstant.GOD_USER))
//		{
//			request.setAttribute("msg", "修改失败，不能操作超级管理员权限！");
//			return new ModelAndView("redirect:/query_manager_power_by_queryName.html?queryName=" + queryName);
//		}
//		List<String> selectedPowerList = Lists.newArrayList(powerSelected);
//		updateManagerPower(selectedPowerList, queryName);
//		request.setAttribute("msg", "更新成功！");
//		return new ModelAndView("redirect:/query_manager_power_by_queryName.html?queryName=" + queryName);
//	}
//
//	private void updateManagerPower(List<String> selectedPowerList, String queryName)
//	{
//		List<String> adminPowerInDbList = powerRelationDao.selectByUsername(queryName);//数据库中的权限
//
//		List<String> addList = new ArrayList<String>();
//		List<String> deleteList = new ArrayList<String>();
//		List<String> temp = new ArrayList<String>();
//		temp.addAll(selectedPowerList);
//
//		selectedPowerList.removeAll(adminPowerInDbList);
//		addList = selectedPowerList;
//
//		adminPowerInDbList.removeAll(temp);
//		deleteList = adminPowerInDbList;
//
//		for (String s : addList)
//		{
//			UserPowerRelationVo relation = new UserPowerRelationVo();
//			relation.setUsername(queryName);
//			relation.setPowerId(s);
//			powerRelationDao.add(relation);
//		}
//
//		for (String s : deleteList)
//		{
//			UserPowerRelationVo relation = new UserPowerRelationVo();
//			relation.setUsername(queryName);
//			relation.setPowerId(s);
//			powerRelationDao.delete(relation);
//		}
//	}
//}
