package com.youxue.pc.uc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.MessageDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.MPage;
import com.youxue.core.vo.MessageVo;
import com.youxue.core.vo.Page;

/***
 * 用户站内信
 * @author liyongchao
 *
 */
@Controller
public class UserMessageController extends BaseController
{
	private final static Log LOG = LogFactory.getLog(UserMessageController.class);
	@Resource
	private MessageDao messageDao;

	/***
	 * 用户站内信
	 * @param request
	 * @param response
	 * @param pageNo
	 * @param startDate yyyyMMdd
	 * @return
	 */
	@RequestMapping(path = "/uc/usermessage.do")
	@ResponseBody
	public String userPageMessageInfo(HttpServletRequest request, HttpServletResponse response, String pageNo,
			String startDate)
	{
		String accountId = getCurrentLoginUserName(request);
		LOG.info("查询 用户站内信，accountId=" + accountId + ",startDate=" + startDate);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		int pNum = Page.getPageNo(pageNo);
		MPage<MessageVo> page = new MPage<MessageVo>(pNum, Page.DEFAULT_PAGESIZE);
		page = (MPage<MessageVo>) messageDao.selectPageMessageByType(page, accountId, startDate);
		int unReadCount = messageDao.selectUnReadCount(accountId, startDate);
		page.setUnReadCount(unReadCount);
		return JsonUtil.serialize(page);
	}
}
