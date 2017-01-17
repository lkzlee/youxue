package com.youxue.core.dao;

import com.youxue.core.vo.AboutUsVo;

public interface AboutUsDao {
    int insert(AboutUsVo record);

    int insertSelective(AboutUsVo record);
}