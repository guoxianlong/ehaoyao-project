package com.ehaoyao.logistics.common.mapper.ordercenter;

import com.ehaoyao.logistics.common.model.ordercenter.OrderInfo;

public interface OrderInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}