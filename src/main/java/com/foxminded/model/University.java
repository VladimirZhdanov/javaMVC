package com.foxminded.model;

import java.util.List;
import java.util.Objects;

/**
 * University class.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class University {
    private List<Department> departments;
    private List<ClassRoom> classRooms;
    private List<Group> groups;
    private Schedule schedule;

    public University() {
    }

    public University(List<Department> departments, List<ClassRoom> classRooms, List<Group> groups, Schedule schedule) {
        this.departments = departments;
        this.classRooms = classRooms;
        this.groups = groups;
        this.schedule = schedule;
    }

    public List<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(List<ClassRoom> classRooms) {
        this.classRooms = classRooms;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        University that = (University) o;
        return Objects.equals(departments, that.departments)
                && Objects.equals(classRooms, that.classRooms)
                && Objects.equals(groups, that.groups)
                && Objects.equals(schedule, that.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departments, classRooms, groups, schedule);
    }

    @Override
    public String toString() {
        return "University{"
                + "departments=" + departments
                + ", classRooms=" + classRooms
                + ", groups=" + groups
                + ", schedule=" + schedule
                + '}';
    }
}
