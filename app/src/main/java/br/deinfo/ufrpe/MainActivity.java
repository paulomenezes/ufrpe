package br.deinfo.ufrpe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.deinfo.ufrpe.fragments.CalendarFragment;
import br.deinfo.ufrpe.fragments.TodayFragment;
import br.deinfo.ufrpe.fragments.WeekFragment;
import br.deinfo.ufrpe.models.User;
import br.deinfo.ufrpe.utils.Data;
import br.deinfo.ufrpe.utils.Session;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User mUser;

    private static boolean sTodaySelectWeek = false;
    private static int sSelectedMenu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sSelectedMenu = R.id.home;

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.home));

        mUser = Data.getUser(this);

        Session.setUser(mUser);
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
        if (sSelectedMenu == R.id.home) {
            if (!sTodaySelectWeek)
                getMenuInflater().inflate(R.menu.menu_today_week, menu);
            else
                getMenuInflater().inflate(R.menu.menu_today_day, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.day:
                sTodaySelectWeek = false;
                invalidateOptionsMenu();

                changeFragment(new TodayFragment());
                break;
            case R.id.week:
                sTodaySelectWeek = true;
                invalidateOptionsMenu();

                changeFragment(new WeekFragment());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        sSelectedMenu = id;

        if (item.isChecked())
            item.setChecked(false);
        else
            item.setChecked(true);

        if (id == R.id.home) {
            changeFragment(new TodayFragment());
        } else if (id == R.id.calendar) {
            changeFragment(new CalendarFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }
}
