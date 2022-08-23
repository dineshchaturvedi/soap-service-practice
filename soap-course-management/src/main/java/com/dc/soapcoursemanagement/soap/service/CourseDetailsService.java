package com.dc.soapcoursemanagement.soap.service;

import com.dc.soapcoursemanagement.soap.bean.Course;
import com.in28minutes.courses.Status;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CourseDetailsService {
    private static List<Course> courses = new ArrayList<>();
    public enum Status{
        SUCCESS,FAILURE;
    }

    static{
        Course course1 = new Course(1,"Spring","10 Steps");
        courses.add(course1);
        Course course2 = new Course(2,"MVC","10 Examples");
        courses.add(course2);
        Course course3 = new Course(3,"Boot","6 K Students");
        courses.add(course3);
        Course course4 = new Course(4,"Maven","Most Popular Maven Course");
        courses.add(course4);
    }
    // course //1
    public Course findById(int id) {
        for (Course course : courses) {
            if (course.getId()==id){
                return course;
            }
        }
        return null;
    }
        //courses
        public List<Course> findAll(){
            return courses;
        }

        //deleteCourses
        public CourseDetailsService.Status deleteById(int id){
            Iterator<Course> iterator = courses.iterator();
            while (iterator.hasNext()){
                Course course = iterator.next();
                if(course.getId()==id){
                    iterator.remove();
                    return Status.SUCCESS;
                }
            }
            return Status.FAILURE;
        }


    }