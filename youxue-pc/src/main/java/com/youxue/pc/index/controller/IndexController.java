package com.youxue.pc.index.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CategoryVo;
import com.youxue.pc.index.dto.IndexCampsDetailsDto;

/**
 * @author Masterwind
 * 2016年11月20日上午11:24:28
 * @Description 首页接口
 */
@Controller
public class IndexController extends BaseController
{
	protected final static Log LOG = LogFactory.getLog(IndexController.class);

	@Autowired
	CampsDao campsDao;
	@Autowired
	CatetoryDao catetoryDao;
	@Autowired
	JedisProxy jedisProxy;

	/**
	 * @param request
	 * @param response
	 * 查询国家列表、要做什么列表
	 */
	@RequestMapping("/getCategroyList.html")
	@ResponseBody
	public String getCategroyList(HttpServletRequest request, HttpServletResponse response, Integer categoryType)
	{
		if (categoryType == null
				|| (categoryType != CategoryTypeEnum.LOCALE.getValue() && categoryType != CategoryTypeEnum.TODO
						.getValue()))
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数不合法"));
		List<CategoryVo> categoryList = catetoryDao.selectByCategoryType(categoryType);
		return JsonUtil.serialize(categoryList);
	}

	/**
	 * @param request
	 * @param response
	 * @return 首页信息列表
	 */
	@RequestMapping("/getIndexCampsDetail.html")
	@ResponseBody
	public String getIndexCampsDetail(HttpServletRequest request, HttpServletResponse response)
	{
		IndexCampsDetailsDto dto = new IndexCampsDetailsDto();
		List<CampsVo> hotCampsList = catetoryDao.getCampusListByType(CategoryTypeEnum.HOT, 1, 3);
		dto.setHotCampsList(hotCampsList);
		List<CategoryVo> categoryList = catetoryDao.selectByCategoryType(CategoryTypeEnum.SUBJECT.getValue());
		dto.setSubjectList(categoryList);
		return JsonUtil.serialize(dto);
	}

}
