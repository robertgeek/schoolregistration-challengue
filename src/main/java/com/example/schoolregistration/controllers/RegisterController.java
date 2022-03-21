package com.example.schoolregistration.controllers;

import com.example.schoolregistration.courses.dto.CourseDto;
import com.example.schoolregistration.courses.services.RegisterStudentToCourseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Api("Registration Api")
@RestController
@Slf4j
@RequestMapping("/api/register")
public class RegisterController {

  @Autowired
  RegisterStudentToCourseService registerService;

  @PostMapping(value = "/student/{idUser}/to/course", produces = MediaType.APPLICATION_JSON_VALUE)
  public void registerStudentToCourse(@PathVariable("idUser") Long id,
                                      @RequestBody CourseDto courseDto) {
    try {
      registerService.registerStudentToCourse(id, courseDto);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot subscribe to more Courses -> "+e.getMessage(),
          e.getCause());
    }
  }
}
