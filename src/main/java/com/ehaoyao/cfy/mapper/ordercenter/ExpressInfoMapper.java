package com.ehaoyao.cfy.mapper.ordercenter;

import com.ehaoyao.cfy.model.ordercenter.ExpressInfo;

public interface ExpressInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressInfo record);

    int insertSelective(ExpressInfo record);

    ExpressInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressInfo record);

    int updateByPrimaryKey(ExpressInfo record);
}