package com.ehaoyao.cfy.mapper.operationcenter;

import com.ehaoyao.cfy.model.operationcenter.OrderDetailThirdParty;

public interface OperationOrderDetailThirdPartyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetailThirdParty record);

    int insertSelective(OrderDetailThirdParty record);

    OrderDetailThirdParty selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetailThirdParty record);

    int updateByPrimaryKey(OrderDetailThirdParty record);
}