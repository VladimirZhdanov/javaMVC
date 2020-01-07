package com.foxminded.business.model;

import java.util.List;
import java.util.Objects;

/**
 * Schedule class.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class Schedule {
    private List<Lecture> lectures;

    public Schedule() {
    }

    public Schedule(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        return Objects.equals(lectures, schedule.lectures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectures);
    }

    @Override
    public String toString() {
        return "Schedule{" + "lectures=" + lectures + '}';
    }
}
