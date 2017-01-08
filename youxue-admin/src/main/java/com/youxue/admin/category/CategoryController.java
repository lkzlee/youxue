package com.youxue.admin.category;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dto.CategoryListDto;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CategoryVo;

@Controller
public class CategoryController
{
	private static final Log logger = LogFactory.getLog(CategoryController.class);

	@Autowired
	private CatetoryDao categoryDao;
	@Autowired
	private CommonDao commonDao;

	@RequestMapping("/campsCategoryListIndex.do")
	public String campsCategoryListIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Integer categoryType)
	{
		try
		{
			List<CategoryVo> categoryList = new LinkedList<>();
			if (categoryType == null || CategoryTypeEnum.getByValue(categoryType) == null)
			{
				categoryList = categoryDao.selectAll();
				categoryType = 0;
			}
			else
			{
				categoryList = categoryDao.selectByCategoryType(categoryType);
			}
			modelMap.put("categoryTypeMap", CategoryTypeEnum.getCateTypeMap());
			modelMap.put("categoryList", categoryList);
			modelMap.put("categoryType", categoryType);
			return "camps/category/categoryList";
		}
		catch (Exception e)
		{
			logger.info("error in campsCategoryListIndex", e);
			return "camps/category/categoryList";
		}
	}

	/**
	 * @param request
	 * @param response
	 * 获取类别列表:如查询国家列表、要做什么列表
	 */
	@RequestMapping("/getCategroyListByType.do")
	@ResponseBody
	public String getCategroyList(HttpServletRequest request, HttpServletResponse response, Integer categoryType)
	{
		if (categoryType == null || CategoryTypeEnum.getByValue(categoryType) == null)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数不合法"));
		logger.info("getCategroyList,categoryType:" + categoryType);
		CategoryListDto dto = new CategoryListDto();
		dto.setResult(100);
		List<CategoryVo> categoryList = categoryDao.selectByCategoryType(categoryType);
		dto.setCategoryList(categoryList);
		return JsonUtil.serialize(dto);
	}

	@RequestMapping(value = "addCategoryIndex.do")
	public String addCategoryIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
	{
		modelMap.put("categoryTypeMap", CategoryTypeEnum.getCateTypeMap());
		return "camps/category/addCategory";
	}

	@RequestMapping(value = "doAddCategory.do")
	public String doAddCategory(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			CategoryVo category)
	{
		try
		{
			if (category == null || StringUtils.isBlank(category.getCategoryName()))
			{
				modelMap.put("msg", "添加分类异常:参数缺失");
				return "redirect:/campsCategoryListIndex.do";
			}
			category.setCategoryId(commonDao.getIdByPrefix(CommonConstant.CATEGORY_ID_PREFIX));
			categoryDao.insertSelective(category);
			return "redirect:/campsCategoryListIndex.do";
		}
		catch (Exception e)
		{
			logger.error("doAddCategory()--error", e);
			modelMap.put("msg", "添加分类异常:后台异常");
			return "redirect:/campsCategoryListIndex.do";
		}
	}

	@RequestMapping(value = "modifyCategoryIndex.do")
	public String modifyCampsIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String categoryId)
	{
		try
		{
			if (StringUtils.isBlank(categoryId))
			{
				return "camps/category/categoryList";
			}
			CategoryVo category = categoryDao.selectByPrimaryKey(categoryId);
			if (category == null)
			{
				return "camps/category/categoryList";
			}
			modelMap.put("category", category);
			modelMap.put("categoryType", CategoryTypeEnum.getCateTypeMap());
		}
		catch (Exception e)
		{
			logger.error("modifyCampsIndex()--error", e);
			return "camps/campsList";
		}
		return "camps/addCamps";
	}

	@RequestMapping(value = "doModifyCategory.do")
	@ResponseBody
	public String doModifyCamps(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			CategoryVo category)
	{
		try
		{
			if (category == null || StringUtils.isBlank(category.getCategoryId()))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("修改分类异常"));
			}
			categoryDao.updateByPrimaryKeySelective(category);
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("修改分类成功"));
		}
		catch (Exception e)
		{
			logger.error("doModifyCamps()--error", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("修改分类异常"));
		}
	}

	@RequestMapping(value = "doDeleteCategory.do")
	@ResponseBody
	public String doDeleteCategory(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String categoryId)
	{
		try
		{
			if (StringUtils.isBlank(categoryId))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("删除分类异常，参数错误"));
			}
			categoryDao.deleteByPrimaryKey(categoryId);
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("删除分类成功"));
		}
		catch (Exception e)
		{
			logger.error("doDeleteCategory()--error", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("删除分类异常"));
		}
	}
}
