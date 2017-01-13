package com.youxue.pc.shopCart.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShopCartCampsDetail implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String campsId;

	private String campsName;

	private String campsTitle;

	private String campsImages;

	private String realCampsImages;

	private BigDecimal totalPrice;

	private Integer cartBuyCount;

	public String getCampsId()
	{
		return campsId;
	}

	public void setCampsId(String campsId)
	{
		this.campsId = campsId;
	}

	public String getCampsName()
	{
		return campsName;
	}

	public void setCampsName(String campsName)
	{
		this.campsName = campsName;
	}

	public String getCampsTitle()
	{
		return campsTitle;
	}

	public void setCampsTitle(String campsTitle)
	{
		this.campsTitle = campsTitle;
	}

	public String getCampsImages()
	{
		return campsImages;
	}

	public void setCampsImages(String campsImages)
	{
		this.campsImages = campsImages;
	}

	public String getRealCampsImages()
	{
		return realCampsImages;
	}

	public void setRealCampsImages(String realCampsImages)
	{
		this.realCampsImages = realCampsImages;
	}

	public BigDecimal getTotalPrice()
	{
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public Integer getCartBuyCount()
	{
		return cartBuyCount;
	}

	public void setCartBuyCount(Integer cartBuyCount)
	{
		this.cartBuyCount = cartBuyCount;
	}

	@Override
	public String toString()
	{
		return "ShopCartCampsDetail [campsId=" + campsId + ", campsName=" + campsName + ", campsTitle=" + campsTitle
				+ ", campsImages=" + campsImages + ", realCampsImages=" + realCampsImages + ", totalPrice="
				+ totalPrice + ", cartBuyCount=" + cartBuyCount + "]";
	}

}
