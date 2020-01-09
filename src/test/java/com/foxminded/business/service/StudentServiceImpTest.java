package com.foxminded.business.service;

import com.foxminded.business.dao.ExecutorQuery;
import com.foxminded.business.exceptions.DAOException;
import com.foxminded.business.model.*;
import com.foxminded.business.service.layers.*;
import com.foxminded.test.SpringTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static com.foxminded.business.constants.Constants.NULL_WAS_PASSED;
import static com.google.inject.internal.util.ImmutableList.of;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringTestConfig.class })
class StudentServiceImpTest {
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


    public Teacher teacherOne = new Teacher(FIRST_NAME_ONE, LAST_NAME_ONE, new Course(1), new Department(1));
    public Teacher teacherTwo = new Teacher(FIRST_NAME_TWO, LAST_NAME_TWO, new Course(2), new Department(2));
    public Teacher teacherThree = new Teacher(FIRST_NAME_THREE, LAST_NAME_THREE, new Course(3), new Department(3));

    public Department departmentOne = new Department(TEST_NAME_ONE);
    public Department departmentTwo = new Department(TEST_NAME_TWO);
    public Department departmentThree = new Department(TEST_NAME_THREE);

    public ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
    public ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);
    public ClassRoom classRoomThree = new ClassRoom(204, TEST_NAME_THREE, 300);

    public Group groupOne = new Group(TEST_NAME_ONE);
    public Group groupTwo = new Group(TEST_NAME_TWO);
    public Group groupThree = new Group(TEST_NAME_THREE);

    public Course courseOne = new Course(TEST_NAME_ONE);
    public Course courseTwo = new Course(TEST_NAME_TWO);
    public Course courseThree = new Course(TEST_NAME_THREE);

    public LectureService lectureService;
    public ClassRoomService classRoomService;
    public DepartmentService departmentService;
    public TeacherService teacherService;
    public GroupService groupService;
    public StudentService studentService;
    public CourseService courseService;
    public ExecutorQuery executorQuery;

    @Autowired
    public void setLectureService(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @Autowired
    public void setClassRoomService(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
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
    public void shouldReturnCorrectedStudentsWhenGetAllStudents() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentService.insert(studentOne);
        studentService.insert(studentTwo);
        studentService.insert(studentThree);

        List<Student> actual = studentService.getAll();
        List<Student> expected = of(studentOne, studentTwo, studentThree);

        assertEquals(expected, actual,
                "Should return corrected students when getById all students");
    }

    @Test
    public void shouldReturnAllRelatedCoursesToTheStudentWhenGetCourses() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentService.insert(studentOne);
        studentService.insert(studentTwo);
        studentService.insert(studentThree);
        studentService.insertCourseToStudent(FIRST_NAME_ONE, LAST_NAME_ONE, courseOne);
        studentService.insertCourseToStudent(FIRST_NAME_ONE, LAST_NAME_ONE, courseTwo);

        List<Course> actual = studentService.getCourses(FIRST_NAME_ONE, LAST_NAME_ONE);
        List<Course> expected = of(courseOne, courseTwo);

        assertEquals(expected, actual,
                "Should return all related courses to the student when getCoursesByStudent(Student student)");
    }

    @Test
    public void shouldReturnCorrectedScheduleWhenGetFullSchedule() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(3));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(2));

        studentService.insert(studentOne);
        studentService.insert(studentTwo);
        studentService.insert(studentThree);
        lectureService.insert(lectureOne);
        lectureService.insert(lectureTwo);
        lectureService.insert(lectureTree);


        Schedule actual = studentService.getFullSchedule(FIRST_NAME_ONE, LAST_NAME_ONE);
        Schedule expected = new Schedule(Arrays.asList(lectureOne, lectureTwo));

        assertEquals(expected, actual,
                "Should return corrected schedule when getFullSchedule(Student student)");
    }

    @Test
    public void shouldReturnCorrectedScheduleWhenGetScheduleForYear() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));
        Lecture lectureFour = new Lecture("nil", DATE_FOUR, new Teacher(3), new ClassRoom(204), new Group(2), new Course(3));
        Lecture lectureFive = new Lecture("bolor", DATE_FIVE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));
        Lecture lectureSix = new Lecture("did", DATE_SIX, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(3));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(2));

        studentService.insert(studentOne);
        studentService.insert(studentTwo);
        studentService.insert(studentThree);
        lectureService.insert(lectureOne);
        lectureService.insert(lectureTwo);
        lectureService.insert(lectureTree);
        lectureService.insert(lectureFour);
        lectureService.insert(lectureFive);
        lectureService.insert(lectureSix);

        Schedule actual = studentService.getScheduleForYear(2018, FIRST_NAME_TWO, LAST_NAME_TWO);
        Schedule expected = new Schedule(Arrays.asList(lectureFive, lectureSix));

        assertEquals(expected, actual,
                "Should return corrected schedule when getScheduleForYear(int year, Student student)");
    }

    @Test
    public void shouldReturnCorrectedScheduleWhenGetScheduleForMonth() throws DAOException {
        Lecture lectureOne = new Lecture(TEST_NAME_ONE, DATE_ONE, new Teacher(1), new ClassRoom(202), new Group(1), new Course(1));
        Lecture lectureTwo = new Lecture(TEST_NAME_TWO, DATE_TWO, new Teacher(2), new ClassRoom(203), new Group(1), new Course(2));
        Lecture lectureTree = new Lecture(TEST_NAME_THREE, DATE_THREE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));
        Lecture lectureFour = new Lecture("nil", DATE_FOUR, new Teacher(3), new ClassRoom(204), new Group(2), new Course(3));
        Lecture lectureFive = new Lecture("bolor", DATE_FIVE, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));
        Lecture lectureSix = new Lecture("did", DATE_SIX, new Teacher(3), new ClassRoom(204), new Group(3), new Course(3));

        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(2));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(2));

        studentService.insert(studentOne);
        studentService.insert(studentTwo);
        studentService.insert(studentThree);
        lectureService.insert(lectureOne);
        lectureService.insert(lectureTwo);
        lectureService.insert(lectureTree);
        lectureService.insert(lectureFour);
        lectureService.insert(lectureFive);
        lectureService.insert(lectureSix);

        Schedule actual = studentService.getScheduleForMonth(2, 2018, FIRST_NAME_TWO, LAST_NAME_TWO);
        Schedule expected = new Schedule(singletonList(lectureFour));

        assertEquals(expected, actual,
                "Should return corrected schedule when getScheduleForYear(int year, Student student)");
    }

    @Test
    public void shouldReturnCorrectedStudentsWhenChangeGroup() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(2));

        studentService.insert(studentOne);
        studentTwo.setId(1);
        studentService.changeGroup(FIRST_NAME_ONE, LAST_NAME_ONE, TEST_NAME_TWO);

        Student actual = studentService.getById(studentOne.getId());

        assertEquals(studentTwo, actual,
                "Should return corrected students when changeGroup()");
    }

    @Test
    public void shouldReturnCorrectedCoursesWhenGetCoursesById() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1), of(courseOne, courseThree));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2), of(courseTwo, courseThree));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3), of(courseThree));

        studentService.insert(studentOne);
        studentService.insert(studentTwo);
        studentService.insert(studentThree);
        studentService.insertRelationshipStudentsToCourses(Arrays.asList(studentOne, studentTwo, studentThree));


        List<Course> actual = studentService.getCourses(FIRST_NAME_ONE, LAST_NAME_ONE);
        List<Course> expected = of(courseOne, courseThree);

        assertEquals(expected, actual,
                "Should return corrected courses when get courses by student id");
    }

    @Test
    public void shouldReturnCorrectedStudentWhenUpdateStudent() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentService.insert(studentOne);
        studentService.insert(studentTwo);
        studentThree.setId(2);
        studentService.update(studentThree);

        List<Student> actual = studentService.getAll();
        List<Student> expected = of(studentOne, studentThree);

        assertEquals(expected, actual,
                "Should return corrected students when update student");
    }

    @Test
    public void shouldReturnCorrectedStudentsWhenDeleteOneAndGetAllStudents() throws DAOException {
        Student studentOne = new Student(FIRST_NAME_ONE, LAST_NAME_ONE, new Group(1));
        Student studentTwo = new Student(FIRST_NAME_TWO, LAST_NAME_TWO, new Group(2));
        Student studentThree = new Student(FIRST_NAME_THREE, LAST_NAME_THREE, new Group(3));

        studentService.insert(studentOne);
        studentService.insert(studentTwo);
        studentService.insert(studentThree);
        studentService.delete(FIRST_NAME_TWO, LAST_NAME_TWO);

        List<Student> actual = studentService.getAll();
        List<Student> expected = of(studentOne, studentThree);

        assertEquals(expected, actual,
                "Should return corrected students when getById all students after delete one");
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToInsertMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentService.insert(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

    @Test
    public void shouldThrowDAOExceptionWhenNullWasPassedToUpdateMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                studentService.update(null));
        assertEquals(NULL_WAS_PASSED, exception.getMessage());
    }

}