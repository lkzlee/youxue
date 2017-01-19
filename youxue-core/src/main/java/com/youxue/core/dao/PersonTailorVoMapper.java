package com.youxue.core.dao;

import com.youxue.core.vo.PersonTailorVo;

public interface PersonTailorVoMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonTailorVo record);

    int insertSelective(PersonTailorVo record);

    PersonTailorVo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonTailorVo record);

    int updateByPrimaryKey(PersonTailorVo record);
}