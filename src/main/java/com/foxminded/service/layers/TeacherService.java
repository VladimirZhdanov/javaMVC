package com.foxminded.service.layers;

import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;
import com.foxminded.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface TeacherService {

    /**
     * Gets teacher by id.
     *
     * @param id - id
     * @return - Teacher
     * @throws DAOException - DAOException
     */
    Teacher getById(int id) throws DAOException;

    /**
     * Gets all teachers.
     *
     * @return - List<Teacher>
     */
    List<Teacher> getAll();

    /**
     * Gets full schedule for the teacher.
     *
     * @param teacherFirstName - teacherFirstName
     * @param teacherLastName - teacherLastName
     * @return - Schedule
     * @throws DAOException - DAOException
     */
    Schedule getFullSchedule(String teacherFirstName, String teacherLastName) throws DAOException;

    /**
     * Gets month schedule for the student.
     *
     * @param teacherFirstName - teacherFirstName
     * @param teacherLastName - teacherLastName
     * @param month - month(1-12)
     * @param year - year(yyyy)
     * @return - Schedule
     * @throws DAOException - DAOException
     */
    Schedule getScheduleForMonth(int month, int year, String teacherFirstName, String teacherLastName) throws DAOException;

    /**
     * Gets year schedule for the student.
     *
     * @param teacherFirstName - teacherFirstName
     * @param teacherLastName - teacherLastName
     * @param year - year(yyyy)
     * @return - Schedule
     * @throws DAOException - DAOException
     */
    Schedule getScheduleForYear(int year, String teacherFirstName, String teacherLastName) throws DAOException;

    /**
     * Inserts a teacher to the table.
     *
     * @param teacher - teacher
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean insert(Teacher teacher) throws DAOException;

    /**
     * Updates a recorded data by id.
     *
     * @param teacher - Teacher
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean update(Teacher teacher) throws DAOException;

    /**
     * Deletes a record.
     *
     * @param teacherFirstName - teacherFirstName
     * @param teacherLastName - teacherLastName
     * @return - Teacher
     * @throws DAOException - DAOException
     */
    Teacher delete(String teacherFirstName, String teacherLastName) throws DAOException;
}
