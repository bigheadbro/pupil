package com.pupil.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping(value="/test")
	public ModelAndView test(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q10");

		return mv;
	}
	
	@RequestMapping(value = "/logoff")
	public ModelAndView logoff(final HttpServletRequest request,final HttpServletResponse response, Model model) {
		request.getSession().invalidate();
		model.asMap().remove("account");
		return new ModelAndView(new RedirectView("index"));
		
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
				return new ModelAndView(new RedirectView("introducer")); 
			}
			else
			{
				if(accnt.getState() <= 3)
				{
					return new ModelAndView(new RedirectView("q" + String.valueOf(accnt.getState() + 1) + "t")); 
				}
				else if(accnt.getState() == 12)
				{
					return new ModelAndView(new RedirectView("q14")); 
				}
				else if(accnt.getState() == 18)
				{
					return new ModelAndView(new RedirectView("q21")); 
				}
				else if(accnt.getState() == 35)
				{
					return new ModelAndView(new RedirectView("q37")); 
				}
				else if(accnt.getState() == 52)
				{
					return new ModelAndView(new RedirectView("q56")); 
				}
				else
				{
					if(accnt.getState() == 85)
					{
						return new ModelAndView(new RedirectView("end"));
					}
					return new ModelAndView(new RedirectView("q" + String.valueOf(accnt.getState() + 1))); 
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
				account.setUserName(user.getUserName());
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
						return new ModelAndView(new RedirectView("introducer")); 
					}
					else
					{
						if(account.getState() <= 2)
						{
							return new ModelAndView(new RedirectView("q" + String.valueOf(account.getState() + 1) + "t")); 
						}
						else if(account.getState() == 12)
						{
							return new ModelAndView(new RedirectView("q14")); 
						}
						else if(account.getState() == 18)
						{
							return new ModelAndView(new RedirectView("q21")); 
						}
						else if(account.getState() == 35)
						{
							return new ModelAndView(new RedirectView("q37")); 
						}
						else if(account.getState() == 52)
						{
							return new ModelAndView(new RedirectView("q56")); 
						}
						else
						{
							if(account.getState() == 85)
							{
								return new ModelAndView(new RedirectView("end"));
							}
							return new ModelAndView(new RedirectView("q" + String.valueOf(account.getState() + 1))); 
						}
					}
				}
				// 登陆成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("introducer"));
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
	
	@RequestMapping(value="/introducer5")
	public ModelAndView introducer5(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/introducer5");

		return mv;
	}
	
	@RequestMapping(value="/introducer")
	public ModelAndView introducer(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/introducer");

		return mv;
	}
	
	@RequestMapping(value="/ending1")
	public ModelAndView ending1(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/ending1");

		return mv;
	}

	@RequestMapping(value="/ending2")
	public ModelAndView ending2(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/ending2");

		return mv;
	}
	
	@RequestMapping(value="/ending3")
	public ModelAndView ending3(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/ending3");

		return mv;
	}
	
	@RequestMapping(value="/ending4")
	public ModelAndView ending4(final HttpServletRequest request,final HttpServletResponse response)
	{ 
		ModelAndView mv = new ModelAndView("/evaluation/ending4");

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
				
				return new ModelAndView(new RedirectView("q2t"));
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
				return new ModelAndView(new RedirectView("q3t"));
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
				return new ModelAndView(new RedirectView("introducer2"));
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
		return new ModelAndView(new RedirectView("q4"));
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
				return new ModelAndView(new RedirectView("q5"));
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
		return new ModelAndView(new RedirectView("q5"));
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
				return new ModelAndView(new RedirectView("q6"));
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
		return new ModelAndView(new RedirectView("q6"));
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
				return new ModelAndView(new RedirectView("introducer3"));
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
				return new ModelAndView(new RedirectView("q8"));
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
				return new ModelAndView(new RedirectView("q9"));
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
				return new ModelAndView(new RedirectView("introducer4"));
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
				return new ModelAndView(new RedirectView("q11"));
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
				return new ModelAndView(new RedirectView("q12"));
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
				return new ModelAndView(new RedirectView("q14"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	/*@RequestMapping(value="/q13")
	public ModelAndView q13(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q13");
		form.setQuestionid(13);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("ending1"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}*/
	
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
				return new ModelAndView(new RedirectView("q15"));
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
				return new ModelAndView(new RedirectView("q16"));
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
				return new ModelAndView(new RedirectView("q17"));
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
				return new ModelAndView(new RedirectView("q18"));
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
				return new ModelAndView(new RedirectView("q21"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	/*@RequestMapping(value="/q19")
	public ModelAndView q19(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q19");
		form.setQuestionid(19);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q20"));
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
				return new ModelAndView(new RedirectView("q21"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}*/
	
	@RequestMapping(value="/q21")
	public ModelAndView q21(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q21");
		form.setQuestionid(21);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q22"));
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
				return new ModelAndView(new RedirectView("q23"));
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
				return new ModelAndView(new RedirectView("q24"));
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
				return new ModelAndView(new RedirectView("q25"));
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
				return new ModelAndView(new RedirectView("q26"));
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
				return new ModelAndView(new RedirectView("ending2"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	//乐群
	@RequestMapping(value="/q27")
	public ModelAndView q27(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q27");
		form.setQuestionid(27);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q28"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	
	@RequestMapping(value="/q28")
	public ModelAndView q28(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q28");
		form.setQuestionid(28);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q29"));
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
				return new ModelAndView(new RedirectView("q30"));
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
				return new ModelAndView(new RedirectView("q31"));
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
				return new ModelAndView(new RedirectView("q32"));
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
				return new ModelAndView(new RedirectView("q33"));
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
				return new ModelAndView(new RedirectView("q34"));
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
				return new ModelAndView(new RedirectView("q35"));
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
				return new ModelAndView(new RedirectView("ending3"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	/*@RequestMapping(value="/q36")
	public ModelAndView q36(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q36");
		form.setQuestionid(36);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("ending3"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}*/
	
	//情景活学
	@RequestMapping(value="/q37")
	public ModelAndView q37(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q37");
		form.setQuestionid(37);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q38"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q38")
	public ModelAndView q38(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q38");
		form.setQuestionid(38);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q39"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q39")
	public ModelAndView q39(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q39");
		form.setQuestionid(39);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q40"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q40")
	public ModelAndView q40(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q40");
		form.setQuestionid(40);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q41"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q41")
	public ModelAndView q41(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q41");
		form.setQuestionid(41);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q42"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q42")
	public ModelAndView q42(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q42");
		form.setQuestionid(42);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q43"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q43")
	public ModelAndView q43(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q43");
		form.setQuestionid(43);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q44"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q44")
	public ModelAndView q44(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q44");
		form.setQuestionid(44);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q45"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q45")
	public ModelAndView q45(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q45");
		form.setQuestionid(45);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q46"));
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q47"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q47")
	public ModelAndView q47(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q47");
		form.setQuestionid(47);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q48"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q48")
	public ModelAndView q48(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q48");
		form.setQuestionid(48);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q49"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q49")
	public ModelAndView q49(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q49");
		form.setQuestionid(49);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q50"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q50")
	public ModelAndView q50(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q50");
		form.setQuestionid(50);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q51"));
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q52"));
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("ending4"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	/*@RequestMapping(value="/q53")
	public ModelAndView q53(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q53");
		form.setQuestionid(53);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q54"));
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q55"));
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
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("ending4"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}*/
	
	@RequestMapping(value="/q56")
	public ModelAndView q56(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q56");
		form.setQuestionid(56);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q57"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q57")
	public ModelAndView q57(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q57");
		form.setQuestionid(57);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q58"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q58")
	public ModelAndView q58(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q58");
		form.setQuestionid(58);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q59"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q59")
	public ModelAndView q59(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q59");
		form.setQuestionid(59);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q60"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q60")
	public ModelAndView q60(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q60");
		form.setQuestionid(60);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q61"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q61")
	public ModelAndView q61(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q61");
		form.setQuestionid(61);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q62"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q62")
	public ModelAndView q62(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q62");
		form.setQuestionid(62);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q63"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q63")
	public ModelAndView q63(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q63");
		form.setQuestionid(63);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q64"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q64")
	public ModelAndView q64(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q64");
		form.setQuestionid(64);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q65"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q65")
	public ModelAndView q65(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q65");
		form.setQuestionid(65);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q66"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q66")
	public ModelAndView q66(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q66");
		form.setQuestionid(66);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q67"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q67")
	public ModelAndView q67(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q67");
		form.setQuestionid(67);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q68"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q68")
	public ModelAndView q68(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q68");
		form.setQuestionid(68);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q69"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q69")
	public ModelAndView q69(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q69");
		form.setQuestionid(69);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q70"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q70")
	public ModelAndView q70(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q70");
		form.setQuestionid(70);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q71"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q71")
	public ModelAndView q71(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q71");
		form.setQuestionid(71);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q72"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q72")
	public ModelAndView q72(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q72");
		form.setQuestionid(72);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q73"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q73")
	public ModelAndView q73(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q73");
		form.setQuestionid(73);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q74"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q74")
	public ModelAndView q74(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q74");
		form.setQuestionid(74);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q75"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q75")
	public ModelAndView q75(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q75");
		form.setQuestionid(75);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q76"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q76")
	public ModelAndView q76(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q76");
		form.setQuestionid(76);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q77"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q77")
	public ModelAndView q77(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q77");
		form.setQuestionid(77);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q78"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q78")
	public ModelAndView q78(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q78");
		form.setQuestionid(78);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q79"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q79")
	public ModelAndView q79(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q79");
		form.setQuestionid(79);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q80"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q80")
	public ModelAndView q80(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q80");
		form.setQuestionid(80);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q81"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q81")
	public ModelAndView q81(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q81");
		form.setQuestionid(81);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q82"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q82")
	public ModelAndView q82(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q82");
		form.setQuestionid(82);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q83"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q83")
	public ModelAndView q83(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q83");
		form.setQuestionid(83);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q84"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q84")
	public ModelAndView q84(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q84");
		form.setQuestionid(84);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("q85"));
			else
				return mv;
		}
		else
		{
			return mv;
		}
	}
	
	@RequestMapping(value="/q85")
	public ModelAndView q85(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")QuestionForm form)
	{
		ModelAndView mv = new ModelAndView("/evaluation/q85");
		form.setQuestionid(85);
		form.setUserid(account.getUserId());
		if(isDoSubmit(request))
		{
			if(commonService.insertQuestion(form) > 0)
				return new ModelAndView(new RedirectView("end"));
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

		return mv;
	}
}
