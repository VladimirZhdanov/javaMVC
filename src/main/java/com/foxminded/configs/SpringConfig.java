package com.foxminded.configs;

import com.foxminded.dao.ExecutorQuery;
import com.foxminded.dao.layers.*;
import com.foxminded.dao.postgresql.*;
import com.foxminded.service.*;
import com.foxminded.service.layers.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */

@Configuration
@PropertySource("classpath:properties/postgresql.properties")
public class SpringConfig {

    @Value("${postgresql.url}")
    private String url;
    @Value("${postgresql.username}")
    private String username;
    @Value("${postgresql.password}")
    private String password;
    @Value("${postgresql.driverClassName}")
    private String driver;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public ExecutorQuery executorQuery() {
        return new ExecutorQuery(getDataSource());
    }

    @Bean
    public ClassRoomDAO classRoomDAO() {
        return new ClassRoomPostgreSQL(getDataSource());
    }

    @Bean
    public CourseDAO courseDAO() {
        return new CoursePostgreSQL(getDataSource());
    }

    @Bean
    public DepartmentDAO departmentDAO() {
        return new DepartmentPostgreSQL(getDataSource());
    }

    @Bean
    public GroupDAO groupDAO() {
        return new GroupPostgreSQL(getDataSource());
    }

    @Bean
    public LectureDAO lectureDAO() {
        return new LecturePostgreSQL(getDataSource());
    }

    @Bean
    public StudentDAO studentDAO() {
        return new StudentPostgreSQL(getDataSource());
    }

    @Bean
    public TeacherDAO teacherDAO() {
        return new TeacherPostgreSQL(getDataSource());
    }

    @Bean
    public ClassRoomService classRoomService() {
        return new ClassRoomServiceImp(classRoomDAO());
    }

    @Bean
    public CourseService courseService() {
        return new CourseServiceImp(courseDAO(), studentDAO());
    }

    @Bean
    public DepartmentService departmentService() {
        return new DepartmentServiceImp(departmentDAO(), teacherDAO());
    }

    @Bean
    public GroupService groupService() {
        return new GroupServiceImp(studentDAO(), groupDAO());
    }

    @Bean
    public LectureService lectureService() {
        return new LectureServiceImp(lectureDAO(),
                teacherDAO(), groupDAO(), classRoomDAO(), courseDAO());
    }

    @Bean
    public StudentService studentService() {
        return new StudentServiceImp(studentDAO(), groupDAO(), lectureDAO());
    }

    @Bean
    public TeacherService teacherService() {
        return new TeacherServiceImp(courseDAO(),
                departmentDAO(), teacherDAO(), lectureDAO());
    }
}
