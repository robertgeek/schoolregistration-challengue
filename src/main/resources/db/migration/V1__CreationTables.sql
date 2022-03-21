CREATE TABLE student (
   student_id BIGINT NOT NULL AUTO_INCREMENT,
   first_name VARCHAR(255) NULL,
   last_name VARCHAR(255) NULL,
   address VARCHAR(255) NULL,
   CONSTRAINT student_pk PRIMARY KEY (student_id)
);

  CREATE TABLE course (
     course_id BIGINT NOT NULL AUTO_INCREMENT,
     course_name VARCHAR(255) NULL,
     CONSTRAINT course_pk PRIMARY KEY (course_id),
     UNIQUE KEY course_name_key (course_name)
  );

CREATE TABLE student_course(
    student_course_id BIGINT NOT NULL AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    PRIMARY KEY(student_course_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    UNIQUE KEY student_course_key (course_id,student_id)
);

