package com.ehaoyao.cfy.mapper.operationcenter;

import com.ehaoyao.cfy.model.operationcenter.OrderAuditLog;

public interface OrderAuditLogMapper {
    int deleteByPrimaryKey(Long orderAuditLogId);

    int insert(OrderAuditLog record);

    int insertSelective(OrderAuditLog record);

    OrderAuditLog selectByPrimaryKey(Long orderAuditLogId);

    int updateByPrimaryKeySelective(OrderAuditLog record);

    int updateByPrimaryKey(OrderAuditLog record);
}