package com.foxminded.service.layers;

import com.foxminded.domain.Lecture;
import com.foxminded.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface LectureService {

    /**
     * Gets lecture by id.
     *
     * @param id - id
     * @return - Lecture
     * @throws DAOException - DAOException
     */
    Lecture getById(int id) throws DAOException;

    /**
     * Gets all lectures.
     *
     * @return - List<Lecture>
     */
    List<Lecture> getAll();

    /**
     * Changes a teacher and course in the lecture.
     *
     * @param lectureName - lectureName
     * @param teacherFirstName - teacherName
     * @param teacherLastName - teacherName
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean changeTeacher(String lectureName, String teacherFirstName, String teacherLastName) throws DAOException;

    /**
     * Changes a class room in the lecture.
     *
     * @param lectureName - lectureName
     * @param classRoomName - classRoomName
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean changeClassRoom(String lectureName, String classRoomName) throws DAOException;

    /**
     * Changes a group in the lecture.
     *
     * @param lectureName - lectureName
     * @param groupName - groupName
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean changeGroup(String lectureName, String groupName) throws DAOException;

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
     * Deletes a record.
     *
     * @param lectureName - lectureName
     * @return - Lecture
     * @throws DAOException - DAOException
     */
    Lecture delete(String lectureName) throws DAOException;
}
