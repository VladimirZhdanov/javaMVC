package com.foxminded.business.dao.postgresql;

import com.foxminded.business.dao.ExecutorQuery;
import com.foxminded.business.dao.layers.ClassRoomDAO;
import com.foxminded.business.exceptions.DAOException;
import com.foxminded.business.model.ClassRoom;
import com.foxminded.test.SpringTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static com.foxminded.business.constants.Constants.NULL_WAS_PASSED;
import static com.google.inject.internal.util.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
class ClassRoomPostgreSQLTest {
    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";
    public static final String TEST_NAME_THREE = "testNameThree";

    public ApplicationContext context = new AnnotationConfigApplicationContext(SpringTestConfig.class);
    public ClassRoomDAO classRoomDAO = context.getBean("classRoomDAO", ClassRoomPostgreSQL.class);
    public ExecutorQuery executorQuery = context.getBean("executorQuery", ExecutorQuery.class);

    @BeforeEach
    public void setUp() {
        executorQuery.executeQuery("sql/dropDB.sql");
        executorQuery.executeQuery("sql/tablesCreation.sql");
    }

    @Test
    public void shouldReturnCorrectedClassRoomsWhenGetAllClassRooms() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
        ClassRoom classRoomThree = new ClassRoom(204, TEST_NAME_THREE, 300);

        classRoomDAO.insert(classRoomOne);
        classRoomDAO.insert(classRoomTwo);
        classRoomDAO.insert(classRoomThree);

        List<ClassRoom> actual = classRoomDAO.getAll();
        List<ClassRoom> expected = of(classRoomOne, classRoomTwo, classRoomThree);

        assertEquals(expected, actual,
                "Should return corrected class rooms when getById all class rooms");
    }

    @Test
    public void shouldReturnCorrectedClassRoomWhenGetClassRoomsById() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
        ClassRoom classRoomThree = new ClassRoom(204, TEST_NAME_THREE, 300);

        classRoomDAO.insert(classRoomOne);
        classRoomDAO.insert(classRoomTwo);
        classRoomDAO.insert(classRoomThree);

        ClassRoom actual = classRoomDAO.getById(203);

        assertEquals(classRoomTwo, actual,
                "Should return corrected class room when getById class room by id");
    }

    @Test
    public void shouldReturnCorrectedClassRoomWhenUpdateClassroom() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
        ClassRoom classRoomThree = new ClassRoom(203, TEST_NAME_THREE, 300);

        classRoomDAO.insert(classRoomOne);
        classRoomDAO.insert(classRoomTwo);
        classRoomDAO.update(classRoomThree);

        List<ClassRoom> actual = classRoomDAO.getAll();
        List<ClassRoom> expected = of(classRoomOne, classRoomThree);

        assertEquals(expected, actual,
                "Should return corrected class room when update class room");
    }

    @Test
    public void shouldReturnCorrectedClassRoomWhenGetByName() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
        ClassRoom classRoomThree = new ClassRoom(204, TEST_NAME_THREE, 300);

        classRoomDAO.insert(classRoomOne);
        classRoomDAO.insert(classRoomTwo);
        classRoomDAO.insert(classRoomThree);

        ClassRoom actual = classRoomDAO.getByName(TEST_NAME_TWO);

        assertEquals(classRoomTwo, actual,
                "Should return corrected class rooms when getByName()");
    }

    @Test
    public void shouldReturnCorrectedClassRoomsWhenDeleteOneAndGetAllClassRooms() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
        ClassRoom classRoomThree = new ClassRoom(204, TEST_NAME_THREE, 300);

        classRoomDAO.insert(classRoomOne);
        classRoomDAO.insert(classRoomTwo);
        classRoomDAO.insert(classRoomThree);
        classRoomDAO.delete(classRoomTwo);

        List<ClassRoom> actual = classRoomDAO.getAll();
        List<ClassRoom> expected = of(classRoomOne, classRoomThree);

        assertEquals(expected, actual,
                "Should return corrected class rooms when getById all class rooms after delete one");
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                classRoomDAO.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                classRoomDAO.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenNullWasPassedToGetByName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                classRoomDAO.getByName(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenNullWasPassedToDelete() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                classRoomDAO.delete(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenTryToInsertDuplicateWithTheSameName() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_ONE, 200);
        classRoomDAO.insert(classRoomOne);

        Exception exception = assertThrows(DAOException.class, () ->
                classRoomDAO.insert(classRoomTwo));
        assertEquals("Unique index or primary key violation", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenTryToInsertDuplicateWithTheSameId() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(202, TEST_NAME_TWO, 200);
        classRoomDAO.insert(classRoomOne);

        Exception exception = assertThrows(DAOException.class, () ->
                classRoomDAO.insert(classRoomTwo));
        assertEquals("Unique index or primary key violation", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomById() {
        Exception exception = assertThrows(DAOException.class, () ->
                classRoomDAO.getById(666));
        assertEquals("Can't find class room by passed id", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomByName() {
        Exception exception = assertThrows(DAOException.class, () ->
                classRoomDAO.getByName("666"));
        assertEquals("Can't find class room by passed name", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenUpdate() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 300);
        classRoomDAO.insert(classRoomOne);

        Exception exception = assertThrows(DAOException.class, () ->
                classRoomDAO.update(classRoomTwo));
        assertEquals("The class room does not exist", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenDelete() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 300);
        classRoomDAO.insert(classRoomOne);

        Exception exception = assertThrows(DAOException.class, () ->
                classRoomDAO.delete(classRoomTwo));
        assertEquals("The class room does not exist", exception.getMessage());
    }
}