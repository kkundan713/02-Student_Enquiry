package com.kundan.service;

import com.kundan.binding.LoginForm;
import com.kundan.binding.SinUpForm;
import com.kundan.binding.UnlockForm;

public interface UserService {
	
public boolean signUp(SinUpForm sinUpForm) throws Exception;

public boolean unlock(UnlockForm form);

public String login(LoginForm form);

public boolean forgotPwd(String email);

	

}
