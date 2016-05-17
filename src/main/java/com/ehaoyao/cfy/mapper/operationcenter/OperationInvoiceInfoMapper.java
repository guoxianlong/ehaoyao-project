package com.ehaoyao.cfy.mapper.operationcenter;

import com.ehaoyao.cfy.model.operationcenter.InvoiceInfo;

public interface OperationInvoiceInfoMapper {
    int deleteByPrimaryKey(Long invoiceId);

    int insert(InvoiceInfo record);

    int insertSelective(InvoiceInfo record);

    InvoiceInfo selectByPrimaryKey(Long invoiceId);

    int updateByPrimaryKeySelective(InvoiceInfo record);

    int updateByPrimaryKey(InvoiceInfo record);
}