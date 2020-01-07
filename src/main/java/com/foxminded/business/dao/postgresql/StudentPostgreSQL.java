package com.foxminded.business.dao.postgresql;

import com.foxminded.business.dao.PropertyLoader;
import com.foxminded.business.dao.layers.StudentDAO;
import com.foxminded.business.dao.mappers.CourseMapper;
import com.foxminded.business.dao.mappers.StudentCourseMapper;
import com.foxminded.business.dao.mappers.StudentMapper;
import com.foxminded.business.exceptions.DAOException;
import com.foxminded.business.model.Course;
import com.foxminded.business.model.Group;
import com.foxminded.business.model.Student;
import com.foxminded.business.model.StudentCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;

import static com.foxminded.business.constants.Constants.NULL_WAS_PASSED;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Repository
public class StudentPostgreSQL implements StudentDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentPostgreSQL.class);


    private PropertyLoader propertyLoader;
    private Properties properties;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public StudentPostgreSQL(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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
     * Gets student by id with id, name, group with id.
     *
     * @param id - id
     * @return - Student
     */
    @Override
    public Student getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        String sql = properties.getProperty("getStudentById");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new StudentMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find student by passed id: {}", id);
            throw new DAOException("Can't find student by passed id", e);
        }
    }

    /**
     * Gets student by name.
     *
     * @param firstName - firstName
     * @param lastName  - lastName
     * @return - Student
     */
    @Override
    public Student getByName(String firstName, String lastName) throws DAOException {
        LOGGER.debug("Invoke method getByName({}, {})", firstName, lastName);
        if (lastName == null || firstName == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getStudentByName");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{firstName, lastName}, new StudentMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find student by passed name: {}, {}", firstName, lastName);
            throw new DAOException("Can't find student by passed name", e);
        }
    }

    /**
     * Gets students by course.
     *
     * @param course - course
     * @return - List<Student>
     */
    @Override
    public List<Student> getStudentsByCourse(Course course) {
        LOGGER.debug("Invoke method getStudentsByCourse({})", course);
        if (course == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getStudentsByCourseId");
        List<Student> students = jdbcTemplate.query(sql, new Object[]{course.getId()}, new StudentMapper());
        LOGGER.debug("Returned size of students: {}", students.size());
        return students;
    }

    /**
     * Gets students by group.
     *
     * @param group - group
     * @return - List<Student>
     */
    @Override
    public List<Student> getStudentsByGroup(Group group) {
        LOGGER.debug("Invoke method getStudentsByGroup({})", group);
        if (group == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getStudentsByGroupId");
        List<Student> students = jdbcTemplate.query(sql, new Object[]{group.getId()}, new StudentMapper());
        LOGGER.debug("Returned size of students: {}", students.size());
        return students;
    }

    /**
     * Gets all courses by student.
     *
     * @param student - student
     * @return - List<Course>
     */
    @Override
    public List<Course> getCoursesByStudent(Student student) {
        LOGGER.debug("Invoke method getCoursesByStudent({})", student);
        if (student == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getCoursesByStudentId");
        List<Course> courses = jdbcTemplate.query(sql, new Object[]{student.getId()}, new CourseMapper());
        LOGGER.debug("Returned size of courses: {}", courses.size());
        return courses;
    }

    /**
     * Gets all students with id, name, group.
     *
     * @return - List<Student>
     */
    @Override
    public List<Student> getAll() {
        String sql = properties.getProperty("getAllStudents");
        return jdbcTemplate.query(sql, new StudentMapper());
    }

    /**
     * Gets all relationship between students and courses with ids.
     *
     * @return - List<StudentCourse>
     */
    @Override
    public List<StudentCourse> getAllStudentCourse() {
        String sql = properties.getProperty("getAllStudentCourse");
        return jdbcTemplate.query(sql, new StudentCourseMapper());
    }

    /**
     * Changes a group at the student.
     *
     * @param student - student
     * @param group   - group
     * @return - boolean
     */
    @Override
    public boolean changeGroup(Student student, Group group) {
        LOGGER.debug("Invoke method changeGroup({}, {})", student, group);
        if (student == null || group == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("ChangeGroupAtStudent");
        boolean result = jdbcTemplate.update(sql, group.getId(), student.getId()) > 0;
        if (!result) {
            LOGGER.warn("Something went wrong while changing group...");
            return false;
        }
        return true;
    }

    /**
     * Inserts student to the table.
     *
     * @param student - student
     * @return - boolean
     */
    @Override
    public boolean insert(Student student) throws DAOException {
        LOGGER.debug("Insert student: {}", student);
        if (student == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("insertStudent");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = 0;

        try {
            result = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(sql, new String[]{"id"});
                ps.setString(1, student.getFirstName());
                ps.setString(2, student.getLastName());
                ps.setInt(3, student.getGroup().getId());
                return ps;
            }, keyHolder);

            student.setId((int) (keyHolder.getKey()));
        } catch (DuplicateKeyException e) {
            LOGGER.warn("Unique index or primary key violation");
            throw new DAOException("Unique index or primary key violation", e);
        }
        return result > 0;
    }

    /**
     * Inserts relationship: Student - Course.
     *
     * @param students - students with relationship: Student - Course
     */
    @Override
    public void insertRelationshipStudentsToCourses(List<Student> students) {
        LOGGER.debug("insert relationship students To courses student, size of students: {}", students.size());
        students.forEach(student -> student.getCourses()
                .forEach(course -> insertCourseToStudent(student, course)));
    }

    /**
     * Inserts a course to a student.
     *
     * @param student - student
     * @param course - student
     * @return - added\didn't add - boolean
     */
    @Override
    public boolean insertCourseToStudent(Student student, Course course) {
        LOGGER.debug("insert course To student, student: {}, course: {}", student, course);
        if (student == null || course == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("insertCourseToStudentById");
        boolean result = jdbcTemplate.update(sql, student.getId(), course.getId()) > 0;
        if (!result) {
            LOGGER.warn("Something went wrong while inserting course to the student...");
            return false;
        }
        return true;
    }

    /**
     * Updates a recorded data by id.
     *
     * @param student - Student
     * @return - boolean
     */
    @Override
    public boolean update(Student student) throws DAOException {
        LOGGER.debug("Update student: {}", student);
        if (student == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("updateStudent");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("first_name", student.getFirstName())
                .addValue("last_name", student.getLastName())
                .addValue("group_id", student.getGroup().getId())
                .addValue("id", student.getId());

        boolean result = namedParameterJdbcTemplate.update(sql, namedParameters) > 0;
        if (!result) {
            LOGGER.warn("The student does not exist: {}", student);
            throw new DAOException("The student does not exist");
        }
        return true;
    }

    /**
     * Deletes a record from the table by student.
     *
     * @param student - student
     * @return - Student
     * @throws DAOException - DAOException
     */
    @Override
    public Student delete(Student student) throws DAOException {
        LOGGER.debug("Delete student: {}", student);
        if (student == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("deleteStudent");
        boolean result = jdbcTemplate.update(sql, student.getId()) > 0;
        if (!result) {
            LOGGER.warn("The student does not exist: {}", student);
            throw new DAOException("The student does not exist");
        }
        return student;
    }
}
