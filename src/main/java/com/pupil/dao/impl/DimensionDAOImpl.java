package com.pupil.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.pupil.dao.DimensionDAO;
import com.pupil.entity.DimensionEntity;

@Repository("dimensionDAO")
public class DimensionDAOImpl extends SqlSessionDaoSupport implements DimensionDAO {

	@Override
	public DimensionEntity queryDimensionByQid(DimensionEntity dim) {
		return this.getSqlSession().selectOne("queryDimensionByQid", dim);
	}

}
