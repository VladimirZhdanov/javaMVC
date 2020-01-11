package com.foxminded.service;

import com.foxminded.dao.layers.CourseDAO;
import com.foxminded.dao.layers.StudentDAO;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.Course;
import com.foxminded.model.Student;
import com.foxminded.service.layers.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Component
@Service("courseService")
public class CourseServiceImp implements CourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImp.class);

    private CourseDAO courseDAO;
    private StudentDAO studentDAO;

    @Autowired
    public CourseServiceImp(CourseDAO courseDAO, StudentDAO studentDAO) {
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
    }

    /**
     * Gets course by id.
     *
     * @param id - id
     * @return - Course
     */
    @Override
    @Transactional(readOnly = true)
    public Course getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        return courseDAO.getById(id);
    }

    /**
     * Gets all courses.
     *
     * @return - List<Course>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Course> getAll() {
        return courseDAO.getAll();
    }

    /**
     * Gets students related to the passed course.
     *
     * @param courseName - courseName
     * @return - List<Student>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudents(String courseName) throws DAOException {
        LOGGER.debug("Invoke method getStudents({})", courseName);
        Course course = courseDAO.getByName(courseName);
        return studentDAO.getStudentsByCourse(course);
    }

    /**
     * Inserts a course to the table.
     *
     * @param course - course
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean insert(Course course) throws DAOException {
        LOGGER.debug("Invoke method insert({})", course);
        return courseDAO.insert(course);
    }

    /**
     * Updates a course.
     *
     * @param course - course
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean update(Course course) throws DAOException {
        LOGGER.debug("Invoke method update({})", course);
        return courseDAO.update(course);
    }

    /**
     * Deletes a record.
     *
     * @param courseName - courseName
     * @return - Course
     */
    @Override
    @Transactional
    public Course delete(String courseName) throws DAOException {
        LOGGER.debug("Invoke method delete({})", courseName);
        Course course = courseDAO.getByName(courseName);
        return courseDAO.delete(course);
    }
}
