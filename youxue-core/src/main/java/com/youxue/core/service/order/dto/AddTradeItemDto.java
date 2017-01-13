package com.youxue.core.service.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

/***
 * 下单相关参数
 * @author lkzlee
 *
 */
public class AddTradeItemDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codeId;

	private String campsId;

	private String totalCount;

	private String contactName;

	private String contactEmail;

	private String contactPhone;

	private BigDecimal totalPrice;

	private BigDecimal codePrice;

	private BigDecimal payPrice;

	private AddOrderPersonDto[] outPersonList;

	public AddOrderPersonDto[] getOutPersonList()
	{
		return outPersonList;
	}

	public void setOutPersonList(AddOrderPersonDto[] outPersonList)
	{
		this.outPersonList = outPersonList;
	}

	public String getCodeId()
	{
		return codeId;
	}

	public void setCodeId(String codeId)
	{
		this.codeId = codeId;
	}

	public String getCampsId()
	{
		return campsId;
	}

	public void setCampsId(String campsId)
	{
		this.campsId = campsId;
	}

	public String getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(String totalCount)
	{
		this.totalCount = totalCount;
	}

	public String getContactName()
	{
		return contactName;
	}

	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}

	public String getContactEmail()
	{
		return contactEmail;
	}

	public void setContactEmail(String contactEmail)
	{
		this.contactEmail = contactEmail;
	}

	public String getContactPhone()
	{
		return contactPhone;
	}

	public void setContactPhone(String contactPhone)
	{
		this.contactPhone = contactPhone;
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

	public BigDecimal getPayPrice()
	{
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice)
	{
		this.payPrice = payPrice;
	}

	@Override
	public String toString()
	{
		return "AddTradeItemDto [codeId=" + codeId + ", campsId=" + campsId + ", totalCount=" + totalCount
				+ ", contactName=" + contactName + ", contactEmail=" + contactEmail + ", contactPhone=" + contactPhone
				+ ", totalPrice=" + totalPrice + ", codePrice=" + codePrice + ", payPrice=" + payPrice
				+ ", outPersonList=" + Arrays.toString(outPersonList) + "]";
	}

}
