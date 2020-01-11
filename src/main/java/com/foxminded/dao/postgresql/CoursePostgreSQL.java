package com.foxminded.dao.postgresql;

import com.foxminded.dao.PropertyLoader;
import com.foxminded.dao.layers.CourseDAO;
import com.foxminded.dao.mappers.CourseMapper;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.Course;
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
public class CoursePostgreSQL implements CourseDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoursePostgreSQL.class);

    private PropertyLoader propertyLoader;
    private Properties properties;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CoursePostgreSQL(DataSource dataSource) {
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
     * Gets course by id.
     *
     * @param id - id
     * @return - Course
     * @throws DAOException - DAOException
     */
    @Override
    public Course getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        String sql = properties.getProperty("getCourseById");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CourseMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find course by passed id: {}", id);
            throw new DAOException("Can't find course by passed id", e);
        }
    }

    /**
     * Gets course by name.
     *
     * @param name - name
     * @return - Course
     * @throws DAOException - DAOException
     */
    @Override
    public Course getByName(String name) throws DAOException {
        LOGGER.debug("Invoke method getByName({})", name);
        if (name == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getCourseByName");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, new CourseMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find course by passed name: {}", name);
            throw new DAOException("Can't find course by passed name", e);
        }
    }

    /**
     * Gets all courses.
     *
     * @return - List<Course>
     */
    @Override
    public List<Course> getAll() {
        String sql = properties.getProperty("getAllCourses");
        return jdbcTemplate.query(sql, new CourseMapper());
    }

    /**
     * Inserts a course to the table.
     *
     * @param course - course
     * @return - boolean
     * @throws DAOException - DAOException
     */
    @Override
    public boolean insert(Course course) throws DAOException {
        LOGGER.debug("Insert course: {}", course);
        if (course == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("insertCourse");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = 0;

        try {
            result = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(sql, new String[]{"id"});
                ps.setString(1, course.getName());
                return ps;
            }, keyHolder);
            course.setId((int) (keyHolder.getKey()));
        } catch (DuplicateKeyException e) {
            LOGGER.warn("Unique index or primary key violation");
            throw new DAOException("Unique index or primary key violation", e);
        }
        return result > 0;
    }

    /**
     * Updates a course.
     *
     * @param course - course
     * @return - boolean
     * @throws DAOException - DAOException
     */
    @Override
    public boolean update(Course course) throws DAOException {
        LOGGER.debug("Update course: {}", course);
        if (course == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("updateCourse");
        boolean result = jdbcTemplate.update(sql, course.getName(), course.getId()) > 0;
        if (!result) {
            LOGGER.warn("The course does not exist: {}", course);
            throw new DAOException("The course does not exist");
        }
        return true;
    }

    /**
     * Deletes a record from the table by course.
     *
     * @param course - course
     * @return - Course
     * @throws DAOException - DAOException
     */
    @Override
    public Course delete(Course course) throws DAOException {
        LOGGER.debug("Delete course: {}", course);
        if (course == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("deleteCourse");
        boolean result = jdbcTemplate.update(sql, course.getId()) > 0;
        if (!result) {
            LOGGER.warn("The course does not exist: {}", course);
            throw new DAOException("The course does not exist");
        }
        return course;
    }
}
