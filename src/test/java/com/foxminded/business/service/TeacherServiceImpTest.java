package com.foxminded.business.service;

import com.foxminded.test.SpringTestConfig;
import com.foxminded.business.dao.ExecutorQuery;
import com.foxminded.business.model.ClassRoom;
import com.foxminded.business.model.Course;
import com.foxminded.business.model.Department;
import com.foxminded.business.model.Group;
import com.foxminded.business.model.Lecture;
import com.foxminded.business.model.Schedule;
import com.foxminded.business.model.Teacher;
import com.foxminded.business.exceptions.DAOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
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

import static java.util.Collections.singletonList;

import static com.foxminded.business.constants.Constants.NULL_WAS_PASSED;
import static com.google.inject.internal.util.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
class TeacherServiceImpTest {
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
    public static final LocalDateTime DATE_THREE = LocalDateTime.of(2019, Month.MAY, 3, 13, 0);
    public static final LocalDateTime DATE_FOUR = LocalDateTime.of(2018, Month.FEBRUARY, 3, 13, 0);
    public static final LocalDateTime DATE_FIVE = LocalDateTime.of(2018, Month.JANUARY, 3, 13, 0);
    public static final LocalDateTime DATE_SIX = LocalDateTime.of(2018, Month.JANUARY, 3, 13, 0);

    public Course courseOne = new Course(TEST_NAME_ONE);
    public Course courseTwo = new Course(TEST_NAME_TWO);
    public Course courseThree = new Course(TEST_NAME_THREE);

    public Department departmentOne = new Department(TEST_NAME_ONE);
    public Department departmentTwo = new Department(TEST_NAME_TWO);
    public Department departmentThree = new Department(TEST_NAME_THREE);

    public ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
    public ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
    public ClassRoom classRoomThree = new ClassRoom(204, TEST_NAME_THREE, 300);

    public Group groupOne = new Group(TEST_NAME_ONE);
    public Group groupTwo = new Group(TEST_NAME_TWO);
    public Group groupThree = new Group(TEST_NAME_THREE);

    public ApplicationContext context = new AnnotationConfigApplicationContext(SpringTestConfig.class);
    public TeacherService teacherService = context.getBean(TeacherService.class);
    public ClassRoomService classRoomService = context.getBean(ClassRoomService.class);
    public GroupService groupService = context.getBean(GroupService.class);
    public DepartmentService departmentService = context.getBean(DepartmentService.class);
    public CourseService courseService = context.getBean(CourseService.class);
    public LectureService lectureService = context.getBean(LectureService.class);
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
    }

    @Test
    public void shouldReturnCorrectedTeachersWhenGetAllTeachers() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

        teacherService.insert(teacherOne);
        teacherService.insert(teacherTwo);
        teacherService.insert(teacherThree);

        List<Teacher> actual = teacherService.getAll();
        List<Teacher> expected = of(teacherOne, teacherTwo, teacherThree);

        assertEquals(expected, actual,
                "Should return corrected teachers when getById all teachers");
    }

    @Test
    public void shouldReturnCorrectedScheduleWhenGetFullSchedule() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(1), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

        teacherService.insert(teacherOne);
        teacherService.insert(teacherTwo);
        teacherService.insert(teacherThree);
        lectureService.insert(lectureOne);
        lectureService.insert(lectureTwo);
        lectureService.insert(lectureTree);


        Schedule actual = teacherService.getFullSchedule(FIRST_NAME_ONE, LAST_NAME_ONE);
        Schedule expected = new Schedule(Arrays.asList(lectureOne, lectureTwo));

        assertEquals(expected, actual,
                "Should return corrected schedule when getFullSchedule(Teacher teacher)");
    }

    @Test
    public void shouldReturnCorrectedScheduleWhenGetScheduleForYear() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));
        Lecture lectureFour = new Lecture("dil", DATE_FOUR, new Teacher(3), new ClassRoom(204), new Group(2), new Course(3));
        Lecture lectureFive = new Lecture("doll", DATE_FIVE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));
        Lecture lectureSix = new Lecture("hook", DATE_SIX, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

        teacherService.insert(teacherOne);
        teacherService.insert(teacherTwo);
        teacherService.insert(teacherThree);
        lectureService.insert(lectureOne);
        lectureService.insert(lectureTwo);
        lectureService.insert(lectureTree);
        lectureService.insert(lectureFour);
        lectureService.insert(lectureFive);
        lectureService.insert(lectureSix);

        Schedule actual = teacherService.getScheduleForYear(2018, FIRST_NAME_THREE, LAST_NAME_THREE);
        Schedule expected = new Schedule(Arrays.asList(lectureFour, lectureFive, lectureSix));

        assertEquals(expected, actual,
                "Should return corrected schedule when getScheduleForYear(int year, Teacher teacher)");
    }

    @Test
    public void shouldReturnCorrectedScheduleWhenGetScheduleForMonth() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));
        Lecture lectureFour = new Lecture("lec", DATE_FOUR, new Teacher(3), new ClassRoom(204), new Group(2), new Course(3));
        Lecture lectureFive = new Lecture("doc", DATE_FIVE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));
        Lecture lectureSix = new Lecture("koor", DATE_SIX, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

        teacherService.insert(teacherOne);
        teacherService.insert(teacherTwo);
        teacherService.insert(teacherThree);
        lectureService.insert(lectureOne);
        lectureService.insert(lectureTwo);
        lectureService.insert(lectureTree);
        lectureService.insert(lectureFour);
        lectureService.insert(lectureFive);
        lectureService.insert(lectureSix);

        Schedule actual = teacherService.getScheduleForMonth(2, 2018, FIRST_NAME_THREE, LAST_NAME_THREE);
        Schedule expected = new Schedule(singletonList(lectureFour));

        assertEquals(expected, actual,
                "Should return corrected schedule when getScheduleForYear(int year, Teacher teacher)");
    }

    @Test
    public void shouldReturnCorrectedTeachersWhenUpdateTeacher() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

        teacherService.insert(teacherOne);
        teacherService.insert(teacherTwo);
        teacherThree.setId(2);
        teacherService.update(teacherThree);

        List<Teacher> actual = teacherService.getAll();
        List<Teacher> expected = of(teacherOne, teacherThree);

        assertEquals(expected, actual,
                "Should return corrected teachers when update teacher");
    }

    @Test
    public void shouldReturnCorrectedTeachersWhenDeleteOneAndGetAllTeachers() throws DAOException {
        Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
        Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
        Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

        teacherService.insert(teacherOne);
        teacherService.insert(teacherTwo);
        teacherService.insert(teacherThree);
        teacherService.delete(FIRST_NAME_TWO, LAST_NAME_TWO);

        List<Teacher> actual = teacherService.getAll();
        List<Teacher> expected = of(teacherOne, teacherThree);

        assertEquals(expected, actual,
                "Should return corrected teachers when getById all teachers after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                teacherService.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                teacherService.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }
}