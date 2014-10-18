package com.pupil.dao;

import java.util.List;

import com.pupil.entity.QuestionEntity;

public interface QuestionDAO {
	/**
	 * 插入回答信息
	 * @param companyUser
	 * @return
	 */
	public int insertQuestionEntity(QuestionEntity question);
	
	//插入维度题
	public int insertQuestionList(List<QuestionEntity> list);
}
