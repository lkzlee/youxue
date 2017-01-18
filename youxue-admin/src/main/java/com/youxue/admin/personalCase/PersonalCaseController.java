package com.youxue.admin.personalCase;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youxue.admin.constant.AdminBaseController;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.PersonalCaseDao;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.PersonalCaseVo;

@Controller
public class PersonalCaseController extends AdminBaseController
{
	private static final Log logger = LogFactory.getLog(PersonalCaseController.class);
	@Autowired
	PersonalCaseDao personalCaseDao;
	@Autowired
	CommonDao commonDao;

	@RequestMapping("personalCaseList.do")
	public String personalCaseList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String pageNo) throws Exception
	{
		try
		{
			Page<PersonalCaseVo> personalCaseList = personalCaseDao.selectByPage(Page.getPageNo(pageNo), 5);
			modelMap.put("personalCaseList", personalCaseList);
		}
		catch (Exception e)
		{
			logger.info("error in personalCaseList", e);
		}
		return "personalCase/personalCaseList";
	}

	@RequestMapping(value = "doAddPersonalCase.do")
	public String doAddPersonalCase(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			PersonalCaseVo personalCase)
	{
		try
		{
			if (personalCase == null || StringUtils.isBlank(personalCase.getCaseName()))
			{
				return "redirect:/personalCaseList.do";
			}
			personalCase.setCaseId(commonDao.getIdByPrefix(CommonConstant.PERSONAL_CASE_ID_PREFIX));
			personalCase.setUpdateTime(new Date());
			personalCase.setCreateTime(new Date());
			personalCaseDao.insertSelective(personalCase);
			logger.info("添加personalCase成功,id:" + personalCase.getCaseId() + ",current user:"
					+ getCurrentAdminLoginUserName(request));
			return "redirect:/personalCaseList.do";
		}
		catch (Exception e)
		{
			logger.error("doAddPersonalCase()--error", e);
			return "redirect:/personalCaseList.do";
		}
	}

	@RequestMapping(value = "modifyPersonalCase.do")
	public String modifyPersonalCase(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String caseId)
	{
		try
		{
			if (StringUtils.isBlank(caseId))
			{
				return "redirect:/personalCaseList.do";
			}
			PersonalCaseVo personalCase = personalCaseDao.selectByPrimaryKey(caseId);
			if (personalCase == null)
			{
				logger.info("personalCase not exist,caseId:" + caseId);
				return "redirect:/personalCaseList.do";
			}
			modelMap.put("personalCase", personalCase);
			return "personalCase/modifyPersonalCase";
		}
		catch (Exception e)
		{
			logger.error("modifyPersonalCase()--error", e);
			return "redirect:/personalCaseList.do";
		}
	}

	@RequestMapping(value = "doModifyPersonalCase.do")
	public String doModifyPersonalCase(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			PersonalCaseVo personalCase)
	{
		try
		{
			//			logger.info("get doModifyPersonalCase,personalCase:" + personalCase);
			if (personalCase == null || StringUtils.isBlank(personalCase.getCaseId()))
			{
				return "redirect:/personalCaseList.do";
			}
			personalCase.setUpdateTime(new Date());
			personalCaseDao.updateByPrimaryKeySelective(personalCase);
			logger.info("修改personalCase成功,id:" + personalCase.getCaseId() + ",current user:"
					+ getCurrentAdminLoginUserName(request));
			return "redirect:/personalCaseList.do";
		}
		catch (Exception e)
		{
			logger.error("doModifyPersonalCase()--error", e);
			return "redirect:/personalCaseList.do";
		}
	}

	@RequestMapping(value = "doDeletePersonalCase.do")
	public String doDeletePersonalCase(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String caseId)
	{
		try
		{
			if (StringUtils.isBlank(caseId))
			{
				logger.info("caseid缺失，无法删除");
				return "redirect:/personalCaseList.do";
			}
			personalCaseDao.deleteByPrimaryKey(caseId);
			logger.info("删除personalCase成功,id:" + caseId + ",current user:" + getCurrentAdminLoginUserName(request));
		}
		catch (Exception e)
		{
			logger.error("doDeletePersonalCase()--error", e);
		}
		return "redirect:/personalCaseList.do";
	}
}
