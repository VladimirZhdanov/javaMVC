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
class LectureControllerTest {
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

    public Lecture lectureOne = new Lecture(1, TEST_NAME_ONE, DATE_ONE, teacherOne, classRoomOne, groupOne, courseOne);
    public Lecture lectureTwo = new Lecture(2, TEST_NAME_TWO, DATE_TWO, teacherTwo, classRoomTwo, groupTwo, courseTwo);

    public MockMvc mockMvc;

    @Autowired
    public LectureService lectureServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {
        Mockito.reset(lectureServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldNotToBeNullWhenGetBeans() {
        assertNotNull(lectureServiceMock);
        assertNotNull(webApplicationContext);
    }

    @Test
    public void shouldReturnCorrectedLecturesPageWhenGetLecturesPage() throws Exception {
        mockMvc.perform(get("/lectures"))
                .andExpect(status().isOk())
                .andExpect(view().name("lectures"));
    }

    @Test
    public void shouldCheckForAttributeExistenceWhenGetLecturesPage() throws Exception {
        mockMvc.perform(get("/lectures"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lectures"))
                .andExpect(view().name("lectures"));
    }

    @Test
    public void shouldReturnCorrectedLectureAttributesWhenGetLectures() throws Exception {
        when(lectureServiceMock.getAll()).thenReturn(List.of(lectureOne, lectureTwo));

        mockMvc.perform(get("/lectures"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("lectures", hasSize(2)))
                .andExpect(model().attribute("lectures", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is(TEST_NAME_ONE)),
                                hasProperty("date", is(DATE_ONE)),
                                hasProperty("classRoom", is(classRoomOne)),
                                hasProperty("group", is(groupOne)),
                                hasProperty("teacher", is(teacherOne)),
                                hasProperty("course", is(courseOne))
                        ))))
                .andExpect(model().attribute("lectures", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is(TEST_NAME_TWO)),
                                hasProperty("date", is(DATE_TWO)),
                                hasProperty("classRoom", is(classRoomTwo)),
                                hasProperty("group", is(groupTwo)),
                                hasProperty("teacher", is(teacherTwo)),
                                hasProperty("course", is(courseTwo))
                        ))));
        verify(lectureServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(lectureServiceMock);
    }
}
