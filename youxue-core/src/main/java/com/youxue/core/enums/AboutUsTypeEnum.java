package com.youxue.core.enums;

/**
 * @author Masterwind
 * 2017年1月5日下午7:59:37
 * @Description 关于我们分类
 */
public enum AboutUsTypeEnum
{
	ABOUTUS("aboutUs"), QINGGU("qinggu"), CREATOR("creator"), MAINPRODUCT("mainProduct");
	private AboutUsTypeEnum(String desc)
	{
		this.setDesc(desc);
	}

	public static AboutUsTypeEnum getByDesc(String desc)
	{
		for (AboutUsTypeEnum type : AboutUsTypeEnum.values())
		{
			if (type.getDesc().equalsIgnoreCase(desc))
			{
				return type;
			}
		}
		return null;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	private String desc;
}
