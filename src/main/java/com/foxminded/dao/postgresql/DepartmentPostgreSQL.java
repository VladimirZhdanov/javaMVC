package com.foxminded.dao.postgresql;

import com.foxminded.dao.PropertyLoader;
import com.foxminded.dao.layers.DepartmentDAO;
import com.foxminded.dao.mappers.DepartmentMapper;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;

import static com.foxminded.constants.Constants.NULL_WAS_PASSED;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Repository
public class DepartmentPostgreSQL implements DepartmentDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentPostgreSQL.class);

    private PropertyLoader propertyLoader;
    private Properties properties;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartmentPostgreSQL(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        properties = new Properties();
        propertyLoader = new PropertyLoader();
        init();
    }

    /**
     * Initialisation properties.
     */
    private void init() {
        propertyLoader.loadProperty(properties, "properties/queriesPostrgeSQL.properties");
    }

    /**
     * Gets department by id.
     *
     * @param id - id
     * @return - Department
     * @throws DAOException - DAOException
     */
    @Override
    public Department getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        String sql = properties.getProperty("getDepartmentById");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new DepartmentMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find department by passed id: {}", id);
            throw new DAOException("Can't find department by passed id", e);
        }
    }

    /**
     * Gets department by name.
     *
     * @param name - name
     * @return - Department
     * @throws DAOException - DAOException
     */
    @Override
    public Department getByName(String name) throws DAOException {
        LOGGER.debug("Invoke method getByName({})", name);
        if (name == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getDepartmentByName");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, new DepartmentMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find department by passed name: {}", name);
            throw new DAOException("Can't find department by passed name", e);
        }
    }

    /**
     * Gets all departments.
     *
     * @return - List<Department>
     */
    @Override
    public List<Department> getAll() {
        String sql = properties.getProperty("getAllDepartments");
        return jdbcTemplate.query(sql, new DepartmentMapper());
    }

    /**
     * Inserts a department to the table.
     *
     * @param department - department
     * @return - boolean
     * @throws DAOException - DAOException
     */
    @Override
    public boolean insert(Department department) throws DAOException {
        LOGGER.debug("Insert department: {}", department);
        if (department == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("insertDepartment");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = 0;

        try {
            result = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(sql, new String[]{"id"});
                ps.setString(1, department.getName());
                return ps;
            }, keyHolder);
            department.setId((int) (keyHolder.getKey()));
        } catch (DuplicateKeyException e) {
            LOGGER.warn("Unique index or primary key violation");
            throw new DAOException("Unique index or primary key violation", e);
        }
        return result > 0;
    }

    /**
     * Updates a department.
     *
     * @param department - department
     * @return - boolean
     * @throws DAOException - DAOException
     */
    @Override
    public boolean update(Department department) throws DAOException {
        LOGGER.debug("Update department: {}", department);
        if (department == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("updateDepartment");
        boolean result = jdbcTemplate.update(sql, department.getName(), department.getId()) > 0;
        if (!result) {
            LOGGER.warn("The department does not exist: {}", department);
            throw new DAOException("The department does not exist");
        }
        return true;
    }

    /**
     * Deletes a record from the table by department.
     *
     * @param department - department
     * @return - Department
     * @throws DAOException - DAOException
     */
    @Override
    public Department delete(Department department) throws DAOException {
        LOGGER.debug("Delete department: {}", department);
        if (department == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("deleteDepartment");

        boolean result = jdbcTemplate.update(sql, department.getId()) > 0;
        if (!result) {
            LOGGER.warn("The department does not exist: {}", department);
            throw new DAOException("The department does not exist");
        }
        return department;
    }
}
