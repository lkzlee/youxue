package com.youxue.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class LogicOrderVo
{
	private String logicOrderId;

	private String accountId;

	private Date createTime;

	private Date updateTime;

	private String orderIp;

	private Integer payType;

	private Integer payStatus;

	private Date payTime;
	private Date notifyTime;

	private String platformOrderId;

	private BigDecimal totalMoney;

	private BigDecimal totalPayMoney;

	public final static int UNPAY = 0;
	public final static int PAY = 1;
	public final static int REFUND = 2;

	public String getLogicOrderId()
	{
		return logicOrderId;
	}

	public void setLogicOrderId(String logicOrderId)
	{
		this.logicOrderId = logicOrderId == null ? null : logicOrderId.trim();
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

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public String getOrderIp()
	{
		return orderIp;
	}

	public void setOrderIp(String orderIp)
	{
		this.orderIp = orderIp == null ? null : orderIp.trim();
	}

	public Integer getPayType()
	{
		return payType;
	}

	public void setPayType(Integer payType)
	{
		this.payType = payType;
	}

	public Integer getPayStatus()
	{
		return payStatus;
	}

	public void setPayStatus(Integer payStatus)
	{
		this.payStatus = payStatus;
	}

	public Date getPayTime()
	{
		return payTime;
	}

	public void setPayTime(Date payTime)
	{
		this.payTime = payTime;
	}

	public String getPlatformOrderId()
	{
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId)
	{
		this.platformOrderId = platformOrderId == null ? null : platformOrderId.trim();
	}

	public BigDecimal getTotalMoney()
	{
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney)
	{
		this.totalMoney = totalMoney;
	}

	public BigDecimal getTotalPayMoney()
	{
		return totalPayMoney;
	}

	public void setTotalPayMoney(BigDecimal totalPayMoney)
	{
		this.totalPayMoney = totalPayMoney;
	}

	public Date getNotifyTime()
	{
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime)
	{
		this.notifyTime = notifyTime;
	}

	@Override
	public String toString()
	{
		return "LogicOrderVo [logicOrderId=" + logicOrderId + ", accountId=" + accountId + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", orderIp=" + orderIp + ", payType=" + payType + ", payStatus="
				+ payStatus + ", payTime=" + payTime + ", notifyTime=" + notifyTime + ", platformOrderId="
				+ platformOrderId + ", totalMoney=" + totalMoney + ", totalPayMoney=" + totalPayMoney + "]";
	}
}