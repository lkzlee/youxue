package com.youxue.admin.product.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youxue.admin.constant.ConstantMapUtil;
import com.youxue.core.common.BaseController;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.SurroundProductDao;
import com.youxue.core.vo.SurroundProductVo;

@Controller
public class ProductController extends BaseController
{
	private static final Log log = LogFactory.getLog(ProductController.class);
	@Resource
	private SurroundProductDao surroundProductDao;
	@Resource
	private CommonDao commonDao;

	@RequestMapping("/admin/addProduct.do")
	public String addProduct(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			SurroundProductVo productVo) throws Exception
	{
		try
		{
			if (productVo != null && StringUtils.isBlank(productVo.getProductId()))
			{
				productVo.setProductId(commonDao.getIdByPrefix(CommonConstant.PRODUCT_ID_PREFIX));
				surroundProductDao.insertSelective(productVo);
			}
			else
			{
				surroundProductDao.updateByPrimaryKeySelective(productVo);
			}
		}
		catch (Exception e)
		{
			log.fatal("error in addProduct.do，msg:", e);
		}
		return "redirect:/admin/productDetail.do";
	}

	@RequestMapping("/admin/productDetail.do")
	public String productDetail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception
	{
		try
		{
			SurroundProductVo product1 = surroundProductDao.selectProductByType(1);
			SurroundProductVo product2 = surroundProductDao.selectProductByType(2);
			if (product1 != null)
			{
				modelMap.put("product1", product1);
			}
			if (product2 != null)
			{
				modelMap.put("product2", product2);
			}
		}
		catch (Exception e)
		{
			log.fatal("error in productDetail.do，msg:", e);
		}
		modelMap.put("productTypeMap", ConstantMapUtil.productTypeMap);
		return "/product/productDetail";
	}
}
