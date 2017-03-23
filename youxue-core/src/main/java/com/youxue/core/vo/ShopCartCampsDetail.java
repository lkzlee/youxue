package com.youxue.core.vo;

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

	private BigDecimal totalPrice;

	private Integer cartBuyCount;
	private String detailId;
	private String detailName;

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
				+ ", campsImages=" + campsImages + ", totalPrice=" + totalPrice + ", cartBuyCount=" + cartBuyCount
				+ "]";
	}

	public String getDetailId()
	{
		return detailId;
	}

	public void setDetailId(String detailId)
	{
		this.detailId = detailId;
	}

	public String getDetailName()
	{
		return detailName;
	}

	public void setDetailName(String detailName)
	{
		this.detailName = detailName;
	}

}
