package com.ehaoyao.opertioncenter.member.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.member.dao.HealthKeywordHeadDao;
import com.ehaoyao.opertioncenter.member.model.HealthKeywordHead;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.haoyao.goods.dao.impl.BaseDaoImpl;

/**
 * 健康分类Dao实现类
 * @author kxr
 *
 */
@Repository
public class HealthKeywordHeadDaoImpl extends BaseDaoImpl<Member, Long> implements HealthKeywordHeadDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<HealthKeywordHead> getHealthKeywordHead() {
		return getHibernateTemplate().find("from HealthKeywordHead");
	}

	/**
	 * 添加健康分类
	 */
	@Override
	public Object saveHealthKeywordHead(final HealthKeywordHead health) {
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				session.save(health);
				session.flush();
				return null;
			}
		});
	}

	/**
	 * 修改健康分类
	 */
	@Override
	public void updateHealthKeywordHead(final HealthKeywordHead health) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.update(health);
				session.flush();
				return null;
			}
		});
	}

	/**
	 * 删除健康分类
	 */
	@Override
	public void delhealthKeywordHead(final HealthKeywordHead healthKeywordHead) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,SQLException {
				session.delete(healthKeywordHead);
				session.flush();
				return null;
			}
		});
	}

}
