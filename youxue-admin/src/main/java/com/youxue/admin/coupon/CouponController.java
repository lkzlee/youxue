package com.youxue.admin.coupon;

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
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.dao.CouponCodeDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CategoryVo;
import com.youxue.core.vo.CouponCodeVo;
import com.youxue.core.vo.Page;

@Controller
public class CouponController
{
	private static final Log logger = LogFactory.getLog(CouponController.class);

	@Autowired
	private CatetoryDao categoryDao;
	@Autowired
	private CouponCodeDao couponCodeDao;

	@RequestMapping("/couponListIndex.do")
	public String couponListIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
	{
		List<CategoryVo> localeCategoryList = categoryDao.selectByCategoryType(CategoryTypeEnum.LOCALE.getValue());
		List<CategoryVo> subjectCategoryList = categoryDao.selectByCategoryType(CategoryTypeEnum.SUBJECT.getValue());
		List<CategoryVo> categoryList = new LinkedList<>();
		categoryList.addAll(localeCategoryList);
		categoryList.addAll(subjectCategoryList);
		modelMap.put("categoryList", categoryList);

		Map<String, Object> conditions = new HashMap<>();
		Page<CouponCodeVo> couponPage = couponCodeDao.selectPageByConditions(new Page<CouponCodeVo>(1,
				Page.DEFAULT_PAGESIZE), conditions);
		modelMap.put("couponPage", couponPage);
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
			String couponName, String couponValue, String categoryId, String validTime, String pageNo)
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
			if (coupon == null || StringUtils.isBlank(coupon.getCodeValue()))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数缺失异常"));
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
				return "redirect:/couponListIndex.do";
			}
			CouponCodeVo coupon = couponCodeDao.selectByPrimaryKey(couponId);
			if (coupon == null)
			{
				return "redirect:/couponListIndex.do";
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
			return "redirect:/couponListIndex.do";
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
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("修改异常"));
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
}
