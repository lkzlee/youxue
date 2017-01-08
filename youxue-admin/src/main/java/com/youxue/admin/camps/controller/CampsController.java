package com.youxue.admin.camps.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CategoryVo;
import com.youxue.core.vo.Page;

@Controller
public class CampsController
{
	private static final Log logger = LogFactory.getLog(CampsController.class);
	public static final int pageSize = 10;
	@Autowired
	private CampsDao campsDao;
	@Autowired
	private CatetoryDao categoryDao;
	@Autowired
	private CommonDao commonDao;
	static Map<String, String> campsStatusMap = new HashMap<>();
	static
	{
		campsStatusMap.put("0", "下架");
		campsStatusMap.put("1", "上架");
	}

	/**
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "campsList.do")
	public String campsListIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String campsNameKey, String categoryId, Integer status, String pageNo)
	{
		try
		{
			Map<String, Object> conditions = new HashMap<>();
			if (StringUtils.isNotBlank(campsNameKey))
			{
				conditions.put("campsNameKey", campsNameKey);
			}
			if (StringUtils.isNotBlank(categoryId))
			{
				CategoryVo category = categoryDao.selectByPrimaryKey(categoryId);
				if (category != null)
				{
					if (category.getCategoryType() == CategoryTypeEnum.LOCALE.getValue())
					{
						conditions.put("localeCategoryId", categoryId);
					}
					if (category.getCategoryType() == CategoryTypeEnum.SUBJECT.getValue())
					{
						conditions.put("subjectCategoryId", categoryId);
					}
					if (category.getCategoryType() == CategoryTypeEnum.DURATION.getValue())
					{
						conditions.put("durationCategoryId", categoryId);
					}
					if (category.getCategoryType() == CategoryTypeEnum.DEPARTURETIME.getValue())
					{
						conditions.put("departureCategoryId", categoryId);
					}
					if (category.getCategoryType() == CategoryTypeEnum.PRICE.getValue())
					{
						conditions.put("priceCategoryId", categoryId);
					}
				}
			}
			if (status != null && status >= 0)
			{
				conditions.put("status", status);
			}
			int page = 1;
			if (StringUtils.isNotBlank(pageNo))
			{
				page = Integer.valueOf(pageNo);
			}
			Page<CampsVo> campsList = campsDao.selectByConditions(conditions, page, pageSize);
			modelMap.put("campsList", campsList);
			List<CategoryVo> categoryList = categoryDao.selectAll();
			modelMap.put("categoryList", categoryList);
			modelMap.put("categoryTypeMap", CategoryTypeEnum.getCateTypeMap());
			modelMap.put("campsStatusMap", campsStatusMap);
			modelMap.put("campsNameKey", StringUtils.isBlank(campsNameKey) ? "" : campsNameKey);
			modelMap.put("categoryId", StringUtils.isBlank(categoryId) ? "" : categoryId);
			modelMap.put("status", status);
		}
		catch (Exception e)
		{
			logger.error("campsListIndex()--error", e);
			return "login";
		}
		return "camps/campsList";
	}

	/**
	 * @param request
	 * @param response
	 * @param campsNameKey
	 * @param categoryType
	 * @param categoryId
	 * @param status
	 * @param pageNo
	 * @return ajax方式获取页面营地列表
	 */
	@RequestMapping(value = "doGetCampsList.do")
	@ResponseBody
	public String doGetCampsList(HttpServletRequest request, HttpServletResponse response, String campsNameKey,
			Integer categoryType, String categoryId, Integer status, String pageNo)
	{
		try
		{
			Map<String, Object> conditions = new HashMap<>();
			if (StringUtils.isNotBlank(campsNameKey))
			{
				conditions.put("campsNameKey", campsNameKey);
			}
			if (categoryType != null || StringUtils.isNotBlank(categoryId))
			{
				if (categoryType == CategoryTypeEnum.LOCALE.getValue())
				{
					conditions.put("localeCategoryId", categoryId);
				}
				if (categoryType == CategoryTypeEnum.SUBJECT.getValue())
				{
					conditions.put("subjectCategoryId", categoryId);
				}
				if (categoryType == CategoryTypeEnum.DURATION.getValue())
				{
					conditions.put("durationCategoryId", categoryId);
				}
				if (categoryType == CategoryTypeEnum.DEPARTURETIME.getValue())
				{
					conditions.put("departureCategoryId", categoryId);
				}
				if (categoryType == CategoryTypeEnum.PRICE.getValue())
				{
					conditions.put("priceCategoryId", categoryId);
				}
			}
			if (status != null && status >= 0)
			{
				conditions.put("status", status);
			}
			int page = 1;
			if (StringUtils.isNotBlank(pageNo))
			{
				page = Integer.valueOf(pageNo);
			}
			Page<CampsVo> campsList = campsDao.selectByConditions(conditions, page, pageSize);
			return JsonUtil.serialize(campsList);
		}
		catch (Exception e)
		{
			logger.error("campsListIndex()--error", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("获取营地列表异常"));
		}
	}

