package br.deinfo.ufrpe;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import br.deinfo.ufrpe.adapters.CourseContentAdapter;
import br.deinfo.ufrpe.adapters.GradeAdapter;
import br.deinfo.ufrpe.adapters.UsersAdapter;
import br.deinfo.ufrpe.models.AVAUser;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.CourseContent;
import br.deinfo.ufrpe.models.Grade;
import br.deinfo.ufrpe.models.Grades;
import br.deinfo.ufrpe.models.Table;
import br.deinfo.ufrpe.models.Tabledatum;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.Functions;
import br.deinfo.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseActivity extends AppCompatActivity {

    private AVAService mAvaService;
    private Course mCourse;

    private List<CourseContent> mCourseContent;
    private List<AVAUser> mParticipants;
    private List<Tabledatum> mGrades;

    private CourseContentAdapter mCourseContentAdapter;
    private UsersAdapter mUsersAdapter;
    private GradeAdapter mGradeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        mAvaService = Requests.getInstance().getAVAService();
        mCourse = Parcels.unwrap(getIntent().getParcelableExtra("course"));

        mCourseContentAdapter = new CourseContentAdapter(this, new ArrayList<CourseContent>(), mCourse);
        mUsersAdapter = new UsersAdapter(new ArrayList<AVAUser>());
        mGradeAdapter = new GradeAdapter(new ArrayList<Tabledatum>());

        setTitle(mCourse.getClasses().getName());
        getSupportActionBar().setSubtitle(mCourse.getShortname());

        Functions.actionBarColor(this, mCourse.getNormalColor(), mCourse.getDarkColor());

        mCourseContent = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);

        Call<List<CourseContent>> courseContentCall = mAvaService.getCourseContent(mCourse.getId(),
                Requests.FUNCTION_GET_COURSE_CONTENTS, Session.getUser().getToken());

        courseContentCall.enqueue(new Callback<List<CourseContent>>() {
            @Override
            public void onResponse(Call<List<CourseContent>> call, Response<List<CourseContent>> response) {
                mCourseContent = response.body();

                mCourseContentAdapter = new CourseContentAdapter(CourseActivity.this, mCourseContent, mCourse);
                mCourseContentAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mCourseContentAdapter);
            }

            @Override
            public void onFailure(Call<List<CourseContent>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Call<List<AVAUser>> participantsCall = mAvaService.getParticipants(mCourse.getId(),
                "limitfrom", 0, "limitnumber", 50, "sortby", "siteorder", Requests.FUNCTION_GET_PARTICIPANTS, Session.getUser().getToken());

        participantsCall.enqueue(new Callback<List<AVAUser>>() {
            @Override
            public void onResponse(Call<List<AVAUser>> call, Response<List<AVAUser>> response) {
                mParticipants = response.body();
                mUsersAdapter = new UsersAdapter(mParticipants);
                mUsersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AVAUser>> call, Throwable t) {

            }
        });

        Call<Grades> gradesCall = mAvaService.getGrades(mCourse.getId(), Session.getUser().getAvaID(),
                Requests.FUNCTION_GET_GRADES, Session.getUser().getToken());

        gradesCall.enqueue(new Callback<Grades>() {
            @Override
            public void onResponse(Call<Grades> call, Response<Grades> response) {
                mGrades = response.body().getTables().get(0).getTabledata();
                mGradeAdapter = new GradeAdapter(mGrades);
                mGradeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Grades> call, Throwable t) {

            }
        });

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setActiveTabColor(Color.parseColor(mCourse.getNormalColor()));
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.topics:
                        recyclerView.setAdapter(mCourseContentAdapter);
                        break;
                    case R.id.participants:
                        recyclerView.setAdapter(mUsersAdapter);
                        break;
                    case R.id.grades:
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
}
