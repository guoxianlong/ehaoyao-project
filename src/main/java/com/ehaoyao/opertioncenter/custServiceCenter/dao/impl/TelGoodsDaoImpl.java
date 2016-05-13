package com.ehaoyao.opertioncenter.custServiceCenter.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.custServiceCenter.dao.TelGoodsDao;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Product;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ProductVO;
import com.haoyao.goods.dao.impl.BaseDaoImpl;

@Repository
public class TelGoodsDaoImpl extends BaseDaoImpl<Product, String> implements TelGoodsDao {

	/**
	 * 电销商品查询
	 * @param firstResult
	 * @param pageSize
	 * @param pvo
	 * @return
	 */
	@Override
	public List<Product> queryProductList(int firstResult, Integer pageSize, ProductVO pvo) {
		String hql = "from "+Product.class.getName() + " where 1=1 ";
		final Map<String,Object> paramMap = new HashMap<String,Object>();
		hql += getWhereSql(pvo,paramMap);
		List<Product> ls = queryByHQL(hql.toString() , paramMap, firstResult, pageSize);
		return ls;
	}
	
	//电销商品总数
	@Override
	public int queryProductCount(ProductVO pvo) {
		String hql = "select count(*) from "+Product.class.getName()+ " where 1=1 ";
		final Map<String,Object> paramMap = new HashMap<String,Object>();
		hql += getWhereSql(pvo,paramMap);
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
	//获取查询条件
	private String getWhereSql(ProductVO pvo,Map<String,Object> paramMap){
		StringBuffer hql = new StringBuffer() ;
		if(pvo!=null){
			//主推状态
			if(pvo.getStatus()!=null && pvo.getStatus().trim().length()>0){
				hql.append(" and status = :status");
				paramMap.put("status", pvo.getStatus().trim());
			}
			//主商品名称
			if(pvo.getProductName()!=null && pvo.getProductName().trim().length()>0){
				hql.append(" and productName like :productName ");
				paramMap.put("productName", "%"+pvo.getProductName().trim()+"%");
			}
			//主商品编码
			if(pvo.getProductCode()!=null && pvo.getProductCode().trim().length()>0){
				hql.append(" and productCode = :productCode ");
				paramMap.put("productCode", pvo.getProductCode().trim());
			}
			//副本名称
			if(pvo.getSecondName()!=null && pvo.getSecondName().trim().length()>0){
				hql.append(" and secondName like :secondName ");
				paramMap.put("secondName", "%"+pvo.getSecondName().trim()+"%");
			}
			//处方类型
			if(pvo.getIsCf()!=null && pvo.getIsCf().trim().length()>0){
				hql.append(" and isCf = :isCf");
				paramMap.put("isCf", pvo.getIsCf().trim());
			}
		}
		return hql.toString();
	}

	//删除商品
	@Override
	public void delGoods(List<String> idList) {
		String hql = "delete from "+Product.class.getName()+" where secondID in (:idList)";
		Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		q.setParameterList("idList", idList);
		q.executeUpdate();
	}
	
	//设置主推状态
	@Override
	public void updateStatus(List<String> secondIDList, String status) {
		String hql = "update "+Product.class.getName()+" set status=:status where secondID in (:idList)";
		Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		q.setParameter("status",status);
		q.setParameterList("idList", secondIDList);
		q.executeUpdate();
	}
	
}
