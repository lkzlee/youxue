package com.youxue.core.dao;

import com.youxue.core.vo.MessageVo;

public interface MessageDao {
    int deleteByPrimaryKey(String messageId);

    int insert(MessageVo record);

    int insertSelective(MessageVo record);

    MessageVo selectByPrimaryKey(String messageId);

    int updateByPrimaryKeySelective(MessageVo record);

    int updateByPrimaryKey(MessageVo record);
}