package com.ehaoyao.cfy.mapper.operationcenter;

import java.util.List;
import java.util.Map;

import com.ehaoyao.cfy.model.operationcenter.OrderAuditLog;
import com.ehaoyao.cfy.vo.operationcenter.OrderInfoVo;
import com.ehaoyao.cfy.vo.operationcenter.OrderMainInfo;

public interface OrderAuditLogMapper {
	/**
	 * 根据主键删除实体
	 * @param orderAuditLogId
	 * @return
	 */
    int deleteByPrimaryKey(Long orderAuditLogId);

    /**
     * 插入实体对象数据
     * @param record
     * @return
     */
    int insert(OrderAuditLog record);

    /**
     * 选择性插入实体对象数据
     * @param record
     * @return
     */
    int insertSelective(OrderAuditLog record);

    /**
     * 根据主键查询实体对象
     * @param orderAuditLogId
     * @return
     */
    OrderAuditLog selectByPrimaryKey(Long orderAuditLogId);
    
    /**
     * 根据条件查询订单信息
     * @param param
     * @return 返回数据集合
     */
    List<OrderAuditLog> selectByConditionMap(Map<String, Object> param);
    
    /**
     * 查询最新审核数据
     * @param vo
     * @return
     */
    OrderAuditLog selectByLastAudit(OrderInfoVo vo);
    
    /**
     * 根据条件分页查询订单信息
     * @param orderInfoVo 查询实体
     * @return 返回数据集合
     */
	List<OrderMainInfo> selectAllFieldsByConditionMapPage(OrderInfoVo orderInfoVo);
	
	/**
	 * 返回一笔订单最新审核所有数据
	 * @param orderInfoVo
	 * @return
	 */
	List<OrderMainInfo> selectOrderMainInfo(OrderInfoVo orderInfoVo);

    /**
     * 根据主键选择性更新实体
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(OrderAuditLog record);

    /**
     * 根据主键更新实体
     * @param record
     * @return
     */
    int updateByPrimaryKey(OrderAuditLog record);
}