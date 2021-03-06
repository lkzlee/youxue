package com.youxue.admin.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.youxue.core.dao.WordCountDao;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.WordCountVo;

@Controller
public class SearchController
{
	private static final Log log = LogFactory.getLog(SearchController.class);
	@Autowired
	WordCountDao wordCountDao;

	@RequestMapping("/searchList.do")
	public String searchList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, String pageNo)
			throws Exception
	{
		try
		{
			Page<WordCountVo> searchList = wordCountDao.selectPageByConditions(
					new Page<WordCountVo>(Page.getPageNo(pageNo), Page.DEFAULT_PAGESIZE), Maps.newHashMap());
			modelMap.put("searchList", searchList);

		}
		catch (Exception e)
		{
			log.fatal("error in searchList，msg:", e);
		}
		return "search/searchList";
	}

}
