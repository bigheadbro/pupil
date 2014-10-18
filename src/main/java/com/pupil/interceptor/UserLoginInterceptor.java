package com.pupil.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.pupil.common.Account;
import com.pupil.entity.UserEntity;
import com.pupil.service.CommonService;
import com.pupil.util.JsonUtil;
import com.pupil.util.StringUtil;
import com.pupil.util.Util;

public class UserLoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {	
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account != null)
		{
			return true;
		}
		else
		{
			throw new ModelAndViewDefiningException(new ModelAndView(new RedirectView("/index")));
		}
	}
}
