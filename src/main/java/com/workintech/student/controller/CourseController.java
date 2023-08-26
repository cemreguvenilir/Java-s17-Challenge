package com.workintech.student.controller;

import com.workintech.student.entity.Course;
import com.workintech.student.entity.CourseGpa;
import com.workintech.student.exceptions.CourseException;
import com.workintech.student.exceptions.CourseValidator;
import com.workintech.student.mapping.CourseResponse;
import com.workintech.student.mapping.CourseResponseFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private List<Course> courses;
    private CourseGpa lowGpa;
    private CourseGpa mediumGpa;
    private CourseGpa highGpa;

    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") CourseGpa lowGpa,
                            @Qualifier("mediumCourseGpa") CourseGpa mediumGpa,
                            @Qualifier("highCourseGpa") CourseGpa highGpa) {
        this.lowGpa = lowGpa;
        this.mediumGpa = mediumGpa;
        this.highGpa = highGpa;
    }

    @PostConstruct
    public void init(){
        courses = new ArrayList<>();
    }
    @GetMapping("/")
    public List<Course> get(){
        return courses;
    }
    @GetMapping("/{name}")
    public Course getByName(@PathVariable String name){
        List<Course> course = courses.stream().filter(course1 -> course1.getName().equals(name)).collect(Collectors.toList());
        if (course.size() == 0){
            throw new CourseException("course is not exist", HttpStatus.NOT_FOUND);
        }
        return course.get(0);
    }
    @PostMapping("/")
    public CourseResponse save(@RequestBody Course course){
        CourseValidator.isIdValid(course.getId());
        CourseValidator.isCourseValid(course);
        CourseValidator.isNameExist(courses, course.getName());
        courses.add(course);
        return CourseResponseFactory.saveCourse(course, lowGpa, mediumGpa, highGpa);
    }
    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable int id, @RequestBody Course course){
        CourseValidator.isIdValid(id);
        CourseValidator.isCourseValid(course);
        Optional<Course> findCourse = courses.stream().filter(course1 -> course1.getId() == id).findFirst();
        if(findCourse.isPresent()){
            course.setId(id);
            int index = courses.indexOf(findCourse.get());
            courses.set(index, course);
            return CourseResponseFactory.saveCourse(course, lowGpa, mediumGpa, highGpa);
        }
        else {
            throw new CourseException("course is not exist", HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/{id}")
    public Course delete(@PathVariable int id){
        CourseValidator.isIdValid(id);
        Optional<Course> findCourse = courses.stream().filter(course1 -> course1.getId() == id).findFirst();
        if(findCourse.isPresent()){
            int index = courses.indexOf(findCourse.get());
            courses.remove(index);
            return findCourse.get();
        } else {
            throw new CourseException("id is not exist", HttpStatus.NOT_FOUND);
        }

    }


}
