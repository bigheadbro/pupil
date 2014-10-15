package com.pupil.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.pupil.common.Account;
import com.pupil.common.Result;
import com.pupil.entity.DimensionEntity;
import com.pupil.entity.QuestionEntity;
import com.pupil.entity.ScoreEntity;
import com.pupil.entity.UserEntity;
import com.pupil.form.QuestionForm;
import com.pupil.form.UserForm;
import com.pupil.service.CommonService;
import com.pupil.util.Util;

@Controller
@RequestMapping("/")
@SessionAttributes({"account"})
public class CommonController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(CommonController.class);
	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;
	
	@RequestMapping(value="/")
	public ModelAndView main(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/evaluation/index");

		return mv;
	}
	
	@RequestMapping(value="/index")
	public ModelAndView index(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/evaluation/index");

		return mv;
	}
	
	@RequestMapping(value="/log")
	public ModelAndView log(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/evaluation/log");
		Account accnt = (Account) WebUtils.getSessionAttribute(request, "account");
		if(accnt != null && accnt.isLogin())
		{
			return new ModelAndView(new RedirectView("/user/main")); 
		}
		if(isDoSubmit(request))
		{
		
		}
		else
		{
			
		}
		return mv;
	}
	
	@RequestMapping(value="/profile")
	public ModelAndView profile(final HttpServletRequest request,final HttpServletResponse response, 
			@ModelAttribute("account")Account account, @ModelAttribute("form")UserForm form, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ModelAndView("/evaluation/profile");
		}
		ModelAndView mv = new ModelAndView("/evaluation/profile");
		if(isDoSubmit(request))
		{
			UserEntity user = form.getUser();
			account.setGender(form.getUser().getGender());
			user.setCardno(account.getCardno());
			user.setName(account.getUserName());
			user.setSchool(account.getSchool());
			user.setState(0);
			commonService.insertUser(user);
			account.setUserId(user.getId());
			return new ModelAndView(new RedirectView("/evaluation/introducer"));
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/introducer")
	public ModelAndView introducer(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/introducer");

		return mv;
	}
	
	@RequestMapping(value="/q1")
	public ModelAndView q1(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q1");
		form.setQuestionid(1);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(1, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q2"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q2")
	public ModelAndView q2(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q2");
		form.setQuestionid(2);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(2, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q3"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q3")
	public ModelAndView q3(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q3");
		form.setQuestionid(3);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(3, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q4"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q4")
	public ModelAndView q4(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q4");
		form.setQuestionid(4);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(4, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q5"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q5")
	public ModelAndView q5(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q5");
		form.setQuestionid(5);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(5, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q6"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q6")
	public ModelAndView q6(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q6");
		form.setQuestionid(6);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(6, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q7"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q7")
	public ModelAndView q7(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q7");
		form.setQuestionid(7);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(7, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q8"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}

	@RequestMapping(value="/q8")
	public ModelAndView q8(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ModelAndView("/evaluation/q8");
		}
		ModelAndView mv = new ModelAndView("/evaluation/q8");
		
		form.setQuestionid(8);
		if(isDoSubmit(request))
		{
			DimensionEntity dim1 = commonService.getDim(8, 0, 1);
			DimensionEntity dim2 = commonService.getDim(8, 0, 2);
			DimensionEntity dim3 = commonService.getDim(8, 0, 3);
			DimensionEntity dim4 = commonService.getDim(8, 0, 4);
			List<QuestionEntity> list = Util.calcDimensionScore(dim1,dim2,dim3,dim4, account.getUserId(), form);
			commonService.insertQuestionList(list);
			return new ModelAndView(new RedirectView("/evaluation/q9"));
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q9")
	public ModelAndView q9(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ModelAndView("/evaluation/q9");
		}
		ModelAndView mv = new ModelAndView("/evaluation/q9");
		form.setQuestionid(9);
		if(isDoSubmit(request))
		{
			DimensionEntity dim1 = commonService.getDim(9, 0, 1);
			DimensionEntity dim2 = commonService.getDim(9, 0, 2);
			DimensionEntity dim3 = commonService.getDim(9, 0, 3);
			DimensionEntity dim4 = commonService.getDim(9, 0, 4);
			List<QuestionEntity> list = Util.calcDimensionScore(dim1,dim2,dim3,dim4, account.getUserId(), form);
			commonService.insertQuestionList(list);
			return new ModelAndView(new RedirectView("/evaluation/q10"));
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q10")
	public ModelAndView q10(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ModelAndView("/evaluation/q10");
		}
		ModelAndView mv = new ModelAndView("/evaluation/q10");
		form.setQuestionid(10);
		if(isDoSubmit(request))
		{
			DimensionEntity dim1 = commonService.getDim(10, 0, 1);
			DimensionEntity dim2 = commonService.getDim(10, 0, 2);
			DimensionEntity dim3 = commonService.getDim(10, 0, 3);
			DimensionEntity dim4 = commonService.getDim(10, 0, 4);
			List<QuestionEntity> list = Util.calcDimensionScore(dim1,dim2,dim3,dim4, account.getUserId(), form);
			commonService.insertQuestionList(list);
			return new ModelAndView(new RedirectView("/evaluation/q11"));
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q11")
	public ModelAndView q11(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ModelAndView("/evaluation/q11");
		}
		ModelAndView mv = new ModelAndView("/evaluation/q11");
		form.setQuestionid(11);
		if(isDoSubmit(request))
		{
			DimensionEntity dim1 = commonService.getDim(11, 0, 1);
			DimensionEntity dim2 = commonService.getDim(11, 0, 2);
			DimensionEntity dim3 = commonService.getDim(11, 0, 3);
			DimensionEntity dim4 = commonService.getDim(11, 0, 4);
			List<QuestionEntity> list = Util.calcDimensionScore(dim1,dim2,dim3,dim4, account.getUserId(), form);
			commonService.insertQuestionList(list);
			return new ModelAndView(new RedirectView("/evaluation/q12"));
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q12")
	public ModelAndView q12(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ModelAndView("/evaluation/q12");
		}
		ModelAndView mv = new ModelAndView("/evaluation/q12");
		form.setQuestionid(12);
		if(isDoSubmit(request))
		{
			DimensionEntity dim1 = commonService.getDim(12, 0, 1);
			DimensionEntity dim2 = commonService.getDim(12, 0, 2);
			DimensionEntity dim3 = commonService.getDim(12, 0, 3);
			DimensionEntity dim4 = commonService.getDim(12, 0, 4);
			List<QuestionEntity> list = Util.calcDimensionScore(dim1,dim2,dim3,dim4, account.getUserId(), form);
			commonService.insertQuestionList(list);
			return new ModelAndView(new RedirectView("/evaluation/q13"));
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q13")
	public ModelAndView q13(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q13");
		form.setQuestionid(13);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(13, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q46"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q46")
	public ModelAndView q46(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q46");
		form.setQuestionid(46);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(46, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcIntelligenceScore(dim, account.getUserId(), 46, form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q51"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q51")
	public ModelAndView q51(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q51");
		form.setQuestionid(51);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(51, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcIntelligenceScore(dim, account.getUserId(), 51, form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q52"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}

	@RequestMapping(value="/q52")
	public ModelAndView q52(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q52");
		form.setQuestionid(52);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(52, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcIntelligenceScore(dim, account.getUserId(), 52, form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q53"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q53")
	public ModelAndView q53(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q53");
		form.setQuestionid(53);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(53, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcIntelligenceScore(dim, account.getUserId(), 53, form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q54"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q54")
	public ModelAndView q54(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q54");
		form.setQuestionid(54);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(54, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcIntelligenceScore(dim, account.getUserId(), 54, form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q55"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q55")
	public ModelAndView q55(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q55");
		form.setQuestionid(55);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(55, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcIntelligenceScore(dim, account.getUserId(), 55, form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q56"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q56")
	public ModelAndView q56(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q56");
		form.setQuestionid(56);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(56, form.getChoice(), form.getNumber());
			QuestionEntity ques = Util.calcIntelligenceScore(dim, account.getUserId(), 56, form.getChoice(), form.getTime());
			if(commonService.insertQuestion(ques) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q14"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	
	@RequestMapping(value="/q14")
	public ModelAndView q14(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14");
		form.setQuestionid(14);
		if(isDoSubmit(request))
		{
			account.setQ14time(form.getTime());
			if(form.getChoice() == 0)
			{
				UserEntity user = new UserEntity();
				user.setId(account.getUserId());
				user.setState(141);
				commonService.updateState(user);
				return new ModelAndView(new RedirectView("/evaluation/q14a"));
			}
			else if(form.getChoice() == 1)
			{
				UserEntity user = new UserEntity();
				user.setId(account.getUserId());
				user.setState(142);
				commonService.updateState(user);
				return new ModelAndView(new RedirectView("/evaluation/q14b"));
			}
			else if(form.getChoice() == 2)
			{
				UserEntity user = new UserEntity();
				user.setId(account.getUserId());
				user.setState(143);
				commonService.updateState(user);
				return new ModelAndView(new RedirectView("/evaluation/q14c"));
			}
			else
			{
				DimensionEntity dim = commonService.getDim(14, 1, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 1, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q12"));
				else
					return mv;
			}
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q14a")
	public ModelAndView q14a(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14a");
		form.setQuestionid(14);
		if(isDoSubmit(request))
		{
			account.setQ14time(account.getQ14time() + form.getTime());
			if(form.getChoice() == 0)
			{
				UserEntity user = new UserEntity();
				user.setId(account.getUserId());
				user.setState(1411);
				commonService.updateState(user);
				return new ModelAndView(new RedirectView("/evaluation/q14a1"));
			}
			else if(form.getChoice() == 1)
			{
				UserEntity user = new UserEntity();
				user.setId(account.getUserId());
				user.setState(1412);
				commonService.updateState(user);
				return new ModelAndView(new RedirectView("/evaluation/q14a2"));
			}
			else if(form.getChoice() == 2)
			{
				DimensionEntity dim = commonService.getDim(14, 2, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 2, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q14c"));
				else
					return mv;
			}
			else
			{
				DimensionEntity dim = commonService.getDim(14, 3, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 3, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q14a1")
	public ModelAndView q14a1(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14a1");
		form.setQuestionid(14);
		if(isDoSubmit(request))
		{
			account.setQ14time(account.getQ14time() + form.getTime());
			if(form.getChoice() == 0)
			{
				DimensionEntity dim = commonService.getDim(14, 4, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 4, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else if(form.getChoice() == 1)
			{
				DimensionEntity dim = commonService.getDim(14, 5, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 5, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else
			{
				DimensionEntity dim = commonService.getDim(14, 6, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 6, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q14a2")
	public ModelAndView q14a2(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14a2");
		form.setQuestionid(14);
		if(isDoSubmit(request))
		{
			account.setQ14time(account.getQ14time() + form.getTime());
			if(form.getChoice() == 0)
			{
				UserEntity user = new UserEntity();
				user.setId(account.getUserId());
				user.setState(14121);
				commonService.updateState(user);
				return new ModelAndView(new RedirectView("/evaluation/q14a21"));
			}
			else if(form.getChoice() == 1)
			{
				DimensionEntity dim = commonService.getDim(14, 7, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 7, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else
			{
				DimensionEntity dim = commonService.getDim(14, 8, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 8, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q14a21")
	public ModelAndView q14a21(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14a21");
		form.setQuestionid(14);
		if(isDoSubmit(request))
		{
			account.setQ14time(account.getQ14time() + form.getTime());
			if(form.getChoice() == 0)
			{
				DimensionEntity dim = commonService.getDim(14, 9, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 9, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else if(form.getChoice() == 1)
			{
				DimensionEntity dim = commonService.getDim(14, 10, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 10, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else
			{
				DimensionEntity dim = commonService.getDim(14, 11, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 11, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q14b")
	public ModelAndView q14b(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14b");
		form.setQuestionid(14);
		if(isDoSubmit(request))
		{
			account.setQ14time(account.getQ14time() + form.getTime());
			if(form.getChoice() == 0)
			{
				DimensionEntity dim = commonService.getDim(14, 12, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 12, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else if(form.getChoice() == 1)
			{
				DimensionEntity dim = commonService.getDim(14, 13, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 13, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else
			{
				DimensionEntity dim = commonService.getDim(14, 14, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 14, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q14c")
	public ModelAndView q14c(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14c");
		form.setQuestionid(14);
		if(isDoSubmit(request))
		{
			account.setQ14time(account.getQ14time() + form.getTime());
			if(form.getChoice() == 0)
			{
				UserEntity user = new UserEntity();
				user.setId(account.getUserId());
				user.setState(1431);
				commonService.updateState(user);
				return new ModelAndView(new RedirectView("/evaluation/q14c1"));
			}
			else if(form.getChoice() == 1)
			{
				UserEntity user = new UserEntity();
				user.setId(account.getUserId());
				user.setState(1432);
				commonService.updateState(user);
				return new ModelAndView(new RedirectView("/evaluation/q14c2"));
			}
			else if(form.getChoice() == 2)
			{
				DimensionEntity dim = commonService.getDim(14, 15, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 15, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else
			{
				DimensionEntity dim = commonService.getDim(14, 16, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 16, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q14c1")
	public ModelAndView q14c1(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14c1");
		form.setQuestionid(14);
		if(isDoSubmit(request))
		{
			account.setQ14time(account.getQ14time() + form.getTime());
			if(form.getChoice() == 0)
			{
				DimensionEntity dim = commonService.getDim(14, 17, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 17, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else if(form.getChoice() == 1)
			{
				DimensionEntity dim = commonService.getDim(14, 18, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 18, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else
			{
				DimensionEntity dim = commonService.getDim(14, 19, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 19, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q14c2")
	public ModelAndView q14c2(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14c2");
		form.setQuestionid(14);
		if(isDoSubmit(request))
		{
			account.setQ14time(account.getQ14time() + form.getTime());
			if(form.getChoice() == 0)
			{
				UserEntity user = new UserEntity();
				user.setId(account.getUserId());
				user.setState(14321);
				commonService.updateState(user);
				return new ModelAndView(new RedirectView("/evaluation/q14c21"));
			}
			else if(form.getChoice() == 1)
			{
				DimensionEntity dim = commonService.getDim(14, 20, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 20, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else
			{
				DimensionEntity dim = commonService.getDim(14, 21, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 21, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q14c21")
	public ModelAndView q14c21(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14c21");
		form.setQuestionid(14);
		if(isDoSubmit(request))
		{
			account.setQ14time(account.getQ14time() + form.getTime());
			if(form.getChoice() == 0)
			{
				DimensionEntity dim = commonService.getDim(14, 22, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 22, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else if(form.getChoice() == 1)
			{
				DimensionEntity dim = commonService.getDim(14, 23, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 23, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
			else
			{
				DimensionEntity dim = commonService.getDim(14, 24, 0);
				QuestionEntity ques = Util.calcSingleScore(dim, account.getUserId(), 24, account.getQ14time());
				if(commonService.insertQuestion(ques) > 0)
					return new ModelAndView(new RedirectView("/evaluation/q15"));
				else
					return mv;
			}
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q15")
	public ModelAndView q15(final HttpServletRequest request,final HttpServletResponse response,@ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q15");
		form.setQuestionid(15);
		if(isDoSubmit(request))
		{
			if(form.getChoice() == 4)//选D
			{
				DimensionEntity dim = commonService.getDim(15, 4, 1);
				DimensionEntity dim2 = new DimensionEntity();
				DimensionEntity dim3 = new DimensionEntity();
				List<QuestionEntity> ques = Util.calcProcessScore(dim, dim2, dim3, account.getUserId(), form);
				commonService.insertQuestionList(ques);
				return new ModelAndView(new RedirectView("/evaluation/q16"));
			}
			if(form.getChoice2() == 0 || form.getChoice3() == 0 || form.getChoice4() == 0)
			{
				return new ModelAndView("/evaluation/q15");
			}
			DimensionEntity dim = commonService.getDim(15, form.getChoice(), 1);
			DimensionEntity dim2 = commonService.getDim(15, 1, 2);
			DimensionEntity dim3 = commonService.getDim(15, 2, 2);
			List<QuestionEntity> ques = Util.calcProcessScore(dim, dim2, dim3, account.getUserId(), form);
			commonService.insertQuestionList(ques);
			return new ModelAndView(new RedirectView("/evaluation/q16"));
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q16")
	public ModelAndView q16(final HttpServletRequest request,final HttpServletResponse response,@ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q16");
		form.setQuestionid(16);
		if(isDoSubmit(request))
		{
			DimensionEntity dim = commonService.getDim(16, form.getChoice(), 1);
			QuestionEntity q = Util.calcQ16MainScore(dim, account.getUserId(), form);
			if(commonService.insertQuestion(q) > 0)
				return new ModelAndView(new RedirectView("/evaluation/q162"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q162")
	public ModelAndView q162(final HttpServletRequest request,final HttpServletResponse response,@ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ModelAndView("/evaluation/q162");
		}
		ModelAndView mv = new ModelAndView("/evaluation/q162");
		form.setQuestionid(16);
		if(isDoSubmit(request))
		{
			DimensionEntity dim2 = commonService.getDim(16, form.getChoice2(), 2);
			DimensionEntity dim3 = commonService.getDim(16, form.getChoice3(), 3);
			DimensionEntity dim4 = commonService.getDim(16, form.getChoice4(), 4);
			List<QuestionEntity> list = Util.calcQ16Score(dim2, dim3, dim4, account.getUserId(), form);
			commonService.insertQuestionList(list);
			UserEntity user = new UserEntity();
			user.setId(account.getUserId());
			user.setState(162);
			commonService.updateState(user);
			return new ModelAndView(new RedirectView("/evaluation/end"));
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/end")
	public ModelAndView end(final HttpServletRequest request,final HttpServletResponse response,@ModelAttribute("account")Account account)
	{
		
		ModelAndView mv = new ModelAndView("/evaluation/end");
		ScoreEntity score = commonService.getScore(account.getUserId());
		mv.addObject("score",score);
		return mv;
	}
}
