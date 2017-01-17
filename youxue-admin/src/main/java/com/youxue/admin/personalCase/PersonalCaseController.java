package com.youxue.admin.personalCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.youxue.core.dao.PersonalCaseDao;
import com.youxue.core.dao.WordCountDao;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.WordCountVo;

@Controller
public class PersonalCaseController
{
	private static final Log log = LogFactory.getLog(PersonalCaseController.class);
	@Autowired
	WordCountDao wordCountDao;
	@Autowired
	PersonalCaseDao personalCaseDao;

	@RequestMapping("/personalCaseList.do")
	public String personalCaseList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String pageNo) throws Exception
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