	@RequestMapping(value = "addCampsIndex.do")
	public String addCampsIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
	{
		try
		{
			Map<Integer, List<CategoryVo>> categoryMap = new HashMap<>();
			List<CategoryVo> categoryList = categoryDao.selectAll();
			for (CategoryVo category : categoryList)
			{
				if (categoryMap.containsKey(category.getCategoryType()))
				{
					categoryMap.get(category.getCategoryType()).add(category);
				}
				else
				{
					List<CategoryVo> newCategoryList = new LinkedList<>();
					newCategoryList.add(category);
					categoryMap.put(category.getCategoryType(), newCategoryList);
				}
			}
			modelMap.put("categoryMap", categoryMap);
			modelMap.put("categoryTypeMap", CategoryTypeEnum.getCateTypeMap());
			modelMap.put("campsStatusMap", campsStatusMap);
		}
		catch (Exception e)
		{
			logger.error("addCampsIndex()--error", e);
			return "redirect:/campsList.do";
		}
		return "camps/addCamps";
	}

	@RequestMapping(value = "doAddCamps.do")
	public String doAddCamps(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, CampsVo camps)
	{
		try
		{
			if (camps == null || StringUtils.isBlank(camps.getCampsName())
					|| StringUtils.isBlank(camps.getCampsTitle()))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("添加营地异常"));
			}
			camps.setCampsId(commonDao.getIdByPrefix(CommonConstant.CAMPS_ID_PREFIX));
			camps.setCreateTime(new Date());
			campsDao.insertSelective(camps);
		}
		catch (Exception e)
		{
			logger.error("doAddCampsIndex()--error", e);
		}
		return "redirect:/campsList.do";
	}

	@RequestMapping(value = "modifyCampsIndex.do")
	public String modifyCampsIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String campsId)
	{
		try
		{
			if (StringUtils.isBlank(campsId))
			{
				return "camps/campsList";
			}
			CampsVo camps = campsDao.selectByPrimaryKey(campsId);
			if (camps == null)
			{
				return "camps/campsList";
			}
			modelMap.put("camps", camps);

			Map<Integer, List<CategoryVo>> categoryMap = new HashMap<>();
			List<CategoryVo> categoryList = categoryDao.selectAll();
			for (CategoryVo category : categoryList)
			{
				if (categoryMap.containsKey(category.getCategoryType()))
				{
					categoryMap.get(category.getCategoryType()).add(category);
				}
				else
				{
					List<CategoryVo> newCategoryList = new LinkedList<>();
					newCategoryList.add(category);
					categoryMap.put(category.getCategoryType(), newCategoryList);
				}
			}
			modelMap.put("categoryMap", categoryMap);
		}
		catch (Exception e)
		{
			logger.error("modifyCampsIndex()--error", e);
			return "camps/campsList";
		}
		return "camps/addCamps";
	}

	@RequestMapping(value = "doModifyCamps.do")
	@ResponseBody
	public String doModifyCamps(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			CampsVo camps)
	{
		try
		{
			if (camps == null || StringUtils.isBlank(camps.getCampsName())
					|| StringUtils.isBlank(camps.getCampsTitle()) || StringUtils.isBlank(camps.getCampsId()))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("添加营地异常"));
			}
			campsDao.updateByPrimaryKeySelective(camps);
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("添加营地成功"));
		}
		catch (Exception e)
		{
			logger.error("doModifyCampsIndex()--error", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("修改营地异常"));
		}
	}
}
