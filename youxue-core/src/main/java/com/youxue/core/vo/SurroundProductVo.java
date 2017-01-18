package com.youxue.core.vo;

import java.math.BigDecimal;

public class SurroundProductVo
{
	private String productId;

	private String productName;

	private Integer type;

	private String productDesc;

	private String productPhotos;

	private BigDecimal productPrice;

	private String weixinQrcodeUrl;

	private String alipayQrcodeUrl;

	public String getProductId()
	{
		return productId;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}

	public String getWeixinQrcodeUrl()
	{
		return weixinQrcodeUrl;
	}

	public void setWeixinQrcodeUrl(String weixinQrcodeUrl)
	{
		this.weixinQrcodeUrl = weixinQrcodeUrl;
	}

	public String getAlipayQrcodeUrl()
	{
		return alipayQrcodeUrl;
	}

	public void setAlipayQrcodeUrl(String alipayQrcodeUrl)
	{
		this.alipayQrcodeUrl = alipayQrcodeUrl;
	}

	public void setProductId(String productId)
	{
		this.productId = productId == null ? null : productId.trim();
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName == null ? null : productName.trim();
	}

	public String getProductDesc()
	{
		return productDesc;
	}

	public void setProductDesc(String productDesc)
	{
		this.productDesc = productDesc == null ? null : productDesc.trim();
	}

	public String getProductPhotos()
	{
		return productPhotos;
	}

	public void setProductPhotos(String productPhotos)
	{
		this.productPhotos = productPhotos == null ? null : productPhotos.trim();
	}

	public BigDecimal getProductPrice()
	{
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice)
	{
		this.productPrice = productPrice;
	}

	@Override
	public String toString()
	{
		return "SurroundProductVo [productId=" + productId + ", productName=" + productName + ", type=" + type
				+ ", productDesc=" + productDesc + ", productPhotos=" + productPhotos + ", productPrice="
				+ productPrice + ", weixinQrcodeUrl=" + weixinQrcodeUrl + ", alipayQrcodeUrl=" + alipayQrcodeUrl + "]";
	}
}