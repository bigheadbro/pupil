package com.pupil.dao;

import com.pupil.entity.ScoreEntity;

public interface ScoreDAO {
	/**
	 * 查询评分标准
	 * @param companyUser
	 * @return
	 */
	public ScoreEntity queryScoreByUserid(int userid);
}
