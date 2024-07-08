package com.kundan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kundan.binding.LoginForm;
import com.kundan.binding.SinUpForm;
import com.kundan.binding.UnlockForm;
import com.kundan.constant.AppConstant;
import com.kundan.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/sign")
	public String signUpPage(Model model) {
		model.addAttribute("user" , new SinUpForm());
		return AppConstant.STR_SIGNUP;
	}

	@PostMapping("/sign")
	public String handalerSignUp( @ModelAttribute("user") SinUpForm form,Model model) throws Exception {
		boolean status = userService.signUp(form);
		if (status) {
			model.addAttribute(AppConstant.STR_SUCCMSG, "Account is Created ! Plz Check Your Email");

		} else {
			model.addAttribute(AppConstant.STR_ERRMSG, "Chose Unique Email");

		}

		return AppConstant.STR_SIGNUP;
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return AppConstant.STR_LOGIN;
	}
	
	
	@PostMapping("/login")
	public String login( @ModelAttribute("loginForm") LoginForm form,Model model) {
		
		String user = userService.login(form);
		
		if(user.contains(AppConstant.STR_SUCCESS)) {
			return"redirect:/dashboard";
		}
		model.addAttribute(AppConstant.STR_ERRMSG, user);
		
		return AppConstant.STR_LOGIN;
	}

	

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email,Model model) {
		UnlockForm form=new UnlockForm();
		form.setEmail(email);
		model.addAttribute("unlock",form);
		
		return AppConstant.STR_UNLOCKSTR;
	}
	
	
	
	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm form,Model model) {
		System.err.println(form);
		if(form.getNewPwd().equals(form.getConfPwd())) {
			boolean unlock = userService.unlock(form);
			if(unlock) {
				model.addAttribute(AppConstant.STR_SUCCMSG,"Your Account Unlocked Successfilly!");
			}else {
				model.addAttribute(AppConstant.STR_ERRMSG,"Given Temprory Pwd is incorrect, Plz check your Email");
				
			}
			
		}else {
			model.addAttribute(AppConstant.STR_ERRMSG,"New Pdw and Confirm Pwd should be Match!");
		}
		
		
		return AppConstant.STR_UNLOCKSTR;
	}

	@GetMapping("/forget")
	public String forgotPwdPage() {
		return AppConstant.STR_FORGOTPSSW;
	}
	
	@PostMapping("/forget")
	public String forgotPwd(@RequestParam("email") String email,Model model) {
		boolean status = userService.forgotPwd(email);
		System.err .println(email);
		if(status) {
			//send successful Msg
			model.addAttribute(AppConstant.STR_SUCCMSG, "Pwd send to Youe Email!");
		}else {
			//send error msg
			model.addAttribute(AppConstant.STR_ERRMSG,"Invalid Email!");
		}
		
		return AppConstant.STR_FORGOTPSSW;
	}

}
