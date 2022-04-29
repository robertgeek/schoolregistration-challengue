package com.example.schoolregistration.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.schoolregistration.courses.dto.CourseDto;
import com.example.schoolregistration.exceptions.StudentNotFoundException;
import com.example.schoolregistration.students.dto.StudentCoursesDto;
import com.example.schoolregistration.students.dto.StudentDto;
import com.example.schoolregistration.students.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  StudentService service;

  @Test
  public void getAllRecordsStudentTest() throws Exception {
    StudentDto record1 = new StudentDto();
    record1.setFirstName("John");
    record1.setLastName("Doe");
    record1.setAddress("Av. Springfield #111");
    List<StudentDto> records = new ArrayList<>(Arrays.asList(record1));

    Mockito.when(service.getAll()).thenReturn(records);
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/students/")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].firstName", is("John")));
  }

  @Test
  public void getRecordsStudentByNameTest() throws Exception {
    StudentDto record1 = new StudentDto();
    record1.setFirstName("John");
    record1.setLastName("Doe");
    record1.setAddress("Av. Springfield #111");
    List<StudentDto> records = new ArrayList<>(Arrays.asList(record1));

    Mockito.when(service.getAll()).thenReturn(records);
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/students/byName/John")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].firstName", is("John")));
  }

  @Test
  public void getRecordsStudentByIdTest() throws Exception {
    StudentDto record1 = new StudentDto();
    record1.setFirstName("John");
    record1.setLastName("Doe");
    record1.setAddress("Av. Springfield #111");
    record1.setId(1L);
    Mockito.when(service.getStudentById(1L)).thenReturn(Optional.of(record1));
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/students/byId/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is("John")));
  }
  @Test
  public void getRelathionshipTest() throws Exception {
    StudentDto record1 = new StudentDto(1L,"John","Doe","Av. Springfield #111");
    CourseDto course1 = new CourseDto(1L,"Spanish");
    StudentCoursesDto stuCourDto = new StudentCoursesDto(1L,record1.getFirstName(),record1.getLastName(),record1.getAddress(),Arrays.asList(course1));
    Mockito.when(service.relationshipsStudentCourses()).thenReturn(Arrays.asList(stuCourDto));
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/students/courses/relationship")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$..coursesSubscribed", hasSize(1)));
  }

  @Test
  public void getRelathionshipExceptionTest() throws Exception {
    Mockito.when(service.relationshipsStudentCourses()).thenThrow(StudentNotFoundException.class);
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/students/courses/relationship")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

  }

  @Test
  public void createStudentTest() throws Exception {
    StudentDto student = new StudentDto(1L,"John","Doe","Av. Springfield #111");
    Mockito.when(service.create(student)).thenReturn(student);
    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/students/create")
            .content(new ObjectMapper().writeValueAsString(student))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is("John")));
  }

}
