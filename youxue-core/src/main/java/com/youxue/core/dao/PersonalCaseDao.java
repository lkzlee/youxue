package com.youxue.core.dao;

import com.youxue.core.vo.PersonalCaseVo;

public interface PersonalCaseDao {
    int deleteByPrimaryKey(String caseId);

    int insert(PersonalCaseVo record);

    int insertSelective(PersonalCaseVo record);

    PersonalCaseVo selectByPrimaryKey(String caseId);

    int updateByPrimaryKeySelective(PersonalCaseVo record);

    int updateByPrimaryKey(PersonalCaseVo record);
}