package com.youxue.admin.news;

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
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.NewsVoDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.NewsVo;
import com.youxue.core.vo.Page;

/**
 * 文章资讯
 */
@Controller
public class NewsController extends AdminBaseController
{
	private static final Log logger = LogFactory.getLog(NewsController.class);

	@Autowired
	private NewsVoDao newsDao;
	@Autowired
	private CommonDao commonDao;

	@RequestMapping(value = "news.do")
	public String newsList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, String pageNo)
	{
		try
		{
			Page<NewsVo> newsList = newsDao.selectByPage(Page.getPageNo(pageNo), 10);
			logger.info("resultList is:" + newsList.getResultList().size());
			modelMap.put("newsList", newsList);
		}
		catch (Exception e)
		{
			logger.info("error in newsList", e);
		}
		return "news/newsList";
	}

	@RequestMapping(value = "addNews.do")
	public String addCategoryIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
	{
		return "news/addNews";
	}

	@RequestMapping(value = "doAddNews.do")
	public String doAddNews(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, NewsVo news)
	{
		try
		{
			if (news == null || StringUtils.isBlank(news.getNewsTitle()) || StringUtils.isBlank(news.getNewsContent()))
			{
				return "redirect:/news.do";
			}
			news.setNewsId(commonDao.getIdByPrefix(CommonConstant.NEWS_ID_PREFIX));
			news.setUpdateTime(new Date());
			news.setCreateTime(new Date());
			newsDao.insertSelective(news);
			return "redirect:/news.do";
		}
		catch (Exception e)
		{
			logger.error("doAddNews()--error", e);
			modelMap.put("msg", "添加news异常:后台异常");
			return "redirect:/news.do";
		}
	}

	@RequestMapping(value = "modifyNews.do")
	public String modifyNews(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, String newsId)
	{
		try
		{
			if (StringUtils.isBlank(newsId))
			{
				return "redirect:/news.do";
			}
			NewsVo news = newsDao.selectByPrimaryKey(newsId);
			if (news == null)
			{
				logger.info("news not exist,newsId:" + newsId);
				return "redirect:/news.do";
			}
			modelMap.put("news", news);
			return "new/modifyNews";
		}
		catch (Exception e)
		{
			logger.error("modifyNews()--error", e);
			return "redirect:/news.do";
		}
	}

	@RequestMapping(value = "doModifyNews.do")
	public String doModifyNews(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, NewsVo news)
	{
		try
		{
			if (news == null || StringUtils.isBlank(news.getNewsId()))
			{
				modelMap.put("msg", "修改news异常:参数缺失");
				return "redirect:/news.do";
			}
			newsDao.updateByPrimaryKeySelective(news);
			modelMap.put("msg", "修改新闻成功");
			logger.info("修改分类成功,id:" + news.getNewsId() + ",current user:" + getCurrentAdminLoginUserName(request));
			return "redirect:/news.do";
		}
		catch (Exception e)
		{
			logger.error("doModifyNews()--error", e);
			return "redirect:/news.do";
		}
	}

	@RequestMapping(value = "doDeleteNews.do")
	public String doDeleteNews(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String newsId)
	{
		try
		{
			if (StringUtils.isBlank(newsId))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("删除news异常，参数错误"));
			}
			newsDao.deleteByPrimaryKey(newsId);
			logger.info("删除news成功,id:" + newsId + ",current user:" + getCurrentAdminLoginUserName(request));
		}
		catch (Exception e)
		{
			logger.error("doDeleteNews()--error", e);
		}
		return "redirect:/news.do";
	}
}
