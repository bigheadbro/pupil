package com.pupil.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.pupil.common.Result;
import com.pupil.dao.DimensionDAO;
import com.pupil.dao.QuestionDAO;
import com.pupil.dao.ScoreDAO;
import com.pupil.dao.UserDAO;
import com.pupil.dao.UserinfoDAO;
import com.pupil.entity.DimensionEntity;
import com.pupil.entity.QuestionEntity;
import com.pupil.entity.ScoreEntity;
import com.pupil.entity.UserEntity;
import com.pupil.entity.UserinfoEntity;
import com.pupil.form.LoginForm;
import com.pupil.form.QuestionForm;
import com.pupil.util.StringUtil;


/**
 * @author guichaoqun
 *
 */
@Service("commonService")
public class CommonService {
	@Autowired
	@Qualifier("dimensionDAO")
	private DimensionDAO dimensionDAO;

	@Autowired
	@Qualifier("questionDAO")
	private QuestionDAO questionDAO;
	
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("userinfoDAO")
	private UserinfoDAO userinfoDAO;
	
	@Autowired
	@Qualifier("scoreDAO")
	private ScoreDAO scoreDAO;
	
	public DimensionEntity getDim(int qid, int choice, int number)
	{
		DimensionEntity dim = new DimensionEntity();
		dim.setQid(qid);
		dim.setChoice(choice);
		dim.setNumber(number);
    	return dimensionDAO.queryDimensionByQid(dim);
	}
	
	public int insertQuestion(QuestionEntity ques)
	{
		return questionDAO.insertQuestionEntity(ques);
	}
	
	public int insertQuestion(QuestionForm form)
	{
		QuestionEntity ques = new QuestionEntity();
		ques.setQid(form.getQuestionid());
		ques.setUserId(form.getUserid());
		if(questionDAO.queryQuestion(ques) != null)
		{
			return 1;
		}
		ques.setChoice(form.getChoice());
		return questionDAO.insertQuestionEntity(ques);
	}
	
	public int insertQuestionList(List<QuestionEntity> list)
	{
		return questionDAO.insertQuestionList(list);
	}
	
	public UserEntity getUser(int id)
	{
		return userDAO.queryUserEntityById(id);
	}
	
	public UserEntity getUserByMail(String cn)
	{
		return userDAO.queryUserEntityByMail(cn);
	}
	
	public ScoreEntity getScore(int userid)
	{
		return scoreDAO.queryScoreByUserid(userid);
	}
	
	public Result checkLogin(LoginForm form, Errors errors)
	{
		Result result = new Result();
		if(StringUtil.isEmpty(form.getMail()))
		{
			errors.rejectValue("mail", "MAIL_IS_NOT_NULL"); // 邮箱不能为空
			return result;
		}
		
		
		UserEntity user = userDAO.queryUserEntityByMail(form.getMail());
		if(user == null)
		{
			errors.rejectValue("mail", "MAIL_IS_NOT_EXISTS"); // 邮箱不存在
			return result;
		}
		if(StringUtil.isNotEqual(user.getPassword(), form.getPassword()))
		{
			errors.rejectValue("password", "PASSWORD_ERROR"); // 密码错误
			return result;
		}
		result.add("user", user);
		result.add("userType", 1);
		return result;
	}

	public void insertUserinfo(UserEntity user)
	{
		UserinfoEntity info = new UserinfoEntity();
		info.setUserId(user.getUserId());
		info.setState(0);
		userinfoDAO.insertUserinfo(info);
	}
	
	public UserinfoEntity getUserinfo(int userid)
	{
		return userinfoDAO.queryUserinfoEntityByUserid(userid);
	}
}