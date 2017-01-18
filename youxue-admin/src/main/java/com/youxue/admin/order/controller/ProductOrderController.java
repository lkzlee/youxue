package com.youxue.admin.order.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.admin.constant.ConstantMapUtil;
import com.youxue.core.common.BaseController;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.ProductOrderVoDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.ProductOrderVo;

/***
 * 产品订单页
 * @author lkzlee
 *
 */
@Controller
public class ProductOrderController extends BaseController
{
	private final static Log LOG = LogFactory.getLog(ProductOrderController.class);
	@Resource
	private ProductOrderVoDao productOrderDao;
	@Resource
	private CommonDao commonDao;

	/***
	 * 
	 * @param request
	 * @param response
	 * @param orderId
	 * @param accountId
	 * @param payType
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(path = "/admin/productorder.do")
	public String userPageOrderInfo(HttpServletRequest request, HttpServletResponse response, String orderId,
			String accountId, String payType, String startTime, String endTime, String pageNo, ModelMap modelMap)
	{
		LOG.info("查询周边产品订单页 orderId=" + orderId + ",accountId=" + accountId + ",payType=" + payType + ",startTime="
				+ startTime + ",endTime=" + endTime + ",pageNo=" + pageNo);
		PayTypeEnum pType = PayTypeEnum.UNKNOW_PAY;
		Date stTime = null;
		Date edTime = null;
		try
		{
			if (StringUtils.isNotBlank(payType))
			{
				int tmpType = Integer.parseInt(payType);
				pType = PayTypeEnum.getByValue(tmpType);
			}
			if (StringUtils.isNotBlank(startTime))
			{
				stTime = DateUtil.formatToDate(startTime, DateUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
			}
			if (StringUtils.isNotBlank(endTime))
			{
				edTime = DateUtil.formatToDate(endTime, DateUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
			}
		}
		catch (Exception e)
		{
		}

		int pNum = Page.getPageNo(pageNo);
		Page<ProductOrderVo> page = new Page<ProductOrderVo>(pNum, Page.DEFAULT_PAGESIZE);

		page = productOrderDao.selectPageOrderListByInfo(page, pType, orderId, accountId, stTime, edTime);

		LOG.info("查询周边订单页的结果为：resultList=" + page.getResultList());
		modelMap.put("page", page);
		modelMap.put("orderId", orderId);
		modelMap.put("accountId", accountId);
		modelMap.put("payType", pType.getValue());
		modelMap.put("startTime", startTime);
		modelMap.put("endTime", endTime);
		modelMap.put("payTypeMap", ConstantMapUtil.payTypeMap);
		modelMap.put("productTypeMap", ConstantMapUtil.productTypeMap);
		return "/productOrder/productOrderList";

	}

	@RequestMapping(path = "/admin/addproductorder.do")
	public String userPageOrderInfo(HttpServletRequest request, HttpServletResponse response,
			ProductOrderVo productOrder)
	{

		try
		{
			checkParamAndFill(productOrder);
			LOG.info("插入产品订单操作 productOrder=" + productOrder);
			productOrderDao.insertSelective(productOrder);
		}
		catch (Exception e)
		{
			request.setAttribute("addMsg", e.getMessage());
		}
		return "redirect:/admin/productorder.do";

	}

	private void checkParamAndFill(ProductOrderVo productOrder)
	{
		if (productOrder == null)
			throw new BusinessException("参数非法");
		productOrder.setCreateTime(DateUtil.getCurrentTimestamp());
		productOrder.setBuyTime(DateUtil.getCurrentTimestamp());
		if (StringUtils.isNotEmpty(productOrder.getBuyTimeStr()))
		{
			productOrder.setBuyTime(DateUtil.formatToDate(productOrder.getBuyTimeStr(),
					DateUtil.DATE_FORMAT_YYYYMMDD_HHMMSS));
		}
		productOrder.setOrderId(commonDao.getIdByPrefix(CommonConstant.PRODUCT_ORDER_PREFIX));
	}
}
