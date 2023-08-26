package com.workintech.student.mapping;

import com.workintech.student.entity.*;

public class CourseResponseFactory {
    public static CourseResponse saveCourse(Course course, CourseGpa lowGpa, CourseGpa mediumGpa, CourseGpa highGpa){
        if(course.getCredit() <= 2){
            return new CourseResponse(course,
                    course.getGrade().getCoefficient() * course.getCredit() * ((LowCourseGpa)lowGpa).getGpa());
        }
        else if (course.getCredit() == 3){
            return new CourseResponse(course,
                    course.getGrade().getCoefficient() * course.getCredit() * ((MediumCourseGpa)mediumGpa).getGpa());
        }
        else if(course.getCredit() == 3){
            return new CourseResponse(course,
                    course.getGrade().getCoefficient() * course.getCredit() * ((HighCourseGpa)highGpa).getGpa());
        }
        return null;
    }
}
