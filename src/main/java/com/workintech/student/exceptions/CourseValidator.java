package com.workintech.student.exceptions;


import com.workintech.student.entity.Course;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class CourseValidator {

    public static void isNameExist(List<Course> courses, String name){
       Optional<Course> findCourse = courses.stream().filter(course -> course.getName().equals(name)).findFirst();
       if(findCourse.isPresent()){
           throw new CourseException("the course already exist", HttpStatus.BAD_REQUEST);
       }
    }
    public static void isCourseValid(Course course){
        if(course.getCredit()<0 || course.getCredit() > 4 || course.getName().isEmpty() || course.getName() == null){
            throw new CourseException("course credentials not valid", HttpStatus.BAD_REQUEST);
        }
    }
    public static void isIdValid(int id){
        if(id <= 0){
            throw new CourseException("id is not valid", HttpStatus.BAD_REQUEST);
        }
    }

}
