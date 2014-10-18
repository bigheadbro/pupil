package com.pupil.dao;

import com.pupil.entity.UserinfoEntity;

public interface UserinfoDAO {
	/**
	 * @param id
	 * @return
	 */
	UserinfoEntity queryUserinfoEntityByUserid(int id);
	
	int insertUserinfo(UserinfoEntity info);
}
