package com.youxue.core.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *	后台系统权限
 * @author www.inxedu.com
 */

public class SysFunction implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**权限ID*/
	private int functionId;
	/**权限父ID*/
	private int parentId;
	/**权限名*/
	private String functionName;
	/**权限路径*/
	private String functionUrl;
	/**权限类型 1菜单 2功能*/
	private int functionType;
	/**权限创建时间*/
	private Date createTime;
	/**排序*/
	private int sort;
	/**子级权限List*/
	private List<SysFunction> childList;

	public int getFunctionId()
	{
		return functionId;
	}

	public void setFunctionId(int functionId)
	{
		this.functionId = functionId;
	}

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public String getFunctionName()
	{
		return functionName;
	}

	public void setFunctionName(String functionName)
	{
		this.functionName = functionName;
	}

	public String getFunctionUrl()
	{
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl)
	{
		this.functionUrl = functionUrl;
	}

	public int getFunctionType()
	{
		return functionType;
	}

	public void setFunctionType(int functionType)
	{
		this.functionType = functionType;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(int sort)
	{
		this.sort = sort;
	}

	public List<SysFunction> getChildList()
	{
		return childList;
	}

	public void setChildList(List<SysFunction> childList)
	{
		this.childList = childList;
	}

	@Override
	public String toString()
	{
		return "SysFunction [functionId=" + functionId + ", parentId=" + parentId + ", functionName=" + functionName
				+ ", functionUrl=" + functionUrl + ", functionType=" + functionType + ", createTime=" + createTime
				+ ", sort=" + sort + ", childList=" + childList + "]";
	}

}
