package com.youxue.core.vo;

import java.io.Serializable;

public class OrderPersonVo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderId;

	private String personName;

	private String personPhone;

	private String personIdno;

	private String personAddress;

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getPersonName()
	{
		return personName;
	}

	public void setPersonName(String personName)
	{
		this.personName = personName == null ? null : personName.trim();
	}

	public String getPersonPhone()
	{
		return personPhone;
	}

	public void setPersonPhone(String personPhone)
	{
		this.personPhone = personPhone == null ? null : personPhone.trim();
	}

	public String getPersonIdno()
	{
		return personIdno;
	}

	public void setPersonIdno(String personIdno)
	{
		this.personIdno = personIdno == null ? null : personIdno.trim();
	}

	public String getPersonAddress()
	{
		return personAddress;
	}

	public void setPersonAddress(String personAddress)
	{
		this.personAddress = personAddress == null ? null : personAddress.trim();
	}

	@Override
	public String toString()
	{
		return "OrderPersonVo [orderId=" + orderId + ", personName=" + personName + ", personPhone=" + personPhone
				+ ", personIdno=" + personIdno + ", personAddress=" + personAddress + "]";
	}
}