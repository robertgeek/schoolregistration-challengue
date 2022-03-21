package com.example.schoolregistration.courses.services;

import com.example.schoolregistration.courses.dto.CourseDto;
import com.example.schoolregistration.courses.entity.Course;
import com.example.schoolregistration.courses.entity.StudentCourse;
import com.example.schoolregistration.courses.repository.CourseRepository;
import com.example.schoolregistration.courses.repository.StudentCourseRepository;
import com.example.schoolregistration.exceptions.StudentFullCourseFoundException;
import com.example.schoolregistration.students.entity.Student;
import com.example.schoolregistration.students.repository.StudentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterStudentToCourseService {

  @Autowired
  StudentRepository studentRepository;

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  StudentCourseRepository studentCourseRepository;

  public void registerStudentToCourse(Long idUser, CourseDto courseDto) {
    Optional<Student> studentDB = studentRepository.findById(idUser);
    Course course = courseRepository.findByCourseName(courseDto.getNameCourse());
    int countStudentsInCourse =studentCourseRepository.countStudentsInCourse(course.getId());
    if(countStudentsInCourse >= 50){
      throw new StudentFullCourseFoundException("The course it's full the maximum students allowed are 50");
    }
    if (studentDB.isPresent()) {
      Student student = studentDB.get();
      if(student.getStudentCourses().size()==5){
        throw new StudentFullCourseFoundException("This user it's subscribed in 5 courses this is the limit");
      }
      StudentCourse studentCourse = new StudentCourse();
      studentCourse.setStudent(student);
      studentCourse.setCourse(course);
      student.getStudentCourses().add(studentCourse);
      studentRepository.saveAndFlush(student);
    }
  }
}
