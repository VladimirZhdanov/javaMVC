package com.foxminded.web.controllers;

import com.foxminded.business.model.*;
import com.foxminded.business.service.layers.*;
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
public class HomeController {

    @Autowired
    private ClassRoomService classRoomService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping(value = {"/", "home"})
    public String showPage() {
        return "home";
    }

    @ModelAttribute("allClassRooms")
    public List<ClassRoom> getClassRooms() {
        return classRoomService.getAll();
    }

    @ModelAttribute("allCourses")
    public List<Course> getCourses() {
        return courseService.getAll();
    }

    @ModelAttribute("allDepartments")
    public List<Department> getDepartments() {
        return departmentService.getAll();
    }

    @ModelAttribute("allGroups")
    public List<Group> getGroups() {
        return groupService.getAll();
    }

    @ModelAttribute("allLectures")
    public List<Lecture> getLectures() {
        return lectureService.getAll();
    }

    @ModelAttribute("allStudents")
    public List<Student> getStudents() {
        return studentService.getAll();
    }

    @ModelAttribute("allTeachers")
    public List<Teacher> getTeachers() {
        return teacherService.getAll();
    }
    @ModelAttribute("allStudentCourse")
    public List<StudentCourse> getAllStudentCourse() {
        return studentService.getAllStudentCourse();
    }
}
