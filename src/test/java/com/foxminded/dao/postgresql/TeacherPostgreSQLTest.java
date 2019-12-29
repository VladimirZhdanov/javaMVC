package com.foxminded.dao.postgresql;

import com.foxminded.dao.ExecutorQuery;
import com.foxminded.dao.layers.CourseDAO;
import com.foxminded.dao.layers.DepartmentDAO;
import com.foxminded.dao.layers.TeacherDAO;
import com.foxminded.domain.Course;
import com.foxminded.domain.Department;
import com.foxminded.domain.Group;
import com.foxminded.domain.Student;
import com.foxminded.domain.Teacher;
import com.foxminded.exceptions.DAOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static com.foxminded.constants.Constants.NULL_WAS_PASSED;
import static com.google.inject.internal.util.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
class TeacherPostgreSQLTest {
    public static final String FIRST_NAME_ONE = "firstNameOne";
    public static final String FIRST_NAME_TWO = "firstNameTwo";
    public static final String FIRST_NAME_THREE = "firstNameThree";
    public static final String LAST_NAME_ONE = "lastNameOne";
    public static final String LAST_NAME_TWO = "lastNameTwo";
    public static final String LAST_NAME_THREE = "lastNameThree";

    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";
    public static final String TEST_NAME_THREE = "testNameThree";

    public Course courseOne = new Course(TEST_NAME_ONE);
    public Course courseTwo = new Course(TEST_NAME_TWO);
    public Course courseThree = new Course(TEST_NAME_THREE);

    public Department departmentOne = new Department(TEST_NAME_ONE);
    public Department departmentTwo = new Department(TEST_NAME_TWO);
    public Department departmentThree = new Department(TEST_NAME_THREE);

    public ApplicationContext context = new ClassPathXmlApplicationContext("h2-config.xml");
    public TeacherDAO teacherDAO = (TeacherPostgreSQL) context.getBean("teacherPostgreSQL");
    public DepartmentDAO departmentDAO = (DepartmentPostgreSQL) context.getBean("departmentPostgreSQL");
    public CourseDAO courseDAO = (CoursePostgreSQL) context.getBean("coursePostgreSQL");
    public ExecutorQuery executorQuery = (ExecutorQuery) context.getBean("executorQuery");

    @BeforeEach
    public void setUp() throws DAOException {
        executorQuery.executeQuery("dropDB.sql");
        executorQuery.executeQuery("tablesCreation.sql");
        departmentDAO.insert(departmentOne);
        departmentDAO.insert(departmentTwo);
        departmentDAO.insert(departmentThree);
        courseDAO.insert(courseOne);
        courseDAO.insert(courseTwo);
        courseDAO.insert(courseThree);

    }

    @Test
    public void shouldReturnCorrectedTeachersWhenGetAllTeachers() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

        teacherDAO.insert(teacherOne);
        teacherDAO.insert(teacherTwo);
        teacherDAO.insert(teacherThree);

        List<Teacher> actual = teacherDAO.getAll();
        List<Teacher> expected = of(teacherOne, teacherTwo, teacherThree);

        assertEquals(expected, actual,
                "Should return corrected teachers when getById all teachers");
    }

    @Test
    public void shouldReturnCorrectedTeachersWhenGetTeacherByName() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

        teacherDAO.insert(teacherOne);
        teacherDAO.insert(teacherTwo);
        teacherDAO.insert(teacherThree);

        Teacher actual = teacherDAO.getByName(FIRST_NAME_TWO, LAST_NAME_TWO);

        assertEquals(teacherTwo, actual,
                "Should return corrected teacher when getByName()");
    }

    @Test
    public void shouldReturnCorrectedTeachersWhenGetByDepartmentId() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(2));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(3));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(2));

        teacherDAO.insert(teacherOne);
        teacherDAO.insert(teacherTwo);
        teacherDAO.insert(teacherThree);

        List<Teacher> actual = teacherDAO.getTeachersByDepartment(departmentTwo);
        List<Teacher> expected = of(teacherOne, teacherThree);

        assertEquals(expected, actual,
                "Should return corrected teachers when get by department id");
    }

    @Test
    public void shouldReturnCorrectedTeachersWhenUpdateTeacher() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

        teacherDAO.insert(teacherOne);
        teacherDAO.insert(teacherTwo);
        teacherThree.setId(2);
        teacherDAO.update(teacherThree);

        List<Teacher> actual = teacherDAO.getAll();
        List<Teacher> expected = of(teacherOne, teacherThree);

        assertEquals(expected, actual,
                "Should return corrected teachers when update teacher");
    }

    @Test
    public void shouldReturnCorrectedTeachersWhenDeleteOneAndGetAllTeachers() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

        teacherDAO.insert(teacherOne);
        teacherDAO.insert(teacherTwo);
        teacherDAO.insert(teacherThree);
        teacherDAO.delete(teacherTwo);

        List<Teacher> actual = teacherDAO.getAll();
        List<Teacher> expected = of(teacherOne, teacherThree);

        assertEquals(expected, actual,
                "Should return corrected teachers when getById all teachers after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                teacherDAO.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                teacherDAO.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetByName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                teacherDAO.getByName(null, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToDelete() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                teacherDAO.delete(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetTeachersByDepartment() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                teacherDAO.getTeachersByDepartment(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenTryToInsertDuplicateWithTheSameName() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        teacherDAO.insert(teacherOne);

        Exception exception = assertThrows(DAOException.class, () ->
                teacherDAO.insert(teacherOne));
        assertEquals("Unique index or primary key violation", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomById() {
        Exception exception = assertThrows(DAOException.class, () ->
                teacherDAO.getById(666));
        assertEquals("Can't find teacher by passed id", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomByName() {
        Exception exception = assertThrows(DAOException.class, () ->
                teacherDAO.getByName("666", "777"));
        assertEquals("Can't find teacher by passed name", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenUpdate() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        teacherDAO.insert(teacherOne);

        Exception exception = assertThrows(DAOException.class, () ->
                teacherDAO.update(teacherTwo));
        assertEquals("The teacher does not exist", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenDelete() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        teacherDAO.insert(teacherOne);

        Exception exception = assertThrows(DAOException.class, () ->
                teacherDAO.delete(teacherTwo));
        assertEquals("The teacher does not exist", exception.getMessage());
    }
}