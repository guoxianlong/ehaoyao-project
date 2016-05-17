package com.ehaoyao.cfy.mapper.operationcenter;

import com.ehaoyao.cfy.model.operationcenter.OrderDetail;

public interface OperationOrderDetailMapper {
    int deleteByPrimaryKey(Long orderDetailId);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Long orderDetailId);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}