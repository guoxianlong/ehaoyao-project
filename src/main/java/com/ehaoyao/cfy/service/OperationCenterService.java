package com.ehaoyao.cfy.service;

import java.util.List;

import com.ehaoyao.cfy.vo.operationcenter.OrderInfoVo;
import com.ehaoyao.cfy.vo.operationcenter.OrderMainInfo;

public interface OperationCenterService {

    public List<OrderMainInfo> selectAuditPassList(OrderInfoVo orderInfoVo) throws Exception;
}
