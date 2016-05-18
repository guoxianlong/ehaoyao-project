package com.ehaoyao.cfy.mapper.ordercenter;

import java.util.List;

import com.ehaoyao.cfy.model.ordercenter.InvoiceInfo;

public interface InvoiceInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InvoiceInfo record);

    int insertSelective(InvoiceInfo record);

    InvoiceInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvoiceInfo record);

    int updateByPrimaryKey(InvoiceInfo record);
    
    /**
     * 批量保存订单发票信息
     * @param invoiceInfoList
     * @return
     */
    int insertInvoiceInfoBatch(List<InvoiceInfo> invoiceInfoList);
}