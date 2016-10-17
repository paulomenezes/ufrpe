package br.deinfo.ufrpe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.deinfo.ufrpe.adapters.CoursesAdapter;
import br.deinfo.ufrpe.models.Classes;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.CourseAssignment;
import br.deinfo.ufrpe.models.Courses;
import br.deinfo.ufrpe.models.User;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.Data;
import br.deinfo.ufrpe.utils.Functions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User mUser;
    private List<Classes> mClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mUser = Data.getUser(this);

        try {
            loadSchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        final CoursesAdapter coursesAdapter = new CoursesAdapter(mUser.getCourses(), mClasses);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(coursesAdapter);

        Calendar calendar = Calendar.getInstance();

        TextView day = (TextView) findViewById(R.id.day);
        day.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        TextView date = (TextView) findViewById(R.id.date);

        String dayOfWeek = getResources().getStringArray(R.array.day_of_week)[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        String month = getResources().getStringArray(R.array.month)[calendar.get(Calendar.MONTH)].toLowerCase();

        date.setText(String.format("%s, %s", dayOfWeek, month));

        AVAService mAVAServices = Requests.getInstance().getAVAService();
        List<Integer> courseids = new ArrayList<>();
        for (Course course: mUser.getCourses()) {
            if (Functions.thisSemester(course.getShortname())) {
                courseids.add(course.getId());
            }
        }

        int[] courseIDS = new int[courseids.size()];
        for (int i = 0; i < courseids.size(); i++) {
            courseIDS[i] = courseids.get(i);
        }

        Call<CourseAssignment> object = mAVAServices.getAssigments(courseIDS, Requests.FUNCTION_GET_ASSIGNMENTS, mUser.getToken());
        object.enqueue(new Callback<CourseAssignment>() {
            @Override
            public void onResponse(Call<CourseAssignment> call, Response<CourseAssignment> response) {
                CourseAssignment assignment = response.body();

                for (Courses assigments: assignment.getCourses()) {
                    for (int j = 0; j < mUser.getCourses().size(); j++) {
                        if (assigments.getId().equals(String.valueOf(mUser.getCourses().get(j).getId()))) {
                            coursesAdapter.setAssignments(Integer.parseInt(assigments.getId()), assigments.getAssignments());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CourseAssignment> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadSchedule() throws Exception {
        InputStream is = this.getResources().openRawResource(R.raw.schedules);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        String bufferString = new String(buffer);

        JSONArray jsonArray = new JSONArray(bufferString);

        mClasses = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Classes classes = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), Classes.class);
            mClasses.add(classes);
        }
    }
}
