package com.foxminded.web.controllers;

import com.foxminded.configs.WebConfig;
import com.foxminded.configs.WebTestConfig;
import com.foxminded.model.*;
import com.foxminded.service.layers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, WebTestConfig.class})
@WebAppConfiguration
class HomeControllerTest {
    public static final LocalDateTime DATE_ONE = LocalDateTime.of(2018, Month.FEBRUARY, 1, 9, 0);
    public static final LocalDateTime DATE_TWO = LocalDateTime.of(2019, Month.FEBRUARY, 2, 11, 0);

    public static final String FIRST_NAME_ONE = "firstNameOne";
    public static final String FIRST_NAME_TWO = "firstNameTwo";
    public static final String LAST_NAME_ONE = "lastNameOne";
    public static final String LAST_NAME_TWO = "lastNameTwo";

    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";

    public Course courseOne = new Course(1, TEST_NAME_ONE);
    public Course courseTwo = new Course(2, TEST_NAME_TWO);

    public Group groupOne = new Group(1, TEST_NAME_ONE);
    public Group groupTwo = new Group(2, TEST_NAME_TWO);

    public Department departmentOne = new Department(1, TEST_NAME_ONE);
    public Department departmentTwo = new Department(2, TEST_NAME_TWO);

    public ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
    public ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);

    public Teacher teacherOne = new Teacher(1, FIRST_NAME_ONE, LAST_NAME_ONE, courseOne, departmentOne);
    public Teacher teacherTwo = new Teacher(2, FIRST_NAME_TWO, LAST_NAME_TWO, courseTwo, departmentTwo);

    public Student studentOne = new Student(1, FIRST_NAME_ONE, LAST_NAME_ONE, groupOne);
    public Student studentTwo = new Student(2, FIRST_NAME_TWO, LAST_NAME_TWO, groupTwo);

    public Lecture lectureOne = new Lecture(1, TEST_NAME_ONE, DATE_ONE, teacherOne, classRoomOne, groupOne, courseOne);
    public Lecture lectureTwo = new Lecture(2, TEST_NAME_TWO, DATE_TWO, teacherTwo, classRoomTwo, groupTwo, courseTwo);

    public MockMvc mockMvc;

    @Autowired
    public ClassRoomService classRoomServiceMock;

    @Autowired
    public CourseService courseServiceMock;

    @Autowired
    public DepartmentService departmentServiceMock;

    @Autowired
    public GroupService groupServiceMock;

    @Autowired
    public LectureService lectureServiceMock;

    @Autowired
    public StudentService studentServiceMock;

    @Autowired
    public TeacherService teacherServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {
        Mockito.reset(classRoomServiceMock, courseServiceMock, departmentServiceMock, groupServiceMock,
                lectureServiceMock, studentServiceMock, teacherServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldNotToBeNullWhenGetBeans() {
        assertNotNull(classRoomServiceMock);
        assertNotNull(webApplicationContext);
    }


    @Test
    public void shouldReturnCorrectedStartPageWhenGetStartPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void shouldReturnCorrectedHomePageWhenGetHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void shouldCheckForAttributesExistenceWhenGetStartPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allClassRooms",
                        "allCourses",
                        "allDepartments",
                        "allGroups",
                        "allLectures",
                        "allStudents",
                        "allTeachers"))
                .andExpect(view().name("home"));
    }

    @Test
    public void shouldCheckForAttributesExistenceWhenGetHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allClassRooms",
                        "allCourses",
                        "allDepartments",
                        "allGroups",
                        "allLectures",
                        "allStudents",
                        "allTeachers"))
                .andExpect(view().name("home"));
    }

    @Test
    public void shouldReturnCorrectedClassRoomAttributeWhenGetAllClassRooms() throws Exception {
        when(classRoomServiceMock.getAll()).thenReturn(List.of(classRoomOne, classRoomTwo));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allClassRooms", hasSize(2)))
        .andExpect(model().attribute("allClassRooms", hasItem(
                allOf(
                        hasProperty("id", is(202)),
                        hasProperty("name", is(TEST_NAME_ONE)),
                        hasProperty("capacity", is(200)
                )
        ))))
        .andExpect(model().attribute("allClassRooms", hasItem(
                allOf(
                        hasProperty("id", is(203)),
                        hasProperty("name", is(TEST_NAME_TWO)),
                        hasProperty("capacity", is(400)
                        )))));
        verify(classRoomServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(classRoomServiceMock);
    }

    @Test
    public void shouldReturnCorrectedCourseAttributeWhenGetAllCourses() throws Exception {
        when(courseServiceMock.getAll()).thenReturn(List.of(courseOne, courseTwo));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allCourses", hasSize(2)))
                .andExpect(model().attribute("allCourses", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is(TEST_NAME_ONE))
                                )
                        )))
                .andExpect(model().attribute("allCourses", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is(TEST_NAME_TWO))
                                ))));
        verify(classRoomServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(classRoomServiceMock);
    }

    @Test
    public void shouldReturnCorrectedDepartmentAttributeWhenGetAllDepartments() throws Exception {
        when(departmentServiceMock.getAll()).thenReturn(List.of(departmentOne, departmentTwo));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allDepartments", hasSize(2)))
                .andExpect(model().attribute("allDepartments", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is(TEST_NAME_ONE))
                        )
                )))
                .andExpect(model().attribute("allDepartments", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is(TEST_NAME_TWO))
                        ))));
        verify(classRoomServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(classRoomServiceMock);
    }

    @Test
    public void shouldReturnCorrectedGroupAttributeWhenGetAllGroups() throws Exception {
        when(groupServiceMock.getAll()).thenReturn(List.of(groupOne, groupTwo));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allGroups", hasSize(2)))
                .andExpect(model().attribute("allGroups", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is(TEST_NAME_ONE))
                        )
                )))
                .andExpect(model().attribute("allGroups", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is(TEST_NAME_TWO))
                        ))));
        verify(classRoomServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(classRoomServiceMock);
    }

    @Test
    public void shouldReturnCorrectedLectureAttributeWhenGetAllLectures() throws Exception {
        when(lectureServiceMock.getAll()).thenReturn(List.of(lectureOne, lectureTwo));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allLectures", hasSize(2)))
                .andExpect(model().attribute("allLectures", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is(TEST_NAME_ONE)),
                                hasProperty("date", is(DATE_ONE)),
                                hasProperty("classRoom", is(classRoomOne)),
                                hasProperty("group", is(groupOne)),
                                hasProperty("teacher", is(teacherOne)),
                                hasProperty("course", is(courseOne))
                        ))))
                .andExpect(model().attribute("allLectures", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is(TEST_NAME_TWO)),
                                hasProperty("date", is(DATE_TWO)),
                                hasProperty("classRoom", is(classRoomTwo)),
                                hasProperty("group", is(groupTwo)),
                                hasProperty("teacher", is(teacherTwo)),
                                hasProperty("course", is(courseTwo))
                        ))));
        verify(classRoomServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(classRoomServiceMock);
    }

    @Test
    public void shouldReturnCorrectedStudentAttributeWhenGetAllStudents() throws Exception {
        when(studentServiceMock.getAll()).thenReturn(List.of(studentOne, studentTwo));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allStudents", hasSize(2)))
                .andExpect(model().attribute("allStudents", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is(FIRST_NAME_ONE)),
                                hasProperty("lastName", is(LAST_NAME_ONE)),
                                hasProperty("group", is(groupOne))
                        )
                )))
                .andExpect(model().attribute("allStudents", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("firstName", is(FIRST_NAME_TWO)),
                                hasProperty("lastName", is(LAST_NAME_TWO)),
                                hasProperty("group", is(groupTwo))
                        ))));
        verify(classRoomServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(classRoomServiceMock);
    }

    @Test
    public void shouldReturnCorrectedTeacherAttributeWhenGetAllTeachers() throws Exception {
        when(teacherServiceMock.getAll()).thenReturn(List.of(teacherOne, teacherTwo));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allTeachers", hasSize(2)))
                .andExpect(model().attribute("allTeachers", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is(FIRST_NAME_ONE)),
                                hasProperty("lastName", is(LAST_NAME_ONE)),
                                hasProperty("department", is(departmentOne)),
                                hasProperty("course", is(courseOne))
                        )
                )))
                .andExpect(model().attribute("allTeachers", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("firstName", is(FIRST_NAME_TWO)),
                                hasProperty("lastName", is(LAST_NAME_TWO)),
                                hasProperty("department", is(departmentTwo)),
                                hasProperty("course", is(courseTwo))
                        ))));
        verify(classRoomServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(classRoomServiceMock);
    }
}
