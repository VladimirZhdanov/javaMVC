package com.foxminded.service.layers;

import com.foxminded.model.Course;
import com.foxminded.model.Schedule;
import com.foxminded.model.Student;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.StudentCourse;

import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface StudentService {
    /**
     * Gets student by id.
     *
     * @param id - id
     * @return - Student
     * @throws DAOException - DAOException
     */
    Student getById(int id) throws DAOException;

    /**
     * Gets all students.
     *
     * @return - List<Student>
     */
    List<Student> getAll();

    /**
     * Gets all relationship between students and courses with ids.
     *
     * @return - List<StudentCourse>
     */
    List<StudentCourse> getAllStudentCourse();

    /**
     * Gets all courses related to the student.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName - studentLastName
     * @return - List<Course>
     * @throws DAOException - DAOException
     */
    List<Course> getCourses(String studentFirstName, String studentLastName) throws DAOException;

    /**
     * Gets full schedule for the student.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName - studentLastName
     * @return - Schedule
     * @throws DAOException - DAOException
     */
    Schedule getFullSchedule(String studentFirstName, String studentLastName) throws DAOException;

    /**
     * Gets month schedule for the student.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName - studentLastName
     * @param month - month(1-12)
     * @param year - year(yyyy)
     * @return - Schedule
     * @throws DAOException - DAOException
     */
    Schedule getScheduleForMonth(int month, int year, String studentFirstName, String studentLastName) throws DAOException;

    /**
     * Gets year schedule for the student.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName - studentLastName
     * @param year - year(yyyy)
     * @return - Schedule
     * @throws DAOException - DAOException
     */
    Schedule getScheduleForYear(int year, String studentFirstName, String studentLastName) throws DAOException;

    /**
     * Changes a group at the student.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName - studentLastName
     * @param groupName - groupName
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean changeGroup(String studentFirstName, String studentLastName, String groupName) throws DAOException;

    /**
     * Inserts student to the table.
     *
     * @param student - student
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean insert(Student student) throws DAOException;

    /**
     * Inserts a course to a student.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName - studentLastName
     * @param course - student
     * @return - added\didn't add - boolean
     * @throws DAOException - DAOException
     */
    boolean insertCourseToStudent(String studentFirstName, String studentLastName, Course course) throws DAOException;

    /**
     * Inserts relationship: Student - Course.
     *
     * @param students - students with relationship: Student - Course
     */
    void insertRelationshipStudentsToCourses(List<Student> students);

    /**
     * Updates a recorded data by id.
     *
     * @param newStudent - Student
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean update(Student newStudent) throws DAOException;

    /**
     * Deletes a record.
     *
     * @param studentFirstName - studentFirstName
     * @param studentLastName - studentLastName
     * @return - Student
     * @throws DAOException - DAOException
     */
    Student delete(String studentFirstName, String studentLastName) throws DAOException;
}
