package com.youxue.core.dao;

import java.util.List;

import com.youxue.core.vo.MessageVo;
import com.youxue.core.vo.Page;

public interface MessageDao
{
	int deleteByPrimaryKey(String messageId);

	int insert(MessageVo record);

	int insertSelective(MessageVo record);

	MessageVo selectByPrimaryKey(String messageId);

	int updateByPrimaryKeySelective(MessageVo record);

	int updateByPrimaryKey(MessageVo record);

	Page<MessageVo> selectPageMessageByType(Page<MessageVo> page, String accountId, String startDate);

	int selectUnReadCount(String accountId, String startDate);

	int markMessageReadDone(String accountId, List<String> msgIdList);
}