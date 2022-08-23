package com.dc.soapcoursemanagement.soap;

import com.dc.soapcoursemanagement.soap.bean.Course;
import com.dc.soapcoursemanagement.soap.exception.CourseNotFoundException;
import com.dc.soapcoursemanagement.soap.service.CourseDetailsService;
import com.in28minutes.courses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {
    @Autowired
    CourseDetailsService service;
    //method
    // Input GetCourseDetailsRequest
    // output GetCourseDetailsResponse
    @PayloadRoot(namespace = "http://in28minutes.com/courses" , localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse processCourseDetailsRequest(
            @RequestPayload GetCourseDetailsRequest request){
        Course course = service.findById(request.getId());
        if (course == null)
                throw new CourseNotFoundException("Invalid Course Id " + request.getId());
        return mapCourseDetails(course);
    }

    //method
    // Input GetAllCourseDetailsRequest
    // output GetAllCourseDetailsResponse
    @PayloadRoot(namespace = "http://in28minutes.com/courses" , localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
            @RequestPayload GetAllCourseDetailsRequest request){
        List<Course> courses = service.findAll();
        return mapAllCourseDetails(courses);
            //return 
    }

    //method
    // Input DeleteCourseDetailsRequest
    // output DeleteCourseDetailsResponse
    @PayloadRoot(namespace = "http://in28minutes.com/courses" , localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse processAllCourseDetailsRequest(
            @RequestPayload DeleteCourseDetailsRequest request){
        CourseDetailsService.Status deleteStatus = service.deleteById(request.getId());
        DeleteCourseDetailsResponse deleteCourseDetailsResponse = new DeleteCourseDetailsResponse();
        deleteCourseDetailsResponse.setStatus(mapStatus(deleteStatus));
        return deleteCourseDetailsResponse;
        //return
    }

    private Status mapStatus(CourseDetailsService.Status deleteStatus) {
        if (deleteStatus == CourseDetailsService.Status.SUCCESS){
            return  Status.SUCCESS;
        }
        return Status.FAILURE;
    }

    private static GetAllCourseDetailsResponse mapAllCourseDetails(List <Course> courses) {
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        for(Course course:courses){
            CourseDetails courseDetails = mapCourse(course);
            response.getCourseDetails().add(courseDetails);
        }
//        mapCourse(course, response);
        return response;
    }

    private static GetCourseDetailsResponse mapCourseDetails(Course course) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        response.setCourseDetails(mapCourse(course));
        return response;
    }

    private static CourseDetails mapCourse(Course course) {
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        courseDetails.setDescription(course.getDescription());
        return courseDetails;
//        response.setCourseDetails(courseDetails);
    }
}
