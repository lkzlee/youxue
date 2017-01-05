package com.youxue.core.dao;

import java.util.List;

import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CategoryVo;

public interface CatetoryDao
{
	int deleteByPrimaryKey(String categoryId);

	int insert(CategoryVo record);

	int insertSelective(CategoryVo record);

	CategoryVo selectByPrimaryKey(String categoryId);

	int updateByPrimaryKeySelective(CategoryVo record);

	int updateByPrimaryKey(CategoryVo record);

	List<CampsVo> getCampusListByType(CategoryTypeEnum type, int pageNo, int pageSize);

	List<CategoryVo> selectByCategoryType(Integer categoryType);

	List<CategoryVo> selectAll();
}