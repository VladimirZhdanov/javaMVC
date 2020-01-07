package com.foxminded.business.service.layers;

import com.foxminded.business.model.Course;
import com.foxminded.business.model.Student;
import com.foxminded.business.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface CourseService {

    /**
     * Gets course by id.
     *
     * @param id - id
     * @return - Course
     * @throws DAOException - DAOException
     */
    Course getById(int id) throws DAOException;

    /**
     * Gets all courses.
     *
     * @return - List<Course>
     */
    List<Course> getAll();

    /**
     * Gets students related to the passed course.
     *
     * @param courseName - courseName
     * @return - List<Student>
     * @throws DAOException - DAOException
     */
    List<Student> getStudents(String courseName) throws DAOException;

    /**
     * Inserts a course to the table.
     *
     * @param course - course
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean insert(Course course) throws DAOException;

    /**
     * Updates a course.
     *
     * @param course - course
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean update(Course course) throws DAOException;

    /**
     * Deletes a record.
     *
     * @param courseName - courseName
     * @return - Course
     * @throws DAOException - DAOException
     */
    Course delete(String courseName) throws DAOException;
}
