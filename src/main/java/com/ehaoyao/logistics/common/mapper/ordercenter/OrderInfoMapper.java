package com.ehaoyao.logistics.common.mapper.ordercenter;

import java.util.List;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.model.ordercenter.OrderInfo;

public interface OrderInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
    
    int writeBackUpdateOrderInfo(List<WayBillInfo> wayBillInfoList);
}