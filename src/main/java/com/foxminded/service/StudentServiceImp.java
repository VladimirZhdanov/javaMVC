package com.foxminded.service;

import com.foxminded.dao.layers.GroupDAO;
import com.foxminded.dao.layers.LectureDAO;
import com.foxminded.dao.layers.StudentDAO;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.*;
import com.foxminded.service.layers.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Component
@Service("studentService")
public class StudentServiceImp implements StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImp.class);

    private StudentDAO studentDAO;
    private GroupDAO groupDAO;
    private LectureDAO lectureDAO;

    @Autowired
    public StudentServiceImp(StudentDAO studentDAO, GroupDAO groupDAO, LectureDAO lectureDAO) {
        this.studentDAO = studentDAO;
        this.groupDAO = groupDAO;
        this.lectureDAO = lectureDAO;
    }

    /**
     * Constructs a new object.
     */
    public StudentServiceImp() {
        super();
    }

    /**
     * Gets student by id.
     *
     * @param id - id
     * @return - Student
     */
    @Override
    @Transactional(readOnly = true)
    public Student getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        return studentDAO.getById(id);
    }

    /**
     * Gets all students.
     *
     * @return - List<Student>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> getAll() {
        return studentDAO.getAll();
    }

    /**
     * Gets all relationship between students and courses with ids.
     *
     * @return - List<StudentCourse>
     */
    @Override
    public List<StudentCourse> getAllStudentCourse() {
        return studentDAO.getAllStudentCourse();
    }

    /**
     * Gets all courses related to the student.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName  - studentLastName
     * @return - List<Course>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Course> getCourses(String studentFirstName, String studentLastName) throws DAOException {
        LOGGER.debug("Invoke method getCourses({}, {})", studentFirstName, studentLastName);
        Student student = studentDAO.getByName(studentFirstName, studentLastName);
        return studentDAO.getCoursesByStudent(student);
    }

    /**
     * Gets full schedule for the student.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName  - studentLastName
     * @return - Schedule
     */
    @Override
    @Transactional(readOnly = true)
    public Schedule getFullSchedule(String studentFirstName, String studentLastName) throws DAOException {
        LOGGER.debug("Invoke method getFullSchedule({}, {})", studentFirstName, studentLastName);
        Student student = studentDAO.getByName(studentFirstName, studentLastName);
        return new Schedule(lectureDAO.getLecturesByGroup(student.getGroup()));
    }

    /**
     * Gets month schedule for the student.
     *
     * @param month            - month(1-12)
     * @param year             - year(yyyy)
     * @param studentFirstName - studentFirstName
     * @param studentLastName  - studentLastName
     * @return - Schedule
     */
    @Override
    @Transactional(readOnly = true)
    public Schedule getScheduleForMonth(int month, int year, String studentFirstName, String studentLastName) throws DAOException {
        LOGGER.debug("Invoke method getScheduleForMonth({}, {}, {}, {})", month, year, studentFirstName, studentLastName);
        Student student = studentDAO.getByName(studentFirstName, studentLastName);
        return new Schedule(lectureDAO.getLecturesByGroupForMonth(month, year, student.getGroup()));
    }

    /**
     * Gets year schedule for the student.
     *
     * @param year             - year(yyyy)
     * @param studentFirstName - studentFirstName
     * @param studentLastName  - studentLastName
     * @return - Schedule
     */
    @Override
    @Transactional(readOnly = true)
    public Schedule getScheduleForYear(int year, String studentFirstName, String studentLastName) throws DAOException {
        LOGGER.debug("Invoke method getScheduleForYear({}, {}, {})", year, studentFirstName, studentLastName);
        Student student = studentDAO.getByName(studentFirstName, studentLastName);
        return new Schedule(lectureDAO.getLecturesByGroupForYear(year, student.getGroup()));
    }

    /**
     * Changes a group at the student.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName  - studentLastName
     * @param groupName        - groupName
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean changeGroup(String studentFirstName, String studentLastName, String groupName) throws DAOException {
        LOGGER.debug("Invoke method changeGroup({}, {}, {})", studentFirstName, studentLastName, groupName);
        Student student = studentDAO.getByName(studentFirstName, studentLastName);
        Group group = groupDAO.getByName(groupName);
        return studentDAO.changeGroup(student, group);
    }

    /**
     * Inserts student to the table.
     *
     * @param student - student
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean insert(Student student) throws DAOException {
        LOGGER.debug("Invoke method insert({})", student);
        return studentDAO.insert(student);
    }

    /**
     * Inserts a course to a student.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName  - studentLastName
     * @param course           - student
     * @return - added\didn't add - boolean
     */
    @Override
    @Transactional
    public boolean insertCourseToStudent(String studentFirstName, String studentLastName, Course course) throws DAOException {
        LOGGER.debug("Invoke method insertCourseToStudent({}, {}, {})", studentFirstName, studentLastName, course);
        Student student = studentDAO.getByName(studentFirstName, studentLastName);
        return studentDAO.insertCourseToStudent(student, course);
    }

    /**
     * Inserts relationship: Student - Course.
     *
     * @param students - students with relationship: Student - Course
     */
    @Override
    @Transactional
    public void insertRelationshipStudentsToCourses(List<Student> students) {
        LOGGER.debug("insert relationship students To courses student, size of students: {}", students.size());
        studentDAO.insertRelationshipStudentsToCourses(students);
    }

    /**
     * Updates a recorded data by id.
     *
     * @param student - Student
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean update(Student student) throws DAOException {
        LOGGER.debug("Invoke method update({})", student);
        return studentDAO.update(student);
    }

    /**
     * Deletes a record.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName  - studentLastName
     * @return - boolean
     */
    @Override
    @Transactional
    public Student delete(String studentFirstName, String studentLastName) throws DAOException {
        LOGGER.debug("Invoke method delete({}, {})", studentFirstName, studentLastName);
        Student student = studentDAO.getByName(studentFirstName, studentLastName);
        return studentDAO.delete(student);
    }
}
