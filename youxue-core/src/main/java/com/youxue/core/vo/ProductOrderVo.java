package com.youxue.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ProductOrderVo
{
	private String orderId;

	private Integer buyType;

	private String content;

	private Integer payType;

	private String accountId;

	private Date createTime;

	private Date buyTime;

	private String buyTimeStr;

	private BigDecimal price;

	private String remark;

	public String getBuyTimeStr()
	{
		return buyTimeStr;
	}

	public void setBuyTimeStr(String buyTimeStr)
	{
		this.buyTimeStr = buyTimeStr;
	}

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public Integer getBuyType()
	{
		return buyType;
	}

	public void setBuyType(Integer buyType)
	{
		this.buyType = buyType;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content == null ? null : content.trim();
	}

	public Integer getPayType()
	{
		return payType;
	}

	public void setPayType(Integer payType)
	{
		this.payType = payType;
	}

	public String getAccountId()
	{
		return accountId;
	}

	public void setAccountId(String accountId)
	{
		this.accountId = accountId == null ? null : accountId.trim();
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getBuyTime()
	{
		return buyTime;
	}

	public void setBuyTime(Date buyTime)
	{
		this.buyTime = buyTime;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark == null ? null : remark.trim();
	}
}