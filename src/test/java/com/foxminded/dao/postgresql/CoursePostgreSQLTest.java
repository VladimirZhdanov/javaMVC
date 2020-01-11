package com.foxminded.dao.postgresql;

import com.foxminded.configs.SpringTestConfig;
import com.foxminded.dao.ExecutorQuery;
import com.foxminded.dao.layers.CourseDAO;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
@ContextConfiguration(classes = {SpringTestConfig.class})
class CoursePostgreSQLTest {
    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";
    public static final String TEST_NAME_THREE = "testNameThree";

    public CourseDAO courseDAO;
    public ExecutorQuery executorQuery;

    @Autowired
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Autowired
    public void setExecutorQuery(ExecutorQuery executorQuery) {
        this.executorQuery = executorQuery;
    }

    @BeforeEach
    public void setUp() {
        executorQuery.executeQuery("sql/dropDB.sql");
        executorQuery.executeQuery("sql/tablesCreation.sql");
    }

    @Test
    public void shouldReturnCorrectedCoursesWhenGetAllCourses() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        Course courseThree = new Course(TEST_NAME_THREE);

        courseDAO.insert(courseOne);
        courseDAO.insert(courseTwo);
        courseDAO.insert(courseThree);

        List<Course> actual = courseDAO.getAll();
        List<Course> expected = of(courseOne, courseTwo, courseThree);

        assertEquals(expected, actual,
                "Should return corrected courses when get all courses");
    }

    @Test
    public void shouldReturnCorrectedCoursesWhenGetCourseByName() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        Course courseThree = new Course(TEST_NAME_THREE);

        courseDAO.insert(courseOne);
        courseDAO.insert(courseTwo);
        courseDAO.insert(courseThree);

        Course actual = courseDAO.getByName(TEST_NAME_TWO);


        assertEquals(courseTwo, actual,
                "Should return corrected course when getByName()");
    }

    @Test
    public void shouldReturnCorrectedCourseWhenGetCourseById() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        Course courseThree = new Course(TEST_NAME_THREE);

        courseDAO.insert(courseOne);
        courseDAO.insert(courseTwo);
        courseDAO.insert(courseThree);

        Course actual = courseDAO.getById(2);

        assertEquals(courseTwo, actual,
                "Should return corrected course when get course by id");
    }

    @Test
    public void shouldReturnCorrectedCourseWhenUpdateCourse() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        Course courseThree = new Course(TEST_NAME_THREE);

        courseDAO.insert(courseOne);
        courseDAO.insert(courseTwo);
        courseThree.setId(2);
        courseDAO.update(courseThree);

        List<Course> actual = courseDAO.getAll();
        List<Course> expected = of(courseOne, courseThree);

        assertEquals(expected, actual,
                "Should return corrected courses when update course");
    }

    @Test
    public void shouldReturnCorrectedCoursesWhenDeleteOneAndGetAllCourses() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        Course courseThree = new Course(TEST_NAME_THREE);

        courseDAO.insert(courseOne);
        courseDAO.insert(courseTwo);
        courseDAO.insert(courseThree);
        courseDAO.delete(courseTwo);

        List<Course> actual = courseDAO.getAll();
        List<Course> expected = of(courseOne, courseThree);

        assertEquals(expected, actual,
                "Should return corrected courses when getById all courses after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseDAO.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseDAO.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetByName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseDAO.getByName(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToDelete() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseDAO.delete(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenTryToInsertDuplicateWithTheSameName() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        courseDAO.insert(courseOne);

        Exception exception = assertThrows(DAOException.class, () ->
                courseDAO.insert(courseOne));
        assertEquals("Unique index or primary key violation", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomById() {
        Exception exception = assertThrows(DAOException.class, () ->
                courseDAO.getById(666));
        assertEquals("Can't find course by passed id", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomByName() {
        Exception exception = assertThrows(DAOException.class, () ->
                courseDAO.getByName("666"));
        assertEquals("Can't find course by passed name", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenUpdate() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        courseDAO.insert(courseOne);

        Exception exception = assertThrows(DAOException.class, () ->
                courseDAO.update(courseTwo));
        assertEquals("The course does not exist", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenDelete() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        courseDAO.insert(courseOne);

        Exception exception = assertThrows(DAOException.class, () ->
                courseDAO.delete(courseTwo));
        assertEquals("The course does not exist", exception.getMessage());
    }
}