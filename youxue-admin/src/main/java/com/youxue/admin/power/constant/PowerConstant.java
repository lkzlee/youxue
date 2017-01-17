package com.youxue.admin.power.constant;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.youxue.admin.power.enums.PowerTypeEnum;
import com.youxue.admin.power.vo.MenuShow;

public class PowerConstant
{

	public static Map<Integer, List<MenuShow>> menuMap = new HashMap<>();
	public static List<MenuShow> allMenuList = new LinkedList<>();

	public static final Map<String, String> roleMap = new HashMap<String, String>();
	static
	{
		MenuShow yDGLMenu = new MenuShow(PowerTypeEnum.YDGL.getDesc(), "");
		yDGLMenu.getSonList().add(new MenuShow("营地列表", "/campsList.do"));
		yDGLMenu.getSonList().add(new MenuShow("营地分组", "/campsCategoryListIndex.do"));
		menuMap.put(PowerTypeEnum.YDGL.getValue(), Lists.newArrayList(yDGLMenu));
		allMenuList.add(yDGLMenu);

		MenuShow yxGLMenu = new MenuShow(PowerTypeEnum.YXGL.getDesc(), "");
		yxGLMenu.getSonList().add(new MenuShow("优惠券", "/couponList.do"));
		menuMap.put(PowerTypeEnum.YXGL.getValue(), Lists.newArrayList(yxGLMenu));
		allMenuList.add(yxGLMenu);

		MenuShow ddGLMenu = new MenuShow(PowerTypeEnum.DDGL.getDesc(), "");
		ddGLMenu.getSonList().add(new MenuShow("营地订单", "/admin/userorder.do"));
		ddGLMenu.getSonList().add(new MenuShow("周边产品订单", "/otherOrderList.do"));
		menuMap.put(PowerTypeEnum.DDGL.getValue(), Lists.newArrayList(ddGLMenu));
		allMenuList.add(ddGLMenu);

		MenuShow hyGLMenu = new MenuShow(PowerTypeEnum.HYGL.getDesc(), "");
		hyGLMenu.getSonList().add(new MenuShow("会员用户", "/admin/userList.do"));
		menuMap.put(PowerTypeEnum.HYGL.getValue(), Lists.newArrayList(hyGLMenu));
		allMenuList.add(hyGLMenu);

		MenuShow tyGLMenu = new MenuShow(PowerTypeEnum.TYGL.getDesc(), "");
		//		tyGLMenu.getSonList().add(new MenuShow("首页管理", "/indexManage.do"));
		tyGLMenu.getSonList().add(new MenuShow("搜索管理", "/searchList.do"));
		tyGLMenu.getSonList().add(new MenuShow("关于我们", "/aboutUs.do"));
		tyGLMenu.getSonList().add(new MenuShow("资讯", "/news.do"));
		tyGLMenu.getSonList().add(new MenuShow("私人订制-定制案例", "/personalCaseList.do"));
		tyGLMenu.getSonList().add(new MenuShow("私人订制-周边产品", "/surround.do"));
		menuMap.put(PowerTypeEnum.TYGL.getValue(), Lists.newArrayList(tyGLMenu));
		allMenuList.add(tyGLMenu);

		MenuShow htxtGLMenu = new MenuShow(PowerTypeEnum.HTXTGL.getDesc(), "");
		htxtGLMenu.getSonList().add(new MenuShow("管理员管理", "/sysUserList.do"));
		htxtGLMenu.getSonList().add(new MenuShow("账户修改", "/sysUserModify.do"));
		menuMap.put(PowerTypeEnum.HTXTGL.getValue(), Lists.newArrayList(htxtGLMenu));
		allMenuList.add(htxtGLMenu);

		menuMap.put(PowerTypeEnum.ALL.getValue(), allMenuList);
	}
	static
	{
		for (PowerTypeEnum pt : PowerTypeEnum.values())
		{
			roleMap.put("" + pt.getValue(), pt.getDesc());
		}
	}
	//接口请求的错误信息
	public static final String ERROR_MSG = "errorMessage";
}
