package com.foxminded.dao.layers;

import com.foxminded.domain.Course;
import com.foxminded.domain.Group;
import com.foxminded.domain.Student;
import com.foxminded.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface StudentDAO {

    /**
     * Gets student by id.
     *
     * @param id - id
     * @return - Student
     */
    Student getById(int id) throws DAOException;

    /**
     * Gets student by name.
     *
     * @param firstName - firstName
     * @param lastName - lastName
     * @return - Student
     */
    Student getByName(String firstName, String lastName) throws DAOException;

    /**
     * Gets students by course.
     *
     * @param course - course
     * @return - List<Student>
     */
    List<Student> getStudentsByCourse(Course course);

    /**
     * Gets students by group.
     *
     * @param group - group
     * @return - List<Student>
     */
    List<Student> getStudentsByGroup(Group group);

    /**
     * Gets all students.
     *
     * @return - List<Student>
     */
    List<Student> getAll();

    /**
     * Gets all related courses by student.
     *
     * @param student - student
     * @return - List<Course>
     */
    List<Course> getCoursesByStudent(Student student);

    /**
     * Changes a group at the student.
     *
     * @param student - student
     * @param group - group
     */
    boolean changeGroup(Student student, Group group);

    /**
     * Inserts student to the table.
     *
     * @param student - student
     * @return - boolean
     */
    boolean insert(Student student) throws DAOException;

    /**
     * Inserts relationship: Student - Course.
     *
     * @param students - students with relationship: Student - Course
     */
    void insertRelationshipStudentsToCourses(List<Student> students);

    /**
     * Inserts a course to a student.
     *
     * @param student - student
     * @param course - student
     * @return - added\didn't add - boolean
     */
    boolean insertCourseToStudent(Student student, Course course) throws DAOException;

    /**
     * Updates a recorded data by id.
     *
     * @param newStudent - Student
     * @return - boolean
     */
    boolean update(Student newStudent) throws DAOException;

    /**
     * Deletes a record from the table.
     *
     * @param student - student
     * @return - Student
     * @throws DAOException - DAOException
     */
    Student delete(Student student) throws DAOException;
}
