package com.foxminded.dao.postgresql;

import com.foxminded.dao.PropertyLoader;
import com.foxminded.dao.layers.TeacherDAO;
import com.foxminded.dao.mappers.TeacherMapper;
import com.foxminded.domain.Department;
import com.foxminded.domain.Teacher;
import com.foxminded.exceptions.DAOException;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import static com.foxminded.constants.Constants.NULL_WAS_PASSED;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Repository
public class TeacherPostgreSQL implements TeacherDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherPostgreSQL.class);

    private PropertyLoader propertyLoader;
    private Properties properties;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertTeacher;

    @Autowired
    public TeacherPostgreSQL(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertTeacher = new SimpleJdbcInsert(dataSource).withTableName("teachers").usingGeneratedKeyColumns("id");
        properties = new Properties();
        propertyLoader = new PropertyLoader();
        init();
    }

    /**
     * Initialisation properties.
     */
    private void init() {
        propertyLoader.loadProperty(properties, "queriesPostrgeSQL.properties");
    }

    /**
     * Gets teacher by id with id, first name, last name, department, course.
     *
     * @param id - id
     * @return - Teacher
     */
    @Override
    public Teacher getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        String sql = properties.getProperty("getTeacherById");
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TeacherMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find teacher by passed id: {}", id);
            throw new DAOException("Can't find teacher by passed id", e);
        }
    }

    /**
     * Gets teacher by name.
     *
     * @param firstName - firstName
     * @param lastName  - lastName
     * @return - Teacher
     */
    @Override
    public Teacher getByName(String firstName, String lastName) throws DAOException {
        LOGGER.debug("Invoke method getByName({}, {})", firstName, lastName);
        if (lastName == null || firstName == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getTeacherByName");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{firstName, lastName}, new TeacherMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find teacher by passed name: {}, {}", firstName, lastName);
            throw new DAOException("Can't find teacher by passed name", e);
        }
    }

    /**
     * Gets teacher by department.
     *
     * @param department -department
     * @return - List<Teacher>
     */
    @Override
    public List<Teacher> getTeachersByDepartment(Department department) {
        LOGGER.debug("Invoke method getTeachersByDepartment({})", department);
        if (department == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getTeachersByDepartmentId");
        List<Teacher> teachers = jdbcTemplate.query(sql, new Object[]{department.getId()}, new TeacherMapper());
        LOGGER.debug("Returned size of teachers: {}", teachers.size());
        return teachers;
    }

    /**
     * Gets all teachers with id, first name, last name, department, course.
     *
     * @return - List<Teacher>
     */
    @Override
    public List<Teacher> getAll() {
        String sql = properties.getProperty("getAllTeachers");
        return jdbcTemplate.query(sql, new TeacherMapper());
    }


    /**
     * Inserts a teacher to the table.
     *
     * @param teacher - teacher
     * @return - boolean
     */
    @Override
    public boolean insert(Teacher teacher) throws DAOException {
        LOGGER.debug("Insert teacher: {}", teacher);
        if (teacher == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("first_name", teacher.getFirstName())
                .addValue("last_name", teacher.getLastName())
                .addValue("course_id", teacher.getCourse().getId())
                .addValue("department_id", teacher.getDepartment().getId());

        try {
            Number idFromDB = insertTeacher.executeAndReturnKey(namedParameters);
            teacher.setId(idFromDB.intValue());
        } catch (
                DuplicateKeyException e) {
            LOGGER.warn("Unique index or primary key violation");
            throw new DAOException("Unique index or primary key violation", e);
        }
        return true;
    }

    /**
     * Updates a recorded data by id.
     *
     * @param teacher - Teacher
     * @return - boolean
     */
    @Override
    public boolean update(Teacher teacher) throws DAOException {
        LOGGER.debug("Update teacher: {}", teacher);
        if (teacher == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("updateTeacher");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("first_name", teacher.getFirstName())
                .addValue("last_name", teacher.getLastName())
                .addValue("course_id", teacher.getCourse().getId())
                .addValue("department_id", teacher.getDepartment().getId())
                .addValue("id", teacher.getId());

        boolean result = namedParameterJdbcTemplate.update(sql, namedParameters) > 0;
        if (!result) {
            LOGGER.warn("The teacher does not exist: {}", teacher);
            throw new DAOException("The teacher does not exist");
        }
        return true;
    }

    /**
     * Deletes a record from the table by teacher.
     *
     * @param teacher - teacher
     * @return - Teacher
     */
    @Override
    public Teacher delete(Teacher teacher) throws DAOException {
        LOGGER.debug("Delete teacher: {}", teacher);
        if (teacher == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("deleteTeacher");
        boolean result = jdbcTemplate.update(sql, teacher.getId()) > 0;
        if (!result) {
            LOGGER.warn("The teacher does not exist: {}", teacher);
            throw new DAOException("The teacher does not exist");
        }
        return teacher;
    }
}
