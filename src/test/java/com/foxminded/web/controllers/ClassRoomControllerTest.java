package com.foxminded.web.controllers;

import com.foxminded.configs.WebConfig;
import com.foxminded.configs.WebTestConfig;
import com.foxminded.model.ClassRoom;
import com.foxminded.service.layers.ClassRoomService;
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
class ClassRoomControllerTest {
    public static final String TEST_NAME_ONE = "testNameOne";
    public static final String TEST_NAME_TWO = "testNameTwo";

    public ClassRoom classRoomOne = new ClassRoom(202, TEST_NAME_ONE, 200);
    public ClassRoom classRoomTwo = new ClassRoom(203, TEST_NAME_TWO, 400);

    public MockMvc mockMvc;

    @Autowired
    public ClassRoomService classRoomServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {
        Mockito.reset(classRoomServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldNotToBeNullWhenGetBeans() {
        assertNotNull(classRoomServiceMock);
        assertNotNull(webApplicationContext);
    }

    @Test
    public void shouldReturnCorrectedClassRoomsPageWhenGetClassRoomsPage() throws Exception {
        mockMvc.perform(get("/classRooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("classRooms"));
    }

    @Test
    public void shouldCheckForAttributeExistenceWhenGetClassRoomsPage() throws Exception {
        mockMvc.perform(get("/classRooms"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("classRooms"))
                .andExpect(view().name("classRooms"));
    }

    @Test
    public void shouldReturnCorrectedClassRoomAttributesWhenGetClassRooms() throws Exception {
        when(classRoomServiceMock.getAll()).thenReturn(List.of(classRoomOne, classRoomTwo));

        mockMvc.perform(get("/classRooms"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("classRooms", hasSize(2)))
                .andExpect(model().attribute("classRooms", hasItem(
                        allOf(
                                hasProperty("id", is(202)),
                                hasProperty("name", is(TEST_NAME_ONE)),
                                hasProperty("capacity", is(200)
                                )
                        ))))
                .andExpect(model().attribute("classRooms", hasItem(
                        allOf(
                                hasProperty("id", is(203)),
                                hasProperty("name", is(TEST_NAME_TWO)),
                                hasProperty("capacity", is(400)
                                )))));
        verify(classRoomServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(classRoomServiceMock);
    }
}
