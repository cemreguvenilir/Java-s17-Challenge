package com.workintech.student.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class CourseException extends RuntimeException{
    private HttpStatus status;

    public CourseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
