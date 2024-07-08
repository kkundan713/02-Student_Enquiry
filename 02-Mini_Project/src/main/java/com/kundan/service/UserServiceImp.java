package com.kundan.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kundan.EntityClass.UserDtlsEntity;
import com.kundan.Repository.UserDtlsRepo;
import com.kundan.binding.LoginForm;
import com.kundan.binding.SinUpForm;
import com.kundan.binding.UnlockForm;
import com.kundan.constant.AppConstant;
import com.kundan.utility.EmailUtils;
import com.kundan.utility.PwdUtlis;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserDtlsRepo repo;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private HttpSession session;
	
	

	@Override
	public boolean signUp(SinUpForm sinUpForm) throws Exception {
		
		UserDtlsEntity user = repo.findByEmail(sinUpForm.getEmail());
		if(user!=null) {
			return false;
		}
		
		// TODO: copy data binding object to entity
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(sinUpForm, entity);

		// TODO: generate random password and set to object
		String password = PwdUtlis.generateRandomPsw();
		entity.setUserPwd(password);

		// TODO: set account status is LOCKED

		entity.setUserACCStatus(AppConstant.STR_LOCKED);
		
		// TODO :insert record into the table
		    repo.save(entity);
		    
		// TODO:send email to unlock the account
		    
		String to = sinUpForm.getEmail();
		String subject = AppConstant.STR_UNLOCK_EMAIL_SUBJECT;

		StringBuilder body = new StringBuilder("");
		body.append(AppConstant.STR_EMAIL_HEADING);
		body.append(AppConstant.STR_TEMP_PASSWORD + password);
		body.append(AppConstant.STR_LINE_BREAK);
		body.append(AppConstant.LOCAL_HOST_LINK+ to + AppConstant.STR_UNLOCK_ACCOUNT);
		
		emailUtils.sendEmail(to, subject, body.toString());
		
		return true;
	}





	@Override
	public boolean unlock(UnlockForm form) {
		UserDtlsEntity byEmail = repo.findByEmail(form.getEmail());
		if(byEmail.getUserPwd().equals(form.getTempPwd())) {
			byEmail.setUserPwd(form.getNewPwd());
			byEmail.setUserACCStatus(AppConstant.STR_UNLOCKED);
			repo.save(byEmail);
			return true;
		}
		
		return false;
	}





	@Override
	public String login(LoginForm form) {
		UserDtlsEntity entity = repo.findByEmailAndUserPwd(form.getEmail(), form.getPwd());
		if(entity==null)
		{
			return AppConstant.STR_INVALID_CRED;
		}
		if(entity.getUserACCStatus().equals(AppConstant.STR_LOCKED)) {
			return AppConstant.STR_ACC_LOCKED;
		}
		
		session.setAttribute(AppConstant.STR_USER_ID, entity.getUserId());
		
		return AppConstant.STR_SUCCESS;
	}





	@Override
	public boolean forgotPwd(String email) {
		//check record presence in db with given email
		
		UserDtlsEntity entity = repo.findByEmail(email);
		//if record is not available send error message
		if(entity==null) {
			return false;
		}
		//if record is available send pwd to email and send success message
		String subject=AppConstant.STR_RECOVER_EMAIL_SUBJECT;
		String body=AppConstant.STR_PASSWORD+entity.getUserPwd();
		emailUtils.sendEmail(email, subject, body);
		
		return true;
	}







}
