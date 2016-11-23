package com.youxue.core.vo;

public class CategoryVo
{
	private String categoryId;

	private String categoryName;

	private String categoryUrl;

	private Integer categoryType;
	private Integer categoryWeight;

	public String getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(String categoryId)
	{
		this.categoryId = categoryId == null ? null : categoryId.trim();
	}

	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName == null ? null : categoryName.trim();
	}

	public String getCategoryUrl()
	{
		return categoryUrl;
	}

	public void setCategoryUrl(String categoryUrl)
	{
		this.categoryUrl = categoryUrl == null ? null : categoryUrl.trim();
	}

	public Integer getCategoryType()
	{
		return categoryType;
	}

	public void setCategoryType(Integer categoryType)
	{
		this.categoryType = categoryType;
	}

	public Integer getCategoryWeight()
	{
		return categoryWeight;
	}

	public void setCategoryWeight(Integer categoryWeight)
	{
		this.categoryWeight = categoryWeight;
	}
}