package com.youxue.core.service.order.dto;

import com.youxue.core.common.BaseResponseDto;

/***
 * 下单返回参数
 * @author lkzlee
 *
 */
public class AddTradeResonseDto extends BaseResponseDto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String payUrl;

	private WxJsPayParamDto wxPayParam;

	public String getPayUrl()
	{
		return payUrl;
	}

	public void setPayUrl(String payUrl)
	{
		this.payUrl = payUrl;
	}

	public WxJsPayParamDto getWxPayParam()
	{
		return wxPayParam;
	}

	public void setWxPayParam(WxJsPayParamDto wxPayParam)
	{
		this.wxPayParam = wxPayParam;
	}

	@Override
	public String toString()
	{
		return "AddTradeResonseDto [payUrl=" + payUrl + ", wxPayParam=" + wxPayParam + "],super=" + super.toString();
	}

}
