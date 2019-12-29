package com.foxminded.domain;

import java.util.Objects;

/**
 * Class for many to many relationship between courses and students.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class StudentCourse {
    private int studentId;
    private int courseId;

    /**
     * Constructor of the class.
     */
    public StudentCourse() {
    }

    /**
     * Constructor of the class.
     *
     * @param studentId - student id
     * @param courseId - course id
     */
    public StudentCourse(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentCourse that = (StudentCourse) o;
        return studentId == that.studentId && courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    @Override
    public String toString() {
        return "CoursesConnection{" + "studentId=" + studentId + ", courseId=" + courseId + '}';
    }
}
