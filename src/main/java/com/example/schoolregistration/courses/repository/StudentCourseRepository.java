package com.example.schoolregistration.courses.repository;

import com.example.schoolregistration.courses.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentCourseRepository extends JpaRepository<StudentCourse,Long> {

  @Query(value = "SELECT count(*) AS students FROM student_course sc where sc.course_id = :id group by sc.course_id", nativeQuery = true)
  int countStudentsInCourse(@Param("id") Long id);

}
