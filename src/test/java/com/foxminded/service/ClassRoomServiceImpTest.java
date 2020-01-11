package com.foxminded.service;

import com.foxminded.configs.SpringTestConfig;
import com.foxminded.dao.ExecutorQuery;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.ClassRoom;
import com.foxminded.service.layers.ClassRoomService;
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
@ContextConfiguration(classes = { SpringTestConfig.class })
class ClassRoomServiceImpTest {
    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";
    public static final String TEST_NAME_THREE = "testNameThree";

    public ClassRoomService classRoomService;
    public ExecutorQuery executorQuery;

    @Autowired
    public void setClassRoomService(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
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
    public void shouldReturnCorrectedClassRoomsWhenGetAllClassRooms() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
        ClassRoom classRoomThree = new ClassRoom(204, TEST_NAME_THREE, 300);

        classRoomService.insert(classRoomOne);
        classRoomService.insert(classRoomTwo);
        classRoomService.insert(classRoomThree);

        List<ClassRoom> actual = classRoomService.getAll();
        List<ClassRoom> expected = of(classRoomOne, classRoomTwo, classRoomThree);

        assertEquals(expected, actual,
                "Should return corrected class rooms when getById all class rooms");
    }

    @Test
    public void shouldReturnCorrectedClassRoomWhenGetClassRoomsById() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
        ClassRoom classRoomThree = new ClassRoom(204, TEST_NAME_THREE, 300);

        classRoomService.insert(classRoomOne);
        classRoomService.insert(classRoomTwo);
        classRoomService.insert(classRoomThree);

        ClassRoom actual = classRoomService.getById(203);

        assertEquals(classRoomTwo, actual,
                "Should return corrected class room when getById class room by id");
    }

    @Test
    public void shouldReturnCorrectedClassRoomWhenUpdateClassroom() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
        ClassRoom classRoomThree = new ClassRoom(203, TEST_NAME_THREE, 300);

        classRoomService.insert(classRoomOne);
        classRoomService.insert(classRoomTwo);
        classRoomService.update(classRoomThree);

        List<ClassRoom> actual = classRoomService.getAll();
        List<ClassRoom> expected = of(classRoomOne, classRoomThree);

        assertEquals(expected, actual,
                "Should return corrected class rooms when update class room");
    }

    @Test
    public void shouldReturnCorrectedClassRoomsWhenDeleteOneAndGetAllClassRooms() throws DAOException {
        ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
        ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
        ClassRoom classRoomThree = new ClassRoom(204, TEST_NAME_THREE, 300);

        classRoomService.insert(classRoomOne);
        classRoomService.insert(classRoomTwo);
        classRoomService.insert(classRoomThree);
        classRoomService.delete(203);

        List<ClassRoom> actual = classRoomService.getAll();
        List<ClassRoom> expected = of(classRoomOne, classRoomThree);

        assertEquals(expected, actual,
                "Should return corrected class rooms when getById all class rooms after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                classRoomService.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                classRoomService.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }
}