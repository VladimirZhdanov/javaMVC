package com.foxminded.dao.postgresql;

import com.foxminded.dao.ExecutorQuery;
import com.foxminded.dao.layers.CourseDAO;
import com.foxminded.dao.layers.GroupDAO;
import com.foxminded.dao.layers.StudentDAO;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.Course;
import com.foxminded.model.Group;
import com.foxminded.model.Student;
import com.foxminded.model.StudentCourse;
import com.foxminded.configs.SpringTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static com.foxminded.constants.Constants.NULL_WAS_PASSED;
import static com.google.inject.internal.util.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringTestConfig.class })
class StudentPostgreSQLTest {
    public static final String FIRST_NAME_ONE = "firstNameOne";
    public static final String FIRST_NAME_TWO = "firstNameTwo";
    public static final String FIRST_NAME_THREE = "firstNameThree";
    public static final String LAST_NAME_ONE = "lastNameOne";
    public static final String LAST_NAME_TWO = "lastNameTwo";
    public static final String LAST_NAME_THREE = "lastNameThree";

    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";
    public static final String TEST_NAME_THREE = "testNameThree";

    public Group groupOne = new Group(TEST_NAME_ONE);
    public  Group groupTwo = new Group(TEST_NAME_TWO);
    public  Group groupThree = new Group(TEST_NAME_THREE);

    public  Course courseOne = new Course(TEST_NAME_ONE);
    public  Course courseTwo = new Course(TEST_NAME_TWO);
    public  Course courseThree = new Course(TEST_NAME_THREE);

    public GroupDAO groupDAO;
    public StudentDAO studentDAO;
    public CourseDAO courseDAO;
    public ExecutorQuery executorQuery;

    @Autowired
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Autowired
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Autowired
    public void setExecutorQuery(ExecutorQuery executorQuery) {
        this.executorQuery = executorQuery;
    }

    @BeforeEach
    public void setUp() throws DAOException {
        executorQuery.executeQuery("sql/dropDB.sql");
        executorQuery.executeQuery("sql/tablesCreation.sql");
        groupDAO.insert(groupOne);
        groupDAO.insert(groupTwo);
        groupDAO.insert(groupThree);
        courseDAO.insert(courseOne);
        courseDAO.insert(courseTwo);
        courseDAO.insert(courseThree);
    }

    @Test
    public void shouldReturnCorrectedDataWhenGetAllStudentCourse() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentDAO.insert(studentOne);
        studentDAO.insert(studentTwo);
        studentDAO.insert(studentThree);
        studentDAO.insertCourseToStudent(studentOne, courseTwo);
        studentDAO.insertCourseToStudent(studentOne, courseOne);

        List<StudentCourse> actual = studentDAO.getAllStudentCourse();
        List<StudentCourse> expected = of(new StudentCourse(1, 1), new StudentCourse(1, 2));

