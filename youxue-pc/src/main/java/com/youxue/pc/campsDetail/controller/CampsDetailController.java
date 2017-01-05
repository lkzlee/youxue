package com.youxue.pc.campsDetail.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CampsTraceDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.util.ReflectUtil;
import com.youxue.core.vo.CampsTraceVo;
import com.youxue.core.vo.CampsVo;
import com.youxue.pc.campsDetail.dto.CampsDetailDto;
import com.youxue.pc.shopCart.dto.CampsListDto;

/**
 * @author Masterwind
 * 2016年11月14日下午11:44:05
 * @Description 营地详情
 */
@Controller
public class CampsDetailController extends BaseController
{
	protected final static Log LOG = LogFactory.getLog(CampsDetailController.class);

	@Autowired
	CampsDao campsDao;
	@Autowired
	CampsTraceDao campsTraceDao;
	@Autowired
	JedisProxy jedisProxy;

	/**
	 * @param request
	 * @param response
	 */
	@RequestMapping("/campsDetail.do")
	@ResponseBody
	public String campsDetail(HttpServletRequest request, HttpServletResponse response, String campusId)
	{
		CampsVo camps = campsDao.selectByPrimaryKey(campusId);
		if (camps == null)
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("对应的营地不存在"));
		}
		List<CampsTraceVo> traces = campsTraceDao.selectByCampsId(campusId);
		CampsDetailDto campsDto = new CampsDetailDto();
		try
		{
			ReflectUtil.setObjectFiledsValue(campsDto, camps);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			LOG.error("error during campsDetail,campsId:" + campusId);
		}
		campsDto.setTraces(traces);
		campsDto.setResult(100);
		/**设置当前商品在购物车中数量*/
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isNotBlank(accountId) && jedisProxy.hexist(RedisConstant.SHOP_CART_KEY + accountId, campusId))
		{
			campsDto.setShopCartCount(Integer.valueOf(jedisProxy
					.hget(RedisConstant.SHOP_CART_KEY + accountId, campusId)));
		}
		else
		{
			campsDto.setShopCartCount(1);
		}
		return JsonUtil.serialize(campsDto);
	}

	@RequestMapping("/getCampsListByCategory.do")
	@ResponseBody
	public String getCampsListByCategory(HttpServletRequest request, HttpServletResponse response,
			Integer categoryType, Integer count, Integer pageNo)
	{
		if (categoryType == null || CategoryTypeEnum.getByValue(categoryType) == null)
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数不合法"));
		}
		if (count == null || count <= 0 || count > 50)
			count = 10;
		if (pageNo == null || pageNo <= 0)
			pageNo = 1;
		CampsListDto campsListDto = new CampsListDto();
		List<CampsVo> campsList = new LinkedList<>();
		if (categoryType == 1)
		{
			campsList = campsDao.getHotCampusList(true);
		}
		if (categoryType == 2)
		{
			campsList = campsDao.getPriceCampusList(true);
		}
		else
		{
			campsList = campsDao.getCampusListByType(CategoryTypeEnum.getByValue(categoryType), pageNo, count);
		}
		campsListDto.setCampsList(campsList);
		campsListDto.setResult(100);
		return JsonUtil.serialize(campsListDto);
	}
}
