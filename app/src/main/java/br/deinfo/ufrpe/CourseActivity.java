package br.deinfo.ufrpe;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import br.deinfo.ufrpe.adapters.CourseContentAdapter;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.CourseContent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        mAvaService = Requests.getInstance().getAVAService();
        mCourse = Parcels.unwrap(getIntent().getParcelableExtra("course"));

        setTitle(mCourse.getClasses().getName());
        getSupportActionBar().setSubtitle(mCourse.getShortname());

        Functions.actionBarColor(this, mCourse.getNormalColor(), mCourse.getDarkColor());

        View bottomSheet = findViewById(R.id.bottomSheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    bottomSheetBehavior.setPeekHeight(0);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setActiveTabColor(Color.parseColor(mCourse.getNormalColor()));
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.topics:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    case R.id.participants:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                }
            }
        });

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

                CourseContentAdapter courseContentAdapter = new CourseContentAdapter(CourseActivity.this, mCourseContent, mCourse);
                recyclerView.setAdapter(courseContentAdapter);
            }

            @Override
            public void onFailure(Call<List<CourseContent>> call, Throwable t) {
                t.printStackTrace();
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
