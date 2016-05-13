package com.ehaoyao.opertioncenter.custServiceCenter.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.custServiceCenter.dao.TelGoodsDao;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Product;
import com.ehaoyao.opertioncenter.custServiceCenter.service.TelGoodsService;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ProductVO;
/**
 * 
 * Title: TelGoodsServiceImpl.java
 * 
 * Description: 电销商品
 * 
 * @author Administrator
 * @version 1.0
 * @created 2014年8月15日 下午4:22:01
 */
@Service
public class TelGoodsServiceImpl implements TelGoodsService {
	
	@Autowired
	private TelGoodsDao telGoodsDao;
	
	public void setTelGoodsDao(TelGoodsDao telGoodsDao) {
		this.telGoodsDao = telGoodsDao;
	}

	//查询电销商品
	@Override
	public List<Product> queryProductList(int firstResult, int pageSize, ProductVO pvo) {
		List<Product> pList = telGoodsDao.queryProductList(firstResult, pageSize, pvo);
		return pList;
	}
	//电销商品总数
	@Override
	public int queryProductCount(ProductVO pvo) {
		return telGoodsDao.queryProductCount(pvo);
	}

	//删除商品
	@Override
	public void delGoods(List<String> idList) {
		telGoodsDao.delGoods(idList);
	}

	//设置主推状态
	@Override
	public void updateStatus(List<String> secondIDList, String status) {
		telGoodsDao.updateStatus(secondIDList,status);
	}

	//保存商品信息，有则修改，无则添加
	@Override
	public void saveOrUpdateProduct(Product p) {
		if(p!=null){
			Product pro = telGoodsDao.get(p.getSecondID());
			if(pro!=null){//修改
				//忽略不需要修改的字段
				String[] ignor = {"status","createTime","createUser"};
				BeanUtils.copyProperties(p, pro, ignor);
				telGoodsDao.update(pro);
			}else{//添加商品
				// 0：电销，1：主推 ，默认0 电销
				p.setStatus("0");
				telGoodsDao.save(p);
			}
		}
	}
	
}
