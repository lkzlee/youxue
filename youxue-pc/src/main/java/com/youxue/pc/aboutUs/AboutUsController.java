package com.youxue.pc.aboutUs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.AboutUsDao;
import com.youxue.core.enums.AboutUsTypeEnum;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.AboutUsVo;

@Controller
public class AboutUsController
{
	private static final Log log = LogFactory.getLog(AboutUsController.class);
	@Autowired
	AboutUsDao aboutUsDao;

	@RequestMapping("/aboutUs.do")
	@ResponseBody
	public String aboutUs(HttpServletRequest request, HttpServletResponse response, String type) throws Exception
	{
		try
		{
			if (AboutUsTypeEnum.getByDesc(type) == null)
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
			}
			AboutUsVo aboutUs = aboutUsDao.selectByPrimaryKey(type);
			return JsonUtil.serialize(aboutUs);
		}
		catch (Exception e)
		{
			log.error("error in aboutUs", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("请求异常"));
		}
	}

}
