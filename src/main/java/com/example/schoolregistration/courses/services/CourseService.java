package com.example.schoolregistration.courses.services;

import com.example.schoolregistration.courses.dto.CourseDto;
import com.example.schoolregistration.courses.entity.Course;
import com.example.schoolregistration.courses.repository.CourseRepository;
import com.example.schoolregistration.exceptions.CourseNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  ModelMapper customerMapper;

  public List<CourseDto> getAllCoursesAvailable() {
    return courseRepository.findAll().stream()
                                     .map(course -> customerMapper.map(course, CourseDto.class))
                                     .collect(Collectors.toList());
  }

  public CourseDto getCourseByName(String courseName) {
    return customerMapper.map(courseRepository.findByCourseName(courseName), CourseDto.class);
  }

  public Optional<CourseDto> getCourseById(Long id) {
    Optional<Course> course = courseRepository.findById(id);
    if (course.isPresent()) {
      return Optional.of(customerMapper.map(course.get(), CourseDto.class));
    }
    throw new CourseNotFoundException("Not exist the course with ID: " + id);
  }

  public CourseDto createCourse(CourseDto courseDto) {
    Objects.requireNonNull(courseDto, "Course cannot be empty");
    Course record = customerMapper.map(courseDto, Course.class);
    return customerMapper.map(courseRepository.save(record), CourseDto.class);
  }

  public void delete(Long id) {
    courseRepository.deleteById(id);
  }

  public CourseDto update(Long id, CourseDto courseDto) {
    Optional<Course> courseFromDB = courseRepository.findById(id);
    if (courseFromDB.isPresent()) {
      Course course = courseFromDB.get();
      course.setCourseName(courseDto.getNameCourse());
      return customerMapper.map(courseRepository.save(course), CourseDto.class);
    }
    throw new CourseNotFoundException("Cannot update not exist the course with ID: "+ id);
  }

  public List<CourseDto> getCourseFilterByStudentName(String name) {
    return courseRepository.findCoursesByStudentName(name).stream()
                                                          .map(course-> customerMapper.map(course,CourseDto.class))
                                                          .collect(Collectors.toList());
  }

  public List<CourseDto> getCourseFilterByStudentId(Long id) {
    return courseRepository.findCoursesByStudentId(id).stream()
                                                      .map(course-> customerMapper.map(course,CourseDto.class))
                                                      .collect(Collectors.toList());
  }

  public List<CourseDto> getCourseWithoutStudents() {
    return courseRepository.findCoursesWithoutStudents().stream()
                                                        .map(course-> customerMapper.map(course,CourseDto.class))
                                                        .collect(Collectors.toList());
  }
}
