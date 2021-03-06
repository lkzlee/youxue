package com.youxue.admin.coupon;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.utils.DateUtil;
import com.youxue.admin.constant.AdminConstant;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.CouponCodeDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CategoryVo;
import com.youxue.core.vo.CouponCodeVo;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.SysUser;

@Controller
public class CouponController
{
	private static final Log logger = LogFactory.getLog(CouponController.class);

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@Autowired
	private CatetoryDao categoryDao;
	@Autowired
	private CouponCodeDao couponCodeDao;
	@Autowired
	private CommonDao commonDao;

	@RequestMapping("/couponList.do")
	public String couponListIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Integer couponStatus, String couponName, String couponValue, String categoryId, String validStartTime,
			String validEndTime, String pageNo)
	{
		Map<String, Object> conditions = new HashMap<>();
		if (StringUtils.isNotBlank(couponName))
		{
			conditions.put("couponName", couponName);
		}
		if (StringUtils.isNotBlank(categoryId))
		{
			conditions.put("categoryId", categoryId);
		}
		if (StringUtils.isNotBlank(couponValue))
		{
			conditions.put("couponValue", couponValue);
		}
		if (StringUtils.isNotBlank(validStartTime))
		{
			conditions.put("validStartTime", DateUtil.formatToDate(validStartTime, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(validEndTime))
		{
			conditions.put("validEndTime", DateUtil.formatToDate(validEndTime, "yyyy-MM-dd"));
		}
		int pageNum = Page.getPageNo(pageNo);
		List<CategoryVo> localeCategoryList = categoryDao.selectByCategoryType(CategoryTypeEnum.LOCALE.getValue());
		List<CategoryVo> subjectCategoryList = categoryDao.selectByCategoryType(CategoryTypeEnum.SUBJECT.getValue());
		List<CategoryVo> categoryList = new LinkedList<>();
		categoryList.addAll(localeCategoryList);
		categoryList.addAll(subjectCategoryList);
		modelMap.put("categoryList", categoryList);
		modelMap.put("subjectCategoryList", subjectCategoryList);
		modelMap.put("localeCategoryList", localeCategoryList);
		Map<String, String> cateMap = new HashMap<>();
		for (CategoryVo category : categoryList)
		{
			cateMap.put(category.getCategoryId(), category.getCategoryName());
		}
		Page<CouponCodeVo> couponPage = couponCodeDao.selectPageByConditions(new Page<CouponCodeVo>(pageNum,
				Page.DEFAULT_PAGESIZE), conditions);
		for (CouponCodeVo coupon : couponPage.getResultList())
		{
			String cateIds = coupon.getCategoryIds();
			if (StringUtils.isBlank(cateIds))
			{
				coupon.setCategorys("全部分类");
			}
			else
			{
				String[] categoryIds = cateIds.split(",");
				String cateStrs = "";
				for (String cateId : categoryIds)
				{
					cateStrs = cateStrs + (cateMap.get(cateId) == null ? "" : cateMap.get(cateId)) + " ";
				}
				coupon.setCategorys(cateStrs);
			}
		}
		modelMap.put("couponPage", couponPage);
		modelMap.put("couponName", StringUtils.isBlank(couponName) ? "" : couponName);
		modelMap.put("categoryId", StringUtils.isBlank(categoryId) ? "" : categoryId);
		modelMap.put("couponValue", StringUtils.isBlank(couponValue) ? "" : couponValue);
		modelMap.put("validStartTime", StringUtils.isBlank(validStartTime) ? "" : validStartTime);
		modelMap.put("validEndTime", StringUtils.isBlank(validEndTime) ? "" : validEndTime);
		return "coupon/couponList";
	}

	/**
	 * @param request
	 * @param response
	 * ajax请求
	 */
	@RequestMapping("/doGetCouponList.do")
	@ResponseBody
	public String getCategroyList(HttpServletRequest request, HttpServletResponse response, Integer couponStatus,
			String couponName, String couponValue, String categoryId, String validStartTime, String validEndTime,
			String pageNo)
	{

		try
		{
			Map<String, Object> conditions = new HashMap<>();
			if (StringUtils.isNotBlank(couponName))
			{
				conditions.put("couponName", couponName);
			}
			if (StringUtils.isNotBlank(categoryId))
			{
				conditions.put("categoryId", categoryId);
			}
			if (StringUtils.isNotBlank(couponValue))
			{
				conditions.put("couponValue", couponValue);
			}
			Page<CouponCodeVo> couponPage = couponCodeDao.selectPageByConditions(
					new Page<CouponCodeVo>(Page.getPageNo(pageNo), Page.DEFAULT_PAGESIZE), conditions);
			CouponListDto dto = new CouponListDto();
			dto.setResult(100);
			dto.setCouponList(couponPage.getResultList());
			return JsonUtil.serialize(dto);
		}
		catch (Exception e)
		{
			logger.error("getCategroyList()--error", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("查询异常"));
		}
	}

	@RequestMapping(value = "addCouponIndex.do")
	public String addCouponIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
	{
		List<CategoryVo> localeCategoryList = categoryDao.selectByCategoryType(CategoryTypeEnum.LOCALE.getValue());
		List<CategoryVo> subjectCategoryList = categoryDao.selectByCategoryType(CategoryTypeEnum.SUBJECT.getValue());
		modelMap.put("subjectCategoryList", subjectCategoryList);
		modelMap.put("localeCategoryList", localeCategoryList);

		return "coupon/addCoupon";
	}

	@RequestMapping(value = "doAddCoupon.do")
	@ResponseBody
	public String doAddCoupon(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			CouponCodeVo coupon)
	{
		try
		{
			if (coupon == null || StringUtils.isBlank(coupon.getCodeValue())
					|| StringUtils.isBlank(coupon.getCodeName()) || coupon.getStartTime() == null
					|| coupon.getEndTime() == null || BigDecimal.ZERO.compareTo(coupon.getCodeAmount()) >= 0)
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("输入参数有误，请检查"));
			}
			coupon.setCodeId(commonDao.getIdByPrefix(CommonConstant.COUPON_ID_PREFIX));
			coupon.setStatus(0);
			coupon.setCreateTime(new Date());
			Object sysUser = request.getSession().getAttribute(AdminConstant.CURRENT_USER);
			if (sysUser != null)
			{
				coupon.setCreator(((SysUser) sysUser).getLoginName());
			}
			couponCodeDao.insertSelective(coupon);
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("添加成功"));
		}
		catch (Exception e)
		{
			logger.error("doAddCoupon()--error", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("添加异常"));
		}
	}

	@RequestMapping(value = "modifyCouponIndex.do")
	public String modifyCouponIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String couponId)
	{
		try
		{
			if (StringUtils.isBlank(couponId))
			{
				return "redirect:/couponList.do";
			}
			CouponCodeVo coupon = couponCodeDao.selectByPrimaryKey(couponId);
			if (coupon == null)
			{
				return "redirect:/couponList.do";
			}
			modelMap.put("coupon", coupon);
			List<CategoryVo> localeCategoryList = categoryDao.selectByCategoryType(CategoryTypeEnum.LOCALE.getValue());
			List<CategoryVo> subjectCategoryList = categoryDao
					.selectByCategoryType(CategoryTypeEnum.SUBJECT.getValue());
			modelMap.put("subjectCategoryList", subjectCategoryList);
			modelMap.put("localeCategoryList", localeCategoryList);
			return "coupon/modifyCoupon";
		}
		catch (Exception e)
		{
			logger.error("modifyCouponIndex()--error", e);
			return "redirect:/couponList.do";
		}

	}

	@RequestMapping(value = "doModifyCoupon.do")
	@ResponseBody
	public String doModifyCoupon(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			CouponCodeVo coupon)
	{
		try
		{
			if (coupon == null || StringUtils.isBlank(coupon.getCodeId()))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("修改参数异常"));
			}
			couponCodeDao.updateByPrimaryKeySelective(coupon);
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("修改成功"));
		}
		catch (Exception e)
		{
			logger.error("doModifyCoupon()--error", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("修改异常"));
		}
	}

	@RequestMapping(value = "downOrUpCoupon.do")
	@ResponseBody
	public String downCoupon(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String couponId, Integer status)
	{
		try
		{
			if (StringUtils.isBlank(couponId) || status == null || (status != 0 && status != 1))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数异常"));
			}
			CouponCodeVo coupon = new CouponCodeVo();
			coupon.setCodeId(couponId);
			coupon.setStatus(status);
			couponCodeDao.updateByPrimaryKeySelective(coupon);
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("成功"));
		}
		catch (Exception e)
		{
			logger.error("downCoupon()--error", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("操作异常"));
		}
	}
}
