package com.youxue.core.service.message;

import java.text.MessageFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.MessageDao;
import com.youxue.core.enums.MessageEnum;
import com.youxue.core.vo.MessageVo;

@Service
public class MessageServiceImpl implements MessageService
{
	@Resource
	private MessageDao messageDao;
	@Resource
	private CommonDao commonDao;

	@Override
	public void addOrderMessage(MessageEnum msgEnum, String accountId, String orderId)
	{
		String msgStr = MessageFormat.format(msgEnum.getDesc(), orderId);
		MessageVo message = new MessageVo();
		message.setAccountId(accountId);
		message.setCreateTime(new Date());
		message.setMessageContent(msgStr);
		message.setMessageId(commonDao.getIdByPrefix(CommonConstant.MESSAGE_ID_PREFIX));
		message.setMessageTitle(msgEnum.getTitle());
		message.setReadStatus(0);
		message.setUpdateTime(new Date());
		messageDao.insert(message);
	}
}
