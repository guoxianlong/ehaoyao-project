package com.ehaoyao.cfy.mapper.ordercenter;

import java.util.List;

import com.ehaoyao.cfy.model.ordercenter.OrderDetail;

public interface OrderDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
    
    /**
     * 批量保存订单明细信息
     * @param orderDetailList
     * @return
     */
    int insertOrderDetailBatch(List<OrderDetail> orderDetailList);
}