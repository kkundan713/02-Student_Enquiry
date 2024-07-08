package com.kundan.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.kundan.EntityClass.CourseEntity;
import com.kundan.EntityClass.EnqStatusEntity;
import com.kundan.EntityClass.StudentEnqEntity;
import com.kundan.EntityClass.UserDtlsEntity;
import com.kundan.Repository.CourseRepo;
import com.kundan.Repository.EnqStatusRepo;
import com.kundan.Repository.StudentEnqRepo;
import com.kundan.Repository.UserDtlsRepo;
import com.kundan.binding.DashboardResponse;
import com.kundan.binding.EnquiryForm;
import com.kundan.binding.EnquirySearchCriteria;
import com.kundan.constant.AppConstant;

import jakarta.servlet.http.HttpSession;
@Service
public class EnquiryServiceImp implements EnquiryService {
	

	@Autowired
	private  UserDtlsRepo dtlsRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo enqStatusRepo;
	
	@Autowired
	private StudentEnqRepo studentEnqRepo;
	
	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		
		DashboardResponse response=new DashboardResponse();
		
		 Optional<UserDtlsEntity> byId = dtlsRepo.findById(userId);
		
		if(byId.isPresent()) {
			UserDtlsEntity userDtlsEntity = byId.get();
			List<StudentEnqEntity> enquries = userDtlsEntity.getEnquries();
			
			int totalCnt = enquries.size();
			
			int totalEnrolledCnt = enquries.stream().filter(e->e.getEnqStatus().equals(AppConstant.UNROLLED))
					.collect(Collectors.toList()).size();
			int totalLostCnt = enquries.stream().filter(e->e.getEnqStatus().equals(AppConstant.LOST)).
					collect(Collectors.toList()).size();
			
			response.setEnrolledCnt(totalEnrolledCnt);
			response.setLostCnt(totalLostCnt);
			response.setTotalEnquriesCnt( totalCnt);
		}

		return response;
	
	   }
	
	
	

	@Override
	public List<String> getCourses() {
		 List<CourseEntity> all = courseRepo.findAll();
		 List<String> names=new ArrayList<>();
		 for(CourseEntity entity:all) {
			 names.add(entity.getCourseName());
		 }
		return names;
	}

	@Override
	public List<String> getEnqStatuses() {
		List<EnqStatusEntity> all = enqStatusRepo.findAll();
		List<String> status=new ArrayList<>();
		for(EnqStatusEntity entity:all) {
			status.add(entity.getStatusName());
		}
		return status;
	}

	@Override
	public boolean saveEnquriry(EnquiryForm form) {
	StudentEnqEntity enq=new StudentEnqEntity();
	BeanUtils.copyProperties(form,enq);
	
	Integer userId = (Integer) session.getAttribute(AppConstant.STR_USER_ID);
	          Optional<UserDtlsEntity> userDtlsEntity = dtlsRepo.findById(userId);
	          
	          if(userDtlsEntity.isPresent()) {
	        	  UserDtlsEntity user = userDtlsEntity.get();
	        	  enq.setUser(user);
	          }

	          
	studentEnqRepo.save(enq);
		return true;
	}




	@Override
	public List<StudentEnqEntity> getEnquiries() {
		 Integer userId = (Integer) session.getAttribute(AppConstant.STR_USER_ID);
		 Optional<UserDtlsEntity> byId = dtlsRepo.findById(userId);
		 if(byId.isPresent()) {
			 UserDtlsEntity userDtlsEntity = byId.get();
			 List<StudentEnqEntity> enquries = userDtlsEntity.getEnquries();
			 return enquries;
		 }
		return Collections.emptyList();
	}




	@Override
	public List<StudentEnqEntity> getFilterEnquiries(EnquirySearchCriteria criteria, Integer userId) {
		Optional<UserDtlsEntity> byId = dtlsRepo.findById(userId);
		 if(byId.isPresent()) {
			 UserDtlsEntity userDtlsEntity = byId.get();
			 List<StudentEnqEntity> enquries = userDtlsEntity.getEnquries();
			 //filter logic
			 if(null!=criteria.getCourseName() & !AppConstant.TRING.equals(criteria.getCourseName()) ) {
				 enquries = enquries.stream()
				 .filter(e->e.getCourseName().equals(criteria.getCourseName())).collect(Collectors.toList());
			 }
			 
			 if(null!=criteria.getEnqStatus() & !AppConstant.TRING.equals(criteria.getEnqStatus()) ) {
				 enquries = enquries.stream()
				 .filter(e->e.getEnqStatus().equals(criteria.getEnqStatus())).collect(Collectors.toList());
			 }
			 
			 if(null!=criteria.getClassMode() & !AppConstant.TRING.equals(criteria.getClassMode()) ) {
				 enquries = enquries.stream()
				 .filter(e->e.getClassMode().equals(criteria.getClassMode())).collect(Collectors.toList());
			 }
			 
			 return enquries;
		 }
		return Collections.emptyList();
	}
	}

	



	


	

	

	


