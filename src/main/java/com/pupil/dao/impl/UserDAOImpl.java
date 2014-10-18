package com.pupil.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.pupil.dao.UserDAO;
import com.pupil.entity.UserEntity;

@Repository("userDAO")
public class UserDAOImpl extends SqlSessionDaoSupport implements UserDAO {

	@Override
	public UserEntity queryUserEntityById(int id) {
		return this.getSqlSession().selectOne("queryUserEntityById", id);
	}
	
	@Override
	public UserEntity queryUserEntityByMail(String cn) {
		return this.getSqlSession().selectOne("queryUserEntityByMail", cn);
	}

}
