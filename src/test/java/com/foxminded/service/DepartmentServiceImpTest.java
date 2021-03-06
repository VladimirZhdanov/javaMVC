package com.foxminded.service;

import com.foxminded.configs.SpringTestConfig;
import com.foxminded.dao.ExecutorQuery;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.Course;
import com.foxminded.model.Department;
import com.foxminded.model.Teacher;
import com.foxminded.service.layers.CourseService;
import com.foxminded.service.layers.DepartmentService;
import com.foxminded.service.layers.TeacherService;
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
class DepartmentServiceImpTest {
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

    public Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
    public Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
    public Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(2));

    public DepartmentService departmentService;
    public TeacherService teacherService;
    public CourseService courseService;
    public ExecutorQuery executorQuery;

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
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
    public void shouldReturnAllRelatedTeachersToTheDepartmentWhenGetTeachers() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        Department departmentTwo = new Department(TEST_NAME_TWO);
        Department departmentThree = new Department(TEST_NAME_THREE);

        courseService.insert(courseOne);
        courseService.insert(courseTwo);
        courseService.insert(courseThree);
        departmentService.insert(departmentOne);
        departmentService.insert(departmentTwo);
        departmentService.insert(departmentThree);
        teacherService.insert(teacherOne);
        teacherService.insert(teacherTwo);
        teacherService.insert(teacherThree);

        List<Teacher> actual = departmentService.getTeachers(TEST_NAME_TWO);
        List<Teacher> expected = of(teacherTwo, teacherThree);

        assertEquals(expected, actual,
                "Should return all related teachers to the department when getTeachers(Department department)");
    }

    @Test
    public void shouldReturnCorrectedDepartmentsWhenGetAllDepartments() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        Department departmentTwo = new Department(TEST_NAME_TWO);
        Department departmentThree = new Department(TEST_NAME_THREE);

        departmentService.insert(departmentOne);
        departmentService.insert(departmentTwo);
        departmentService.insert(departmentThree);

        List<Department> actual = departmentService.getAll();
        List<Department> expected = of(departmentOne, departmentTwo, departmentThree);

        assertEquals(expected, actual,
                "Should return corrected departments when getById all departments");
    }

    @Test
    public void shouldReturnCorrectedDepartmentWhenUpdateDepartment() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        Department departmentTwo = new Department(TEST_NAME_TWO);
        Department departmentThree = new Department(TEST_NAME_THREE);

        departmentService.insert(departmentOne);
        departmentService.insert(departmentTwo);
        departmentThree.setId(2);
        departmentService.update(departmentThree);

        List<Department> actual = departmentService.getAll();
        List<Department> expected = of(departmentOne, departmentThree);

        assertEquals(expected, actual,
                "Should return corrected departments when update department");
    }

    @Test
    public void shouldReturnCorrectedDepartmentsWhenDeleteOneAndGetAllDepartments() throws DAOException {
        Department departmentOne = new Department(TEST_NAME_ONE);
        Department departmentTwo = new Department(TEST_NAME_TWO);
        Department departmentThree = new Department(TEST_NAME_THREE);

        departmentService.insert(departmentOne);
        departmentService.insert(departmentTwo);
        departmentService.insert(departmentThree);
        departmentService.delete(TEST_NAME_TWO);

        List<Department> actual = departmentService.getAll();
        List<Department> expected = of(departmentOne, departmentThree);

        assertEquals(expected, actual,
                "Should return corrected departments when getById all departments after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                departmentService.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                departmentService.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

}