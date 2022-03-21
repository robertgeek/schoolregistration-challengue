package com.example.schoolregistration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class StudentFullCourseFoundException extends RuntimeException{

    public StudentFullCourseFoundException() {
      super();
    }
    public StudentFullCourseFoundException(String message, Throwable cause) {
      super(message, cause);
    }
    public StudentFullCourseFoundException(String message) {
      super(message);
    }
    public StudentFullCourseFoundException(Throwable cause) {
      super(cause);
    }
}
