package com.foxminded.test;

import com.foxminded.business.service.layers.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import static com.foxminded.business.constants.Constants.CHARACTER_ENCODING;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Configuration
public class WebTestConfig {
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding(CHARACTER_ENCODING);
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    public ClassRoomService classRoomService() {
        return Mockito.mock(ClassRoomService.class);
    }

    @Bean
    public CourseService courseService() {
        return Mockito.mock(CourseService.class);
    }

    @Bean
    public DepartmentService departmentService() {
        return Mockito.mock(DepartmentService.class);
    }

    @Bean
    public GroupService groupService() {
        return Mockito.mock(GroupService.class);
    }

    @Bean
    public LectureService lectureService() {
        return Mockito.mock(LectureService.class);
    }

    @Bean
    public StudentService studentService() {
        return Mockito.mock(StudentService.class);
    }

    @Bean
    public TeacherService teacherService() {
        return Mockito.mock(TeacherService.class);
    }
}
