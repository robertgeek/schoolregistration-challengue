package com.example.schoolregistration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException() {
      super();
    }
    public CourseNotFoundException(String message, Throwable cause) {
      super(message, cause);
    }
    public CourseNotFoundException(String message) {
      super(message);
    }
    public CourseNotFoundException(Throwable cause) {
      super(cause);
    }
}
