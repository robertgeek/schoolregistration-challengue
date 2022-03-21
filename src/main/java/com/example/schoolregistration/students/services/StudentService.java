package com.example.schoolregistration.students.services;

import com.example.schoolregistration.courses.dto.CourseDto;
import com.example.schoolregistration.courses.entity.Course;
import com.example.schoolregistration.courses.entity.StudentCourse;
import com.example.schoolregistration.courses.repository.StudentCourseRepository;
import com.example.schoolregistration.exceptions.StudentNotFoundException;
import com.example.schoolregistration.students.dto.StudentCoursesDto;
import com.example.schoolregistration.students.dto.StudentDto;
import com.example.schoolregistration.students.entity.Student;
import com.example.schoolregistration.students.repository.StudentRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {

  @Autowired
  StudentRepository studentRepository;

  @Autowired
  StudentCourseRepository studentCourseRepository;

  @Autowired
  ModelMapper studentMapper;

  public List<StudentDto> getAll() {
    return studentRepository.findAll().stream()
                                      .map(student -> studentMapper.map(student, StudentDto.class))
                                      .collect(Collectors.toList());
  }

  public Optional<StudentDto> getStudentById(Long id) {
    Optional<Student> student = studentRepository.findById(id);
    if (student.isPresent()) {
      return Optional.of(studentMapper.map(student.get(), StudentDto.class));
    }
    throw new StudentNotFoundException("Cannot find the student with ID: "+ id);
  }

  public StudentDto create(StudentDto studentDto) {
    Objects.requireNonNull(studentDto, "Student cannot be empty");
    Student record = studentMapper.map(studentDto, Student.class);
    return studentMapper.map(studentRepository.save(record), StudentDto.class);
  }

  public void delete(Long id) {
    studentRepository.deleteById(id);
  }

  public StudentDto update(Long id, StudentDto studentDto) {
    Optional<Student> student = studentRepository.findById(id);
    if (student.isPresent()) {
      Student studentDB = student.get();
      if (Objects.nonNull(studentDto.getFirstName())) {
        studentDB.setFirstName(studentDto.getFirstName());
      }
      if (Objects.nonNull(studentDto.getLastName())) {
        studentDB.setLastName(studentDto.getLastName());
      }
      if (Objects.nonNull(studentDto.getAddress())) {
        studentDB.setAddress(studentDto.getAddress());
      }
      return studentMapper.map(studentRepository.save(studentDB), StudentDto.class);
    }
    throw new StudentNotFoundException("Cannot update the student with Id: " + id + "not found");
  }

  public List<StudentCoursesDto> relationshipsStudentCourses() {
   List<StudentCourse> studentsCourseList = studentCourseRepository.findAll();
   List<StudentCoursesDto> relationshipStudentCoursesList = new ArrayList<>();
   Map<Student, List<Course>> relationshipMap = new HashMap<>();

      for (StudentCourse sc : studentsCourseList) {
        if(relationshipMap.get(sc.getStudent())== null){
          List<Course> courses = new ArrayList<>();
          courses.add(sc.getCourse());
          relationshipMap.put(sc.getStudent(), courses);
        }else{
          relationshipMap.get(sc.getStudent()).add(sc.getCourse());
        }
      }

    Iterator<Map.Entry<Student, List<Course>>> it = relationshipMap.entrySet().iterator();

      while (it.hasNext()) {
      Map.Entry<Student, List<Course>> next=it.next();
      Student student = next.getKey();
      List<Course> coursesList = next.getValue();
      List<CourseDto> courseDtoList = coursesList.stream()
                                           .map(course -> studentMapper.map(course,CourseDto.class))
                                           .collect(Collectors.toList());
      StudentCoursesDto dto = new StudentCoursesDto();
      dto.setId       (student.getId());
      dto.setFirstName(student.getFirstName());
      dto.setLastName (student.getLastName());
      dto.setAddress  (student.getAddress());
      dto.setCoursesSubscribed(courseDtoList);
      relationshipStudentCoursesList.add(dto);
    }
    return relationshipStudentCoursesList;
  }

  public List<StudentDto> getStudentFilterByCourse(String course) {
    return studentRepository.getStudentsByCourse(course).stream()
                                                        .map(student -> studentMapper.map(student,StudentDto.class))
                                                        .collect(Collectors.toList());
  }

  public List<StudentDto> getStudentWithoutCourse() {
    return studentRepository.getStudentsWithoutCourse().stream()
                                                       .map(student -> studentMapper.map(student,StudentDto.class))
                                                       .collect(Collectors.toList());
  }
}
