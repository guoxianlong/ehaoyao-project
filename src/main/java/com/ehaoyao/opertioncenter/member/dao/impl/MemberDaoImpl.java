package com.ehaoyao.opertioncenter.member.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.member.dao.MemberDao;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.model.MemberTransit;
import com.haoyao.goods.dao.impl.BaseDaoImpl;
/**
 * @author Administrator
 * 会员信息
 */
@Repository
public class MemberDaoImpl extends BaseDaoImpl<Member, Long> implements MemberDao{
	
	/*@Resource(name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;*/

	/*@Resource(name="hibernateTemplate")
	HibernateTemplate hibernateTemplate;*/
	/**
	 * 会员查询
	 * @param bean
	 * @return
	 */
	public List<Member> queryMemberList(int firstResult, Integer pageSize, String hqlString) {
		return queryByHQL("from Member where 1 = 1 and trim(tel) <> ''" + hqlString , null, firstResult, pageSize);
	}
	/**
	 * 获得会员总条数
	 */
	@Override
	public int getMemberCount(String hqlString) {
		String hql = "select count(*) from "+Member.class.getName()+ " where 1=1 and trim(tel) <> '' ";
		final Map<String,Object> paramMap = new HashMap<String,Object>();
		hql += hqlString;
		final String strHql = hql;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(strHql);
				query.setProperties(paramMap);
				List list = query.list();
				return list;
			}
		});
		if(list!=null && list.size()>0){
			return Integer.valueOf(list.get(0).toString());
		}else{
			return 0;
		}
	}
	/**
	 * 按联系方式查询会员明细信息
	 * @param tel
	 * @return
	 */
	@Override
	public Member queryMemberByTel(final String tel) {
		return (Member) getHibernateTemplate().execute(new HibernateCallback<Member>() {
			public Member doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query  = session.createQuery("from Member where TRIM(tel) = ?").setString(0, tel);
				List<?> ls = query.list();
				if(ls!=null && ls.size()>0){
					return (Member)ls.get(0);
				}else{
					return null;
				}
			}
		});
	}
	
	/**
	 * 添加会员信息
	 */
	@Override
	public Object addMember(final Member member) {		
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				session.saveOrUpdate(member);
				session.flush();
				return null;
			}
		});
	}
	
	/**
	 * 修改会员信息
	 * @param Member
	 */
	@Override
	public int updateMember(final String hql) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.executeUpdate();
			}
		});
	}
	
	//修改会员信息
	@Override
	public void updateMember(Member member) {
		this.update(member);
	}
	/**
	 * 分配客服
	 */
	@Override
	public void updateMemberByTel(String tel,String userName) {
		String hql = "update "+Member.class.getName()+" set userName='" + userName+ "' where TRIM(tel)=:tel";
		Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		q.setParameter("tel", tel);
		q.executeUpdate();
	}
	/**
	 * 批量更新专属客服
	 */
	public void updateUserNameByTelBatch(List<MemberTransit> userList){
		Session session = null;  
		Transaction tx = null;
		long start = System.currentTimeMillis();
        if (userList != null && userList.size() > 0) {  
           try {  
               session = this.getHibernateTemplate().getSessionFactory().openSession();
               tx = session.beginTransaction();
               String sql ="truncate table member_transit";
               Query  query=session.createSQLQuery(sql);
               query.executeUpdate();
               session.setCacheMode(CacheMode.IGNORE);
               for(int j=0;j<=userList.size()-1;j++){
            	   try{
            		   session.save(userList.get(j));
            	   }catch(Exception e){
            		   e.printStackTrace();
            	   }
                   if (j % 2000 == 0
                           || userList.size() - 1 == j) {  
                       session.flush();  
                       session.clear(); 
                   }  
               }
               String hql = "update "+Member.class.getName()+" as m set userName=(select userName from " + MemberTransit.class.getName()+
               " mt where m.tel = mt.tel) where exists (select tel from "+MemberTransit.class.getName() +" mt where m.tel= mt.tel)";
  			   Query q = session.createQuery(hql);
  			   q.executeUpdate();
               long end = System.currentTimeMillis();
   			   System.out.println("更新会员专属客服所需时间" + (end - start));
   			   tx.commit(); // 提交事务 
           } catch (Exception e) {  
               e.printStackTrace();
               tx.rollback(); // 出错将回滚事务 
           } finally {  
               session.close(); //关闭Session  
           }  
       } 
	}
	/**
	 * 批量插入会员
	 */
	@Override
	public void addMemberBatch(List<Member> userList) {
		Session session = null;  
		Transaction tx = null;
		long start = System.currentTimeMillis();
        if (userList != null && userList.size() > 0) {  
           try {  
               session = this.getHibernateTemplate().getSessionFactory().openSession();
               tx = session.beginTransaction();
               session.setCacheMode(CacheMode.IGNORE);
               for(int j=0;j<=userList.size()-1;j++){
            	   try{
            		   session.save(userList.get(j));
            	   }catch(Exception e){
            		   e.printStackTrace();
            	   }
                   if (j % 2000 == 0
                           || userList.size() - 1 == j) {  
                       session.flush();  
                       session.clear(); 
                   }  
               }
               long end = System.currentTimeMillis();
   			   System.out.println("保存新会员所需时间" +(end - start));
   			   tx.commit(); // 提交事务 
           } catch (Exception e) {  
               e.printStackTrace();
               tx.rollback(); // 出错将回滚事务 
           } finally {  
               session.close(); // 关闭Session  
           }  
       } 
	}
}
