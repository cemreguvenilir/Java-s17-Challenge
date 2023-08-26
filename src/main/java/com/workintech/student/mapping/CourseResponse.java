package com.workintech.student.mapping;

import com.workintech.student.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Course course;
    private double totalGpa;

}
