package com.foxminded.dao.postgresql;

import com.foxminded.dao.ExecutorQuery;
import com.foxminded.dao.layers.DepartmentDAO;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.Department;
import com.foxminded.configs.SpringTestConfig;
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
class DepartmentPostgreSQLTest {
    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";
    public static final String TEST_NAME_THREE = "testNameThree";

    public DepartmentDAO departmentDAO;
    public ExecutorQuery executorQuery;

    @Autowired
    public void setDepartmentDAO(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
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
    public void shouldReturnCorrectedDepartmentsWhenGetAllDepartments() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        Department departmentTwo = new Department(TEST_NAME_TWO);
        Department departmentThree = new Department(TEST_NAME_THREE);

        departmentDAO.insert(departmentOne);
        departmentDAO.insert(departmentTwo);
        departmentDAO.insert(departmentThree);

        List<Department> actual = departmentDAO.getAll();
        List<Department> expected = of(departmentOne, departmentTwo, departmentThree);

        assertEquals(expected, actual,
                "Should return corrected departments when getById all departments");
    }

    @Test
    public void shouldReturnCorrectedDepartmentsWhenGetDepartmentByName() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        Department departmentTwo = new Department(TEST_NAME_TWO);
        Department departmentThree = new Department(TEST_NAME_THREE);

        departmentDAO.insert(departmentOne);
        departmentDAO.insert(departmentTwo);
        departmentDAO.insert(departmentThree);

        Department actual = departmentDAO.getByName(TEST_NAME_TWO);

        assertEquals(departmentTwo, actual,
                "Should return corrected department when getByName()");
    }

    @Test
    public void shouldReturnCorrectedDepartmentWhenUpdateDepartment() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        Department departmentTwo = new Department(TEST_NAME_TWO);
        Department departmentThree = new Department(TEST_NAME_THREE);

        departmentDAO.insert(departmentOne);
        departmentDAO.insert(departmentTwo);
        departmentThree.setId(2);
        departmentDAO.update(departmentThree);

        List<Department> actual = departmentDAO.getAll();
        List<Department> expected = of(departmentOne, departmentThree);

        assertEquals(expected, actual,
                "Should return corrected departments when update department");
    }

    @Test
    public void shouldReturnCorrectedDepartmentsWhenDeleteOneAndGetAllDepartments() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        Department departmentTwo = new Department(TEST_NAME_TWO);
        Department departmentThree = new Department(TEST_NAME_THREE);

        departmentDAO.insert(departmentOne);
        departmentDAO.insert(departmentTwo);
        departmentDAO.insert(departmentThree);
        departmentDAO.delete(departmentTwo);

        List<Department> actual = departmentDAO.getAll();
        List<Department> expected = of(departmentOne, departmentThree);

        assertEquals(expected, actual,
                "Should return corrected departments when getById all departments after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                departmentDAO.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                departmentDAO.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToGetByName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                departmentDAO.getByName(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToDelete() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                departmentDAO.delete(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenTryToInsertDuplicateWithTheSameName() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        departmentDAO.insert(departmentOne);

        Exception exception = assertThrows(DAOException.class, () ->
                departmentDAO.insert(departmentOne));
        assertEquals("Unique index or primary key violation", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomById() {
        Exception exception = assertThrows(DAOException.class, () ->
                departmentDAO.getById(666));
        assertEquals("Can't find department by passed id", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenCanNotFindClassRoomByName() {
        Exception exception = assertThrows(DAOException.class, () ->
                departmentDAO.getByName("666"));
        assertEquals("Can't find department by passed name", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenUpdate() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        Department departmentTwo = new Department(TEST_NAME_TWO);
        departmentDAO.insert(departmentOne);

        Exception exception = assertThrows(DAOException.class, () ->
                departmentDAO.update(departmentTwo));
        assertEquals("The department does not exist", exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenDelete() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        Department departmentTwo = new Department(TEST_NAME_TWO);
        departmentDAO.insert(departmentOne);

        Exception exception = assertThrows(DAOException.class, () ->
                departmentDAO.delete(departmentTwo));
        assertEquals("The department does not exist", exception.getMessage());
    }
}