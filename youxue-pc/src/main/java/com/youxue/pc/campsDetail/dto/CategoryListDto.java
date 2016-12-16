package com.youxue.pc.campsDetail.dto;

import java.util.List;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.vo.CategoryVo;

public class CategoryListDto extends BaseResponseDto
{

	private static final long serialVersionUID = 1L;
	private List<CategoryVo> categoryList;
	public List<CategoryVo> getCategoryList()
	{
		return categoryList;
	}
	public void setCategoryList(List<CategoryVo> categoryList)
	{
		this.categoryList = categoryList;
	}

}
