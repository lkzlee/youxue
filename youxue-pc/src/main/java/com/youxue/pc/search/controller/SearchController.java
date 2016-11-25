package com.youxue.pc.search.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.Page;
import com.youxue.pc.search.dto.SearchResultDto;

/**
 * @author Masterwind
 * 2016年11月20日上午11:24:28
 * @Description 搜索接口
 */
@Controller
public class SearchController extends BaseController
{
	protected final static Log LOG = LogFactory.getLog(SearchController.class);

	@Autowired
	CampsDao campsDao;
	@Autowired
	CatetoryDao catetoryDao;
	@Autowired
	JedisProxy jedisProxy;

	/**
	 * @param request
	 * @param response
	 * 根据查询条件获取对应的营地列表
	 */
	@RequestMapping("/getCampsList.html")
	@ResponseBody
	public String getCampsList(HttpServletRequest request, HttpServletResponse response, String LocaleCategoryId,
			String subjectCategoryId, String timeDuration, String priceRange, String departureTime, Integer pageNo,
			String searchContent)
	{
		try
		{
			if (pageNo == null || pageNo <= 0)
			{
				pageNo = 1;
			}
			Map<String, Object> queryConditions = new HashMap<>();
			if (StringUtils.isNotBlank(timeDuration))
			{
				String[] days = timeDuration.split("-");
				int minDays = Integer.valueOf(days[0]);
				int maxDays = Integer.valueOf(days[1]);
				if (minDays > maxDays || minDays < 0 || maxDays < 0)
					return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("时间周期不合理"));
				queryConditions.put("minDays", minDays);
				queryConditions.put("maxDays", maxDays);
			}
			if (StringUtils.isNotBlank(priceRange))
			{
				String[] prices = priceRange.split("-");
				int minPrice = Integer.valueOf(prices[0]);
				int maxPrice = Integer.valueOf(prices[1]);
				if (minPrice > maxPrice || minPrice < 0 || maxPrice < 0)
					return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("价格周期不合理"));
				queryConditions.put("minPrice", minPrice);
				queryConditions.put("maxPrice", maxPrice);
			}
			if (StringUtils.isNotBlank(departureTime))
			{
				queryConditions.put("departureMonth", Integer.valueOf(departureTime));
			}
			if (StringUtils.isNotBlank(LocaleCategoryId))
			{
				queryConditions.put("LocaleCategoryId", LocaleCategoryId);
			}
			if (StringUtils.isNotBlank(subjectCategoryId))
			{
				queryConditions.put("subjectCategoryId", subjectCategoryId);
			}
			if (StringUtils.isNotBlank(searchContent))
			{
				queryConditions.put("searchContent", searchContent);
				jedisProxy.hincrBy(RedisConstant.SEARCH_MAP_KEY, searchContent, 1);
			}
			SearchResultDto dto = new SearchResultDto();
			Page<CampsVo> campsList = campsDao.selectByConditions(queryConditions, pageNo,
					CommonConstant.CAMPS_PAGE_SIZE);
			dto.setResult(100);
			dto.setCampsList(campsList);
			return JsonUtil.serialize(dto);
		}
		catch (Exception e)
		{
			LOG.error("error during getCampsList", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("查询异常"));
		}
	}

}
