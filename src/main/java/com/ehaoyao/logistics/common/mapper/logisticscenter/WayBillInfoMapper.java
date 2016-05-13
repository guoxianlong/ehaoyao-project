package com.ehaoyao.logistics.common.mapper.logisticscenter;

import java.util.List;
import java.util.Map;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;

/**
 * 运单主表数据库持久化工具类
 *
 */
public interface WayBillInfoMapper {
	/**
	 * 根据主键删除运单
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条运单信息
     * @param record
     * @return
     */
    int insert(WayBillInfo record);

    /**
     * 可选择性插入一条运单数据
     * @param record
     * @return
     */
    int insertSelective(WayBillInfo record);

    /**
     * 根据主键查询运单
     * @param id
     * @return
     */
    WayBillInfo selectByPrimaryKey(Long id);

    /**
     * 根据主键可选择性更新运单
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WayBillInfo record);

    /**
     * 根据主键更新运单
     * @param record
     * @return
     */
    int updateByPrimaryKey(WayBillInfo record);    
    /**
     * 批量保存运单信息
     * @param wayBillInfoList
     * @return
     */
    int insertWayBillInfoBatch(List<WayBillInfo> wayBillInfoList);
    /**
     * 
    * @Description:根据开始时间、结束时间、运单状态集合 查询运单号集合
    * @param @param billInfoVo
    * @param @return
    * @return List<String>
    * @throws
     */
	List<String> queryWayBillNumbers(WayBillInfoVo billInfoVo);
	/**
	 * 
	* @Description:根据运单号获取 运单Info
	* @param @param waybillNumber
	* @param @return
	* @return WayBillInfo
	* @throws
	 */
	WayBillInfo selectByWayBillNumber (String waybillNumber);
   
    /**
   	 * 根据条件获取物流信息集合
   	 * @param map	条件集合 	
   	 * @return
   	 * @throws Exception
   	 */
    List<WayBillInfo> selectWayBillInfoListByCondition(Map<String, Object> map) throws Exception;
    /**
     * 
    * @Description:批量更新运单Info
    * @param @param wayBillInfoList
    * @param @return
    * @return int
    * @throws
     */
    int updateWayBillInfoBatch (List<WayBillInfo> wayBillInfoList);
    /**
   	 * 根据查询条件获取WayBill集合
   	 * @param wayBillInfoVo
   	 * @return
   	 * @throws Exception
   	 */
    List<WayBillInfo> selectWayBillInfoList (WayBillInfoVo wayBillInfoVo);
}