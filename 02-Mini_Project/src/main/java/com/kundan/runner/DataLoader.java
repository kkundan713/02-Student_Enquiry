package com.kundan.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.kundan.EntityClass.CourseEntity;
import com.kundan.EntityClass.EnqStatusEntity;
import com.kundan.Repository.CourseRepo;
import com.kundan.Repository.EnqStatusRepo;
import com.kundan.Repository.UserDtlsRepo;
@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	private  EnqStatusRepo dtlsRepo;
	
	@Autowired
	private CourseRepo courseRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		courseRepo.deleteAll();
		
		CourseEntity c1=new CourseEntity();
		c1.setCourseName("Java");
		
		
		CourseEntity c2=new CourseEntity();
		c2.setCourseName("Python");
		
		CourseEntity c3=new CourseEntity();
		c3.setCourseName("DevOps");
		
		CourseEntity c4=new CourseEntity();
		c4.setCourseName("AWS");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		dtlsRepo.deleteAll();
		
		EnqStatusEntity c5=new EnqStatusEntity();
		c5.setStatusName("New");
		
		EnqStatusEntity c6=new EnqStatusEntity();
		c6.setStatusName("Enrolled");
		
		EnqStatusEntity c7=new EnqStatusEntity();
		c7.setStatusName("Lost");
		
		dtlsRepo.saveAll(Arrays.asList(c5,c6,c7));
		
		
		
	}

}
