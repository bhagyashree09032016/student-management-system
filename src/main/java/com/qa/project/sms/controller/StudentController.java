package com.qa.project.sms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qa.project.sms.model.Student;
import com.qa.project.sms.service.StudentService;

@RestController

public class StudentController {
	
	// auto-wire the StudentsService class
		@Autowired
		StudentService StudentsService;

		@GetMapping("/")
		public String greeting() {
			return "Welcome to Student Management System Please go to \"/allStudents\" for all available Students";
		}

		// creating a get mapping that retrieves all the student detail from the database
		@GetMapping("/allStudent")
		private List<Student> getAllStudent() {
			return StudentsService.getAllStudents();
		}

		// creating a get mapping that retrieves the detail of a specific student
		@GetMapping("/Student/{Studentid}")
		private Optional<Student> getStudentById(@PathVariable("Studentid") Integer studentId) {
			return StudentsService.getStudentById(studentId);
		}

		// creating a delete mapping that deletes a specified student
		@DeleteMapping("/deleteStudent/{Studentid}")
		private void deleteStudent(@PathVariable("Studentid") int Studentid) {
			StudentsService.delete(Studentid);
		}

		// creating post mapping that post the book detail in the database
		@PostMapping("/saveorupdateStudent")
		private ResponseEntity<Student> saveStudent(@RequestBody Student student1) {
			return new ResponseEntity<>(this.StudentsService.saveOrUpdate(student1), HttpStatus.CREATED);
		}

		// creating put mapping that updates the book detail
		@PutMapping("/saveStudent")
		private Student update(@RequestBody Student student1) {
			StudentsService.saveOrUpdate(student1);
			return student1;
		}

}
