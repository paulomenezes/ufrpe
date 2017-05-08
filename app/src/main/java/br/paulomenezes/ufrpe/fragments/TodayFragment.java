package br.paulomenezes.ufrpe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.adapters.CoursesAdapter;
import br.paulomenezes.ufrpe.models.Classes;
import br.paulomenezes.ufrpe.models.Course;
import br.paulomenezes.ufrpe.models.CourseAssignment;
import br.paulomenezes.ufrpe.models.Courses;
import br.paulomenezes.ufrpe.models.User;
import br.paulomenezes.ufrpe.services.AVAService;
import br.paulomenezes.ufrpe.services.Requests;
import br.paulomenezes.ufrpe.utils.CompareTodayCourse;
import br.paulomenezes.ufrpe.utils.Data;
import br.paulomenezes.ufrpe.utils.Functions;
import br.paulomenezes.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paulo on 20/10/2016.
 */

public class TodayFragment extends Fragment {
    public static List<Classes> sClasses;
    private User mUser;

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_today, container, false);

        mUser = Session.getUser(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        boolean classLoaded = false;

        for (int i = 0; i < mUser.getCourses().size(); i++) {
            if (mUser.getCourses().get(i).getClasses() != null) {
                classLoaded = true;
                break;
            }
        }

        if (!classLoaded) {
            if (sClasses == null || sClasses.size() == 0) {
                mDatabase.child("schedules").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        sClasses = dataSnapshot.getValue(new GenericTypeIndicator<List<Classes>>() { });
                        load(view);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                load(view);
            }
        } else {
            load(view);
        }

        return view;
    }

    private void load(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        Calendar calendar = Calendar.getInstance();
        final List<Course> todayCourses = new ArrayList<>();

        int today = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (today == 0 || today == 6)
            today = 1;


        for (int i = 0; i < mUser.getCourses().size(); i++) {
            if (Functions.thisSemester(mUser, mUser.getCourses().get(i).getShortname())) {
                if (sClasses != null) {
                    for (int j = 0; j < sClasses.size(); j++) {
                        if (mUser.getCourses().get(i).getShortname().split("-")[1].contains(sClasses.get(j).getCod())) {
                            mUser.getCourses().get(i).setClasses(sClasses.get(j));
                            break;
                        }
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

        if (sClasses != null) {
            Data.saveUser(getActivity(), mUser);
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
        } else {
            day.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        }

        TextView date = (TextView) view.findViewById(R.id.date);

        String dayOfWeek = getResources().getStringArray(R.array.day_of_week)[today];
        String month = getResources().getStringArray(R.array.month)[calendar.get(Calendar.MONTH)].toLowerCase();

        date.setText(String.format("%s, %s", dayOfWeek, month));

        AVAService mAVAServices = Requests.getInstance().getAVAService();

        int[] courseIDS = new int[todayCourses.size()];
        for (int i = 0; i < todayCourses.size(); i++) {
            if (Functions.thisSemester(mUser, todayCourses.get(i).getShortname())) {
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
    }
}
