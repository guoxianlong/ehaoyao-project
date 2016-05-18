package com.ehaoyao.cfy.mapper.ordercenter;

import java.util.List;

import com.ehaoyao.cfy.model.ordercenter.OrderInfo;
import com.ehaoyao.cfy.vo.operationcenter.OrderInfoVo;

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

    /**
     * 根据条件查询订单
     * @param vo
     * @return
     */
	OrderInfo selectByCondition(OrderInfoVo vo);
}