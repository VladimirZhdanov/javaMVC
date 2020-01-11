package com.foxminded.dao.layers;

import com.foxminded.model.Department;
import com.foxminded.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface DepartmentDAO {

    /**
     * Gets department by id.
     *
     * @param id - id
     * @return - Department
     * @throws DAOException - DAOException
     */
    Department getById(int id) throws DAOException;

    /**
     * Gets department by name.
     *
     * @param name - name
     * @return - Department
     * @throws DAOException - DAOException
     */
    Department getByName(String name) throws DAOException;

    /**
     * Gets all departments.
     *
     * @return - List<Department>
     */
    List<Department> getAll();

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
     * Deletes a record from the table.
     *
     * @param department - department
     * @return - Department
     * @throws DAOException - DAOException
     */
    Department delete(Department department) throws DAOException;
}
