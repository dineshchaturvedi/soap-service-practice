package com.dc.soapcoursemanagement.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.xml.transform.Source;

//Enable Spring Web Services
@EnableWs
//Enable Spring Configuration
@Configuration
public class WebServicesConfig {
    //MessageDispatcherServlet
        //ApplicationContext
        //url ->/ws/*
    @Bean
    public ServletRegistrationBean messageDispatherServlet(ApplicationContext context){
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(messageDispatcherServlet,"/ws/*");
    }

    @Bean(name="courses")
    public DefaultWsdl11Definition defaultWsdl11Definition (XsdSchema coursesSchema){
        DefaultWsdl11Definition definition = new  DefaultWsdl11Definition();
        //ws/courses.wsdl
           //CoursePort
        definition.setPortTypeName("CoursePort");
        //Namespace http://in28minutes.com/courses
        definition.setTargetNamespace("http://in28minutes.com/courses");
        //course-details.xsd
        definition.setSchema(coursesSchema);
        definition.setLocationUri("/ws");
        return  definition;
    }

    @Bean
    public XsdSchema coursesSchema(){
        return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));


    }
}

