package com.ehaoyao.opertioncenter.member.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.member.dao.TaskAllocateDao;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.vo.OrderHouseVO;
import com.haoyao.goods.dao.impl.BaseDaoImpl;
/**
 * @author Administrator
 * 会员信息
 */
@Repository
public class TaskAllocateDaoImpl extends BaseDaoImpl<Member, Long> implements TaskAllocateDao{
	@Resource(name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	/**
	 * 获得会员总条数
	 */
	@Override
	public int getOrderCount(OrderHouseVO vo) {
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select count(*) from fc_data_orderhouse_detail a " +
				"INNER JOIN ( SELECT max( id ) AS id FROM fc_data_orderhouse_detail a where 1=1 ";
		sql += getWhereSql(vo,paramList);
		sql += "GROUP BY tel ) b ON a.id = b.id where 1=1 and trim(a.tel)<>'' ";
		//paramList = new ArrayList<Object>();
	    sql += getWhereSql(vo,paramList);
		Integer count = jdbcTemplate.queryForObject(sql, paramList.toArray(),
				Integer.class);
		return count;
	}
	/**
	 * 会员查询
	 * @param bean
	 * @return
	 */
	public List<OrderHouseVO> getOrderList(int firstResult, Integer pageSize,OrderHouseVO vo) {		
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select a.tel,a.name,a.province,a.city,a.country,a.order_flag,a.one_level,a.two_level,a.three_level,a.good_name,m.user_name,m.tel isExist from fc_data_orderhouse_detail a " +
				"INNER JOIN ( SELECT max( id ) AS id FROM fc_data_orderhouse_detail a where 1=1 ";
		sql += getWhereSql(vo,paramList);
		sql += "GROUP BY tel ) b ON a.id = b.id LEFT JOIN member m on m.tel = a.tel where 1=1 and trim(a.tel)<>''";
		//paramList = new ArrayList<Object>();
	    sql += getWhereSql(vo,paramList);
	    sql += " limit "+firstResult+","+pageSize;
	    List<OrderHouseVO> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<OrderHouseVO>(OrderHouseVO.class));
		return ls;
		//return queryByHQL("from Member where 1 = 1" + hqlString , null, firstResult, pageSize);
	}
	
	//获取查询条件
	private String getWhereSql(OrderHouseVO vo,List<Object> paramList){
		StringBuffer wsql = new StringBuffer();
		if(vo!=null){
			if(vo.getStartDate()!=null && vo.getStartDate().trim().length()>0){
				wsql.append(" and a.date >= ? ");
				paramList.add(vo.getStartDate().trim());
			}
			if(vo.getEndDate()!=null && vo.getEndDate().trim().length()>0){
				wsql.append(" and a.date <= ? ");
				paramList.add(vo.getEndDate().trim());
			}
			if(vo.getOneLevel()!=null && vo.getOneLevel().trim().length()>0){
				wsql.append(" and a.one_level = ? ");
				paramList.add(vo.getOneLevel().trim());
			}
			if(vo.getTwoLevel()!=null && vo.getTwoLevel().trim().length()>0){
				wsql.append(" and a.two_level = ? ");
				paramList.add(vo.getTwoLevel().trim());
			}
			if(vo.getThreeLevel()!=null && vo.getThreeLevel().trim().length()>0){
				wsql.append(" and a.three_level = ? ");
				paramList.add(vo.getThreeLevel().trim());
			}
			if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
				wsql.append(" and a.order_flag = ? ");
				paramList.add(vo.getOrderFlag().trim());
			}
			if(vo.getGoodName()!=null && vo.getGoodName().trim().length()>0){
				wsql.append(" and a.good_name like ?");
				paramList.add(vo.getGoodName().trim() + "%");
			}
		}
		return wsql.toString();
	}
	
	public void addBatchScyh(List<Member> userList){
		/*Session session = null;  
		Transaction tx = null;
		long start = System.currentTimeMillis();
        if (userList != null && userList.size() > 0) {  
           try {  
               session = this.getHibernateTemplate().getSessionFactory().openSession();
               tx = session.beginTransaction();
               String cus = null; 
               String tel = null;
               session.setCacheMode(CacheMode.IGNORE);
               for(int j=0;j<=userList.size()-1;j++){
                   tel = userList.get(j).getTel();
                   cus = userList.get(j).getUserName();
       			   updateMemberByTel(tel, cus);
                   if (userList.size() % 500 == 0
                           || userList.size() - 1 == j) {  
                       session.flush();  
                       session.clear(); 
                   }  
               }
               long end = System.currentTimeMillis();
   			   System.out.println(end - start);
   			   tx.commit(); // 提交事务 
           } catch (Exception e) {  
               e.printStackTrace();
               tx.rollback(); // 出错将回滚事务 
           } finally {  
               session.close(); // 关闭Session  
           }  
       } */
	}
	/**
	 * 分配任务
	 */
	//@Override
	/*public void updateTaskByTel(String tel,String userName) {
		String hql = "update "+Member.class.getName()+" set userName='" + userName+ "' where TRIM(tel)=:tel";
		Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		q.setParameter("tel", tel);
		q.executeUpdate();
	}*/
	
	
	/*public void addBatchScyh(List<Member> userList){
		final List<Member> yhList = userList;
		System.out.println(userList.size());
		long start = System.currentTimeMillis();
		try{
			getConnection().setAutoCommit(false);
			if(yhList != null && yhList.size() > 0){
				super.getJdbcTemplate().batchUpdate("update member set user_name=? where tel=?", new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Member member = yhList.get(i);
						ps.setString(1, member.getUserName().trim());
						ps.setString(2, member.getTel().trim());
						//每2000条进行事物提交
				        if (i%500 == 0) {
				        	ps.executeBatch(); //执行prepareStatement对象中所有的sql语句
				        }
					}
					@Override
					public int getBatchSize() {
						return yhList.size();
					}
				});
			}
			//手动提交
			getConnection().commit();
			//getConnection().setAutoCommit(true);
			long end = System.currentTimeMillis();
			System.out.println(end - start);
		}catch(Exception e){
			try {
				getConnection().rollback();
			} catch (CannotGetJdbcConnectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}*/
}
