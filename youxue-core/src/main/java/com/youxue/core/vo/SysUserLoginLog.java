package com.youxue.core.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户登录日志
 * @author www.inxedu.com
 */
public class SysUserLoginLog implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int logId;//ID
	private Date loginTime;//登录时间
	private String ip;//登录IP
	private int userId;//用户ID
	private String osName;//操作系统
	private String userAgent;//浏览器

	public int getLogId()
	{
		return logId;
	}

	public void setLogId(int logId)
	{
		this.logId = logId;
	}

	public Date getLoginTime()
	{
		return loginTime;
	}

	public void setLoginTime(Date loginTime)
	{
		this.loginTime = loginTime;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getOsName()
	{
		return osName;
	}

	public void setOsName(String osName)
	{
		this.osName = osName;
	}

	public String getUserAgent()
	{
		return userAgent;
	}

	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

	@Override
	public String toString()
	{
		return "SysUserLoginLog [logId=" + logId + ", loginTime=" + loginTime + ", ip=" + ip + ", userId=" + userId
				+ ", osName=" + osName + ", userAgent=" + userAgent + "]";
	}
}
