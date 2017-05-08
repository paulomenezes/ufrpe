package br.ufrpe.conectada;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.ufrpe.conectada.fragments.AcademicCalendarFragment;
import br.ufrpe.conectada.fragments.CalendarFragment;
import br.ufrpe.conectada.fragments.LibraryFragment;
import br.ufrpe.conectada.fragments.MessagesFragment;
import br.ufrpe.conectada.fragments.RestaurantFragment;
import br.ufrpe.conectada.fragments.SubjectFragment;
import br.ufrpe.conectada.fragments.TodayFragment;
import br.ufrpe.conectada.fragments.WeekFragment;
import br.ufrpe.conectada.listeners.MainTitle;
import br.ufrpe.conectada.models.Course;
import br.ufrpe.conectada.models.User;
import br.ufrpe.conectada.services.AVAService;
import br.ufrpe.conectada.services.Requests;
import br.ufrpe.conectada.start.LoginActivity;
import br.ufrpe.conectada.utils.Data;
import br.ufrpe.conectada.utils.Functions;
import br.ufrpe.conectada.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainTitle {

    private User mUser;

    private NavigationView mNavigationView;
    private static int sSelectedMenu = -1;

    private Map<Integer, Fragment> mFragmentMap;
    private ActionBarDrawerToggle mDrawerToggle;

    private ProgressDialog mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mFragmentMap = new HashMap<>();
        mFragmentMap.put(R.id.home, new TodayFragment());
        mFragmentMap.put(R.id.timetable, new WeekFragment());
        mFragmentMap.put(R.id.messages, new MessagesFragment());
        mFragmentMap.put(R.id.library, new LibraryFragment());
        mFragmentMap.put(R.id.restaurant, new RestaurantFragment());
        mFragmentMap.put(R.id.subjects, new SubjectFragment());

        CalendarFragment calendarFragment = new CalendarFragment();
        calendarFragment.setMainTitle(this);
        mFragmentMap.put(R.id.calendar, calendarFragment);

        AcademicCalendarFragment academicCalendarFragment = new AcademicCalendarFragment();
        academicCalendarFragment.setMainTitle(this);
        mFragmentMap.put(R.id.ufrpecalendar, academicCalendarFragment);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        if (Session.getUser(this) == null) {
            mUser = Data.getUser(this);
            Session.setUser(mUser);
        } else {
            mUser = Session.getUser(this);
        }

        View headerView = mNavigationView.inflateHeaderView(R.layout.nav_header_main);

        CircularImageView imageView = (CircularImageView) headerView.findViewById(R.id.picture);
        Picasso.with(this).load(mUser.getPicture()).into(imageView);

        TextView name = (TextView) headerView.findViewById(R.id.name);
        TextView semester = (TextView) headerView.findViewById(R.id.semester);

        name.setText(String.format(Locale.ENGLISH, "%s %s", mUser.getFirstName(), mUser.getLastName()));
        semester.setText(mUser.getCurrentSemester());

        if (savedInstanceState != null) {
            int menu = savedInstanceState.getInt("menu");
            sSelectedMenu = menu;

            onNavigationItemSelected(mNavigationView.getMenu().findItem(menu));
        } else {
            onNavigationItemSelected(mNavigationView.getMenu().findItem(R.id.home));
        }
    }

    private int getCheckedItem() {
        return sSelectedMenu;

//        Menu menu = mNavigationView.getMenu();
//        for (int i = 0; i < menu.size(); i++) {
//            MenuItem item = menu.getItem(i);
//            if (item.isChecked()) {
//                return item.getItemId();
//            } else if (item.hasSubMenu()) {
//                for (int j = 0; j < item.getSubMenu().size(); j++) {
//                    MenuItem subMenu = menu.getItem(i).getSubMenu().getItem(j);
//
//                    if (subMenu.isChecked())
//                        return subMenu.getItemId();
//                }
//            }
//        }
//
//        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (sSelectedMenu == R.id.home)
            getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sync) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setView(R.layout.dialog_sync);
            dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sync();
                }
            });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sync() {
        AVAService mAVAServices = Requests.getInstance().getAVAService();

        Call<List<Course>> coursesCall = mAVAServices.getUsersCourses(
                mUser.getAvaID(),
                Requests.FUNCTION_GET_USER_COURSES,
                mUser.getToken());

        mLoading = new ProgressDialog(this);
        mLoading.setTitle(R.string.loading);
        mLoading.setMessage(getString(R.string.loadingCourses));
        mLoading.setIndeterminate(true);
        mLoading.show();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;

        final String[] normalColors = new String[] { "#F44336", "#E91E63", "#9C27B0", "#673AB7",
                "#3F51B5", "#2196F3", "#039BE5", "#0097A7", "#009688", "#43A047", "#689F38", "#EF6C00", "#FF5722", "#795548", "#757575" };
        final String[] darkColors = new String[] { "#E53935", "#D81B60", "#8E24AA", "#5E35B1",
                "#3949AB", "#1E88E5", "#0288D1", "#00838F", "#00897B", "#388E3C", "#558B2F", "#E65100", "#F4511E", "#6D4C41", "#616161" };


        coursesCall.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                final List<Course> courses = response.body();
                mLoading.setMessage(getString(R.string.saving));

                Collections.reverse(courses);

                mDatabase.child("currentSemester").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mUser.setCurrentSemester(String.valueOf(dataSnapshot.getValue()));

                        int k = 0;
                        for (int i = 0; i < courses.size(); i++) {
                            if (Functions.thisSemester(mUser, courses.get(i).getShortname())) {
                                courses.get(i).setNormalColor(normalColors[k]);
                                courses.get(i).setDarkColor(darkColors[k]);

                                k++;
                                if (k >= normalColors.length - 1)
                                    k = 0;
                            } else {
                                courses.get(i).setNormalColor(normalColors[normalColors.length - 1]);
                                courses.get(i).setDarkColor(darkColors[normalColors.length - 1]);
                            }
                        }

                        mUser.setCourses(courses);
                        Data.saveUser(MainActivity.this, mUser);

                        mLoading.dismiss();

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mLoading.dismiss();
                        Toast.makeText(MainActivity.this, R.string.login_siteinfo_error, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                mLoading.dismiss();
                Toast.makeText(MainActivity.this, R.string.login_courses_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("menu", getCheckedItem());
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        boolean first = sSelectedMenu == -1;

        sSelectedMenu = id;

        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
            mNavigationView.getMenu().getItem(i).setChecked(false);

            if (mNavigationView.getMenu().getItem(i).hasSubMenu()) {
                for (int j = 0; j < mNavigationView.getMenu().getItem(i).getSubMenu().size(); j++) {
                    mNavigationView.getMenu().getItem(i).getSubMenu().getItem(j).setChecked(false);
                }
            }
        }

        item.setChecked(true);

        if (id == R.id.home) {
            setTitle(getString(R.string.overview));
        } else if (id == R.id.calendar) {
            setTitle(getString(R.string.calendar));
        } else if (id == R.id.timetable) {
            setTitle(getString(R.string.timetable));
        } else if (id == R.id.messages) {
            setTitle(getString(R.string.messages));
        } else if (id == R.id.library) {
            setTitle(getString(R.string.library));

            mNavigationView.getMenu().findItem(R.id.ufrpe).getSubMenu().findItem(R.id.library).setChecked(true);
        } else if (id == R.id.restaurant) {
            Intent i = new Intent(this, RestaurantActivity.class);
            startActivity(i);
        } else if (id == R.id.map) {
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
        } else if (id == R.id.ufrpecalendar) {
            setTitle(getString(R.string.ufrpeCalendar));
        } else if (id == R.id.subjects) {
            setTitle(getString(R.string.subjects));
        } else if (id == R.id.logout) {
            Data.saveUser(this, null);

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        if (id != R.id.logout && id != R.id.restaurant && id != R.id.map) {
            supportInvalidateOptionsMenu();
            changeFragment(id, first);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(int id, boolean first) {
        if (mFragmentMap.containsKey(id)) {
            Fragment fragment = mFragmentMap.get(id);

            if (getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName()) != null)
                fragment = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment, fragment.getClass().getSimpleName());

            if (!first)
                fragmentTransaction.addToBackStack(fragment.getClass().getName());

            fragmentTransaction.commit();
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void updateTitle(String title) {
        setTitle(title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (sSelectedMenu == R.id.library) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
