package com.foxminded.dao.postgresql;

import com.foxminded.dao.PropertyLoader;
import com.foxminded.dao.layers.LectureDAO;

import com.foxminded.dao.mappers.LectureMapper;
import com.foxminded.model.ClassRoom;
import com.foxminded.model.Group;
import com.foxminded.model.Lecture;
import com.foxminded.model.Teacher;
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

import static java.sql.Timestamp.valueOf;

import static com.foxminded.constants.Constants.NULL_WAS_PASSED;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Repository
public class LecturePostgreSQL implements LectureDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(LecturePostgreSQL.class);

    private Properties properties;
    private PropertyLoader propertyLoader;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertLecture;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public LecturePostgreSQL(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertLecture = new SimpleJdbcInsert(dataSource).withTableName("lectures").usingGeneratedKeyColumns("id");
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
     * Gets lecture by id with id, name, date, class room, teacher, group.
     *
     * @param id - id
     * @return - Lecture
     * @throws DAOException - DAOException
     */
    @Override
    public Lecture getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        String sql = properties.getProperty("getLectureById");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new LectureMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find lecture by passed id: {}", id);
            throw new DAOException("Can't find lecture by passed id", e);
        }
    }

    /**
     * Gets lecture by name.
     *
     * @param name - name
     * @return - Lecture
     * @throws DAOException - DAOException
     */
    @Override
    public Lecture getByName(String name) throws DAOException {
        LOGGER.debug("Invoke method getByName({})", name);
        if (name == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getLectureByName");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, new LectureMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find lecture by passed name: {}", name);
            throw new DAOException("Can't find lecture by passed name", e);
        }
    }

    /**
     * Gets lectures by group.
     *
     * @param group - group
     * @return - List<Lecture>
     */
    @Override
    public List<Lecture> getLecturesByGroup(Group group) {
        LOGGER.debug("Invoke method getLecturesByGroup({})", group);
        if (group == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getScheduleByGroupId");
        List<Lecture> lectures = jdbcTemplate.query(sql, new Object[]{group.getId()}, new LectureMapper());
        LOGGER.debug("Returned size of lectures: {}", lectures.size());
        return lectures;
    }

    /**
     * Gets lectures by teacher
     *
     * @param teacher - teacher
     * @return - List<Lecture>
     */
    @Override
    public List<Lecture> getLectureByTeacher(Teacher teacher) {
        LOGGER.debug("Invoke method getLectureByTeacher({})", teacher);
        if (teacher == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getScheduleByTeacherId");
        List<Lecture> lectures = jdbcTemplate.query(sql, new Object[]{teacher.getId()}, new LectureMapper());
        LOGGER.debug("Returned size of lectures: {}", lectures.size());
        return lectures;
    }

    /**
     * Gets lectures by year.
     *
     * @param year - year (yyyy)
     * @return - List<Lecture>
     */
    @Override
    public List<Lecture> getLecturesByYear(int year) {
        LOGGER.debug("Invoke method getLecturesByYear({})", year);
        String sql = properties.getProperty("getScheduleByYear");
        List<Lecture> lectures = jdbcTemplate.query(sql, new Object[]{year}, new LectureMapper());
        LOGGER.debug("Returned size of lectures: {}", lectures.size());
        return lectures;
    }

    /**
     * Gets lectures by month.
     *
     * @param month - month (1-12)
     * @param year  - year (yyyy)
     * @return - List<Lecture>
     */
    @Override
    public List<Lecture> getLecturesByMonth(int month, int year) {
        LOGGER.debug("Invoke method getLecturesByMonth({}, {})", month, year);
        String sql = properties.getProperty("getScheduleByMonthYear");
        List<Lecture> lectures = jdbcTemplate.query(sql, new Object[]{month, year}, new LectureMapper());
        LOGGER.debug("Returned size of lectures: {}", lectures.size());
        return lectures;
    }

    /**
     * Gets lectures by year and group.
     *
     * @param year  - year (yyyy)
     * @param group - group
     * @return - List<Lecture>
     */
    @Override
    public List<Lecture> getLecturesByGroupForYear(int year, Group group) {
        LOGGER.debug("Invoke method getLecturesByGroupForYear({}, {})", year, group);
        if (group == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getGroupScheduleByYear");
        List<Lecture> lectures = jdbcTemplate.query(sql, new Object[]{year, group.getId()}, new LectureMapper());
        LOGGER.debug("Returned size of lectures: {}", lectures.size());
        return lectures;
    }

    /**
     * Gets lectures by month and teacher.
     *
     * @param year    - year (yyyy)
     * @param teacher - teacher
     * @return - List<Lecture>
     */
    @Override
    public List<Lecture> getLecturesByTeacherForYear(int year, Teacher teacher) {
        LOGGER.debug("Invoke method getLecturesByTeacherForYear({}, {})", year, teacher);
        if (teacher == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getTeacherScheduleByYear");
        List<Lecture> lectures = jdbcTemplate.query(sql, new Object[]{year, teacher.getId()}, new LectureMapper());
        LOGGER.debug("Returned size of lectures: {}", lectures.size());
        return lectures;
    }

    /**
     * Gets lectures by year and teacher.
     *
     * @param month   - month (1-12)
     * @param year    - year (yyyy)
     * @param teacher - teacher
     * @return - List<Lecture>
     */
    @Override
    public List<Lecture> getLecturesByTeacherForMonth(int month, int year, Teacher teacher) {
        LOGGER.debug("Invoke method getLecturesByTeacherForMonth({}, {}, {})", month, year, teacher);
        if (teacher == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getTeacherScheduleByMonth");
        List<Lecture> lectures = jdbcTemplate.query(sql, new Object[]{month, year, teacher.getId()}, new LectureMapper());
        LOGGER.debug("Returned size of lectures: {}", lectures.size());
        return lectures;
    }

    /**
     * Gets lectures by month and group.
     *
     * @param month - month (1-12)
     * @param year  - year (yyyy)
     * @param group - group
     * @return - List<Lecture>
     */
    @Override
    public List<Lecture> getLecturesByGroupForMonth(int month, int year, Group group) {
        LOGGER.debug("Invoke method getLecturesByGroupForMonth({}, {}, {})", month, year, group);
        if (group == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getGroupScheduleByMonth");
        List<Lecture> lectures = jdbcTemplate.query(sql, new Object[]{month, year, group.getId()}, new LectureMapper());
        LOGGER.debug("Returned size of lectures: {}", lectures.size());
        return lectures;
    }

    /**
     * Gets all lectures with id, name, date, class room, teacher, group.
     *
     * @return - List<Lecture>
     */
    @Override
    public List<Lecture> getAll() {
        String sql = properties.getProperty("getAllLectures");
        return jdbcTemplate.query(sql, new LectureMapper());
    }

    /**
     * Changes a teacher and course in the lecture.
     *
     * @param lecture - lecture
     * @param teacher - teacher
     * @return - boolean
     */
    @Override
    public boolean changeTeacher(Lecture lecture, Teacher teacher) {
        LOGGER.debug("Invoke method changeTeacher({}, {})", lecture, teacher);
        if (lecture == null || teacher == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("ChangeTeacherInLecture");
        boolean result = jdbcTemplate.update(sql, teacher.getId(), teacher.getCourse().getId(), lecture.getId()) > 0;
        if (!result) {
            LOGGER.warn("Something went wrong while changing teacher...");
            return false;
        }
        return true;
    }

    /**
     * Changes a class room in the lecture.
     *
     * @param lecture   - lecture
     * @param classRoom - classRoom
     * @return - boolean
     */
    @Override
    public boolean changeClassRoom(Lecture lecture, ClassRoom classRoom) {
        LOGGER.debug("Invoke method changeClassRoom({}, {})", lecture, classRoom);
        if (lecture == null || classRoom == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("ChangeClassRoomInLecture");
        boolean result = jdbcTemplate.update(sql, classRoom.getId(), lecture.getId()) > 0;
        if (!result) {
            LOGGER.warn("Something went wrong while changing class room...");
            return false;
        }
        return true;
    }

    /**
     * Changes a group in the lecture.
     *
     * @param lecture - lecture
     * @param group   - group
     * @return - boolean
     */
    @Override
    public boolean changeGroup(Lecture lecture, Group group) {
        LOGGER.debug("Invoke method changeGroup({}, {})", lecture, group);
        if (lecture == null || group == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("ChangeGroupInLecture");
        boolean result = jdbcTemplate.update(sql, group.getId(), lecture.getId()) > 0;
        if (!result) {
            LOGGER.warn("Something went wrong while changing group...");
            return false;
        }
        return true;
    }

    /**
     * Inserts a lecture to the table.
     *
     * @param lecture - lecture
     * @return - boolean
     * @throws DAOException - DAOException
     */
    @Override
    public boolean insert(Lecture lecture) throws DAOException {
        LOGGER.debug("Insert lecture: {}", lecture);
        if (lecture == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", lecture.getName())
                .addValue("date", valueOf(lecture.getDate()))
                .addValue("class_room_id", lecture.getClassRoom().getId())
                .addValue("teacher_id", lecture.getTeacher().getId())
                .addValue("group_id", lecture.getGroup().getId())
                .addValue("course_id", lecture.getCourse().getId());

        try {
            Number idFromDB = insertLecture.executeAndReturnKey(namedParameters);
            lecture.setId(idFromDB.intValue());
        } catch (DuplicateKeyException e) {
            LOGGER.warn("Unique index or primary key violation");
            throw new DAOException("Unique index or primary key violation", e);
        }
        return true;
    }

    /**
     * Updates a recorded data.
     *
     * @param lecture - Lecture
     * @return - boolean
     * @throws DAOException - DAOException
     */
    @Override
    public boolean update(Lecture lecture) throws DAOException {
        LOGGER.debug("Update lecture: {}", lecture);
        if (lecture == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("updateLecture");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", lecture.getName())
                .addValue("date", valueOf(lecture.getDate()))
                .addValue("class_room_id", lecture.getClassRoom().getId())
                .addValue("teacher_id", lecture.getTeacher().getId())
                .addValue("group_id", lecture.getGroup().getId())
                .addValue("course_id", lecture.getCourse().getId())
                .addValue("id", lecture.getId());

        boolean result = namedParameterJdbcTemplate.update(sql, namedParameters) > 0;
        if (!result) {
            LOGGER.warn("The lecture does not exist: {}", lecture);
            throw new DAOException("The lecture does not exist");
        }
        return true;
    }

    /**
     * Deletes a record from the table by lecture id.
     *
     * @param lecture - lecture
     * @return - Lecture
     * @throws DAOException - DAOException
     */
    @Override
    public Lecture delete(Lecture lecture) throws DAOException {
        LOGGER.debug("Delete lecture: {}", lecture);
        if (lecture == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("deleteLecture");
        boolean result = jdbcTemplate.update(sql, lecture.getId()) > 0;
        if (!result) {
            LOGGER.warn("The lecture does not exist: {}", lecture);
            throw new DAOException("The lecture does not exist");
        }
        return lecture;
    }
}
