package com.foxminded.dao.layers;

import com.foxminded.domain.Course;
import com.foxminded.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface CourseDAO {

    /**
     * Gets course by id.
     *
     * @param id - id
     * @return - Course
     * @throws DAOException - DAOException
     */
    Course getById(int id) throws DAOException;

    /**
     * Gets course by name.
     *
     * @param name - name
     * @return - Course
     * @throws DAOException - DAOException
     */
    Course getByName(String name) throws DAOException;

    /**
     * Gets all courses.
     *
     * @return - List<Course>
     */
    List<Course> getAll();

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
     * Deletes a record from the table.
     *
     * @param course - course
     * @return - Course
     * @throws DAOException - DAOException
     */
    Course delete(Course course) throws DAOException;
}
