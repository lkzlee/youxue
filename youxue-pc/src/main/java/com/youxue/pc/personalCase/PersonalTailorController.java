package com.youxue.pc.personalCase;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.PersonTailorDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.PersonTailorVo;

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
	PersonTailorDao personTailorDao;
	@Autowired
	CommonDao commonDao;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("personalTailorList.do")
	@ResponseBody
	public String personalTailorList(HttpServletRequest request, HttpServletResponse response, PersonTailorVo record)
			throws Exception
	{
		try
		{
			if (record == null || StringUtils.isBlank(record.getName()) || StringUtils.isBlank(record.getPhone())
					|| StringUtils.isBlank(record.getDestination()) || StringUtils.isBlank(record.getEmail()))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数缺失"));
			}
			record.setId(commonDao.getIdByPrefix(CommonConstant.PERSONAL_TAILOR_ID_PREFIX));
			personTailorDao.insertSelective(record);
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("成功"));
		}
		catch (Exception e)
		{
			logger.info("error in personalTailorList", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("请求异常"));
		}
	}

}
