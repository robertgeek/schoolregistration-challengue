package com.example.schoolregistration.courses.repository;

import com.example.schoolregistration.courses.entity.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course,Long> {

  Course findByCourseName(String courseName);

  @Query(value = "SELECT cou.* FROM course cou INNER JOIN student_course sc ON cou.course_id = sc.course_id "
      + " INNER JOIN student stu ON stu.student_id = sc.student_id "
      + " WHERE stu.first_name = :nameStudent ", nativeQuery = true)
  List<Course> findCoursesByStudentName(@Param("nameStudent") String nameStudent);

  @Query(value = "SELECT cou.* FROM course cou INNER JOIN student_course sc ON cou.course_id = sc.course_id "
      + " INNER JOIN student stu ON stu.student_id = sc.student_id "
      + " WHERE stu.student_id = :id ", nativeQuery = true)
  List<Course> findCoursesByStudentId(@Param("id")Long id);

  @Query(value ="SELECT cou.* FROM course cou LEFT JOIN student_course sc ON cou.course_id = sc.course_id "
      + "LEFT JOIN student stu ON stu.student_id = sc.student_id "
      + "WHERE stu.student_id IS NULL", nativeQuery = true)
  List<Course> findCoursesWithoutStudents();
}
