package com.youxue.pc.coupon.dto;

import java.math.BigDecimal;

import com.youxue.core.common.BaseResponseDto;

public class CalcPayAmountDto extends BaseResponseDto
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal codeAmount;
	private BigDecimal payAmount;

	public BigDecimal getCodeAmount()
	{
		return codeAmount;
	}

	public void setCodeAmount(BigDecimal codeAmount)
	{
		this.codeAmount = codeAmount;
	}

	public BigDecimal getPayAmount()
	{
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount)
	{
		this.payAmount = payAmount;
	}

	@Override
	public String toString()
	{
		return "CalcPayAmountDto [codeAmount=" + codeAmount + ", payAmount=" + payAmount + "]";
	}

}
