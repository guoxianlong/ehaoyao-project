/**
 * 
 */
package com.ehaoyao.opertioncenter.member.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.member.dao.MemberHealthKeywordDao;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.model.MemberHealthKeyword;
import com.haoyao.goods.dao.impl.BaseDaoImpl;

/**
 * 会员健康关键字关系表Dao实现类
 * @author kxr
 *
 */
@Repository
public class MemberHealthKeywordDaoImpl extends BaseDaoImpl<Member, Long> implements MemberHealthKeywordDao {

	/**
	 * 查询会员健康关键字关系表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberHealthKeyword> getMemberHealthKeyword() {
		return getHibernateTemplate().find("from MemberHealthKeyword");
	}
	
	/**List<MemberHealthKeyword> memberHealthList
	 * 添加会员健康关键字关系表
	 * @param memberHealth
	 * @return object
	 */
	@Override
	public void saveMemberHealthKeyword(final MemberHealthKeyword memberHealthList) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.save(memberHealthList);
				session.flush();
				return null;
			}
		});
	}
	
	/**
	 * 修改会员健康关键字关系表
	 * @param memberHealth
	 */
	@Override
	public void updateMemberHealthKeyword(MemberHealthKeyword memberHealth) {
		getHibernateTemplate().update(memberHealth);
	}

	/**
	 * 根据id删除会员健康关键字关系表
	 * @param memberHealth
	 */
	@Override
	public void delMemberHealthKeyword(final String tel) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "delete from MemberHealthKeyword where mid=?";
				Query query = session.createQuery(hql);
				query.setString(0, tel);
				query.executeUpdate();
				return null;
			}
		});
	}

}
