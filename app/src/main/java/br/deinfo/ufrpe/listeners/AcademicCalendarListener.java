package br.deinfo.ufrpe.listeners;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.HashMap;
import java.util.List;

/**
 * Created by phgm on 12/03/2017.
 */

public interface AcademicCalendarListener {
    void loadEvents(HashMap<CalendarDay, List<String>> events);
}
