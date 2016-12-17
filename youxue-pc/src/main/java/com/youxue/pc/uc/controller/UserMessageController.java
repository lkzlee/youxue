package com.youxue.pc.uc.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
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
 * @author lkzlee
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

	@RequestMapping(path = "/uc/markmessage.do")
	@ResponseBody
	public String markReadStatusForMessage(HttpServletRequest request, HttpServletResponse response, String pageNo,
			String[] messageId)
	{
		String accountId = getCurrentLoginUserName(request);
		LOG.info("标记用户站内信已读，accountId=" + accountId + ",messageId=" + Arrays.toString(messageId));
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (ArrayUtils.isEmpty(messageId))
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
		List<String> msgIdList = Arrays.asList(messageId);
		int successCount = messageDao.markMessageReadDone(accountId, msgIdList);
		if (successCount > 0)
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("标记成功"));
		return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("未标记信息为空"));
	}
}
