package com.youxue.core.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderVo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/***
	 * 未支付
	 */
	public static final int UNPAY = 0;
	/***
	 * 支付成功，待审核
	 */
	public static final int PAY = 1;
	/***
	 * 审核成功，待出行
	 */
	public static final int TO_OUT = 2;
	/***
	 * 取消状态
	 */
	public static final int CANCEL = 3;

	private String orderId;

	private String accountId;

	private String logicOrderId;

	private String codeId;

	private Integer status;

	private Date creatTime;

	private Date updateTime;

	private String campsId;

	private BigDecimal totalPrice;

	private BigDecimal codePrice;

	private Integer codeStatus;

	private BigDecimal payPrice;

	private Integer totalCount;

	private String contactName;

	private String contactEmail;

	private String contactPhone;

	private String orderIp;

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getAccountId()
	{
		return accountId;
	}

	public void setAccountId(String accountId)
	{
		this.accountId = accountId == null ? null : accountId.trim();
	}

	public String getLogicOrderId()
	{
		return logicOrderId;
	}

	public void setLogicOrderId(String logicOrderId)
	{
		this.logicOrderId = logicOrderId == null ? null : logicOrderId.trim();
	}

	public String getCodeId()
	{
		return codeId;
	}

	public void setCodeId(String codeId)
	{
		this.codeId = codeId == null ? null : codeId.trim();
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Date getCreatTime()
	{
		return creatTime;
	}

	public void setCreatTime(Date creatTime)
	{
		this.creatTime = creatTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public String getCampsId()
	{
		return campsId;
	}

	public void setCampsId(String campsId)
	{
		this.campsId = campsId == null ? null : campsId.trim();
	}

	public BigDecimal getTotalPrice()
	{
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public BigDecimal getCodePrice()
	{
		return codePrice;
	}

	public void setCodePrice(BigDecimal codePrice)
	{
		this.codePrice = codePrice;
	}

	public Integer getCodeStatus()
	{
		return codeStatus;
	}

	public void setCodeStatus(Integer codeStatus)
	{
		this.codeStatus = codeStatus;
	}

	public BigDecimal getPayPrice()
	{
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice)
	{
		this.payPrice = payPrice;
	}

	public Integer getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Integer totalCount)
	{
		this.totalCount = totalCount;
	}

	public String getContactName()
	{
		return contactName;
	}

	public void setContactName(String contactName)
	{
		this.contactName = contactName == null ? null : contactName.trim();
	}

	public String getContactEmail()
	{
		return contactEmail;
	}

	public void setContactEmail(String contactEmail)
	{
		this.contactEmail = contactEmail == null ? null : contactEmail.trim();
	}

	public String getContactPhone()
	{
		return contactPhone;
	}

	public void setContactPhone(String contactPhone)
	{
		this.contactPhone = contactPhone == null ? null : contactPhone.trim();
	}

	public String getOrderIp()
	{
		return orderIp;
	}

	public void setOrderIp(String orderIp)
	{
		this.orderIp = orderIp == null ? null : orderIp.trim();
	}

	@Override
	public String toString()
	{
		return "OrderVo [orderId=" + orderId + ", accountId=" + accountId + ", logicOrderId=" + logicOrderId
				+ ", codeId=" + codeId + ", status=" + status + ", creatTime=" + creatTime + ", updateTime="
				+ updateTime + ", campsId=" + campsId + ", totalPrice=" + totalPrice + ", codePrice=" + codePrice
				+ ", codeStatus=" + codeStatus + ", payPrice=" + payPrice + ", totalCount=" + totalCount
				+ ", contactName=" + contactName + ", contactEmail=" + contactEmail + ", contactPhone=" + contactPhone
				+ ", orderIp=" + orderIp + "]";
	}

}