package com.youxue.admin.power.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Masterwind
 * 2015年12月28日下午5:15:19

 * @Description 菜单显示的权限
 */
@SuppressWarnings("serial")
public class MenuShow implements Serializable
{
	private String powerId;//暂时用不到
	private String powerName;
	private String powerUrl;
	private String icon;
	private List<MenuShow> sonList = new ArrayList<MenuShow>();

	public MenuShow()
	{
	}

	public MenuShow(String powerName, String powerUrl)
	{
		super();
		this.powerName = powerName;
		this.powerUrl = powerUrl;
	}

	public String getPowerId()
	{
		return powerId;
	}

	public void setPowerId(String powerId)
	{
		this.powerId = powerId;
	}

	public String getPowerName()
	{
		return powerName;
	}

	public void setPowerName(String powerName)
	{
		this.powerName = powerName;
	}

	public String getPowerUrl()
	{
		return powerUrl;
	}

	public void setPowerUrl(String powerUrl)
	{
		this.powerUrl = powerUrl;
	}

	@Override
	public String toString()
	{
		return "MenuShow [powerId=" + powerId + ", powerName=" + powerName + ", powerUrl=" + powerUrl + "]";
	}

	public List<MenuShow> getSonList()
	{
		return sonList;
	}

	public void setSonList(List<MenuShow> sonList)
	{
		this.sonList = sonList;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

}