        assertEquals(expected, actual,
                "Should return corrected data when getAllStudentCourse()");
    }

    @Test
    public void shouldReturnCorrectedStudentsWhenGetAllStudents() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentDAO.insert(studentOne);
        studentDAO.insert(studentTwo);
        studentDAO.insert(studentThree);

        List<Student> actual = studentDAO.getAll();
        List<Student> expected = of(studentOne, studentTwo, studentThree);

        assertEquals(expected, actual,
                "Should return corrected students when getById all students");
    }

    @Test
    public void shouldReturnCorrectedStudentsWhenGetStudentByName() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentDAO.insert(studentOne);
        studentDAO.insert(studentTwo);
        studentDAO.insert(studentThree);

        Student actual = studentDAO.getByName(FIRST_NAME_TWO, LAST_NAME_TWO);

        assertEquals(studentTwo, actual,
                "Should return corrected student when getByName()");
    }

    @Test
    public void shouldReturnCorrectedStudentsWhenChangeGroup() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(2));

        studentDAO.insert(studentOne);
        studentTwo.setId(1);
        studentDAO.changeGroup(studentOne, groupTwo);

        Student actual = studentDAO.getById(studentOne.getId());

        assertEquals(studentTwo, actual,
                "Should return corrected students when changeGroup()");
    }

    @Test
    public void shouldReturnCorrectedStudentsWhenGetByCourseId() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentDAO.insert(studentOne);
        studentDAO.insert(studentTwo);
        studentDAO.insert(studentThree);
        studentDAO.insertCourseToStudent(studentOne, courseOne);
        studentDAO.insertCourseToStudent(studentThree, courseOne);

        List<Student> actual = studentDAO.getStudentsByCourse(courseOne);
        List<Student> expected = of(studentOne, studentThree);

        assertEquals(expected, actual,
                "Should return corrected students when get students by course id");
    }

    @Test
    public void shouldReturnCorrectedStudentsWhenGetByGroupId() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(1));

        studentDAO.insert(studentOne);
        studentDAO.insert(studentTwo);
        studentDAO.insert(studentThree);

        List<Student> actual = studentDAO.getStudentsByGroup(groupOne);
        List<Student> expected = of(studentOne, studentThree);

        assertEquals(expected, actual,
                "Should return corrected students when get students by group id");
    }

    @Test
    public void shouldReturnCorrectedCoursesWhenGetCoursesById() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1), of(courseOne, courseThree));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2), of(courseTwo, courseThree));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3), of(courseThree));

        studentDAO.insert(studentOne);
        studentDAO.insert(studentTwo);
        studentDAO.insert(studentThree);
        studentDAO.insertRelationshipStudentsToCourses(Arrays.asList(studentOne, studentTwo, studentThree));


        List<Course> actual = studentDAO.getCoursesByStudent(studentOne);
        List<Course> expected = of(courseOne, courseThree);

        assertEquals(expected, actual,
                "Should return corrected courses when get courses by student id");
    }

    @Test
    public void shouldReturnCorrectedCoursesWhenGetCoursesByIdAfterInsertCourseToStudent() throws DAOException {
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2), of(courseTwo, courseThree));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentDAO.insert(studentThree);
        studentDAO.insert(studentTwo);
        studentDAO.insertCourseToStudent(studentTwo, courseThree);
        studentDAO.insertCourseToStudent(studentTwo, courseTwo);
        studentDAO.insertCourseToStudent(studentThree, courseThree);

        List<Course> actual = studentDAO.getCoursesByStudent(studentTwo);
        List<Course> expected = of(courseTwo, courseThree);

        assertEquals(expected, actual,
                "Should return corrected courses when get courses by student id after insertCourseToStudent()");
    }

    @Test
    public void shouldReturnCorrectedStudentWhenUpdateStudent() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentDAO.insert(studentOne);
        studentDAO.insert(studentTwo);
        studentThree.setId(2);
        studentDAO.update(studentThree);

        List<Student> actual = studentDAO.getAll();
        List<Student> expected = of(studentOne, studentThree);

        assertEquals(expected, actual,
                "Should return corrected students when update student");
    }

    @Test
    public void shouldReturnCorrectedStudentsWhenDeleteOneAndGetAllStudents() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentDAO.insert(studentOne);
        studentDAO.insert(studentTwo);
        studentDAO.insert(studentThree);
        studentDAO.delete(studentTwo);

        List<Student> actual = studentDAO.getAll();
        List<Student> expected = of(studentOne, studentThree);

        assertEquals(expected, actual,
                "Should return corrected students when getById all students after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentDAO.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentDAO.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetByName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentDAO.getByName(null, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToDelete() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentDAO.delete(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertCourseToStudent() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentDAO.insertCourseToStudent(null, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetCoursesByStudent() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentDAO.getCoursesByStudent(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToChangeGroup() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentDAO.changeGroup(null, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetStudentsByCourse() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentDAO.getStudentsByCourse(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetStudentsByGroup() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentDAO.getStudentsByGroup(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenTryToInsertDuplicateWithTheSameName() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        studentDAO.insert(studentOne);

        Exception exception = assertThrows(DAOException.class, () ->
                studentDAO.insert(studentOne));
        assertEquals("Unique index or primary key violation", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomById() {
        Exception exception = assertThrows(DAOException.class, () ->
                studentDAO.getById(666));
        assertEquals("Can't find student by passed id", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomByName() {
        Exception exception = assertThrows(DAOException.class, () ->
                studentDAO.getByName("666", "777"));
        assertEquals("Can't find student by passed name", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenUpdate() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        studentDAO.insert(studentOne);

        Exception exception = assertThrows(DAOException.class, () ->
                studentDAO.update(studentTwo));
        assertEquals("The student does not exist", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenDelete() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        studentDAO.insert(studentOne);

        Exception exception = assertThrows(DAOException.class, () ->
                studentDAO.delete(studentTwo));
        assertEquals("The student does not exist", exception.getMessage());
    }
}