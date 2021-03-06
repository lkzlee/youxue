package com.youxue.admin.camps.controller;

import java.math.BigDecimal;
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

import com.youxue.admin.constant.AdminBaseController;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CampsDetailDao;
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.util.DateUtil;
import com.youxue.core.vo.CampsDetailVo;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CategoryVo;
import com.youxue.core.vo.Page;

@Controller
public class CampsController extends AdminBaseController
{
	private static final Log logger = LogFactory.getLog(CampsController.class);
	public static final int pageSize = 10;
	@Autowired
	private CampsDao campsDao;
	@Autowired
	private CampsDetailDao campsDetailDao;
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

	@RequestMapping(value = "campsList.do")
	public String campsList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String campsNameKey, Integer status, String localeCategoryIds, String subjectCategoryIds,
			String durationCategoryIds, String priceCategoryIds, String depatureCategoryIds, String pageNo)
	{
		try
		{
			logger.info("campsList request, campsNameKey:" + campsNameKey + ",status:" + status + ",localeCategoryIds:"
					+ localeCategoryIds + ",subjectCategoryIds:" + subjectCategoryIds + ",durationCategoryIds:"
					+ durationCategoryIds + ",priceCategoryIds:" + priceCategoryIds + ",depatureCategoryIds:"
					+ depatureCategoryIds + ",pageNO:" + pageNo);
			Map<String, Object> conditions = new HashMap<>();
			if (StringUtils.isNotBlank(campsNameKey))
			{
				conditions.put("campsNameKey", campsNameKey);
			}
			if (StringUtils.isNotBlank(localeCategoryIds))
			{
				conditions.put("localeCategoryIds", localeCategoryIds.split(","));
			}
			if (StringUtils.isNotBlank(subjectCategoryIds))
			{
				conditions.put("subjectCategoryIds", subjectCategoryIds.split(","));
			}
			if (StringUtils.isNotBlank(durationCategoryIds))
			{
				conditions.put("durationCategoryIds", durationCategoryIds.split(","));
			}
			if (StringUtils.isNotBlank(depatureCategoryIds))
			{
				conditions.put("depatureCategoryIds", depatureCategoryIds.split(","));
			}
			if (StringUtils.isNotBlank(priceCategoryIds))
			{
				conditions.put("priceCategoryIds", priceCategoryIds.split(","));
			}
			if (status != null && (status == 0 || status == 1))
			{
				conditions.put("status", status);
			}
			Page<CampsVo> campsList = campsDao.selectByConditions(conditions, Page.getPageNo(pageNo), pageSize);
			logger.info("campsList size:" + campsList.getResultList().size());
			List<CategoryVo> categoryList = categoryDao.selectAll();
			List<CategoryVo> localeCategoryList = new LinkedList<>();
			List<CategoryVo> subjectCategoryList = new LinkedList<>();
			List<CategoryVo> durationCategoryList = new LinkedList<>();
			List<CategoryVo> depatureCategoryList = new LinkedList<>();
			List<CategoryVo> priceCategoryList = new LinkedList<>();
			Map<String, CategoryVo> categoryMap = new HashMap<>();//cateid与category对应关系
			for (CategoryVo category : categoryList)
			{
				categoryMap.put(category.getCategoryId(), category);
				switch (CategoryTypeEnum.getByValue(category.getCategoryType()))
				{
					case LOCALE:
						localeCategoryList.add(category);
						break;
					case SUBJECT:
						subjectCategoryList.add(category);
						break;
					case DURATION:
						durationCategoryList.add(category);
						break;
					case DEPARTURETIME:
						depatureCategoryList.add(category);
						break;
					case PRICE:
						priceCategoryList.add(category);
				}
			}
			for (CampsVo camps : campsList.getResultList())
			{
				String categoryStrs = "";
				if (StringUtils.isNotBlank(camps.getCampsLocaleId()))
				{
					if (categoryMap.containsKey(camps.getCampsLocaleId()))
					{
						categoryStrs = categoryMap.get(camps.getCampsLocaleId()).getCategoryName();
					}
				}
				if (StringUtils.isNotBlank(camps.getCampsSubjectId()))
				{
					if (categoryMap.containsKey(camps.getCampsSubjectId()))
					{
						categoryStrs = categoryStrs + " "
								+ categoryMap.get(camps.getCampsSubjectId()).getCategoryName();
					}
				}
				camps.setCategoryStrs(categoryStrs);
			}
			modelMap.put("campsList", campsList);
			modelMap.put("localeCategoryList", localeCategoryList);
			modelMap.put("subjectCategoryList", subjectCategoryList);
			modelMap.put("durationCategoryList", durationCategoryList);
			modelMap.put("depatureCategoryList", depatureCategoryList);
			modelMap.put("priceCategoryList", priceCategoryList);
			modelMap.put("categoryTypeMap", CategoryTypeEnum.getCateTypeMap());
			modelMap.put("campsStatusMap", campsStatusMap);
			modelMap.put("campsNameKey", StringUtils.isBlank(campsNameKey) ? "" : campsNameKey);
			modelMap.put("status", status == null ? "" : String.valueOf(status));
			modelMap.put("localeCategoryIds", StringUtils.isBlank(localeCategoryIds) ? "" : localeCategoryIds);
			modelMap.put("subjectCategoryIds", StringUtils.isBlank(subjectCategoryIds) ? "" : subjectCategoryIds);
			modelMap.put("durationCategoryIds", StringUtils.isBlank(durationCategoryIds) ? "" : durationCategoryIds);
			modelMap.put("depatureCategoryIds", StringUtils.isBlank(depatureCategoryIds) ? "" : depatureCategoryIds);
			modelMap.put("priceCategoryIds", StringUtils.isBlank(priceCategoryIds) ? "" : priceCategoryIds);

		}
		catch (Exception e)
		{
			logger.error("campsListIndex()--error", e);
		}
		return "camps/campsList";
	}

	@RequestMapping(value = "addCampsIndex.do")
	public String addCampsIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
	{
		try
		{
			fillCategoryListMap(modelMap);
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

	private void fillCategoryListMap(ModelMap modelMap)
	{
		List<CategoryVo> categoryList = categoryDao.selectAll();
		List<CategoryVo> localeCategoryList = new LinkedList<>();
		List<CategoryVo> subjectCategoryList = new LinkedList<>();
		List<CategoryVo> durationCategoryList = new LinkedList<>();
		List<CategoryVo> depatureCategoryList = new LinkedList<>();
		List<CategoryVo> priceCategoryList = new LinkedList<>();
		for (CategoryVo category : categoryList)
		{
			switch (CategoryTypeEnum.getByValue(category.getCategoryType()))
			{
				case LOCALE:
					localeCategoryList.add(category);
					break;
				case SUBJECT:
					subjectCategoryList.add(category);
					break;
				case DURATION:
					durationCategoryList.add(category);
					break;
				case DEPARTURETIME:
					depatureCategoryList.add(category);
					break;
				case PRICE:
					priceCategoryList.add(category);
			}
		}
		modelMap.put("localeCategoryList", localeCategoryList);
		modelMap.put("subjectCategoryList", subjectCategoryList);
		modelMap.put("durationCategoryList", durationCategoryList);
		modelMap.put("depatureCategoryList", depatureCategoryList);
		modelMap.put("priceCategoryList", priceCategoryList);
	}

	@RequestMapping(value = "doAddCamps.do")
	public String doAddCamps(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			CampsVo camps, String deadlineDateStr, String detailStartDates, String detailNames, String detailPrices,
			String durations)
	{
		try
		{
			if (camps == null || StringUtils.isBlank(camps.getCampsName())
					|| StringUtils.isBlank(camps.getCampsTitle()))
			{
				return "redirect:/addCampsIndex.do";
			}
			String[] startDates = detailStartDates.split(",");
			String[] names = detailNames.split(",");
			String[] prices = detailPrices.split(",");
			String[] durationDays = durations.split(",");
			Date maxCampsStartDate = null;
			List<CampsDetailVo> detailList = new LinkedList<CampsDetailVo>();
			for (int i = 0; i < startDates.length; i++)
			{
				if (StringUtils.isBlank(names[i]) || StringUtils.isBlank(startDates[i])
						|| StringUtils.isBlank(prices[i]) || StringUtils.isBlank(durationDays[i]))
				{
					LOG.error("empty input ,continue");
					continue;
				}
				CampsDetailVo detail = new CampsDetailVo();
				detail.setDetailId(commonDao.getIdByPrefix(CommonConstant.CAMPS_DETAIL_ID_PREFIX));
				detail.setDetailName(names[i]);
				detail.setDetailPrice(new BigDecimal(prices[i]));
				Date startDate = DateUtil.formatToDate(startDates[i], "yyyy-MM-dd");
				detail.setDetailStartTime(startDate);
				detail.setDuration(Integer.valueOf(durationDays[i]));
				detailList.add(detail);
				if (maxCampsStartDate == null || startDate.after(maxCampsStartDate))
				{
					//更新最晚开始时间
					maxCampsStartDate = startDate;
				}
			}
			if (maxCampsStartDate == null)
			{
				LOG.error("营地开始时间为空,请检查");
				modelMap.put("msg", "营地开始时间为空,请检查");
				return "redirect:/modifyCampsIndex.do?campsId=" + camps.getCampsId();
			}
			if (StringUtils.isNotBlank(deadlineDateStr))
			{
				camps.setDeadlineDate(DateUtil.formatToDate(deadlineDateStr, "yyyy-MM-dd"));
			}
			else
			{
				LOG.error("营地截止时间为空,请检查");
				modelMap.put("msg", "营地截止时间为空,请检查");
				return "redirect:/addCampsIndex.do";
			}
			if (camps.getDeadlineDate().after(maxCampsStartDate))
			{
				LOG.error("营地截止时间晚于开始时间,请检查");
				modelMap.put("msg", "营地截止时间晚于开始时间,请检查");
				return "redirect:/addCampsIndex.do";
			}
			camps.setCampsId(commonDao.getIdByPrefix(CommonConstant.CAMPS_ID_PREFIX));
			if (StringUtils.isNotBlank(camps.getCampsLocaleId()))
			{
				CategoryVo category = categoryDao.selectByPrimaryKey(camps.getCampsLocaleId());
				if (category != null)
				{
					camps.setCampsLocale(category.getCategoryName());
				}
			}
			camps.setCreateTime(new Date());
			campsDao.insertSelective(camps);

			for (CampsDetailVo detail : detailList)
			{
				detail.setCampsId(camps.getCampsId());
				campsDetailDao.insert(detail);
			}
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
				return "redirect:/campsList.do";
			}
			CampsVo camps = campsDao.selectByPrimaryKey(campsId);
			if (camps == null)
			{
				return "redirect:/campsList.do";
			}
			List<CampsDetailVo> campsDetailList = campsDetailDao.selectByCampsId(campsId);
			camps.setCampsDetailList(campsDetailList);
			modelMap.put("camps", camps);
			fillCategoryListMap(modelMap);
			modelMap.put("categoryTypeMap", CategoryTypeEnum.getCateTypeMap());
			modelMap.put("campsStatusMap", campsStatusMap);
		}
		catch (Exception e)
		{
			logger.error("modifyCampsIndex()--error", e);
			return "redirect:/campsList.do";
		}
		return "camps/modifyCamps";
	}

	@RequestMapping(value = "doModifyCamps.do")
	public String doModifyCamps(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			CampsVo camps, String detailIds, String detailStartDates, String detailNames, String detailPrices,
			String durations)
	{
		try
		{
			LOG.info("get doModifyCamps request,campsId:" + camps.getCampsId() + ",detailIds" + detailIds
					+ ",detailStartDates：" + detailStartDates);
			if (camps == null || StringUtils.isBlank(camps.getCampsName())
					|| StringUtils.isBlank(camps.getCampsTitle()) || StringUtils.isBlank(camps.getCampsId()))
			{
				return "redirect:/modifyCampsIndex.do?campsId=" + camps.getCampsId();
			}
			if (StringUtils.isNotBlank(camps.getCampsLocaleId()))
			{
				CategoryVo category = categoryDao.selectByPrimaryKey(camps.getCampsLocaleId());
				if (category != null)
				{
					camps.setCampsLocale(category.getCategoryName());
				}
			}
			String[] ids = detailIds.split(",");
			String[] startDates = detailStartDates.split(",");
			String[] names = detailNames.split(",");
			String[] prices = detailPrices.split(",");
			String[] durationDays = durations.split(",");
			Date maxCampsStartDate = null;
			for (int i = 0; i < startDates.length; i++)
			{
				if (StringUtils.isBlank(names[i]) || StringUtils.isBlank(startDates[i])
						|| StringUtils.isBlank(prices[i]) || StringUtils.isBlank(durationDays[i]))
				{
					LOG.error("empty input ,continue");
					continue;
				}
				Date startDate = DateUtil.formatToDate(startDates[i], "yyyy-MM-dd");
				if (i >= ids.length || StringUtils.isBlank(ids[i]))
				{
					CampsDetailVo detail = new CampsDetailVo();
					detail.setDetailId(commonDao.getIdByPrefix(CommonConstant.CAMPS_DETAIL_ID_PREFIX));
					detail.setDetailName(names[i]);
					detail.setDetailPrice(new BigDecimal(prices[i]));
					detail.setDetailStartTime(startDate);
					detail.setDuration(Integer.valueOf(durationDays[i]));
					detail.setCampsId(camps.getCampsId());
					campsDetailDao.insert(detail);
				}
				else
				{
					CampsDetailVo detail = new CampsDetailVo();
					detail.setDetailId(ids[i]);
					detail.setDetailName(names[i]);
					detail.setDetailPrice(new BigDecimal(prices[i]));
					detail.setDetailStartTime(startDate);
					detail.setDuration(Integer.valueOf(durationDays[i]));
					campsDetailDao.updateByPrimaryKeySelective(detail);
				}
				if (maxCampsStartDate == null || startDate.after(maxCampsStartDate))
				{
					//更新最晚时间
					maxCampsStartDate = startDate;
				}
			}
			if (maxCampsStartDate == null)
			{
				LOG.error("营地开始时间为空,请检查");
				modelMap.put("msg", "营地开始时间为空,请检查");
				return "redirect:/modifyCampsIndex.do?campsId=" + camps.getCampsId();
			}

			if (StringUtils.isNotBlank(camps.getDeadlineDateStr()))
			{
				camps.setDeadlineDate(DateUtil.formatToDate(camps.getDeadlineDateStr(), "yyyy-MM-dd"));
			}
			else
			{
				LOG.error("营地截止时间为空,请检查");
				modelMap.put("msg", "营地截止时间为空,请检查");
				return "redirect:/modifyCampsIndex.do?campsId=" + camps.getCampsId();
			}
			if (camps.getDeadlineDate().after(maxCampsStartDate))
			{
				LOG.error("营地截止时间晚于开始时间,请检查");
				modelMap.put("msg", "营地截止时间晚于开始时间,请检查");
				return "redirect:/modifyCampsIndex.do?campsId=" + camps.getCampsId();
			}
			camps.setUpdateTime(new Date());
			campsDao.updateByPrimaryKeySelective(camps);
			logger.info("修改营地成功,id:" + camps.getCampsId() + ",current user:" + getCurrentAdminLoginUserName(request));
		}
		catch (Exception e)
		{
			logger.error("doModifyCampsIndex()--error", e);
		}
		return "redirect:/campsList.do";
	}

	public static void main(String[] args)
	{
		String s = "20170327144428CD1f120e86,,,,";
		System.out.println(s.split(",").length);
		System.out.println(s.split(","));
	}

	@RequestMapping(value = "upOrDownCamps.do")
	public String upOrDownCamps(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String campsId, Integer status)
	{
		try
		{
			if (StringUtils.isBlank(campsId) || (status != 0 && status != 1))
			{
				return "redirect:/campsList.do";
			}
			CampsVo camps = campsDao.selectByPrimaryKey(campsId);
			if (camps == null)
			{
				return "redirect:/campsList.do";
			}
			camps.setStatus(status);
			campsDao.updateByPrimaryKeySelective(camps);
			logger.info("下架或上架成功,id:" + campsId + ",action:" + status + ",current user:"
					+ getCurrentAdminLoginUserName(request));
		}
		catch (Exception e)
		{
			logger.error("upOrDownCamps()--error", e);
		}
		return "redirect:/campsList.do";
	}
}
