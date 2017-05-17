package br.paulomenezes.ufrpe.models;

import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Created by paulomenezes on 16/05/17.
 */

public class Timetable {
    private String dayOfWeek, subject;
    private Date start, end;

    public Timetable() {

    }

    public Timetable(String dayOfWeek, String subject, Date start, Date end) {
        this.dayOfWeek = dayOfWeek;
        this.subject = subject;
        this.start = start;
        this.end = end;
    }
}
