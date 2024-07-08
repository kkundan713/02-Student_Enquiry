package com.kundan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kundan.EntityClass.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer>{

}
