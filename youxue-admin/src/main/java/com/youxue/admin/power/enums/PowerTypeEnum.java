package com.youxue.admin.power.enums;

/**
 * @author Masterwind
 * 2017年1月5日上午1:39:25

 * @Description 权限类别枚举
 */
public enum PowerTypeEnum
{
	ALL(0, "全部权限"), YDGL(1, "营地管理"), YXGL(2, "营销管理"), DDGL(3, "订单管理"), HYGL(4, "会员管理"), TYGL(5, "通用管理"), HTXTGL(6,
			"后台账户管理"), MODIFY(7, "密码管理");
	private final int value;//roleId
	private final String desc;

	public int getValue()
	{
		return value;
	}

	public String getDesc()
	{
		return desc;
	}

	PowerTypeEnum(int value, String desc)
	{
		this.value = value;
		this.desc = desc;
	}
}
