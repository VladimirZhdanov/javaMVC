package com.foxminded.service;

import com.foxminded.dao.ExecutorQuery;
import com.foxminded.domain.Group;
import com.foxminded.domain.Student;
import com.foxminded.exceptions.DAOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.foxminded.service.layers.GroupService;
import com.foxminded.service.layers.StudentService;

import static com.foxminded.constants.Constants.NULL_WAS_PASSED;
import static com.google.inject.internal.util.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
class GroupServiceImpTest {
    public static final String FIRST_NAME_ONE = "firstNameOne";
    public static final String FIRST_NAME_TWO = "firstNameTwo";
    public static final String FIRST_NAME_THREE = "firstNameThree";
    public static final String LAST_NAME_ONE = "lastNameOne";
    public static final String LAST_NAME_TWO = "lastNameTwo";
    public static final String LAST_NAME_THREE = "lastNameThree";

    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";
    public static final String TEST_NAME_THREE = "testNameThree";

    public Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
    public Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
    public Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(2));

    public ApplicationContext context = new ClassPathXmlApplicationContext("h2-config.xml");
    public GroupService groupService = context.getBean(GroupService.class);
    public StudentService studentService = context.getBean(StudentService.class);
    public ExecutorQuery executorQuery = context.getBean(ExecutorQuery.class);

    @BeforeEach
    public void setUp() {
        executorQuery.executeQuery("dropDB.sql");
        executorQuery.executeQuery("tablesCreation.sql");
    }

    @Test
    public void shouldReturnAllRelatedStudentsToTheGroupWhenGetStudents() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        Group groupTwo = new Group(TEST_NAME_TWO);
        Group groupThree = new Group(TEST_NAME_THREE);

        groupService.insert(groupOne);
        groupService.insert(groupTwo);
        groupService.insert(groupThree);
        studentService.insert(studentOne);
        studentService.insert(studentTwo);
        studentService.insert(studentThree);

        List<Student> actual = groupService.getStudents(TEST_NAME_TWO);
        List<Student> expected = of(studentTwo, studentThree);

        assertEquals(expected, actual,
                "Should return all related students to the group when getStudents(Group group)");
    }

    @Test
    public void shouldReturnCorrectedGroupsWhenGetAllGroups() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        Group groupTwo = new Group(TEST_NAME_TWO);
        Group groupThree = new Group(TEST_NAME_THREE);

        groupService.insert(groupOne);
        groupService.insert(groupTwo);
        groupService.insert(groupThree);

        List<Group> actual = groupService.getAll();
        List<Group> expected = of(groupOne, groupTwo, groupThree);

        assertEquals(expected, actual,
                "Should return corrected groups when getById all groups");
    }

    @Test
    public void shouldReturnCorrectedGroupWhenUpdateGroup() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        Group groupTwo = new Group(TEST_NAME_TWO);
        Group groupThree = new Group(TEST_NAME_THREE);

        groupService.insert(groupOne);
        groupService.insert(groupTwo);
        groupThree.setId(2);
        groupService.update(groupThree);

        List<Group> actual = groupService.getAll();
        List<Group> expected = of(groupOne, groupThree);

        assertEquals(expected, actual,
                "Should return corrected groups when update group");
    }

    @Test
    public void shouldReturnCorrectedGroupsWhenDeleteOneAndGetAllGroups() throws DAOException {
        Group groupOne = new Group(TEST_NAME_ONE);
        Group groupTwo = new Group(TEST_NAME_TWO);
        Group groupThree = new Group(TEST_NAME_THREE);

        groupService.insert(groupOne);
        groupService.insert(groupTwo);
        groupService.insert(groupThree);
        groupService.delete(TEST_NAME_TWO);

        List<Group> actual = groupService.getAll();
        List<Group> expected = of(groupOne, groupThree);

        assertEquals(expected, actual,
                "Should return corrected groups when getById all groups after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                groupService.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                groupService.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

}