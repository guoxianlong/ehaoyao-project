package com.ehaoyao.cfy.mapper.ordercenter;

import com.ehaoyao.cfy.model.ordercenter.InvoiceInfo;

public interface InvoiceInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InvoiceInfo record);

    int insertSelective(InvoiceInfo record);

    InvoiceInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvoiceInfo record);

    int updateByPrimaryKey(InvoiceInfo record);
}