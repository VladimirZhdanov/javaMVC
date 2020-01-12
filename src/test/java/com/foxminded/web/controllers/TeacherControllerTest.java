package com.foxminded.web.controllers;

import com.foxminded.configs.WebConfig;
import com.foxminded.configs.WebTestConfig;
import com.foxminded.model.Course;
import com.foxminded.model.Department;
import com.foxminded.model.Teacher;
import com.foxminded.service.layers.TeacherService;
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
class TeacherControllerTest {
    public static final String FIRST_NAME_ONE = "firstNameOne";
    public static final String FIRST_NAME_TWO = "firstNameTwo";
    public static final String LAST_NAME_ONE = "lastNameOne";
    public static final String LAST_NAME_TWO = "lastNameTwo";

    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";

    public Course courseOne = new Course(1, TEST_NAME_ONE);
    public Course courseTwo = new Course(2, TEST_NAME_TWO);

    public Department departmentOne = new Department(1, TEST_NAME_ONE);
    public Department departmentTwo = new Department(2, TEST_NAME_TWO);

    public Teacher teacherOne = new Teacher(1, FIRST_NAME_ONE, LAST_NAME_ONE, courseOne, departmentOne);
    public Teacher teacherTwo = new Teacher(2, FIRST_NAME_TWO, LAST_NAME_TWO, courseTwo, departmentTwo);

    public MockMvc mockMvc;

    @Autowired
    public TeacherService teacherServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {
        Mockito.reset(teacherServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldNotToBeNullWhenGetBeans() {
        assertNotNull(teacherServiceMock);
        assertNotNull(webApplicationContext);
    }


    @Test
    public void shouldReturnCorrectedTeachersPageWhenGetTeachersPage() throws Exception {
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers"));
    }

    @Test
    public void shouldCheckForAttributeExistenceWhenGetTeachersPage() throws Exception {
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("teachers"))
                .andExpect(view().name("teachers"));
    }

    @Test
    public void shouldReturnCorrectedTeacherAttributesWhenGetTeachers() throws Exception {
        when(teacherServiceMock.getAll()).thenReturn(List.of(teacherOne, teacherTwo));

        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("teachers", hasSize(2)))
                .andExpect(model().attribute("teachers", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is(FIRST_NAME_ONE)),
                                hasProperty("lastName", is(LAST_NAME_ONE)),
                                hasProperty("department", is(departmentOne)),
                                hasProperty("course", is(courseOne))
                        )
                )))
                .andExpect(model().attribute("teachers", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("firstName", is(FIRST_NAME_TWO)),
                                hasProperty("lastName", is(LAST_NAME_TWO)),
                                hasProperty("department", is(departmentTwo)),
                                hasProperty("course", is(courseTwo))
                        ))));
        verify(teacherServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(teacherServiceMock);
    }
}
