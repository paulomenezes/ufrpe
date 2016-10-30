package br.deinfo.ufrpe.utils;

import java.util.Calendar;
import java.util.Comparator;

import br.deinfo.ufrpe.models.Course;

/**
 * Created by paulo on 29/10/2016.
 */

public class CompareCourse implements Comparator<Course> {

    private int mDayOfWeek;

     public CompareCourse(int dayOfWeek) {
        super();

         mDayOfWeek = dayOfWeek;
     }

    @Override
    public int compare(Course t1, Course t2) {
        Calendar calendar = Calendar.getInstance();

        int indexT1 = 0;
        int indexT2 = 0;

        for (int i = 0; i < t1.getClasses().getSchedules().size(); i++) {
            for (int j = 0; j < t2.getClasses().getSchedules().size(); j++) {
                if (t1.getClasses().getSchedules().get(i).getDayOfWeek() == mDayOfWeek &&
                        t2.getClasses().getSchedules().get(j).getDayOfWeek() == mDayOfWeek) {
                    indexT1 = i;
                    indexT2 = j;

                    i = t1.getClasses().getSchedules().size();
                    j = t2.getClasses().getSchedules().size();
                }
            }
        }

        return t1.getClasses().getSchedules().get(indexT1).getTimeStart().compareTo(t2.getClasses().getSchedules().get(indexT2).getTimeStart());
    }
}
