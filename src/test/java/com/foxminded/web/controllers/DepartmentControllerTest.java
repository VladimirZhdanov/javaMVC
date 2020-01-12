package com.foxminded.web.controllers;

import com.foxminded.configs.WebConfig;
import com.foxminded.configs.WebTestConfig;
import com.foxminded.model.Department;
import com.foxminded.service.layers.DepartmentService;
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
class DepartmentControllerTest {
    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";

    public Department departmentOne = new Department(1, TEST_NAME_ONE);
    public Department departmentTwo = new Department(2, TEST_NAME_TWO);

    public MockMvc mockMvc;

    @Autowired
    public DepartmentService departmentServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {
        Mockito.reset(departmentServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldNotToBeNullWhenGetBeans() {
        assertNotNull(departmentServiceMock);
        assertNotNull(webApplicationContext);
    }

    @Test
    public void shouldReturnCorrectedDepartmentsPageWhenGetDepartmentsPage() throws Exception {
        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(view().name("departments"));
    }

    @Test
    public void shouldCheckForAttributeExistenceWhenGetDepartmentsPage() throws Exception {
        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("departments"))
                .andExpect(view().name("departments"));
    }


    @Test
    public void shouldReturnCorrectedDepartmentAttributesWhenGetDepartments() throws Exception {
        when(departmentServiceMock.getAll()).thenReturn(List.of(departmentOne, departmentTwo));

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("departments", hasSize(2)))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is(TEST_NAME_ONE))
                        )
                )))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is(TEST_NAME_TWO))
                        ))));
        verify(departmentServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(departmentServiceMock);
    }
}
