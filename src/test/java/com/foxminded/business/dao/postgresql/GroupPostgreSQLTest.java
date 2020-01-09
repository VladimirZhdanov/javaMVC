package com.foxminded.business.dao.postgresql;

import com.foxminded.business.dao.ExecutorQuery;
import com.foxminded.business.dao.layers.GroupDAO;
import com.foxminded.business.exceptions.DAOException;
import com.foxminded.business.model.Group;
import com.foxminded.test.SpringTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.foxminded.business.constants.Constants.NULL_WAS_PASSED;
import static com.google.inject.internal.util.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringTestConfig.class })
class GroupPostgreSQLTest {
    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";
    public static final String TEST_NAME_THREE = "testNameThree";

    public GroupDAO groupDAO;
    public ExecutorQuery executorQuery;

    @Autowired
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
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
    public void shouldReturnCorrectedGroupsWhenGetAllGroups() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        Group groupTwo = new Group(TEST_NAME_TWO);
        Group groupThree = new Group(TEST_NAME_THREE);

        groupDAO.insert(groupOne);
        groupDAO.insert(groupTwo);
        groupDAO.insert(groupThree);

        List<Group> actual = groupDAO.getAll();
        List<Group> expected = of(groupOne, groupTwo, groupThree);

        assertEquals(expected, actual,
                "Should return corrected groups when getById all groups");
    }

    @Test
    public void shouldReturnCorrectedGroupsWhenGetGroupByName() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        Group groupTwo = new Group(TEST_NAME_TWO);
        Group groupThree = new Group(TEST_NAME_THREE);

        groupDAO.insert(groupOne);
        groupDAO.insert(groupTwo);
        groupDAO.insert(groupThree);

        Group actual = groupDAO.getByName(TEST_NAME_TWO);

        assertEquals(groupTwo, actual,
                "Should return corrected group when getByName()");
    }

    @Test
    public void shouldReturnCorrectedGroupWhenUpdateGroup() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        Group groupTwo = new Group(TEST_NAME_TWO);
        Group groupThree = new Group(TEST_NAME_THREE);

        groupDAO.insert(groupOne);
        groupDAO.insert(groupTwo);
        groupThree.setId(2);
        groupDAO.update(groupThree);

        List<Group> actual = groupDAO.getAll();
        List<Group> expected = of(groupOne, groupThree);

        assertEquals(expected, actual,
                "Should return corrected groups when update group");
    }

    @Test
    public void shouldReturnCorrectedGroupsWhenDeleteOneAndGetAllGroups() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        Group groupTwo = new Group(TEST_NAME_TWO);
        Group groupThree = new Group(TEST_NAME_THREE);

        groupDAO.insert(groupOne);
        groupDAO.insert(groupTwo);
        groupDAO.insert(groupThree);
        groupDAO.delete(groupTwo);

        List<Group> actual = groupDAO.getAll();
        List<Group> expected = of(groupOne, groupThree);

        assertEquals(expected, actual,
                "Should return corrected groups when getById all groups after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                groupDAO.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                groupDAO.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetByName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                groupDAO.getByName(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToDelete() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                groupDAO.delete(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenTryToInsertDuplicateWithTheSameName() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        groupDAO.insert(groupOne);

        Exception exception = assertThrows(DAOException.class, () ->
                groupDAO.insert(groupOne));
        assertEquals("Unique index or primary key violation", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomById() {
        Exception exception = assertThrows(DAOException.class, () ->
                groupDAO.getById(666));
        assertEquals("Can't find group by passed id", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomByName() {
        Exception exception = assertThrows(DAOException.class, () ->
                groupDAO.getByName("666"));
        assertEquals("Can't find group by passed name", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenUpdate() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        Group groupTwo = new Group(TEST_NAME_TWO);
        groupDAO.insert(groupOne);

        Exception exception = assertThrows(DAOException.class, () ->
                groupDAO.update(groupTwo));
        assertEquals("The group does not exist", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenDelete() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        Group groupTwo = new Group(TEST_NAME_TWO);
        groupDAO.insert(groupOne);

        Exception exception = assertThrows(DAOException.class, () ->
                groupDAO.delete(groupTwo));
        assertEquals("The group does not exist", exception.getMessage());
    }
}