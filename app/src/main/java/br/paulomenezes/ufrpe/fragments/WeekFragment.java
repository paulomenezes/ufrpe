package br.paulomenezes.ufrpe.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alamkanak.weekview.WeekView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.paulomenezes.ufrpe.CourseActivity;
import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.models.Course;
import br.paulomenezes.ufrpe.models.Timetable;
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

        List<Timetable> timetableList = new ArrayList<>();

        int hourStart = 999;
        int hourEnd = 0;

        for (int i = 0; i < mUser.getCourses().size(); i++) {
            if (Functions.thisSemester(mUser, mUser.getCourses().get(i).getShortname())) {
                for (int j = 0; j < mUser.getCourses().get(i).getClasses().getSchedules().size(); j++) {
                    String[] timeStart = mUser.getCourses().get(i).getClasses().getSchedules().get(j).getTimeStart().split(":");

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStart[0]));
                    cal.set(Calendar.MINUTE, Integer.parseInt(timeStart[1]));

                    Date start = cal.getTime();

                    if (Integer.parseInt(timeStart[0]) < hourStart)
                        hourStart = Integer.parseInt(timeStart[0]);

                    String[] timeEnd = mUser.getCourses().get(i).getClasses().getSchedules().get(j).getTimeEnd().split(":");

                    Calendar cal2 = Calendar.getInstance();
                    cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeEnd[0]));
                    cal2.set(Calendar.MINUTE, Integer.parseInt(timeEnd[1]));

                    Date end = cal2.getTime();

                    if (Integer.parseInt(timeEnd[0]) > hourEnd)
                        hourEnd = Integer.parseInt(timeEnd[0]);

                    Timetable timetable = new Timetable(
                            getActivity().getResources().getStringArray(R.array.day_of_week)[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()],
                            Functions.getCamelSentence(mUser.getCourses().get(i).getFullname().split(" \\| ")[1].split(" - ")[0]),
                            start, end);

                    timetableList.add(timetable);
                }
            }
        }


        WeekView mWeekView = (WeekView) view.findViewById(R.id.weekView);


        /*Calendar calendar = Calendar.getInstance();
        //final List<Course>[] courses = new List[6];
        final HashMap<Integer, Course>[] courses = new HashMap[6];

        int minHour = 9999;

        for (int i = 0; i < mUser.getCourses().size(); i++) {
            if (Functions.thisSemester(mUser, mUser.getCourses().get(i).getShortname())) {
                for (int j = 0; j < mUser.getCourses().get(i).getClasses().getSchedules().size(); j++) {
                    try {
                        int time = Integer.parseInt(mUser.getCourses().get(i).getClasses().getSchedules().get(j).getTimeStart().split(":")[0]);

                        if (courses[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()] == null) {
                            courses[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()] = new HashMap<>();
                        }

                        courses[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()].put(time, mUser.getCourses().get(i));

                        if (time < minHour)
                            minHour = time;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        LinearLayout mon = (LinearLayout) view.findViewById(R.id.mon);
        LinearLayout tue = (LinearLayout) view.findViewById(R.id.tue);
        LinearLayout wed = (LinearLayout) view.findViewById(R.id.wed);
        LinearLayout thu = (LinearLayout) view.findViewById(R.id.thu);
        LinearLayout fri = (LinearLayout) view.findViewById(R.id.fri);

        for (int i = 1; i < courses.length; i++) {
            final Map<Integer, Course> course = new TreeMap(courses[i]);

            for (int j = 0; j < course.size(); j++) {
                if (course.size() > j) {
                    View viewItem = inflater.inflate(R.layout.list_item_week, null);

                    if (i != 5)
                        viewItem.setPadding(viewItem.getPaddingLeft(), viewItem.getPaddingTop(), 0, 0);

                    final Object key = course.keySet().toArray()[j];
                    if (j == 0 && (Integer)key > minHour) {
                        viewItem.setPadding(viewItem.getPaddingLeft(),
                                Functions.dpToPx(getContext(), ((Integer)key - minHour) * 66),
                                viewItem.getPaddingRight(), viewItem.getPaddingBottom());
                    } else if (j > 0) {
                        Object lastKey = course.keySet().toArray()[j - 1];

                        int dayOfWeek = 0;
                        for (int k = 0; k < course.get(key).getClasses().getSchedules().size(); k++) {
                            if (course.get(key).getClasses().getSchedules().get(k).getDayOfWeek() == i) {
                                dayOfWeek = k;
                                break;
                            }
                        }
                        int dayOfWeek2 = 0;
                        for (int k = 0; k < course.get(lastKey).getClasses().getSchedules().size(); k++) {
                            if (course.get(lastKey).getClasses().getSchedules().get(k).getDayOfWeek() == i) {
                                dayOfWeek2 = k;
                                break;
                            }
                        }

                        int nowTimeStart = Integer.parseInt(course.get(key).getClasses().getSchedules().get(dayOfWeek ).getTimeStart().split(":")[0]);
                        int lastTimeStart = Integer.parseInt(course.get(lastKey).getClasses().getSchedules().get(dayOfWeek2).getTimeEnd().split(":")[0]);

                        if (nowTimeStart - lastTimeStart > 1) {
                            viewItem.setPadding(viewItem.getPaddingLeft(),
                                    Functions.dpToPx(getContext(), (nowTimeStart - lastTimeStart) * 69),
                                    viewItem.getPaddingRight(), viewItem.getPaddingBottom());
                        }
                    }

                    TextView textView = (TextView) viewItem.findViewById(R.id.title);
                    textView.setText(Functions.getCamelSentence(course.get(key).getFullname().split(" \\| ")[1].split(" - ")[0]));

                    CardView card = (CardView) viewItem.findViewById(R.id.card);
                    card.setCardBackgroundColor(Color.parseColor(course.get(key).getNormalColor()));

                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), CourseActivity.class);
                            intent.putExtra("course", Parcels.wrap(course.get(key)));
                            getContext().startActivity(intent);
                        }
                    });

                    if (i == 1) mon.addView(viewItem);
                    else if (i == 2) tue.addView(viewItem);
                    else if (i == 3) wed.addView(viewItem);
                    else if (i == 4) thu.addView(viewItem);
                    else if (i == 5) fri.addView(viewItem);
                }
            }
        }*/

        return view;
    }
}

