package com.ehaoyao.cfy.mapper.operationcenter;

import com.ehaoyao.cfy.model.operationcenter.TbPartner;

public interface TbPartnerMapper {
    int deleteByPrimaryKey(Long tbPartnerId);

    int insert(TbPartner record);

    int insertSelective(TbPartner record);

    TbPartner selectByPrimaryKey(Long tbPartnerId);

    int updateByPrimaryKeySelective(TbPartner record);

    int updateByPrimaryKey(TbPartner record);
}