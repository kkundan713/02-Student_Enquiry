package com.kundan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kundan.EntityClass.StudentEnqEntity;
import com.kundan.binding.DashboardResponse;
import com.kundan.binding.EnquiryForm;
import com.kundan.binding.EnquirySearchCriteria;
import com.kundan.constant.AppConstant;
import com.kundan.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	@Autowired
	private HttpSession session;
	@Autowired
	private EnquiryService service;
	
             
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return"index";
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model ) {
		
		Integer userId = (Integer) session.getAttribute(AppConstant.STR_USER_ID);
		
		DashboardResponse dashboardData = service.getDashboardData(userId);
		
		model.addAttribute("dashboardData", dashboardData);

		return"dashboard";
	}
	
	@PostMapping("/addEnq")
	public String addPage(@ModelAttribute("enquiryForm") EnquiryForm enquiryForm,Model model) {
		boolean status = service.saveEnquriry(enquiryForm);
		if(status) {
			model.addAttribute(AppConstant.STR_SUCCMSG, "Enquiry Added Successfully!");
			
			}else {
			model.addAttribute(AppConstant.STR_ERRMSG, "Problem Occured!");
		}
		return AppConstant.STR_ADDENQUIRY;
	} 
	
	@GetMapping("/enquiry")
	public String addEnquirydPage(Model model) {
		initForm(model);
		
		return AppConstant.STR_ADDENQUIRY;
	}
	private void initForm(Model model) {
		//get courses for drop down
				List<String> courses = service.getCourses();
				
				//get Enq status for drop down
				List<String> enqStatuses = service.getEnqStatuses();
				
				//create binding class obj
				EnquiryForm form= new EnquiryForm()	;
				
				//send data in model obj
				model.addAttribute("coursesName", courses);
				model.addAttribute("enqStatuses", enqStatuses);
				model.addAttribute("enqForm", form);
				
		
	}


	
	
	
	@GetMapping("/enquires")
	public String viewEnquiryPage(Model model) {
		initForm(model);
		model.addAttribute("searchForm",new EnquirySearchCriteria());
		 List<StudentEnqEntity> enquiries = service.getEnquiries();
		 model.addAttribute("enquiries", enquiries);
		return"view-enquiries";
	}
	
	@GetMapping("/filterenquiries")
	public String getFilterEnqs(@RequestParam String cname,
			@RequestParam String status,
			@RequestParam String mode, Model model) {
		
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		
		criteria.setCourseName(cname);
		criteria.setEnqStatus(status);
		criteria.setClassMode(mode);
		
		
		  Integer userId = (Integer) session.getAttribute(AppConstant.STR_USER_ID);
		  List<StudentEnqEntity> filterEnquiries = service.getFilterEnquiries(criteria,
		 userId);
		 
		  model.addAttribute("enquiries", filterEnquiries);
		
		
		
		
		return"filterEnq";
		
	}
			

}
