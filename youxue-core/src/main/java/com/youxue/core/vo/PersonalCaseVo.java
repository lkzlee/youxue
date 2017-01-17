package com.youxue.core.vo;

import java.util.Date;

/**
 * @author Masterwind
 * 2017年1月17日下午2:44:52
	
 * @Description 定制案例
 */
public class PersonalCaseVo
{
	private String caseId;

	private String caseName;

	private String caseContent;

	private Date createTime;

	private Date updateTime;

	public String getCaseId()
	{
		return caseId;
	}

	public void setCaseId(String caseId)
	{
		this.caseId = caseId == null ? null : caseId.trim();
	}

	public String getCaseName()
	{
		return caseName;
	}

	public void setCaseName(String caseName)
	{
		this.caseName = caseName == null ? null : caseName.trim();
	}

	public String getCaseContent()
	{
		return caseContent;
	}

	public void setCaseContent(String caseContent)
	{
		this.caseContent = caseContent == null ? null : caseContent.trim();
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
}