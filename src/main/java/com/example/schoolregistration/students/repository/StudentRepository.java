package com.example.schoolregistration.students.repository;

import com.example.schoolregistration.students.entity.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {

  @Query(value =
      "SELECT stu.* FROM student stu INNER JOIN student_course sc ON stu.student_id = sc.student_id "
          + "INNER JOIN course cou ON cou.course_id = sc.course_id "
          + "WHERE cou.course_name = :course", nativeQuery = true)
  List<Student> getStudentsByCourse(String course);

  @Query(value =
      "SELECT stu.* FROM student stu LEFT JOIN student_course sc ON sc.student_id=stu.student_id "
          + "LEFT JOIN course cou on cou.course_id = sc.course_id "
          + "WHERE sc.student_id IS NULL;", nativeQuery = true)
  List<Student> getStudentsWithoutCourse();

}
