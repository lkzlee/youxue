package com.youxue.pc.uc.dto;

import java.io.Serializable;
import java.util.List;

import com.youxue.core.vo.OrderDetailVo;
import com.youxue.core.vo.OrderPersonVo;

public class OrderDetailInfoDto extends OrderDetailVo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int result;
	protected String resultDesc;
	private List<OrderPersonVo> orderPersonList;

	public List<OrderPersonVo> getOrderPersonList()
	{
		return orderPersonList;
	}

	public void setOrderPersonList(List<OrderPersonVo> orderPersonList)
	{
		this.orderPersonList = orderPersonList;
	}

	public int getResult()
	{
		return result;
	}

	public void setResult(int result)
	{
		this.result = result;
	}

	public String getResultDesc()
	{
		return resultDesc;
	}

	public void setResultDesc(String resultDesc)
	{
		this.resultDesc = resultDesc;
	}

	@Override
	public String toString()
	{
		return "OrderDetailInfoDto [result=" + result + ", resultDesc=" + resultDesc + ", orderPersonList="
				+ orderPersonList + "]";
	}

}
