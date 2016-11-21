package com.youxue.pc.order.dto;

import java.io.Serializable;
import java.util.List;

import com.youxue.core.vo.OrderPersonVo;
import com.youxue.core.vo.OrderVo;

/***
 * 下单相关参数
 * @author lkzlee
 *
 */
public class AddTradeItemDto extends OrderVo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<OrderPersonVo> outPersonList;
}
