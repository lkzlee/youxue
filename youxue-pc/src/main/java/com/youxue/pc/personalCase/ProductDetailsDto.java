package com.youxue.pc.personalCase;

import java.util.LinkedList;
import java.util.List;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.vo.SurroundProductVo;

public class ProductDetailsDto extends BaseResponseDto
{

	private static final long serialVersionUID = 1L;
	List<SurroundProductVo> productList = new LinkedList<>();

	public List<SurroundProductVo> getProductList()
	{
		return productList;
	}

	public void setProductList(List<SurroundProductVo> productList)
	{
		this.productList = productList;
	}

}
