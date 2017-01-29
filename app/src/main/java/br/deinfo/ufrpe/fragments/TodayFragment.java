package br.deinfo.ufrpe.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.adapters.CoursesAdapter;
import br.deinfo.ufrpe.models.Classes;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.CourseAssignment;
import br.deinfo.ufrpe.models.Courses;
import br.deinfo.ufrpe.models.User;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.CompareTodayCourse;
import br.deinfo.ufrpe.utils.Functions;
import br.deinfo.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paulo on 20/10/2016.
 */

public class TodayFragment extends Fragment {
    public static List<Classes> sClasses;
    private User mUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        mUser = Session.getUser();

        if (sClasses == null || sClasses.size() == 0) {
            try {
                loadSchedule();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        Calendar calendar = Calendar.getInstance();
        final List<Course> todayCourses = new ArrayList<>();

        int today = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (today == 0 || today == 6)
            today = 1;

        for (int i = 0; i < mUser.getCourses().size(); i++) {
            if (Functions.thisSemester(mUser.getCourses().get(i).getShortname())) {
                for (int j = 0; j < sClasses.size(); j++) {
                    if (sClasses.get(j).getCod().equals(mUser.getCourses().get(i).getShortname().split("-")[1])) {
                        mUser.getCourses().get(i).setClasses(sClasses.get(j));
                        break;
                    }
                }

                for (int j = 0; j < mUser.getCourses().get(i).getClasses().getSchedules().size(); j++) {
                    if (mUser.getCourses().get(i).getClasses().getSchedules().get(j).getDayOfWeek() == today) {
                        todayCourses.add(mUser.getCourses().get(i));
                        break;
                    }
                }
            }
        }

        Collections.sort(todayCourses, new CompareTodayCourse());

        final CoursesAdapter coursesAdapter = new CoursesAdapter(todayCourses);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(coursesAdapter);

        TextView day = (TextView) view.findViewById(R.id.day);

        if (calendar.get(Calendar.DAY_OF_WEEK) - 1 == 0 ) {
            day.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + 1));
        } else if (calendar.get(Calendar.DAY_OF_WEEK) - 1 == 6) {
            day.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + 2));
        }

        TextView date = (TextView) view.findViewById(R.id.date);

        String dayOfWeek = getResources().getStringArray(R.array.day_of_week)[today];
        String month = getResources().getStringArray(R.array.month)[calendar.get(Calendar.MONTH)].toLowerCase();

        date.setText(String.format("%s, %s", dayOfWeek, month));

        AVAService mAVAServices = Requests.getInstance().getAVAService();

        int[] courseIDS = new int[todayCourses.size()];
        for (int i = 0; i < todayCourses.size(); i++) {
            if (Functions.thisSemester(todayCourses.get(i).getShortname())) {
                courseIDS[i] = todayCourses.get(i).getId();
            }
        }

        Call<CourseAssignment> object = mAVAServices.getAssigments(courseIDS, Requests.FUNCTION_GET_ASSIGNMENTS, mUser.getToken());
        object.enqueue(new Callback<CourseAssignment>() {
            @Override
            public void onResponse(Call<CourseAssignment> call, Response<CourseAssignment> response) {
                CourseAssignment assignment = response.body();

                for (Courses assigments: assignment.getCourses()) {
                    for (int j = 0; j < todayCourses.size(); j++) {
                        if (assigments.getId().equals(String.valueOf(todayCourses.get(j).getId()))) {
                            todayCourses.get(j).setAssignments(assigments.getAssignments());
                            break;
                        }
                    }
                }

                coursesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CourseAssignment> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return view;
    }

    private void loadSchedule() throws Exception {
        InputStream is = this.getResources().openRawResource(R.raw.schedules);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        String bufferString = new String(buffer);

        JSONArray jsonArray = new JSONArray(bufferString);

        sClasses = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Classes classes = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), Classes.class);
            sClasses.add(classes);
        }
    }
}
