package com.ehaoyao.opertioncenter.member.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.member.dao.HealthKeywordDetailDao;
import com.ehaoyao.opertioncenter.member.model.HealthKeywordDetail;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.haoyao.goods.dao.impl.BaseDaoImpl;

/**
 * 健康关键字Dao实现类
 * @author kxr
 *
 */
@Repository
public class HealthKeywordDetailDaoImpl extends BaseDaoImpl<Member, Long> implements HealthKeywordDetailDao {

	/**
	 * 根据健康分类查询健康关键字
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HealthKeywordDetail> getHealthKeywordDetailByPid(final Integer pid) {
		return ((List<HealthKeywordDetail>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {
				Query query  = session.createQuery("from HealthKeywordDetail where parentsId = ?").setInteger(0, pid);
				return query.list();
			}
		}));
	}
	
	/**
	 * 根据ID查询健康关键字
	 * @param tel
	 * @return List<MemberHealthKeyword>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HealthKeywordDetail> getMemberHealthKeywordByTel(final String id) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<HealthKeywordDetail>>() {
			public List<HealthKeywordDetail> doInHibernate(final Session session) throws HibernateException,SQLException {
				String hql = "from HealthKeywordDetail h where h.id in (select pid from MemberHealthKeyword where mid = ?)";
				Query query = session.createQuery(hql);
				query.setString(0, id);
				return query.list();
			}
		});
	}

	/**
	 * 添加健康关键字
	 */
	@Override
	public Object saveHealthKeywordDetail(final HealthKeywordDetail healthKeywordDetail) {
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				session.save(healthKeywordDetail);
				session.flush();
				return null;
			}
		});
	}

	/**
	 * 修改健康关键字
	 */
	@Override
	public void updateHealthKeywordDetail(final HealthKeywordDetail healthKeywordDetail) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.update(healthKeywordDetail);
				session.flush();
				return null;
			}
		});
	}

	/**
	 * 删除健康关键字
	 */
	@Override
	public void delHealthKeywordDetail(final HealthKeywordDetail healthKeywordDetail) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.delete(healthKeywordDetail);
				session.flush();
				return null;
			}
		});
	}

}
