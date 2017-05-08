package br.ufrpe.conectada.models;

import com.prolificinteractive.materialcalendarview.CalendarDay;

/**
 * Created by paulo on 10/03/2017.
 */

public class AcademicEvent {
    private CalendarDay date;
    private String event;

    public AcademicEvent(CalendarDay date, String event) {
        this.date = date;
        this.event = event;
    }

    public CalendarDay getDate() {
        return date;
    }

    public void setDate(CalendarDay date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
