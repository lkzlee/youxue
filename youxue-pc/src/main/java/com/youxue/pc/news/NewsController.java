package com.youxue.pc.news;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.NewsVoDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.NewsVo;
import com.youxue.core.vo.Page;

/**
 * 文章资讯
 */
@Controller
public class NewsController
{
	private static final Log logger = LogFactory.getLog(NewsController.class);

	@Autowired
	private NewsVoDao newsDao;
	@Autowired
	private CommonDao commonDao;

	@RequestMapping(value = "newsList.do")
	@ResponseBody
	public String newsList(HttpServletRequest request, HttpServletResponse response, String pageNo)
	{
		try
		{
			Page<NewsVo> newsList = newsDao.selectByPage(Page.getPageNo(pageNo), 5);
			logger.info("newsList is:" + newsList.getResultList().size());
			return JsonUtil.serialize(newsList);
		}
		catch (Exception e)
		{
			logger.info("error in newsList", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("请求异常"));
		}
	}

	@RequestMapping(value = "newsContent.do")
	@ResponseBody
	public String newsContent(HttpServletRequest request, HttpServletResponse response, String newsId)
	{
		try
		{
			if (StringUtils.isBlank(newsId))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数缺失"));
			}
			NewsVo news = newsDao.selectByPrimaryKey(newsId);
			if (news == null)
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("文章不存在"));
			}
			return JsonUtil.serialize(news);
		}
		catch (Exception e)
		{
			logger.info("error in newsList", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("请求异常"));
		}
	}
}
