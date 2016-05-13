package com.ehaoyao.yhdjkg.dao.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.ehaoyao.yhdjkg.domain.Page;
import com.ehaoyao.yhdjkg.utils.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: sjfeng
 * Date: 13-9-9
 * Time: 下午9:15
 * To change this template use File | Settings | File Templates.
 */
public class JdbcBaseDao{

	@Resource(name="jdbcTemplate")
	protected JdbcTemplate orderJdbcTemplate;
    @Resource(name = "transactionTemplate")
    protected TransactionTemplate orderTransactionTemplate;
    

    /**
     * 数据分页查询
     *
     * @param queryString
     *            :SQL
     * @param startIndex
     *            ,起始索引
     * @param pageSize
     *            ,分页大小
     * @return
     */
    private String getPageSQL(String queryString,Integer startIndex, Integer pageSize) {
        String pageSQL = "";
        pageSQL = this.getMySQLPageSQL(queryString, startIndex, pageSize);
        return pageSQL;
    }

    /**
     * 数据查询
     *
     * @param queryString
     * @param countQueryString
     * @param startIndex
     * @param pageSize
     * @param values
     * @return
     */
    public Page<Map<String, Object>> getPageBySQL(String queryString, String countQueryString,
                                                  Integer startIndex, Integer pageSize, Object... values) {
        if (StringUtils.isEmpty(countQueryString)) {
            countQueryString = this.getCountQuerySQL(queryString);
        }
        String pageQueryString = queryString;
        if (null != startIndex && null != pageSize) {
            pageQueryString = this
                    .getPageSQL(queryString, startIndex, pageSize);
        }
        Integer count = this.getCount(countQueryString, values);
        List<Map<String, Object>> items;
        if(values==null){
            items= orderJdbcTemplate.query(pageQueryString, new ColumnMapRowMapper());
        }else{
            items= orderJdbcTemplate.query(pageQueryString, values,new ColumnMapRowMapper());
        }
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize,count, startIndex);
        page.setList(items);
        return page;
    }

    /**
     * 多参数数据查询
     *
     * @param queryString
     * @param startIndex
     * @param pageSize
     * @param values
     * @return
     */
    public Page<Map<String, Object>> getPageBySQL(String queryString, Integer startIndex,
                                                  Integer pageSize, Object... values) {
        return this.getPageBySQL(queryString, null, startIndex, pageSize,
                values);
    }

    /**
     * @Title: getCount
     * @Description: 总条数
     * @param @param queryString
     * @param @param values
     * @param @return
     * @return Integer
     */
    @SuppressWarnings("deprecation")
	private Integer getCount(String queryString, Object... values) {
        Integer count;
        if(values==null){
            count= orderJdbcTemplate.queryForInt(queryString);
        }else{
            count = orderJdbcTemplate.queryForInt(queryString, values);
        }
        return count;
    }

    /**
     * 构造数据总数查询 SQL
     *
     * @param queryString
     * @return
     */
    private String getCountQuerySQL(String queryString) {
        String sql = "";
        if (StringUtils.isNotEmpty(queryString)) {
            sql = "select count(*) from (" + queryString + ") xCount";
        }
        return sql;
    }

    /**
     * 构造MySQL数据分页SQL
     *
     * @param queryString
     * @param startIndex
     * @param pageSize
     * @return
     */
    private String getMySQLPageSQL(String queryString, Integer startIndex,
                                   Integer pageSize) {
        String result = "";
        if (null != startIndex && null != pageSize) {
            result = queryString + " limit " + startIndex + "," + pageSize;
        } else if (null != startIndex && null == pageSize) {
            result = queryString + " limit " + startIndex;
        } else {
            result = queryString;
        }
        return result;
    }
}