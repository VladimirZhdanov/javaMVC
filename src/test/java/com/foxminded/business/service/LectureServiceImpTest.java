package com.foxminded.business.service;

import com.foxminded.test.SpringTestConfig;
import com.foxminded.business.dao.ExecutorQuery;
import com.foxminded.business.model.ClassRoom;
import com.foxminded.business.model.Course;
import com.foxminded.business.model.Department;
import com.foxminded.business.model.Group;
import com.foxminded.business.model.Lecture;
import com.foxminded.business.model.Teacher;
import com.foxminded.business.exceptions.DAOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.foxminded.business.service.layers.ClassRoomService;
import com.foxminded.business.service.layers.CourseService;
import com.foxminded.business.service.layers.DepartmentService;
import com.foxminded.business.service.layers.GroupService;
import com.foxminded.business.service.layers.LectureService;
import com.foxminded.business.service.layers.TeacherService;

import static com.foxminded.business.constants.Constants.NULL_WAS_PASSED;
import static com.google.inject.internal.util.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
class LectureServiceImpTest {
    public static final String FIRST_NAME_ONE = "firstNameOne";
    public static final String FIRST_NAME_TWO = "firstNameTwo";
    public static final String FIRST_NAME_THREE = "firstNameThree";
    public static final String LAST_NAME_ONE = "lastNameOne";
    public static final String LAST_NAME_TWO = "lastNameTwo";
    public static final String LAST_NAME_THREE = "lastNameThree";

    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";
    public static final String TEST_NAME_THREE = "testNameThree";

    public static final LocalDateTime DATE_ONE = LocalDateTime.of(2019, Month.FEBRUARY, 1, 9, 0);
    public static final LocalDateTime DATE_TWO = LocalDateTime.of(2019, Month.FEBRUARY, 2, 11, 0);
    public static final LocalDateTime DATE_THREE = LocalDateTime.of(2019, Month.FEBRUARY, 3, 13, 0);

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
    public LectureService lectureService = context.getBean(LectureService.class);
    public ClassRoomService classRoomService = context.getBean(ClassRoomService.class);
    public DepartmentService departmentService = context.getBean(DepartmentService.class);
    public CourseService courseService = context.getBean(CourseService.class);
    public GroupService groupService = context.getBean(GroupService.class);
    public TeacherService teacherService = context.getBean(TeacherService.class);
    public ExecutorQuery executorQuery = context.getBean(ExecutorQuery.class);

    @BeforeEach
    public void setUp() throws DAOException {
        executorQuery.executeQuery("sql/dropDB.sql");
        executorQuery.executeQuery("sql/tablesCreation.sql");
        groupService.insert(groupOne);
        groupService.insert(groupTwo);
        groupService.insert(groupThree);
        departmentService.insert(departmentOne);
        departmentService.insert(departmentTwo);
        departmentService.insert(departmentThree);
        courseService.insert(courseOne);
        courseService.insert(courseTwo);
        courseService.insert(courseThree);
        classRoomService.insert(classRoomOne);
        classRoomService.insert(classRoomTwo);
        classRoomService.insert(classRoomThree);
        teacherService.insert(teacherOne);
        teacherService.insert(teacherTwo);
        teacherService.insert(teacherThree);

    }

    @Test
    public void shouldReturnCorrectedLecturesWhenGetAllLectures() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureService.insert(lectureOne);
        lectureService.insert(lectureTwo);
        lectureService.insert(lectureTree);

        List<Lecture> actual = lectureService.getAll();
        List<Lecture> expected = of(lectureOne, lectureTwo, lectureTree);

        assertEquals(expected, actual,
                "Should return corrected lectures when getById all lectures");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenChangeTeacher() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(4), new ClassRoom(202), new Group(1), new Course(2));
        teacherOne = new Teacher("Mile", "Lord", new Course(2), new Department(2));
        teacherService.insert(teacherOne);

        lectureService.insert(lectureOne);
        lectureService.insert(lectureTwo);
        lectureTree.setId(1);
        lectureService.changeTeacher(TEST_NAME_ONE, "Mile", "Lord");

        Lecture actual = lectureService.getById(lectureOne.getId());

        assertEquals(lectureTree, actual,
                "Should return corrected lectures when changeTeacher()");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenChangeClassRoom() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(204), new Group(1), new Course(1));

        lectureService.insert(lectureOne);
        lectureTwo.setId(1);
        lectureService.changeClassRoom(TEST_NAME_ONE, TEST_NAME_THREE);

        Lecture actual = lectureService.getById(lectureOne.getId());

        assertEquals(lectureTwo, actual,
                "Should return corrected lectures when changeClassRoom()");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenChangeGroup() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(2), new Course(1));

        lectureService.insert(lectureOne);
        lectureTwo.setId(1);
        lectureService.changeGroup(TEST_NAME_ONE, TEST_NAME_TWO);

        Lecture actual = lectureService.getById(lectureOne.getId());

        assertEquals(lectureTwo, actual,
                "Should return corrected lectures when changeGroup()");
    }

    @Test
    public void shouldReturnCorrectedLectureWhenUpdateLecture() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureService.insert(lectureOne);
        lectureService.insert(lectureTwo);
        lectureTree.setId(2);
        lectureService.update(lectureTree);

        List<Lecture> actual = lectureService.getAll();
        List<Lecture> expected = of(lectureOne, lectureTree);

        assertEquals(expected, actual,
                "Should return corrected lectures when update lecture");
    }

    @Test
    public void shouldReturnCorrectedLecturesWhenDeleteOneAndGetAllLectures() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(2), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        lectureService.insert(lectureOne);
        lectureService.insert(lectureTwo);
        lectureService.insert(lectureTree);
        lectureService.delete(TEST_NAME_TWO);

        List<Lecture> actual = lectureService.getAll();
        List<Lecture> expected = of(lectureOne, lectureTree);

        assertEquals(expected, actual,
                "Should return corrected lectures when getById all lectures after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureService.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lectureService.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }
}