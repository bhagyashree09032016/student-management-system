package com.qa.project.sms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.project.sms.dto.StudentDto;
import com.qa.project.sms.model.Student;
import com.qa.project.sms.repo.StudentRepo;

@Service
public class StudentService {
	
	@Autowired
	StudentRepo studentRepository;
	
	private ModelMapper mapper;
	
	public List<Student> getAllStudents(){
		return (List<Student>) studentRepository.findAll();
	}
	public Optional<Student> getStudentById(int id)   
	{  
		
		return studentRepository.findById(id);
	}  

	
	public Student saveOrUpdate(Student student)   
	{  
	return studentRepository.save(student);  
	}  
	
	 
	public void delete(int id) 
		{  
			studentRepository.deleteById(id);
			} 
			
			 
			public void update(Student student, int Studentid)   
			{  
			studentRepository.save(student);
			}  


		 
		
		
		
	}
	
	
	


