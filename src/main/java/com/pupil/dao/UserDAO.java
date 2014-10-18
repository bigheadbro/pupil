package com.pupil.dao;

import com.pupil.entity.UserEntity;

public interface UserDAO {
	/**
	 * @param id
	 * @return
	 */
	UserEntity queryUserEntityById(int id);

	UserEntity queryUserEntityByMail(String cn);
}
