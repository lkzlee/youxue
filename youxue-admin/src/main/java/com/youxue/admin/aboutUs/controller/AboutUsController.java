package com.youxue.admin.aboutUs.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youxue.core.dao.AboutUsDao;
import com.youxue.core.enums.AboutUsTypeEnum;
import com.youxue.core.vo.AboutUsVo;

@Controller
public class AboutUsController
{
	private static final Log log = LogFactory.getLog(AboutUsController.class);
	@Autowired
	AboutUsDao aboutUsDao;

	@RequestMapping("/aboutUs.do")
	public String aboutUs(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception
	{
		List<AboutUsVo> list = aboutUsDao.selectAll();
		for (AboutUsVo vo : list)
		{
			modelMap.put(vo.getType(), vo);
		}
		return "aboutUs/aboutUs";
	}

	@RequestMapping("/doModifyAboutUs.do")
	public String doModifyAboutUs(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			AboutUsVo aboutUs) throws Exception
	{
		try
		{
			if (aboutUs == null || StringUtils.isBlank(aboutUs.getType())
					|| AboutUsTypeEnum.getByDesc(aboutUs.getType()) == null)
			{
				log.info("no aboutUs type,aboutUs:" + aboutUs);
				return "aboutUs/aboutUs";
			}
			AboutUsVo aboutUsInDb = aboutUsDao.selectByPrimaryKey(aboutUs.getType());
			if (aboutUsInDb == null)
			{
				aboutUs.setCreateTime(new Date());
				aboutUs.setUpdateTime(new Date());
				aboutUsDao.insert(aboutUs);
			}
			else
			{
				aboutUs.setUpdateTime(new Date());
				aboutUsDao.updateByPrimaryKeySelective(aboutUs);
			}
		}
		catch (Exception e)
		{
			log.error("error in doModifyAboutUs", e);
		}
		return "redirect:/aboutUs.do";
	}
}
