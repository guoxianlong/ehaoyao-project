package com.ehaoyao.cfy.mapper.operationcenter;

import com.ehaoyao.cfy.model.operationcenter.ParamConfig;

public interface ParamConfigMapper {
    int deleteByPrimaryKey(Long paramConfigId);

    int insert(ParamConfig record);

    int insertSelective(ParamConfig record);

    ParamConfig selectByPrimaryKey(Long paramConfigId);

    int updateByPrimaryKeySelective(ParamConfig record);

    int updateByPrimaryKey(ParamConfig record);
}