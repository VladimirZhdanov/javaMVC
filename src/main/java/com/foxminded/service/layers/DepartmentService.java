package com.foxminded.service.layers;

import com.foxminded.model.Department;
import com.foxminded.model.Teacher;
import com.foxminded.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface DepartmentService {

    /**
     * Gets department by id.
     *
     * @param id - id
     * @return - Department
     * @throws DAOException - DAOException
     */
    Department getById(int id) throws DAOException;

    /**
     * Gets all departments.
     *
     * @return - List<Department>
     */
    List<Department> getAll();

    /**
     * Gets all teachers related to the department.
     *
     * @param departmentName - departmentName
     * @return - List<Teacher>
     * @throws DAOException - DAOException
     */
    List<Teacher> getTeachers(String departmentName) throws DAOException;

    /**
     * Inserts a department to the table.
     *
     * @param department - department
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean insert(Department department) throws DAOException;

    /**
     * Updates a department.
     *
     * @param department - department
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean update(Department department) throws DAOException;

    /**
     * Deletes a record.
     *
     * @param departmentName - departmentName
     * @return - Department
     * @throws DAOException - DAOException
     */
    Department delete(String departmentName) throws DAOException;
}
