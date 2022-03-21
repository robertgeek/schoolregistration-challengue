package com.example.schoolregistration.controllers;

import com.example.schoolregistration.exceptions.StudentNotFoundException;
import com.example.schoolregistration.students.dto.StudentCoursesDto;
import com.example.schoolregistration.students.dto.StudentDto;
import com.example.schoolregistration.students.services.StudentService;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Api("Student Api")
@RestController
@Slf4j
@RequestMapping("/api/students")
public class StudentController {

  @Autowired
  StudentService studentService;

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<StudentDto> getAllStudents() {
    return studentService.getAll();
  }

  @GetMapping(value = "/byName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<StudentDto> getStudentsByName(@PathVariable("name") String name) {
    return studentService.getAll().stream()
        .filter(student -> student.getFirstName().equalsIgnoreCase(name))
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/byId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<StudentDto> getStudentById(@PathVariable("id") Long id) {
    return studentService.getStudentById(id);
  }

  @GetMapping(value = "/courses/relationship", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<StudentCoursesDto> getRelathionship() {
    try {
      return studentService.relationshipsStudentCourses();
    } catch (StudentNotFoundException snfe) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found -> "+snfe.getMessage(), snfe.getCause());
    }
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public StudentDto createStudent(@RequestBody StudentDto studentDto) {
    return studentService.create(studentDto);
  }

  @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteStudent(@PathVariable("id") Long id) {
    studentService.delete(id);
  }

  @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void updateStudent(@PathVariable("id") Long id, @RequestBody StudentDto studentDto) {
    try {
      studentService.update(id, studentDto);
    } catch (StudentNotFoundException snfe) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found -> "+snfe.getMessage(), snfe.getCause());
    }
  }

  @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<StudentDto> getStudentsFilterBy(@RequestParam(required = false) String course){
   return studentService.getStudentFilterByCourse(course);
  }

  @GetMapping(value = "/without/course", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<StudentDto> getStudentsWithoutCourse(){
    return studentService.getStudentWithoutCourse();
  }

}
