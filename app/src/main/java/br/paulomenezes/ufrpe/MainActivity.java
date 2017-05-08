package br.paulomenezes.ufrpe;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.paulomenezes.ufrpe.fragments.AcademicCalendarFragment;
import br.paulomenezes.ufrpe.fragments.CalendarFragment;
import br.paulomenezes.ufrpe.fragments.LibraryFragment;
import br.paulomenezes.ufrpe.fragments.MessagesFragment;
import br.paulomenezes.ufrpe.fragments.RestaurantFragment;
import br.paulomenezes.ufrpe.fragments.SubjectFragment;
import br.paulomenezes.ufrpe.fragments.TodayFragment;
import br.paulomenezes.ufrpe.fragments.WeekFragment;
import br.paulomenezes.ufrpe.listeners.MainTitle;
import br.paulomenezes.ufrpe.models.User;
import br.paulomenezes.ufrpe.start.LoginActivity;
import br.paulomenezes.ufrpe.utils.Data;
import br.paulomenezes.ufrpe.utils.Session;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainTitle {

    private User mUser;

    private NavigationView mNavigationView;
    private static int sSelectedMenu = -1;

    private Map<Integer, Fragment> mFragmentMap;
    private ActionBarDrawerToggle mDrawerToggle;

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
            invalidateOptionsMenu();
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
