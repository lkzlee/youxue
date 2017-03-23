package com.youxue.pc.shopCart.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CampsDetailKey;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.ShopCartCampsDetail;
import com.youxue.pc.shopCart.dto.AddShopCartDetailDto;
import com.youxue.pc.shopCart.dto.ShopCartListlDto;

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
	 * 加入购物车详情页面
	 * 废弃掉的接口
	 */
	@Deprecated
	@RequestMapping("/shopCartDetail.do")
	@ResponseBody
	public String shopCartDetail(HttpServletRequest request, HttpServletResponse response, String campusId)
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
		dto.setResult(100);
		dto.setCamps(camps);
		dto.setCount(currentCount);

		//		List<CampsVo> hotCampsList = campsDao.getCampusListByType(CategoryTypeEnum.HOT, 1, 10);
		List<CampsVo> hotCampsList = campsDao.getHotCampusList(true);
		dto.setHotCamps(hotCampsList);
		return JsonUtil.serialize(dto);
	}

	/**
	 * @param request
	 * @param response
	 * num为添加购物车数量
	 */
	@RequestMapping("/addCartItem.do")
	@ResponseBody
	public String addCartItem(HttpServletRequest request, HttpServletResponse response, String campusId,
			String detailId, Integer num)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (StringUtils.isBlank(campusId) || num == null || num <= 0)
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数错误"));
		}
		CampsVo camps = campsDao.selectByPrimaryKey(campusId);
		if (camps == null)
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("对应的营地不存在"));
		}

		jedisProxy.hset(RedisConstant.SHOP_CART_KEY + accountId, campusId + "," + detailId, num);
		//		boolean exist = jedisProxy.hexist(RedisConstant.SHOP_CART_KEY + accountId, campusId);
		//		if (exist)
		//		{
		//			int currentNum = Integer.valueOf(jedisProxy.hget(RedisConstant.SHOP_CART_KEY + accountId, campusId));
		//			if (currentNum + num > 0)
		//				jedisProxy.hset(RedisConstant.SHOP_CART_KEY + accountId, campusId, currentNum + num);
		//			else
		//				jedisProxy.hdel(RedisConstant.SHOP_CART_KEY + accountId, campusId);
		//		}
		//		else
		//		{
		//			if (num > 0)
		//				jedisProxy.hset(RedisConstant.SHOP_CART_KEY + accountId, campusId, num);
		//		}
		return JsonUtil.serialize(BaseResponseDto.successDto());
	}

	@RequestMapping("/deleteCartItem.do")
	@ResponseBody
	public String deleteCartItem(HttpServletRequest request, HttpServletResponse response, String[] campusId,
			String[] detailId)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (campusId == null || campusId.length == 0)
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数错误"));
		}
		String[] keys = new String[campusId.length];
		for (int i = 0; i < campusId.length; i++)
		{
			keys[i] = campusId[i] + "," + detailId[i];
		}
		jedisProxy.hdel(RedisConstant.SHOP_CART_KEY + accountId, keys);
		return JsonUtil.serialize(BaseResponseDto.successDto());
	}

	/**
	 * @param request
	 * @param response
	 * 购物车列表页面
	 */
	@RequestMapping("/shopCartDetailList.do")
	@ResponseBody
	public String shopCartList(HttpServletRequest request, HttpServletResponse response)
	{
		String accountId = getCurrentLoginUserName(request);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		Map<String, String> cartMap = jedisProxy.hgetAll(RedisConstant.SHOP_CART_KEY + accountId);
		if (MapUtils.isEmpty(cartMap))
		{
			LOG.info("shop cart is empty,accountId:" + accountId);
			ShopCartListlDto shopCartDto = new ShopCartListlDto();
			shopCartDto.setShopCartList(Lists.newArrayList());
			shopCartDto.setResult(100);
			shopCartDto.setResultDesc("查询成功");
			return JsonUtil.serialize(shopCartDto);
		}
		LOG.info("shop cart is :" + cartMap);
		List<CampsDetailKey> keyList = Lists.newArrayList();
		Set<String> redisKeys = cartMap.keySet();
		for (String redisKey : redisKeys)
		{
			String[] s = redisKey.split(",");
			CampsDetailKey key = new CampsDetailKey();
			key.setCampsId(s[0]);
			key.setDetailId(s[1]);
			keyList.add(key);
		}

		List<ShopCartCampsDetail> campsList = campsDao.selectShopCartCampsListByIds(keyList);
		for (ShopCartCampsDetail camps : campsList)
		{
			String buyCount = cartMap.get(camps.getCampsId() + "," + camps.getDetailId());
			camps.setCartBuyCount(Integer.parseInt(buyCount));
		}
		ShopCartListlDto shopCartDto = new ShopCartListlDto();
		shopCartDto.setShopCartList(campsList);
		shopCartDto.setResult(100);
		shopCartDto.setResultDesc("查询成功");
		return JsonUtil.serialize(shopCartDto);
	}
}
