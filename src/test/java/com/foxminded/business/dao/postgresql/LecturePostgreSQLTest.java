package com.foxminded.business.dao.postgresql;

import com.foxminded.business.dao.ExecutorQuery;
import com.foxminded.business.dao.layers.*;
import com.foxminded.business.exceptions.DAOException;
import com.foxminded.business.model.*;
import com.foxminded.test.SpringTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.foxminded.business.constants.Constants.NULL_WAS_PASSED;
import static com.google.inject.internal.util.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
class LecturePostgreSQLTest {
    public static final String FIRST_NAME_ONE = "firstNameOne";
    public static final String FIRST_NAME_TWO = "firstNameTwo";
    public static final String FIRST_NAME_THREE = "firstNameThree";
    public static final String LAST_NAME_ONE = "lastNameOne";
    public static final String LAST_NAME_TWO = "lastNameTwo";
    public static final String LAST_NAME_THREE = "lastNameThree";

    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";
    public static final String TEST_NAME_THREE = "testNameThree";

    public static final LocalDateTime DATE_ONE = LocalDateTime.of(2018, Month.FEBRUARY, 1, 9, 0);
    public static final LocalDateTime DATE_TWO = LocalDateTime.of(2019, Month.FEBRUARY, 2, 11, 0);
    public static final LocalDateTime DATE_THREE = LocalDateTime.of(2019, Month.MAY, 2, 13, 0);

    public Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
    public Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
    public Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

    public Group groupOne = new Group(TEST_NAME_ONE);
    public Group groupTwo = new Group(TEST_NAME_TWO);
    public Group groupThree = new Group(TEST_NAME_THREE);

    public Department departmentOne = new Department(TEST_NAME_ONE);
    public Department departmentTwo = new Department(TEST_NAME_TWO);
    public Department departmentThree = new Department(TEST_NAME_THREE);

    public Course courseOne = new Course(TEST_NAME_ONE);
    public Course courseTwo = new Course(TEST_NAME_TWO);
    public Course courseThree = new Course(TEST_NAME_THREE);

    public ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
    public ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
    public ClassRoom classRoomThree = new ClassRoom(204, TEST_NAME_THREE, 300);

    public ApplicationContext context = new AnnotationConfigApplicationContext(SpringTestConfig.class);
    public LectureDAO lectureDAO = context.getBean("lectureDAO", LecturePostgreSQL.class);
    public ClassRoomDAO classRoomDAO = context.getBean("classRoomDAO", ClassRoomPostgreSQL.class);
    public DepartmentDAO departmentDAO = context.getBean("departmentDAO", DepartmentPostgreSQL.class);
    public CourseDAO courseDAO = context.getBean("courseDAO", CoursePostgreSQL.class);
    public GroupDAO groupDAO = context.getBean("groupDAO", GroupPostgreSQL.class);
    public TeacherDAO teacherDAO = context.getBean("teacherDAO", TeacherPostgreSQL.class);
    public ExecutorQuery executorQuery = context.getBean("executorQuery", ExecutorQuery.class);

    @BeforeEach
    public void setUp() throws DAOException {
        executorQuery.executeQuery("sql/dropDB.sql");
        executorQuery.executeQuery("sql/tablesCreation.sql");
        groupDAO.insert(groupOne);
        groupDAO.insert(groupTwo);
        groupDAO.insert(groupThree);
        departmentDAO.insert(departmentOne);
        departmentDAO.insert(departmentTwo);
        departmentDAO.insert(departmentThree);
        courseDAO.insert(courseOne);
        courseDAO.insert(courseTwo);
        courseDAO.insert(courseThree);
        classRoomDAO.insert(classRoomOne);
        classRoomDAO.insert(classRoomTwo);
        classRoomDAO.insert(classRoomThree);
        teacherDAO.insert(teacherOne);
        teacherDAO.insert(teacherTwo);
        teacherDAO.insert(teacherThree);

    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetAllLectures() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);

        List<Lecture> actual = lectureDAO.getAll();
        List<Lecture> expected = of(lectureOne, lectureTwo, lectureTree);

