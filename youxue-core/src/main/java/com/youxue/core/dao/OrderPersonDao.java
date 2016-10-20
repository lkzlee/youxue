package com.youxue.core.dao;

import com.youxue.core.vo.OrderPersonVo;

public interface OrderPersonDao {
    int insert(OrderPersonVo record);

    int insertSelective(OrderPersonVo record);
}