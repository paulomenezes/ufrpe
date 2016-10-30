package br.deinfo.ufrpe.fragments;

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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.deinfo.ufrpe.CourseActivity;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Classes;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.User;
import br.deinfo.ufrpe.utils.CompareCourse;
import br.deinfo.ufrpe.utils.CompareTodayCourse;
import br.deinfo.ufrpe.utils.Functions;
import br.deinfo.ufrpe.utils.Session;

/**
 * Created by paulo on 27/10/2016.
 */

public class WeekFragment extends Fragment {
    private List<Classes> sClasses;
    private User mUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        mUser = Session.getUser();
        sClasses = TodayFragment.sClasses;

        Calendar calendar = Calendar.getInstance();
        //final List<Course>[] courses = new List[6];
        final HashMap<Integer, Course>[] courses = new HashMap[6];

        int minHour = 9999;

        for (int i = 0; i < mUser.getCourses().size(); i++) {
            if (Functions.thisSemester(mUser.getCourses().get(i).getShortname())) {
                for (int j = 0; j < sClasses.size(); j++) {
                    if (sClasses.get(j).getCod().equals(mUser.getCourses().get(i).getShortname().split("-")[1])) {
                        mUser.getCourses().get(i).setClasses(sClasses.get(j));
                        break;
                    }
                }

                for (int j = 0; j < mUser.getCourses().get(i).getClasses().getSchedules().size(); j++) {
                    int time = Integer.parseInt(mUser.getCourses().get(i).getClasses().getSchedules().get(j).getTimeStart().split(":")[0]);

                    if (courses[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()] == null) {
                        courses[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()] = new HashMap<>();
                    }
                        //List<Course> key = new ArrayList<>();
                        //key.add(mUser.getCourses().get(i));

                        courses[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()].put(time, mUser.getCourses().get(i));
                    //} else {
                     //   courses[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()].add(time, mUser.getCourses().get(i));
                    //}

                    if (time < minHour)
                        minHour = time;
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
        }

        return view;
    }
}