        assertEquals(expected, actual,
                "Should return corrected lectures when getById all lectures");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetLectureByName() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);

        Lecture actual = lectureDAO.getByName(TEST_NAME_TWO);

        assertEquals(lectureTwo, actual,
                "Should return corrected lecture when getByName()");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenChangeTeacher() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(4), new ClassRoom(202), new Group(1), new Course(2));
        teacherOne = new Teacher("dol", "bil", new Course(2), new Department(2));
        teacherDAO.insert(teacherOne);

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureTree.setId(1);
        lectureDAO.changeTeacher(lectureOne, teacherOne);

        Lecture actual = lectureDAO.getById(lectureOne.getId());

        assertEquals(lectureTree, actual,
                "Should return corrected lectures when changeTeacher()");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenChangeClassRoom() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(204), new Group(1), new Course(1));

        lectureDAO.insert(lectureOne);
        lectureTwo.setId(1);
        lectureDAO.changeClassRoom(lectureOne, classRoomThree);

        Lecture actual = lectureDAO.getById(lectureOne.getId());

        assertEquals(lectureTwo, actual,
                "Should return corrected lectures when changeClassRoom()");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenChangeGroup() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(2), new Course(1));

        lectureDAO.insert(lectureOne);
        lectureTwo.setId(1);
        lectureDAO.changeGroup(lectureOne, groupTwo);

        Lecture actual = lectureDAO.getById(lectureOne.getId());

        assertEquals(lectureTwo, actual,
                "Should return corrected lectures when changeGroup()");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetByGroup() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);

        List<Lecture> actual = lectureDAO.getLecturesByGroup(groupOne);
        List<Lecture> expected = of(lectureOne, lectureTwo);

        assertEquals(expected, actual,
                "Should return corrected lectures when get lectures by group id");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetByTeacher() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(3), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);

        List<Lecture> actual = lectureDAO.getLectureByTeacher(teacherThree);
        List<Lecture> expected = of(lectureOne, lectureTree);

        assertEquals(expected, actual,
                "Should return corrected lectures when get lectures by teacher id");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetByYear() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(3), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);

        List<Lecture> actual = lectureDAO.getLecturesByYear(2019);
        List<Lecture> expected = of(lectureTwo, lectureTree);

        assertEquals(expected, actual,
                "Should return corrected lectures when get lectures by year");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetByMonth() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(3), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);

        List<Lecture> actual = lectureDAO.getLecturesByMonth(5, 2019);
        List<Lecture> expected = of(lectureTree);

        assertEquals(expected, actual,
                "Should return corrected lectures when get lectures by month");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetByGroupForYear() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(3), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);

        List<Lecture> actual = lectureDAO.getLecturesByGroupForYear(2018, groupOne);
        List<Lecture> expected = of(lectureOne);

        assertEquals(expected, actual,
                "Should return corrected lectures when get lectures by group for year");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetByGroupForMonth() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(3), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(1), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);

        List<Lecture> actual = lectureDAO.getLecturesByGroupForMonth(2, 2019, groupOne);
        List<Lecture> expected = of(lectureTwo);

        assertEquals(expected, actual,
                "Should return corrected lectures when get lectures by group for month");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetByTeacherForMonth() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(3), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(1), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);

        List<Lecture> actual = lectureDAO.getLecturesByTeacherForMonth(2, 2019, teacherTwo);
        List<Lecture> expected = of(lectureTwo);

        assertEquals(expected, actual,
                "Should return corrected lectures when get lectures by teacher for month");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetByTeacherForYear() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(2), new ClassRoom(204), new Group(3), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);

        List<Lecture> actual = lectureDAO.getLecturesByTeacherForYear(2019, teacherTwo);
        List<Lecture> expected = of(lectureTwo, lectureTree);

        assertEquals(expected, actual,
                "Should return corrected lectures when get lectures by teacher for year");
    }

    @Test
    public void shouldReturnCorrectedLectureWhenUpdateLecture() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureTree.setId(2);
        lectureDAO.update(lectureTree);

        List<Lecture> actual = lectureDAO.getAll();
        List<Lecture> expected = of(lectureOne, lectureTree);

        assertEquals(expected, actual,
                "Should return corrected lectures when update lecture");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenDeleteOneAndGetAllLectures() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureDAO.insert(lectureOne);
        lectureDAO.insert(lectureTwo);
        lectureDAO.insert(lectureTree);
        lectureDAO.delete(lectureTwo);

        List<Lecture> actual = lectureDAO.getAll();
        List<Lecture> expected = of(lectureOne, lectureTree);

        assertEquals(expected, actual,
                "Should return corrected lectures when getById all lectures after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetByName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.getByName(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToDelete() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.delete(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToChangeClassRoom() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.changeClassRoom(null, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetLectureByTeacher() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.getLectureByTeacher(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToChangeGroup() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.changeGroup(null, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetLecturesByGroupForMonth() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.getLecturesByGroupForMonth(1, 1, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetLecturesByTeacherForMonth() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.getLecturesByTeacherForMonth(1, 1, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetLecturesByGroupForYear() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.getLecturesByGroupForYear(1, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetLecturesByTeacherForYear() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.getLecturesByTeacherForYear(1, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToChangeTeacher() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureDAO.changeTeacher(null, null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenTryToInsertDuplicateWithTheSameName() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        lectureDAO.insert(lectureOne);

        Exception exception = assertThrows(DAOException.class, () ->
                lectureDAO.insert(lectureOne));
        assertEquals("Unique index or primary key violation", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomById() {
        Exception exception = assertThrows(DAOException.class, () ->
                lectureDAO.getById(666));
        assertEquals("Can't find lecture by passed id", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomByName() {
        Exception exception = assertThrows(DAOException.class, () ->
                lectureDAO.getByName("666"));
        assertEquals("Can't find lecture by passed name", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenUpdate() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        lectureDAO.insert(lectureOne);

        Exception exception = assertThrows(DAOException.class, () ->
                lectureDAO.update(lectureTwo));
        assertEquals("The lecture does not exist", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenDelete() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        lectureDAO.insert(lectureOne);

        Exception exception = assertThrows(DAOException.class, () ->
                lectureDAO.delete(lectureTwo));
        assertEquals("The lecture does not exist", exception.getMessage());
    }
}