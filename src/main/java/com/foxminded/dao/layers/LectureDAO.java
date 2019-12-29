package com.foxminded.dao.layers;

import com.foxminded.domain.ClassRoom;
import com.foxminded.domain.Group;
import com.foxminded.domain.Lecture;
import com.foxminded.domain.Teacher;
import com.foxminded.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface LectureDAO {

    /**
     * Gets lecture by id.
     *
     * @param id - id
     * @return - Lecture
     * @throws DAOException - DAOException
     */
    Lecture getById(int id) throws DAOException;

    /**
     * Gets lecture by name.
     *
     * @param name - name
     * @return - Lecture
     * @throws DAOException - DAOException
     */
    Lecture getByName(String name) throws DAOException;

    /**
     * Gets lectures by group.
     *
     * @param group - group
     * @return - List<Lecture>
     */
    List<Lecture> getLecturesByGroup(Group group);

    /**
     * Gets lectures by teacher.
     *
     * @param teacher - teacher
     * @return - List<Lecture>
     */
    List<Lecture> getLectureByTeacher(Teacher teacher);

    /**
     * Gets lectures by year.
     *
     * @param year - year (yyyy)
     * @return - List<Lecture>
     */
    List<Lecture> getLecturesByYear(int year);

    /**
     * Gets lectures by month.
     *
     * @param month - month (1-12)
     * @param year - year (yyyy)
     * @return - List<Lecture>
     */
    List<Lecture> getLecturesByMonth(int month, int year);

    /**
     * Gets lectures by year and group.
     *
     * @param year - year (yyyy)
     * @param group - group
     * @return - List<Lecture>
     */
    List<Lecture> getLecturesByGroupForYear(int year, Group group);

    /**
     * Gets lectures by month and teacher.
     *
     * @param year - year (yyyy)
     * @param teacher - teacher
     * @return - List<Lecture>
     */
    List<Lecture> getLecturesByTeacherForYear(int year, Teacher teacher);

    /**
     * Gets lectures by year and teacher.
     *
     * @param month - month (1-12)
     * @param year - year (yyyy)
     * @param teacher - teacher
     * @return - List<Lecture>
     */
    List<Lecture> getLecturesByTeacherForMonth(int month, int year, Teacher teacher);

    /**
     * Gets lectures by month and group.
     *
     * @param month - month (1-12)
     * @param year - year (yyyy)
     * @param group - group
     * @return - List<Lecture>
     */
    List<Lecture> getLecturesByGroupForMonth(int month, int year, Group group);

    /**
     * Gets all lectures.
     *
     * @return - List<Lecture>
     */
    List<Lecture> getAll();

    /**
     * Changes a teacher and course in the lecture.
     *
     * @param lecture - lecture
     * @param teacher - teacher
     * @return - boolean
     */
    boolean changeTeacher(Lecture lecture, Teacher teacher);

    /**
     * Changes a class room in the lecture.
     *
     * @param lecture - lecture
     * @param classRoom - classRoom
     * @return - boolean
     */
    boolean changeClassRoom(Lecture lecture, ClassRoom classRoom);

    /**
     * Changes a group in the lecture.
     *
     * @param lecture - lecture
     * @param group - group
     * @return - boolean
     */
    boolean changeGroup(Lecture lecture, Group group);

    /**
     * Inserts a lecture to the table.
     *
     * @param lecture - lecture
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean insert(Lecture lecture) throws DAOException;

    /**
     * Updates a recorded data.
     *
     * @param lecture - Lecture
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean update(Lecture lecture) throws DAOException;

    /**
     * Deletes a record from the table.
     *
     * @param lecture - lecture
     * @return - Lecture
     * @throws DAOException - DAOException
     */
    Lecture delete(Lecture lecture) throws DAOException;
}
