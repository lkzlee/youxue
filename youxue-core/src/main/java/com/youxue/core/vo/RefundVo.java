package com.youxue.core.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RefundVo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int INIT = 0;

	public static final int REFUND = 1;

	private String orderId;

	private String logicOrderId;

	private BigDecimal refundAmount;

	private Integer status;

	private Date createTime;

	private Date updateTime;

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getLogicOrderId()
	{
		return logicOrderId;
	}

	public void setLogicOrderId(String logicOrderId)
	{
		this.logicOrderId = logicOrderId == null ? null : logicOrderId.trim();
	}

	public BigDecimal getRefundAmount()
	{
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount)
	{
		this.refundAmount = refundAmount;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
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

	@Override
	public String toString()
	{
		return "RefundVo [orderId=" + orderId + ", logicOrderId=" + logicOrderId + ", refundAmount=" + refundAmount
				+ ", status=" + status + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
}