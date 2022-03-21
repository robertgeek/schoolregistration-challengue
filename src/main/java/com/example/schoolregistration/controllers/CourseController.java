package com.example.schoolregistration.controllers;

import com.example.schoolregistration.courses.dto.CourseDto;
import com.example.schoolregistration.courses.services.CourseService;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.Optional;
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

@Api("Courses Api")
@RestController
@Slf4j
@RequestMapping("/api/courses")
public class CourseController {

  @Autowired
  CourseService courseService;

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CourseDto> getAllCoursesAvailable(){
    return courseService.getAllCoursesAvailable();
  }

  @GetMapping(value = "/byName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CourseDto getCourseByName(@PathVariable("name") String name){
    return courseService.getCourseByName(name);
  }

  @GetMapping(value = "/byId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<CourseDto> getCourseById(@PathVariable("id") Long id){
    try{
    return courseService.getCourseById(id);
    }catch (Exception e){
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot find the course -> "+e.getMessage(),
          e.getCause());
    }
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public CourseDto createCourse(@RequestBody CourseDto courseDto){
    return courseService.createCourse(courseDto);
  }

  @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteCourse(@PathVariable("id") Long id){
    courseService.delete(id);
  }

  @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void updateCourse(@PathVariable("id") Long id, @RequestBody CourseDto courseDto){
    courseService.update(id, courseDto);
  }

  @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CourseDto> getCourseFilterByStudentName(@RequestParam(required = true) String nameStudent ){
    return courseService.getCourseFilterByStudentName(nameStudent);
  }

  @GetMapping(value = "/filter/byId", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CourseDto> getCourseFilterByStudentId(@RequestParam(required = true) Long id ){
    return courseService.getCourseFilterByStudentId(id);
  }

  @GetMapping(value = "/without/students", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CourseDto> getCourseFilterByStudentId(){
    return courseService.getCourseWithoutStudents();
  }
}
