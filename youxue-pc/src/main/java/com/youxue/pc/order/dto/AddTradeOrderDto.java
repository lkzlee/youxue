package com.youxue.pc.order.dto;

import java.io.Serializable;
import java.util.List;

import com.youxue.core.vo.OrderVo;

/***
 * 下单相关参数
 * @author lkzlee
 *
 */
public class AddTradeOrderDto extends OrderVo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer payType = 0;
	private List<AddTradeItemDto> orderList;
}
