package br.paulomenezes.ufrpe;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.LinkedTreeMap;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.paulomenezes.ufrpe.adapters.CourseContentAdapter;
import br.paulomenezes.ufrpe.adapters.GradeAdapter;
import br.paulomenezes.ufrpe.adapters.UsersAdapter;
import br.paulomenezes.ufrpe.models.AVAUser;
import br.paulomenezes.ufrpe.models.Assignments;
import br.paulomenezes.ufrpe.models.Course;
import br.paulomenezes.ufrpe.models.CourseAssignment;
import br.paulomenezes.ufrpe.models.CourseContent;
import br.paulomenezes.ufrpe.models.Grades;
import br.paulomenezes.ufrpe.models.Tabledatum;
import br.paulomenezes.ufrpe.models.User;
import br.paulomenezes.ufrpe.services.AVAService;
import br.paulomenezes.ufrpe.services.Requests;
import br.paulomenezes.ufrpe.start.LoginActivity;
import br.paulomenezes.ufrpe.utils.Data;
import br.paulomenezes.ufrpe.utils.Functions;
import br.paulomenezes.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseActivity extends AppCompatActivity {

    private AVAService mAvaService;
    private Course mCourse;

    private List<CourseContent> mCourseContent;
    private List<AVAUser> mParticipants;
    private List<Tabledatum> mGrades = new ArrayList<>();
    private List<Assignments> mAssignments;

    private CourseContentAdapter mCourseContentAdapter;
    private UsersAdapter mUsersAdapter;
    private GradeAdapter mGradeAdapter;

    private RelativeLayout mLoading;

    private int mOpenTab = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        mAvaService = Requests.getInstance().getAVAService();
        mCourse = Parcels.unwrap(getIntent().getParcelableExtra("course"));

        mLoading = (RelativeLayout) findViewById(R.id.loading);

        mCourseContentAdapter = new CourseContentAdapter(this, new ArrayList<CourseContent>(), mCourse);
        mUsersAdapter = new UsersAdapter(new ArrayList<AVAUser>());
        mGradeAdapter = new GradeAdapter(new ArrayList<Tabledatum>());

        setTitle(mCourse.getClasses().getName());
        getSupportActionBar().setSubtitle(mCourse.getShortname());

        Functions.actionBarColor(this, mCourse.getNormalColor(), mCourse.getDarkColor());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);

        User user = Session.getUser(this);

        if (savedInstanceState != null && savedInstanceState.getParcelable("assignments") != null) {
            mAssignments = Parcels.unwrap(savedInstanceState.getParcelable("assignments"));
            mCourse.setAssignments(mAssignments.toArray(new Assignments[mAssignments.size()]));
        } else {
            Call<CourseAssignment> object = mAvaService.getAssigments(new int[]{mCourse.getId()}, Requests.FUNCTION_GET_ASSIGNMENTS, user.getToken());
            object.enqueue(new Callback<CourseAssignment>() {
                @Override
                public void onResponse(Call<CourseAssignment> call, Response<CourseAssignment> response) {
                    mLoading.setVisibility(View.GONE);

                    CourseAssignment assignment = response.body();
                    if (assignment.getCourses() != null) {
                        mAssignments = Arrays.asList(assignment.getCourses()[0].getAssignments());
                        mCourse.setAssignments(assignment.getCourses()[0].getAssignments());
                    } else {
                        Toast.makeText(CourseActivity.this, R.string.expired_login, Toast.LENGTH_LONG).show();
                        Data.saveUser(CourseActivity.this, null);

                        Intent intent = new Intent(CourseActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<CourseAssignment> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        if (savedInstanceState != null && savedInstanceState.getParcelable("content") != null) {
            mCourseContent = Parcels.unwrap(savedInstanceState.getParcelable("content"));
            mCourseContentAdapter = new CourseContentAdapter(CourseActivity.this, mCourseContent, mCourse);
            mCourseContentAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(mCourseContentAdapter);
        } else {
            Call<List<CourseContent>> courseContentCall = mAvaService.getCourseContent(mCourse.getId(),
                    Requests.FUNCTION_GET_COURSE_CONTENTS, user.getToken());

            courseContentCall.enqueue(new Callback<List<CourseContent>>() {
                @Override
                public void onResponse(Call<List<CourseContent>> call, Response<List<CourseContent>> response) {
                    mCourseContent = response.body();

                    if (mCourseContent != null && mCourseContent.size() > 0) {
                        mCourseContentAdapter = new CourseContentAdapter(CourseActivity.this, mCourseContent, mCourse);
                        mCourseContentAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(mCourseContentAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<CourseContent>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        if (savedInstanceState != null && savedInstanceState.getParcelable("participants") != null) {
            mParticipants = Parcels.unwrap(savedInstanceState.getParcelable("participants"));
            mUsersAdapter = new UsersAdapter(mParticipants);
            mUsersAdapter.notifyDataSetChanged();
        } else {
            Call<List<AVAUser>> participantsCall = mAvaService.getParticipants(mCourse.getId(),
                    "limitfrom", 0, "limitnumber", 50, "sortby", "siteorder", Requests.FUNCTION_GET_PARTICIPANTS, user.getToken());

            participantsCall.enqueue(new Callback<List<AVAUser>>() {
                @Override
                public void onResponse(Call<List<AVAUser>> call, Response<List<AVAUser>> response) {
                    mParticipants = response.body();
                    if (mParticipants != null && mParticipants.size() > 0) {
                        mUsersAdapter = new UsersAdapter(mParticipants);
                        mUsersAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<AVAUser>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        if (savedInstanceState != null && savedInstanceState.getParcelable("grades") != null) {
            mGrades = Parcels.unwrap(savedInstanceState.getParcelable("grades"));
            mGradeAdapter = new GradeAdapter(mGrades);
            mGradeAdapter.notifyDataSetChanged();
        } else {
            Call<Grades> gradesCall = mAvaService.getGrades(mCourse.getId(), user.getAvaID(),
                    Requests.FUNCTION_GET_GRADES, user.getToken());

            final Gson gson = new Gson();

            gradesCall.enqueue(new Callback<Grades>() {
                @Override
                public void onResponse(Call<Grades> call, Response<Grades> response) {
                    if (response.body() != null && response.body().getTables() != null) {
                        List<Object> list = response.body().getTables().get(0).getTabledata();

                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i) instanceof LinkedTreeMap) {
                                JsonElement jsonElement = gson.toJsonTree(list.get(i));
                                Tabledatum pojo = gson.fromJson(jsonElement, Tabledatum.class);

                                mGrades.add(pojo);
                            }
                        }

                        mGradeAdapter = new GradeAdapter(mGrades);
                        mGradeAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Grades> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setActiveTabColor(Color.parseColor(mCourse.getNormalColor()));
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.topics:
                        mOpenTab = R.id.topics;
                        recyclerView.setAdapter(mCourseContentAdapter);
                        break;
                    case R.id.participants:
                        mOpenTab = R.id.participants;
                        recyclerView.setAdapter(mUsersAdapter);
                        break;
                    case R.id.grades:
                        mOpenTab = R.id.grades;
                        recyclerView.setAdapter(mGradeAdapter);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("tab", mOpenTab);

        if (mCourseContent != null)
            outState.putParcelable("content", Parcels.wrap(mCourseContent));

        if (mParticipants != null)
            outState.putParcelable("participants", Parcels.wrap(mParticipants));

        if (mGrades != null)
            outState.putParcelable("grades", Parcels.wrap(mGrades));
    }
}
