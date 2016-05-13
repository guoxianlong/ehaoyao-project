package com.ehaoyao.logistics.common.mapper.logisticscenter;

import java.util.List;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillDetail;

/**
 * 运单明细表数据库持久化工具类
 *
 */
public interface WayBillDetailMapper {
	/**
	 * 根据主键删除运单明细数据
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条运单明细数据
     * @param record
     * @return
     */
    int insert(WayBillDetail record);

    /**
     * 选择性插入一条运单明细数据
     * @param record
     * @return
     */
    int insertSelective(WayBillDetail record);

    /**
     * 根据主键查询运单数据
     * @param id
     * @return
     */
    WayBillDetail selectByPrimaryKey(Long id);

    /**
     * 更新运单明细
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WayBillDetail record);

    /**
     * 可选择行更新运单明细
     * @param record
     * @return
     */
    int updateByPrimaryKey(WayBillDetail record);
    
    /**
     * 批量保存运单明细信息
     * @param wayBillDetailList
     * @return
     */
    int insertWayBillDetailBatch(List<WayBillDetail> wayBillDetailList);
}