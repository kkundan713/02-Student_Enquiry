package com.kundan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kundan.EntityClass.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity , Integer> {

}
