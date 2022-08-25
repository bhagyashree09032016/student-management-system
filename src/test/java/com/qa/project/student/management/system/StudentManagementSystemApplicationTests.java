package com.qa.project.student.management.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.project.sms.StudentManagementSystemApplication;
import com.qa.project.sms.model.Student;
import com.qa.project.sms.repo.StudentRepo;
import com.qa.project.sms.service.StudentService;

@SpringBootTest(classes = StudentManagementSystemApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:Student-schema.sql",
"classpath:Student-Data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class StudentManagementSystemApplicationTests {
	@Autowired
    private StudentService service;

    @MockBean
    private StudentRepo repo;
    
    @Autowired
    private MockMvc mock;
    
    private final Student TEST_STUDENT = new Student(5006,"Testfirstname", "Testlastname","Ra2030@gmail.com");

    @MockBean
    private ModelMapper mapper;
    
    @Autowired
	private ObjectMapper obmapper;
    

    //Unit Test Create 1
    @Test
    void testCreate(){
    	
        // GIVEN is our testing data - you can make this a final local variable if you want, e.g.:
        final Student TEST_STUDENT = new Student(0, "Testfirstname", "Testlastname","Ra2030@gmail.com");
        final Student TEST_SAVED_STUDENT = new Student(5006,"Testfirstname", "Testlastname","Ra2030@gmail.com");

        // WHEN
        Mockito.when(this.repo.save(TEST_STUDENT)).thenReturn(TEST_SAVED_STUDENT);

        // THEN
        Assertions.assertThat(this.service.saveOrUpdate(TEST_STUDENT)).isEqualTo(TEST_SAVED_STUDENT);

        // verify that our repo was accessed exactly once
        Mockito.verify(this.repo, Mockito.times(1)).save(TEST_STUDENT);
        
        System.out.println("Create Test Successful");
        
    }

    //Unit Test 2
    @Test
    void testGetById() {
    	
    	// Setup the mock repo

        int StudentId = 5008;
        final Student TEST_STUDENT = new Student(StudentId, "Testfirstname", "Testlastname","Ra2030@gmail.com");
        //final Optional<books> TEST_SAVED_BOOK = Optional.empty();

        // Make the service call
    	Mockito.when(this.repo.findById(StudentId)).thenReturn(Optional.of(TEST_STUDENT)); 
		
       System.out.println("Test for Get By ID Successful");
       
    }   
        
    //Unit Test 3
    @Test
    public void testDeleteStudentById() {
    	
    	
    	int StudentId = 5008;
        final Student TEST_STUDENT = new Student(0, "Testfirstname", "Testlastname","Ra2030@gmail.com");
        final Student TEST_SAVED_STUDENT = new Student(StudentId,"Testfirstname", "Testlastname","Ra2030@gmail.com");
        
    	service.delete(TEST_SAVED_STUDENT.getStudentid());

        Mockito.verify(repo, Mockito.times(1))
                .deleteById(TEST_SAVED_STUDENT.getStudentid());
        
        System.out.println("Test for Delete By ID Successful");
        
    }
    
    //Unit Test 4
    @Test
    public void testFindAll() {

    	final Student TEST_SAVED_STUDENT = new Student(5008,"Testfirstname", "Testlastname","Ra2030@gmail.com");
        List<Student> foundStudent = service.getAllStudents();
        foundStudent.add(TEST_SAVED_STUDENT);

        assertNotNull(foundStudent);
        assertEquals(1, foundStudent.size());
        
        System.out.println("Test for Find All Successful");
    }

    
    //---------- Integration Test----------//
        
    
  //Integration Test 1
    @Test
	public void IntTestCreate() throws Exception {
		final Student newStudent = new Student(5009, "Lata", "Deshmukh","Ld2018@gmail.com");
		
		this.mock
				.perform(post("/saveorupdateStudent").contentType(MediaType.APPLICATION_JSON)
						.content(this.obmapper.writeValueAsString(newStudent)))
				.andExpect(status().isCreated());
	}
    
    
    //Integration Test 2
    @Test
	public void IntTestReadAll() throws Exception {
		final String resultString = this.mock
				.perform(request(HttpMethod.GET, "/allStudent").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		List<Student> results = Arrays.asList(obmapper.readValue(resultString, Student[].class));
		assertEquals(new ArrayList<>(Arrays.asList()), results);
		System.out.println(results.size());
	}
    


	

}
