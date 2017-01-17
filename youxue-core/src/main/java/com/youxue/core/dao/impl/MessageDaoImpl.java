package com.youxue.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.MessageDao;
import com.youxue.core.vo.MessageVo;
import com.youxue.core.vo.Page;

@Repository
public class MessageDaoImpl extends BaseDao implements MessageDao
{

	@Override
	public int deleteByPrimaryKey(String messageId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.MessageDao.deleteByPrimaryKey", messageId);
	}

	@Override
	public int insert(MessageVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.MessageDao.insert", record);
	}

	@Override
	public int insertSelective(MessageVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.MessageDao.insertSelective", record);
	}

	@Override
	public MessageVo selectByPrimaryKey(String messageId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.MessageDao.selectByPrimaryKey", messageId);
	}

	@Override
	public int updateByPrimaryKeySelective(MessageVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.MessageDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(MessageVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.MessageDao.updateByPrimaryKey", record);
	}

	@Override
	public Page<MessageVo> selectPageMessageByType(Page<MessageVo> page, String accountId, String startDate)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(startDate))
		{
			param.put("startDate", startDate);
		}
		param.put("accountId", accountId);
		return getPageList(page, "com.youxue.core.dao.MessageDao.selectPageMessageByType",
				"com.youxue.core.dao.MessageDao.selectCountPageMessageByType", param);
	}

	@Override
	public int selectUnReadCount(String accountId, String startDate)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(startDate))
		{
			param.put("startDate", startDate);
		}
		param.put("accountId", accountId);
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.MessageDao.selectUnReadCount", param);
	}

	@Override
	public int markMessageReadDone(String accountId, List<String> msgIdList)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("msgIdList", msgIdList);
		param.put("accountId", accountId);
		return sqlSessionTemplate.update("com.youxue.core.dao.MessageDao.markMessageReadDone", param);
	}

	@Override
	public int deleteMessage(String accountId, List<String> msgIdList)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("msgIdList", msgIdList);
		param.put("accountId", accountId);
		return sqlSessionTemplate.delete("com.youxue.core.dao.MessageDao.deleteMessage", param);
	}
}
