package com.youxue.admin.personalCase;

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
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.PersonTailorDao;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.PersonTailorVo;

/**
 * @author Masterwind
 * 2017年1月19日下午8:06:47

 * @Description 私人订制列表
 */
@Controller
public class PersonalTailorController extends AdminBaseController
{
	private static final Log logger = LogFactory.getLog(PersonalTailorController.class);
	@Autowired
	PersonTailorDao personTailorDao;
	@Autowired
	CommonDao commonDao;

	@RequestMapping("personalTailorList.do")
	public String personalCaseList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String pageNo) throws Exception
	{
		try
		{
			Page<PersonTailorVo> personalTailorList = personTailorDao.selectByPage(Page.getPageNo(pageNo), 20);
			modelMap.put("personalTailorList", personalTailorList);
		}
		catch (Exception e)
		{
			logger.info("error in personalTailorList", e);
		}
		return "personalCase/personalTailorList";
	}

	@RequestMapping(value = "modifyPersonalTailor.do")
	public String modifyPersonalTailor(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String id)
	{
		try
		{
			if (StringUtils.isBlank(id))
			{
				return "redirect:/personalTailorList.do";
			}
			PersonTailorVo personTailor = personTailorDao.selectByPrimaryKey(id);
			if (personTailor == null)
			{
				logger.info("personTailor not exist,id:" + id);
				return "redirect:/personalTailorList.do";
			}
			modelMap.put("personalTailor", personTailor);
			return "personalCase/modifyPersonalTailor";
		}
		catch (Exception e)
		{
			logger.error("modifyPersonalTailor()--error", e);
			return "redirect:/personalTailorList.do";
		}
	}

	@RequestMapping(value = "doModifyPersonalTailor.do")
	public String doModifyPersonalTailor(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			PersonTailorVo personTailor)
	{
		try
		{
			if (personTailor == null || StringUtils.isBlank(personTailor.getRemark())
					|| StringUtils.isBlank(personTailor.getId()))
			{
				return "redirect:/personalTailorList.do";
			}
			personTailorDao.updateByPrimaryKeySelective(personTailor);
			logger.info("修改personTailor成功,id:" + personTailor.getId() + ",current user:"
					+ getCurrentAdminLoginUserName(request));
		}
		catch (Exception e)
		{
			logger.error("doModifyPersonalTailor()--error", e);
		}
		return "redirect:/personalTailorList.do";
	}
}
