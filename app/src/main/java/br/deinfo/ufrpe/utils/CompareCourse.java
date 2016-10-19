package br.deinfo.ufrpe.utils;

import java.util.Calendar;
import java.util.Comparator;

import br.deinfo.ufrpe.models.Course;

/**
 * Created by phgm on 19/10/2016.
 */

public class CompareCourse implements Comparator<Course> {

    @Override
    public int compare(Course t1, Course t2) {
        Calendar calendar = Calendar.getInstance();

        int indexT1 = 0;
        int indexT2 = 0;

        for (int i = 0; i < t1.getClasses().getSchedules().size(); i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) - 1 == t1.getClasses().getSchedules().get(i).getDayOfWeek()) {
                indexT1 = i;
                break;
            }
        }

        for (int i = 0; i < t2.getClasses().getSchedules().size(); i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) - 1 == t2.getClasses().getSchedules().get(i).getDayOfWeek()) {
                indexT2 = i;
                break;
            }
        }

        return t1.getClasses().getSchedules().get(indexT1).getTimeStart().compareTo(t2.getClasses().getSchedules().get(indexT2).getTimeStart());
    }
}
