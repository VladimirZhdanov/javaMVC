package com.foxminded.service;

import com.foxminded.dao.layers.CourseDAO;
import com.foxminded.dao.layers.DepartmentDAO;
import com.foxminded.dao.layers.LectureDAO;
import com.foxminded.dao.layers.TeacherDAO;
import com.foxminded.domain.Schedule;
import com.foxminded.domain.Teacher;
import com.foxminded.exceptions.DAOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foxminded.service.layers.TeacherService;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Component
@Service("teacherManager")
public class TeacherServiceImp implements TeacherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImp.class);

    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private TeacherDAO teacherDAO;
    private LectureDAO lectureDAO;

    @Autowired
    public TeacherServiceImp(CourseDAO courseDAO, DepartmentDAO departmentDAO, TeacherDAO teacherDAO, LectureDAO lectureDAO) {
        this.courseDAO = courseDAO;
        this.departmentDAO = departmentDAO;
        this.teacherDAO = teacherDAO;
        this.lectureDAO = lectureDAO;
    }

    /**
     * Gets teacher by id.
     *
     * @param id - id
     * @return - Teacher
     */
    @Override
    @Transactional(readOnly = true)
    public Teacher getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        return teacherDAO.getById(id);
    }

    /**
     * Gets all teachers.
     *
     * @return - List<Teacher>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Teacher> getAll() {
        return teacherDAO.getAll();
    }

    /**
     * Gets full schedule for the teacher.
     *
     * @param teacherFirstName - teacherFirstName
     * @param teacherLastName  - teacherLastName
     * @return - Schedule
     */
    @Override
    @Transactional(readOnly = true)
    public Schedule getFullSchedule(String teacherFirstName, String teacherLastName) throws DAOException {
        LOGGER.debug("Invoke method getFullSchedule({}, {})", teacherFirstName, teacherLastName);
        Teacher teacher = teacherDAO.getByName(teacherFirstName, teacherLastName);
        return new Schedule(lectureDAO.getLectureByTeacher(teacher));
    }

    /**
     * Gets month schedule for the student.
     *
     * @param month            - month(1-12)
     * @param year             - year(yyyy)
     * @param teacherFirstName - teacherFirstName
     * @param teacherLastName  - teacherLastName
     * @return - Schedule
     */
    @Override
    @Transactional(readOnly = true)
    public Schedule getScheduleForMonth(int month, int year, String teacherFirstName, String teacherLastName) throws DAOException {
        LOGGER.debug("Invoke method getScheduleForMonth({}, {}, {}, {})", month, year, teacherFirstName, teacherLastName);
        Teacher teacher = teacherDAO.getByName(teacherFirstName, teacherLastName);
        return new Schedule(lectureDAO.getLecturesByTeacherForMonth(month, year, teacher));
    }

    /**
     * Gets year schedule for the student.
     *
     * @param year             - year(yyyy)
     * @param teacherFirstName - teacherFirstName
     * @param teacherLastName  - teacherLastName
     * @return - Schedule
     */
    @Override
    @Transactional(readOnly = true)
    public Schedule getScheduleForYear(int year, String teacherFirstName, String teacherLastName) throws DAOException {
        LOGGER.debug("Invoke method getScheduleForYear({}, {}, {})", year, teacherFirstName, teacherLastName);
        Teacher teacher = teacherDAO.getByName(teacherFirstName, teacherLastName);
        return new Schedule(lectureDAO.getLecturesByTeacherForYear(year, teacher));
    }

    /**
     * Inserts a teacher to the table.
     *
     * @param teacher - teacher
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean insert(Teacher teacher) throws DAOException {
        LOGGER.debug("Invoke method insert({})", teacher);
        return teacherDAO.insert(teacher);
    }

    /**
     * Updates a recorded data by id.
     *
     * @param teacher - Teacher
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean update(Teacher teacher) throws DAOException {
        LOGGER.debug("Invoke method update({})", teacher);
        return teacherDAO.update(teacher);
    }

    /**
     * Deletes a record.
     *
     * @param teacherFirstName - teacherFirstName
     * @param teacherLastName  - teacherLastName
     * @return - Teacher
     */
    @Override
    @Transactional
    public Teacher delete(String teacherFirstName, String teacherLastName) throws DAOException {
        LOGGER.debug("Invoke method delete({},{})", teacherFirstName, teacherLastName);
        Teacher teacher = teacherDAO.getByName(teacherFirstName, teacherLastName);
        return teacherDAO.delete(teacher);
    }
}
