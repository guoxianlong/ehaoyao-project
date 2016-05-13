package com.ehaoyao.opertioncenter.doctorInHospital.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.doctorInHospital.dao.HosDoctorUrlDao;
import com.ehaoyao.opertioncenter.doctorInHospital.vo.HosDoctorUrlVO;
import com.haoyao.goods.dao.impl.BaseDaoImpl;

/**
 * 医院，医生，url关系查询
 * @author zhang
 *
 */
@Repository
public class HosDoctorUrlDaoImpl extends BaseDaoImpl<HosDoctorUrlVO, Integer> implements
		HosDoctorUrlDao {
	@Resource(name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	/** 
	 * 根据urlId查询医院和医生id
	 * @see com.ehaoyao.opertioncenter.doctorInHospital.dao.HosDoctorUrlDao#getHosDoctorUrlInfo(int)
	 */
	@Override
	public HosDoctorUrlVO getHosDoctorUrlId(int id) {
		String sql = "select d.id doctorId,sr.id salesRepId from doctor_url du left join doctor d on d.id = du.doctor_id left join sales_rep sr on sr.id = d.sales_rep_id where du.id="+id;
        List<HosDoctorUrlVO> ls = jdbcTemplate.query(sql,new BeanPropertyRowMapper<HosDoctorUrlVO>(HosDoctorUrlVO.class));
        HosDoctorUrlVO vo = null;
        if(ls != null && ls.size() > 0){
        	vo = ls.get(0);
        }
		return vo;
	}
}
