package com.foxminded.web.controllers;

import com.foxminded.configs.WebConfig;
import com.foxminded.configs.WebTestConfig;
import com.foxminded.model.Group;
import com.foxminded.model.Student;
import com.foxminded.service.layers.StudentService;
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
class StudentControllerTest {
    public static final String FIRST_NAME_ONE = "firstNameOne";
    public static final String FIRST_NAME_TWO = "firstNameTwo";
    public static final String LAST_NAME_ONE = "lastNameOne";
    public static final String LAST_NAME_TWO = "lastNameTwo";

    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";

    public Group groupOne = new Group(1, TEST_NAME_ONE);
    public Group groupTwo = new Group(2, TEST_NAME_TWO);

    public Student studentOne = new Student(1, FIRST_NAME_ONE, LAST_NAME_ONE, groupOne);
    public Student studentTwo = new Student(2, FIRST_NAME_TWO, LAST_NAME_TWO, groupTwo);

    public MockMvc mockMvc;

    @Autowired
    public StudentService studentServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {
        Mockito.reset(studentServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldNotToBeNullWhenGetBeans() {
        assertNotNull(studentServiceMock);
        assertNotNull(webApplicationContext);
    }


    @Test
    public void shouldReturnCorrectedStudentsPageWhenGetStudentsPage() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students"));
    }

    @Test
    public void shouldCheckForAttributeExistenceWhenGetStudentsPage() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("students"))
                .andExpect(view().name("students"));
    }

    @Test
    public void shouldReturnCorrectedStudentAttributesWhenGetStudents() throws Exception {
        when(studentServiceMock.getAll()).thenReturn(List.of(studentOne, studentTwo));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("students", hasSize(2)))
                .andExpect(model().attribute("students", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is(FIRST_NAME_ONE)),
                                hasProperty("lastName", is(LAST_NAME_ONE)),
                                hasProperty("group", is(groupOne))
                        )
                )))
                .andExpect(model().attribute("students", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("firstName", is(FIRST_NAME_TWO)),
                                hasProperty("lastName", is(LAST_NAME_TWO)),
                                hasProperty("group", is(groupTwo))
                        ))));
        verify(studentServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(studentServiceMock);
    }

}
