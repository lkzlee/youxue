package com.youxue.pc.shopCart.controller;

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
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CampsVo;
import com.youxue.pc.shopCart.dto.AddShopCartDetailDto;

/**
 * @author Masterwind
 * 2016年11月14日下午11:44:05
 * @Description 购物车接口
 */
@Controller
public class ShopCartController extends BaseController
{
	protected final static Log LOG = LogFactory.getLog(ShopCartController.class);

	@Autowired
	CampsDao campsDao;
	@Autowired
	CatetoryDao catetoryDao;
	@Autowired
	JedisProxy jedisProxy;

	/**
	 * @param request
	 * @param response
	 * 加入购物车页面
	 */
	@RequestMapping("/addCartItemDetail.html")
	@ResponseBody
	public String addCartItemDetail(HttpServletRequest request, HttpServletResponse response, String campusId)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (StringUtils.isBlank(campusId))
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数错误"));
		}
		CampsVo camps = campsDao.selectByPrimaryKey(campusId);
		if (camps == null)
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("对应的营地不存在"));
		}

		boolean exist = jedisProxy.hexist(RedisConstant.SHOP_CART_KEY + accountId, campusId);
		int currentCount = 1;
		if (exist)
		{
			currentCount = Integer.valueOf(jedisProxy.hget(RedisConstant.SHOP_CART_KEY + accountId, campusId));
		}
		else
		{
			jedisProxy.hset(RedisConstant.SHOP_CART_KEY + accountId, campusId, currentCount);
		}
		AddShopCartDetailDto dto = new AddShopCartDetailDto();
		dto.setCamps(camps);
		dto.setCount(currentCount);

		List<CampsVo> hotCampsList = campsDao.getCampusListByType(CategoryTypeEnum.HOT, 1, 3);
		dto.setHotCamps(hotCampsList);
		return JsonUtil.serialize(BaseResponseDto.successDto());
	}

	/**
	 * @param request
	 * @param response
	 * num为1表示添加购物车，num为-1表示减少购物车中个数
	 */
	@RequestMapping("/addCartItem.html")
	@ResponseBody
	public String addCartItem(HttpServletRequest request, HttpServletResponse response, String campusId, Integer num)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (StringUtils.isBlank(campusId) || num == null)
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数错误"));
		}
		CampsVo camps = campsDao.selectByPrimaryKey(campusId);
		if (camps == null)
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("对应的营地不存在"));
		}

		boolean exist = jedisProxy.hexist(RedisConstant.SHOP_CART_KEY + accountId, campusId);
		if (exist)
		{
			int currentNum = Integer.valueOf(jedisProxy.hget(RedisConstant.SHOP_CART_KEY + accountId, campusId));
			if (currentNum + num > 0)
				jedisProxy.hset(RedisConstant.SHOP_CART_KEY + accountId, campusId, currentNum + num);
			else
				jedisProxy.hdel(RedisConstant.SHOP_CART_KEY + accountId, campusId);
		}
		else
		{
			if (num > 0)
				jedisProxy.hset(RedisConstant.SHOP_CART_KEY + accountId, campusId, num);
		}
		return JsonUtil.serialize(BaseResponseDto.successDto());
	}

	@RequestMapping("/deleteCartItem.html")
	@ResponseBody
	public String deleteCartItem(HttpServletRequest request, HttpServletResponse response, String campusId)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (StringUtils.isBlank(campusId))
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数错误"));
		}
		boolean exist = jedisProxy.hexist(RedisConstant.SHOP_CART_KEY + accountId, campusId);
		if (exist)
		{
			jedisProxy.hdel(RedisConstant.SHOP_CART_KEY + accountId, campusId);
		}
		return JsonUtil.serialize(BaseResponseDto.successDto());
	}

}
