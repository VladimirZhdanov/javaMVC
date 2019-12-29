package com.foxminded.service;

import com.foxminded.dao.layers.DepartmentDAO;
import com.foxminded.dao.layers.TeacherDAO;
import com.foxminded.domain.Department;
import com.foxminded.domain.Teacher;
import com.foxminded.exceptions.DAOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foxminded.service.layers.DepartmentService;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Component
@Service("departmentManager")
public class DepartmentServiceImp implements DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImp.class);

    private DepartmentDAO departmentDAO;
    private TeacherDAO teacherDAO;

    @Autowired
    public DepartmentServiceImp(DepartmentDAO departmentDAO, TeacherDAO teacherDAO) {
        this.departmentDAO = departmentDAO;
        this.teacherDAO = teacherDAO;
    }

    /**
     * Gets department by id.
     *
     * @param id - id
     * @return - Department
     */
    @Override
    @Transactional(readOnly = true)
    public Department getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        return departmentDAO.getById(id);
    }

    /**
     * Gets all departments.
     *
     * @return - List<Department>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Department> getAll() {
        return departmentDAO.getAll();
    }

    /**
     * Gets all teachers related to the department.
     *
     * @param departmentName - departmentName
     * @return - List<Teacher>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Teacher> getTeachers(String departmentName) throws DAOException {
        LOGGER.debug("Invoke method getTeachers({})", departmentName);
        Department department = departmentDAO.getByName(departmentName);
        return teacherDAO.getTeachersByDepartment(department);
    }

    /**
     * Inserts a department to the table.
     *
     * @param department - department
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean insert(Department department) throws DAOException {
        LOGGER.debug("Invoke method insert({})", department);
        return departmentDAO.insert(department);
    }

    /**
     * Updates a department.
     *
     * @param department - department
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean update(Department department) throws DAOException {
        LOGGER.debug("Invoke method update({})", department);
        return departmentDAO.update(department);
    }

    /**
     * Deletes a record.
     *
     * @param departmentName - departmentName
     * @return - Department
     */
    @Override
    @Transactional
    public Department delete(String departmentName) throws DAOException {
        LOGGER.debug("Invoke method delete({})", departmentName);
        Department department = departmentDAO.getByName(departmentName);
        return departmentDAO.delete(department);
    }
}
