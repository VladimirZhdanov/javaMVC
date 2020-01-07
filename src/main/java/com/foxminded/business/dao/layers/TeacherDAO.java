package com.foxminded.business.dao.layers;

import com.foxminded.business.model.Department;
import com.foxminded.business.model.Teacher;
import com.foxminded.business.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface TeacherDAO {

    /**
     * Gets teacher by id.
     *
     * @param id - id
     * @return - Teacher
     */
    Teacher getById(int id) throws DAOException;

    /**
     * Gets teacher by name.
     *
     * @param firstName - firstName
     * @param lastName - lastName
     * @return - Teacher
     */
    Teacher getByName(String firstName, String lastName) throws DAOException;

    /**
     * Gets teacher by department.
     *
     * @param department - department
     * @return - List<Teacher>
     */
    List<Teacher> getTeachersByDepartment(Department department);

    /**
     * Gets all teachers.
     *
     * @return - List<Teacher>
     */
    List<Teacher> getAll();

    /**
     * Inserts a teacher to the table.
     *
     * @param teacher - teacher
     * @return - boolean
     */
    boolean insert(Teacher teacher) throws DAOException;

    /**
     * Updates a recorded data by id.
     *
     * @param teacher - Teacher
     * @return - boolean
     */
    boolean update(Teacher teacher) throws DAOException;

    /**
     * Deletes a record from the table by teacher id.
     *
     * @param teacher - teacher
     * @return - boolean
     */
    Teacher delete(Teacher teacher) throws DAOException;
}
