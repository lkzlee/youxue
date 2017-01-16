package com.youxue.admin.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.util.JsonUtil;

@Controller
public class SearchController
{
	private static final Log log = LogFactory.getLog(SearchController.class);

	@RequestMapping("/searchList.do")
	public String searchList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, String pageNo)
			throws Exception
	{
		try
		{
			return JsonUtil.serialize(resultMap);
		}
		catch (Exception e)
		{
			log.fatal("上传文件异常，msg:" + e.getMessage(), e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("上传文件失败，系统繁忙"));
		}
	}

}
