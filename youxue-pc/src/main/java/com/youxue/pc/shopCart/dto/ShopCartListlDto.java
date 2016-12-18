package com.youxue.pc.shopCart.dto;

import java.io.Serializable;
import java.util.List;

import com.youxue.core.common.BaseResponseDto;

public class ShopCartListlDto extends BaseResponseDto implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<ShopCartCampsDetail> shopCartList;

	public List<ShopCartCampsDetail> getShopCartList()
	{
		return shopCartList;
	}

	public void setShopCartList(List<ShopCartCampsDetail> shopCartList)
	{
		this.shopCartList = shopCartList;
	}

	@Override
	public String toString()
	{
		return "ShopCartDetailDto [shopCartList=" + shopCartList + "]";
	}
}
