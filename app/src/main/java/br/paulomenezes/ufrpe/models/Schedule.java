package br.paulomenezes.ufrpe.models;

import org.parceler.Parcel;

/**
 * Created by phgm on 10/10/2016.
 */
@Parcel
public class Schedule {
    private int dayOfWeek;
    private String timeStart;
    private String timeEnd;

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
