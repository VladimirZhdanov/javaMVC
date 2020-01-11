package com.foxminded.web.controllers;

import com.foxminded.model.*;
import com.foxminded.service.layers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private ClassRoomService classRoomService;
    private CourseService courseService;
    private DepartmentService departmentService;
    private GroupService groupService;
    private LectureService lectureService;
    private StudentService studentService;
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

    @Autowired
    public void setClassRoomService(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setLectureService(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
}
