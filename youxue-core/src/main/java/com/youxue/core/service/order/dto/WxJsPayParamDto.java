package com.youxue.core.service.order.dto;

import java.io.Serializable;

public class WxJsPayParamDto implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String timeStamp;
	private String appId;
	private String package_;
	private String nonceStr;
	private String signType;
	private String paySign;

	public String getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getPackage_()
	{
		return package_;
	}

	public void setPackage_(String package_)
	{
		this.package_ = package_;
	}

	public String getNonceStr()
	{
		return nonceStr;
	}

	public void setNonceStr(String nonceStr)
	{
		this.nonceStr = nonceStr;
	}

	public String getSignType()
	{
		return signType;
	}

	public void setSignType(String signType)
	{
		this.signType = signType;
	}

	public String getPaySign()
	{
		return paySign;
	}

	public void setPaySign(String paySign)
	{
		this.paySign = paySign;
	}

	@Override
	public String toString()
	{
		return "WxJsPayParamDto [timeStamp=" + timeStamp + ", appId=" + appId + ", package_=" + package_
				+ ", nonceStr=" + nonceStr + ", signType=" + signType + ", paySign=" + paySign + "]";
	}
}
