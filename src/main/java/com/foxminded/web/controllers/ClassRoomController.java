package com.foxminded.web.controllers;

import com.foxminded.model.ClassRoom;
import com.foxminded.service.layers.ClassRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Controller
public class ClassRoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomController.class);

    private ClassRoomService classRoomService;

    @Autowired
    public void setClassRoomService(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @GetMapping(value = {"classRooms"})
    public String showGroupPage() {
        return "classRooms";
    }

    @ModelAttribute("classRooms")
    public List<ClassRoom> getGroups() {
        return classRoomService.getAll();
    }
}
