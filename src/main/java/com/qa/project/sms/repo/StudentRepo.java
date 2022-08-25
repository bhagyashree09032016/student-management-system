package com.qa.project.sms.repo;

import org.springframework.data.repository.CrudRepository;

import com.qa.project.sms.model.*; 

public interface StudentRepo extends CrudRepository<Student, Integer> {

}
