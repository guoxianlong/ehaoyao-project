package com.ehaoyao.cfy.mapper.ordercenter;

import java.util.List;

import com.ehaoyao.cfy.model.ordercenter.OrderInfo;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
    
    /**
     * 批量保存订单信息
     * @param orderInfoList
     * @return
     */
    int insertOrderInfoBatch(List<OrderInfo> orderInfoList);
}