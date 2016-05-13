package com.ehaoyao.logistics.common.mapper.ordercenter;

import java.util.List;
import java.util.Map;

import com.ehaoyao.logistics.common.model.ordercenter.ExpressInfoRemove;
import com.ehaoyao.logistics.common.vo.OrderExpressVo;

/**
 * 订单中心-订单拆单信息表数据持久化接口
 * @date 2016-05-05
 */
public interface ExpressInfoRemoveMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressInfoRemove record);

    int insertSelective(ExpressInfoRemove record);

    ExpressInfoRemove selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressInfoRemove record);

    int updateByPrimaryKey(ExpressInfoRemove record);

    /**
	 * 根据条件获取拆单订单的物流信息集合
	 * @param map	条件集合 	
	 * @return
	 * @throws Exception
	 */
	List<OrderExpressVo> selectHasShipByCondition(Map<String, Object> map) throws Exception;
}