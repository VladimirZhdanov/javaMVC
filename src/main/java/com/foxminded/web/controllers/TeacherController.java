package com.foxminded.web.controllers;

import com.foxminded.model.Teacher;
import com.foxminded.service.layers.TeacherService;
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
public class TeacherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    private TeacherService teacherService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping(value = {"teachers"})
    public String showGroupPage() {
        return "teachers";
    }

    @ModelAttribute("teachers")
    public List<Teacher> getGroups() {
        return teacherService.getAll();
    }
}
