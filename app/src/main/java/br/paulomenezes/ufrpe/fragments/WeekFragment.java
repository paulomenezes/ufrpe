package br.paulomenezes.ufrpe.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.paulomenezes.ufrpe.CourseActivity;
import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.models.Course;
import br.paulomenezes.ufrpe.models.User;
import br.paulomenezes.ufrpe.utils.Functions;
import br.paulomenezes.ufrpe.utils.Session;

/**
 * Created by paulo on 27/10/2016.
 */

public class WeekFragment extends Fragment {
    private User mUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        mUser = Session.getUser(getActivity());

        final List<WeekViewEvent> timetableList = new ArrayList<>();

        int hour = 24;
        int dayOfWeek = 7;
        Calendar firstDay = Calendar.getInstance();

        for (int i = 0; i < mUser.getCourses().size(); i++) {
            if (Functions.thisSemester(mUser, mUser.getCourses().get(i).getShortname())) {
                if (mUser.getCourses().get(i).getClasses() != null && mUser.getCourses().get(i).getClasses().getSchedules() != null) {
                    for (int j = 0; j < mUser.getCourses().get(i).getClasses().getSchedules().size(); j++) {
                        String[] timeStart = mUser.getCourses().get(i).getClasses().getSchedules().get(j).getTimeStart().split(":");

                        int hourStart = Integer.parseInt(timeStart[0]);
                        if (hourStart < hour)
                            hour = hourStart;

                        int dayOfWeekStart = mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek() + 1;

                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_WEEK, dayOfWeekStart);
                        cal.set(Calendar.HOUR_OF_DAY, hourStart);
                        cal.set(Calendar.MINUTE, Integer.parseInt(timeStart[1]) + 1);

                        if (dayOfWeekStart < dayOfWeek) {
                            dayOfWeek = dayOfWeekStart;
                            firstDay.set(Calendar.DAY_OF_WEEK, dayOfWeekStart);
                        }

                        String[] timeEnd = mUser.getCourses().get(i).getClasses().getSchedules().get(j).getTimeEnd().split(":");

                        Calendar cal2 = Calendar.getInstance();
                        cal2.set(Calendar.DAY_OF_WEEK, mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek() + 1);
                        cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeEnd[0]));
                        cal2.set(Calendar.MINUTE, Integer.parseInt(timeEnd[1]) - 1);

                        WeekViewEvent timetable = new WeekViewEvent(
                                mUser.getCourses().get(i).getId(),
                                Functions.getCamelSentence(mUser.getCourses().get(i).getFullname().split(" \\| ")[1].split(" - ")[0]),
                                cal, cal2);
                        timetable.setColor(Color.parseColor(mUser.getCourses().get(i).getNormalColor()));
                        timetableList.add(timetable);
                    }
                }
            }
        }

        final boolean[] alreadyAdd = {false};

        WeekView mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                Intent intent = new Intent(getContext(), CourseActivity.class);
                Course course = null;
                for (int i = 0; i < mUser.getCourses().size(); i++) {
                    if (mUser.getCourses().get(i).getId() == event.getId()) {
                        course = mUser.getCourses().get(i);
                        break;
                    }
                }
                intent.putExtra("course", Parcels.wrap(course));
                getContext().startActivity(intent);
            }
        });
        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                if (!alreadyAdd[0]) {
                    alreadyAdd[0] = true;
                    return timetableList;
                } else {
                    return new ArrayList<>();
                }
            }
        });
        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });

        mWeekView.goToDate(firstDay);
        mWeekView.goToHour(hour);

        return view;
    }
}

