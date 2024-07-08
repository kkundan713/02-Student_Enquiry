package com.kundan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kundan.EntityClass.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
