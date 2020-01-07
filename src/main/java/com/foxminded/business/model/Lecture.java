package com.foxminded.business.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Lecture class.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class Lecture {
    private int id;
    private String name;
    private LocalDateTime date;
    private Teacher teacher;
    private Group group;
    private ClassRoom classRoom;
    private Course course;

    public Lecture() {
    }

    public Lecture(int id) {
        this.id = id;
    }

    public Lecture(String name) {
        this.name = name;
    }

    public Lecture(String name, LocalDateTime date) {
        this.name = name;
        this.date = date;
    }

    public Lecture(String name, LocalDateTime date, Group group) {
        this.name = name;
        this.date = date;
        this.group = group;
    }

    public Lecture(String name, LocalDateTime date, Teacher teacher, ClassRoom classRoom) {
        this.name = name;
        this.date = date;
        this.teacher = teacher;
        this.classRoom = classRoom;
    }

    public Lecture(String name, LocalDateTime date, Teacher teacher, ClassRoom classRoom, Group group) {
        this.name = name;
        this.date = date;
        this.teacher = teacher;
        this.group = group;
        this.classRoom = classRoom;
    }

    public Lecture(String name, LocalDateTime date, Teacher teacher, ClassRoom classRoom, Group group, Course course) {
        this.name = name;
        this.date = date;
        this.teacher = teacher;
        this.group = group;
        this.classRoom = classRoom;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return group;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Course getCourse() {
        return course;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lecture lecture = (Lecture) o;
        return id == lecture.id
                && Objects.equals(name, lecture.name)
                && Objects.equals(date, lecture.date)
                && Objects.equals(teacher, lecture.teacher)
                && Objects.equals(group, lecture.group)
                && Objects.equals(classRoom, lecture.classRoom)
                && Objects.equals(course, lecture.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, teacher, group, classRoom, course);
    }

    @Override
    public String toString() {
        return "Lecture{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", date=" + date
                + ", teacher=" + teacher
                + ", group=" + group
                + ", classRoom=" + classRoom
                + ", course=" + course
                + '}';
    }
}
