package com.youxue.pc.personalCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.PersonalCaseDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.PersonalCaseVo;

/**
 * @author Masterwind
 * 2017年1月19日下午8:06:47

 * @Description 私人订制 提交表单接口
 */
@Controller
public class PersonalTailorController
{
	private static final Log logger = LogFactory.getLog(PersonalTailorController.class);
	@Autowired
	PersonalCaseDao personalCaseDao;

	@RequestMapping("personalTailorList.do")
	@ResponseBody
	public String personalTailorList(HttpServletRequest request, HttpServletResponse response, String pageNo)
			throws Exception
	{
		try
		{
			Page<PersonalCaseVo> personalCaseList = personalCaseDao.selectByPage(Page.getPageNo(pageNo), 10);
			return JsonUtil.serialize(personalCaseList);
		}
		catch (Exception e)
		{
			logger.info("error in personalCaseList", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("请求异常"));
		}

	}

}
