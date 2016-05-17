package com.ehaoyao.cfy.service;

import java.util.List;

import com.ehaoyao.cfy.vo.operationcenter.OrderInfoVo;

public interface OperationCenterService {

    public List selectAuditPassList(OrderInfoVo orderInfoVo) throws Exception;
}
