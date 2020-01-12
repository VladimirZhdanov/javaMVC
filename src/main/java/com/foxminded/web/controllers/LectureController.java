package com.foxminded.web.controllers;

import com.foxminded.model.Lecture;
import com.foxminded.service.layers.LectureService;
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
public class LectureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LectureController.class);

    private LectureService lectureService;

    @Autowired
    public void setLectureService(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping(value = {"lectures"})
    public String showGroupPage() {
        return "lectures";
    }

    @ModelAttribute("lectures")
    public List<Lecture> getGroups() {
        return lectureService.getAll();
    }
}
