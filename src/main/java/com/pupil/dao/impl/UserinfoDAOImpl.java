package com.pupil.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.pupil.dao.UserinfoDAO;
import com.pupil.entity.UserinfoEntity;

@Repository("userinfoDAO")
public class UserinfoDAOImpl extends SqlSessionDaoSupport implements UserinfoDAO {

	@Override
	public UserinfoEntity queryUserinfoEntityByUserid(int id) {
		return this.getSqlSession().selectOne("queryUserinfoEntityByUserid", id);
	}
	
	@Override
	public int insertUserinfo(UserinfoEntity info) {
		return this.getSqlSession().insert("insertUserinfoEntity", info);
	}

}
