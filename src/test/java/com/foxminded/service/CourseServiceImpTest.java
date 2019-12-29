package com.foxminded.service;

import com.foxminded.dao.ExecutorQuery;
import com.foxminded.domain.Course;
import com.foxminded.domain.Group;
import com.foxminded.domain.Student;
import com.foxminded.exceptions.DAOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.foxminded.service.layers.CourseService;
import com.foxminded.service.layers.GroupService;
import com.foxminded.service.layers.StudentService;

import static com.foxminded.constants.Constants.NULL_WAS_PASSED;
import static com.google.inject.internal.util.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
class CourseServiceImpTest {
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
    public Group groupTwo = new Group(TEST_NAME_TWO);
    public Group groupThree = new Group(TEST_NAME_THREE);

    public Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
    public Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
    public Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

    public ApplicationContext context = new ClassPathXmlApplicationContext("h2-config.xml");
    public CourseService courseService = context.getBean(CourseService.class);
    public StudentService studentService = context.getBean(StudentService.class);
    public GroupService groupService = context.getBean(GroupService.class);
    public ExecutorQuery executorQuery = context.getBean(ExecutorQuery.class);

    @BeforeEach
    public void setUp() {
        executorQuery.executeQuery("dropDB.sql");
        executorQuery.executeQuery("tablesCreation.sql");

    }

    @Test
    public void shouldReturnAllRelatedStudentsToTheCourseWhenGetStudents() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        Course courseThree = new Course(TEST_NAME_THREE);

        groupService.insert(groupOne);
        groupService.insert(groupTwo);
        groupService.insert(groupThree);
        studentService.insert(studentOne);
        studentService.insert(studentTwo);
        studentService.insert(studentThree);
        courseService.insert(courseOne);
        courseService.insert(courseTwo);
        courseService.insert(courseThree);
        studentService.insertCourseToStudent(FIRST_NAME_ONE, LAST_NAME_ONE, courseTwo);
        studentService.insertCourseToStudent(FIRST_NAME_TWO, LAST_NAME_TWO, courseOne);
        studentService.insertCourseToStudent(FIRST_NAME_THREE, LAST_NAME_THREE, courseTwo);

        List<Student> actual = courseService.getStudents(TEST_NAME_TWO);
        List<Student> expected = of(studentOne, studentThree);

        assertEquals(expected, actual,
                "Should return all related students to the course when getStudents(Course course)");
    }

    @Test
    public void shouldReturnCorrectedCoursesWhenGetAllCourses() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        Course courseThree = new Course(TEST_NAME_THREE);

        courseService.insert(courseOne);
        courseService.insert(courseTwo);
        courseService.insert(courseThree);

        List<Course> actual = courseService.getAll();
        List<Course> expected = of(courseOne, courseTwo, courseThree);

        assertEquals(expected, actual,
                "Should return corrected courses when get all courses");
    }

    @Test
    public void shouldReturnCorrectedCourseWhenGetCourseById() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        Course courseThree = new Course(TEST_NAME_THREE);

        courseService.insert(courseOne);
        courseService.insert(courseTwo);
        courseService.insert(courseThree);

        Course actual = courseService.getById(2);

        assertEquals(courseTwo, actual,
                "Should return corrected course when get course by id");
    }

    @Test
    public void shouldReturnCorrectedCourseWhenUpdateCourse() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        Course courseThree = new Course(TEST_NAME_THREE);

        courseService.insert(courseOne);
        courseService.insert(courseTwo);
        courseThree.setId(2);
        courseService.update(courseThree);

        List<Course> actual = courseService.getAll();
        List<Course> expected = of(courseOne, courseThree);

        assertEquals(expected, actual,
                "Should return corrected courses when update course");
    }

    @Test
    public void shouldReturnCorrectedCoursesWhenDeleteOneAndGetAllCourses() throws DAOException {
        Course courseOne = new Course(TEST_NAME_ONE);
        Course courseTwo = new Course(TEST_NAME_TWO);
        Course courseThree = new Course(TEST_NAME_THREE);

        courseService.insert(courseOne);
        courseService.insert(courseTwo);
        courseService.insert(courseThree);
        courseService.delete(TEST_NAME_TWO);

        List<Course> actual = courseService.getAll();
        List<Course> expected = of(courseOne, courseThree);

        assertEquals(expected, actual,
                "Should return corrected courses when getById all courses after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseService.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseService.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }
}