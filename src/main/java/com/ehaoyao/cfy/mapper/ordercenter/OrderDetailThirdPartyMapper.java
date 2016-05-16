package com.ehaoyao.cfy.mapper.ordercenter;

import com.ehaoyao.cfy.model.ordercenter.OrderDetailThirdParty;

public interface OrderDetailThirdPartyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetailThirdParty record);

    int insertSelective(OrderDetailThirdParty record);

    OrderDetailThirdParty selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetailThirdParty record);

    int updateByPrimaryKey(OrderDetailThirdParty record);
}