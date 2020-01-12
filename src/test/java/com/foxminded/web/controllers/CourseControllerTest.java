package com.foxminded.web.controllers;

import com.foxminded.configs.WebConfig;
import com.foxminded.configs.WebTestConfig;
import com.foxminded.model.Course;
import com.foxminded.service.layers.CourseService;
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
class CourseControllerTest {

    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";

    public Course courseOne = new Course(1, TEST_NAME_ONE);
    public Course courseTwo = new Course(2, TEST_NAME_TWO);

    public MockMvc mockMvc;


    @Autowired
    public CourseService courseServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {
        Mockito.reset(courseServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldNotToBeNullWhenGetBeans() {
        assertNotNull(courseServiceMock);
        assertNotNull(webApplicationContext);
    }


    @Test
    public void shouldReturnCorrectedCoursesPageWhenGetCoursesPage() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses"));
    }

    @Test
    public void shouldCheckForAttributeExistenceWhenGetCoursesPage() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courses"))
                .andExpect(view().name("courses"));
    }

    @Test
    public void shouldReturnCorrectedCourseAttributesWhenGetCourses() throws Exception {
        when(courseServiceMock.getAll()).thenReturn(List.of(courseOne, courseTwo));

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("courses", hasSize(2)))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is(TEST_NAME_ONE))
                                )
                        )))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is(TEST_NAME_TWO))
                                ))));
        verify(courseServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(courseServiceMock);
    }
}
