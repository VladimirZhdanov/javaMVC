package com.foxminded.service;

import com.foxminded.dao.layers.ClassRoomDAO;
import com.foxminded.dao.layers.CourseDAO;
import com.foxminded.dao.layers.GroupDAO;
import com.foxminded.dao.layers.LectureDAO;
import com.foxminded.dao.layers.TeacherDAO;
import com.foxminded.domain.ClassRoom;
import com.foxminded.domain.Group;
import com.foxminded.domain.Lecture;
import com.foxminded.domain.Teacher;
import com.foxminded.exceptions.DAOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foxminded.service.layers.LectureService;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Component
@Service("lectureManager")
public class LectureServiceImp implements LectureService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LectureServiceImp.class);

    private LectureDAO lectureDAO;
    private TeacherDAO teacherDAO;
    private GroupDAO groupDAO;
    private ClassRoomDAO classRoomDAO;
    private CourseDAO courseDAO;

    @Autowired
    public LectureServiceImp(LectureDAO lectureDAO, TeacherDAO teacherDAO, GroupDAO groupDAO, ClassRoomDAO classRoomDAO, CourseDAO courseDAO) {
        this.lectureDAO = lectureDAO;
        this.teacherDAO = teacherDAO;
        this.groupDAO = groupDAO;
        this.classRoomDAO = classRoomDAO;
        this.courseDAO = courseDAO;
    }

    /**
     * Gets lecture by id.
     *
     * @param id - id
     * @return - Lecture
     */
    @Override
    @Transactional(readOnly = true)
    public Lecture getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        return lectureDAO.getById(id);
    }

    /**
     * Gets all lectures.
     *
     * @return - List<Lecture>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Lecture> getAll() {
        return lectureDAO.getAll();
    }

    /**
     * Changes a teacher and course in the lecture.
     *
     * @param lectureName - lectureName
     * @param teacherFirstName - teacherName
     * @param teacherLastName - teacherName
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean changeTeacher(String lectureName, String teacherFirstName, String teacherLastName) throws DAOException {
        LOGGER.debug("Invoke method changeTeacher({}, {}, {})", lectureName, teacherFirstName, teacherLastName);
        Teacher teacher = teacherDAO.getByName(teacherFirstName, teacherLastName);
        Lecture lecture = lectureDAO.getByName(lectureName);
        return lectureDAO.changeTeacher(lecture, teacher);
    }

    /**
     * Changes a class room in the lecture.
     *
     * @param lectureName - lectureName
     * @param classRoomName - classRoomName
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean changeClassRoom(String lectureName, String classRoomName) throws DAOException {
        LOGGER.debug("Invoke method changeClassRoom({}, {})", lectureName, classRoomName);
        Lecture lecture = lectureDAO.getByName(lectureName);
        ClassRoom classRoom = classRoomDAO.getByName(classRoomName);
        return lectureDAO.changeClassRoom(lecture, classRoom);
    }

    /**
     * Changes a group in the lecture.
     *
     * @param lectureName - lectureName
     * @param groupName - groupName
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean changeGroup(String lectureName, String groupName) throws DAOException {
        LOGGER.debug("Invoke method changeGroup({}, {})", lectureName, groupName);
        Lecture lecture = lectureDAO.getByName(lectureName);
        Group group = groupDAO.getByName(groupName);
        return lectureDAO.changeGroup(lecture, group);
    }

    /**
     * Inserts a lecture to the table.
     *
     * @param lecture - lecture
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean insert(Lecture lecture) throws DAOException {
        LOGGER.debug("Invoke method insert({})", lecture);
        return lectureDAO.insert(lecture);
    }

    /**
     * Updates a recorded data.
     *
     * @param lecture - Lecture
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean update(Lecture lecture) throws DAOException {
        LOGGER.debug("Invoke method update({})", lecture);
        return lectureDAO.update(lecture);
    }

    /**
     * Deletes a record.
     *
     * @param lectureName - lectureName
     * @return - Lecture
     */
    @Override
    @Transactional
    public Lecture delete(String lectureName) throws DAOException {
        LOGGER.debug("Invoke method delete({})", lectureName);
        Lecture lecture = lectureDAO.getByName(lectureName);
        return lectureDAO.delete(lecture);
    }
}

