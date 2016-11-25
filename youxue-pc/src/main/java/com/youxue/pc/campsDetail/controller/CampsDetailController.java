package com.youxue.pc.campsDetail.controller;

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
import com.youxue.core.dao.CampsTraceDao;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.util.ReflectUtil;
import com.youxue.core.vo.CampsTraceVo;
import com.youxue.core.vo.CampsVo;
import com.youxue.pc.campsDetail.dto.CampsDetailDto;

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
		return JsonUtil.serialize(campsDto);
	}
}
