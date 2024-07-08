package com.kundan.service;

import java.util.List;

import com.kundan.EntityClass.StudentEnqEntity;
import com.kundan.binding.DashboardResponse;
import com.kundan.binding.EnquiryForm;
import com.kundan.binding.EnquirySearchCriteria;

public interface EnquiryService {

public DashboardResponse getDashboardData(Integer userId);

public List<String> getCourses();

public List<String> getEnqStatuses();

public boolean saveEnquriry(EnquiryForm form); 

public List<StudentEnqEntity> getEnquiries();

public List<StudentEnqEntity> getFilterEnquiries(EnquirySearchCriteria criteria,Integer userId);




}
