package br.deinfo.ufrpe.models;

import org.parceler.Parcel;

/**
 * Created by phgm on 10/10/2016.
 */
@Parcel
public class Schedule {
    private String dayOfWeek;
    private String timeStart;
    private String timeEnd;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
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
