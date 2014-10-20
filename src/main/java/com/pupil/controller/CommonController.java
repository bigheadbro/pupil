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
import com.pupil.entity.UserinfoEntity;
import com.pupil.form.LoginForm;
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
	public ModelAndView log(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result)
	{
		ModelAndView mv = new ModelAndView("/evaluation/log");
		Account accnt = (Account) WebUtils.getSessionAttribute(request, "account");
		if(accnt != null && accnt.isLogin())
		{
			if(accnt.getState() == 0)
			{
				return new ModelAndView(new RedirectView("/introducer1")); 
			}
			else
			{
				if(accnt.getState() <= 3)
				{
					return new ModelAndView(new RedirectView("/q" + String.valueOf(accnt.getState() + 1) + "t")); 
				}
				else
				{
					return new ModelAndView(new RedirectView("/q" + String.valueOf(accnt.getState() + 1))); 
				}
			}
		}
		if(isDoSubmit(request))
		{
			Result re = commonService.checkLogin(form, result);
			
			if(!result.hasErrors())
			{
				Account account = new Account();
				UserEntity user = (UserEntity)re.get("user");
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getLoginName());
				account.setSchool(user.getSchoolName());
				account.setUserId(user.getUserId());
				request.getSession().setAttribute("account", account);
				UserinfoEntity info = commonService.getUserinfo(user.getUserId());
				if(info == null)
				{
					commonService.insertUserinfo(user);
				}
				else
				{
					account.setState(info.getState());
					if(account.getState() == 0)
					{
						return new ModelAndView(new RedirectView("/introducer1")); 
					}
					else
					{
						if(account.getState() <= 2)
						{
							return new ModelAndView(new RedirectView("/q" + String.valueOf(account.getState() + 1) + "t")); 
						}
						else
						{
							return new ModelAndView(new RedirectView("/q" + String.valueOf(account.getState() + 1))); 
						}
					}
				}
				// 登陆成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/introducer1"));
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/introducer1")
	public ModelAndView introducer1(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/introducer1");

		return mv;
	}
	
	@RequestMapping(value="/introducer2")
	public ModelAndView introducer2(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/introducer2");

		return mv;
	}
	
	@RequestMapping(value="/introducer3")
	public ModelAndView introducer3(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/introducer3");

		return mv;
	}
	
	@RequestMapping(value="/introducer4")
	public ModelAndView introducer4(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/introducer4");

		return mv;
	}
	
	@RequestMapping(value="/introducer")
	public ModelAndView introducer(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/introducer");

		return mv;
	}
	
	@RequestMapping(value="/q1t")
	public ModelAndView q1t(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q1t");
		return mv;
	}
	
	@RequestMapping(value="/q1")
	public ModelAndView q1(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q1");
		form.setQuestionid(1);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				
				return new ModelAndView(new RedirectView("/q2t"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q2t")
	public ModelAndView q2t(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q2t");
		return mv;
	}
	
	@RequestMapping(value="/q2")
	public ModelAndView q2(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q2");
		form.setQuestionid(2);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q3t"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q3t")
	public ModelAndView q3t(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q3t");
		return mv;
	}
	
	@RequestMapping(value="/q3")
	public ModelAndView q3(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q3");
		form.setQuestionid(3);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/introducer2"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q4t")
	public ModelAndView q4t(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		return new ModelAndView(new RedirectView("/q4"));
	}
	
	@RequestMapping(value="/q4")
	public ModelAndView q4(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q4");
		form.setQuestionid(4);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q5"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q5t")
	public ModelAndView q5t(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		return new ModelAndView(new RedirectView("/q5"));
	}
	
	@RequestMapping(value="/q5")
	public ModelAndView q5(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q5");
		form.setQuestionid(5);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q6"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q6t")
	public ModelAndView q6t(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		return new ModelAndView(new RedirectView("/q6"));
	}
	
	@RequestMapping(value="/q6")
	public ModelAndView q6(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q6");
		form.setQuestionid(6);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/introducer3"));
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q8"));
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q9"));
			else
				return mv;
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/introducer4"));
			else
				return mv;
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q11"));
			else
				return mv;
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q12"));
			else
				return mv;
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q13"));
			else
				return mv;
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q14"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	//情景慧动
	@RequestMapping(value="/q14")
	public ModelAndView q14(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q14");
		form.setQuestionid(14);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q15"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}

	@RequestMapping(value="/q15")
	public ModelAndView q15(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q15");
		form.setQuestionid(15);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q16"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q16")
	public ModelAndView q16(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q16");
		form.setQuestionid(16);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q17"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	@RequestMapping(value="/q17")
	public ModelAndView q17(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q17");
		form.setQuestionid(17);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q18"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q18")
	public ModelAndView q18(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q18");
		form.setQuestionid(18);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q19"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q19")
	public ModelAndView q19(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q19");
		form.setQuestionid(19);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q20"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q20")
	public ModelAndView q20(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q20");
		form.setQuestionid(20);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q21"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q21")
	public ModelAndView q21(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q21");
		form.setQuestionid(21);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q22"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q22")
	public ModelAndView q22(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q22");
		form.setQuestionid(22);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q23"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q23")
	public ModelAndView q23(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q23");
		form.setQuestionid(23);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q24"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q24")
	public ModelAndView q24(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q24");
		form.setQuestionid(24);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q25"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q25")
	public ModelAndView q25(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q25");
		form.setQuestionid(25);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q26"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q26")
	public ModelAndView q26(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q26");
		form.setQuestionid(26);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q27"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q27")
	public ModelAndView q27(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q27");
		form.setQuestionid(27);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q28"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	//乐群
	@RequestMapping(value="/q28")
	public ModelAndView q28(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q28");
		form.setQuestionid(28);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q29"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q29")
	public ModelAndView q29(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q29");
		form.setQuestionid(29);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q30"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q30")
	public ModelAndView q30(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q30");
		form.setQuestionid(30);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q31"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q31")
	public ModelAndView q31(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q31");
		form.setQuestionid(31);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q32"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q32")
	public ModelAndView q32(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q32");
		form.setQuestionid(32);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q33"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q33")
	public ModelAndView q33(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q33");
		form.setQuestionid(33);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q34"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q34")
	public ModelAndView q34(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q34");
		form.setQuestionid(34);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q35"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q35")
	public ModelAndView q35(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q35");
		form.setQuestionid(35);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q36"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q36")
	public ModelAndView q36(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q36");
		form.setQuestionid(36);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q37"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q37")
	public ModelAndView q37(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q37");
		form.setQuestionid(37);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("/q38"));
			else
				return mv;
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
