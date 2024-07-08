package com.kundan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kundan.EntityClass.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity,Integer>{
	
	public UserDtlsEntity findByEmail(String email);
	
	public UserDtlsEntity findByEmailAndUserPwd(String email,String pwd);

	
	

}
