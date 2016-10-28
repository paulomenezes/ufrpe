package br.deinfo.ufrpe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Classes;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.User;
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
        final List<Course>[] courses = new List[6];

        for (int i = 0; i < mUser.getCourses().size(); i++) {
            if (Functions.thisSemester(mUser.getCourses().get(i).getShortname())) {
                for (int j = 0; j < sClasses.size(); j++) {
                    if (sClasses.get(j).getCod().equals(mUser.getCourses().get(i).getShortname().split("-")[1])) {
                        mUser.getCourses().get(i).setClasses(sClasses.get(j));
                        break;
                    }
                }


                for (int j = 0; j < mUser.getCourses().get(i).getClasses().getSchedules().size(); j++) {
                    if (courses[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()] == null) {
                        List<Course> key = new ArrayList<>();
                        key.add(mUser.getCourses().get(i));

                        courses[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()] = key;
                    } else {
                        courses[mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek()].add(mUser.getCourses().get(i));
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
            List<Course> course = courses[i];

            for (int j = 0; j < course.size(); j++) {
                if (course.size() > j) {
                    TextView textView = new TextView(getContext());
                    textView.setText(course.get(j).getFullname().split(" \\| ")[1]);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    if (i == 1) mon.addView(textView);
                    else if (i == 2) tue.addView(textView);
                    else if (i == 3) wed.addView(textView);
                    else if (i == 4) thu.addView(textView);
                    else if (i == 5) fri.addView(textView);
                }
            }
        }

        return view;
    }
}

