package br.deinfo.ufrpe;

import android.content.Intent;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Locale;

import br.deinfo.ufrpe.fragments.CalendarFragment;
import br.deinfo.ufrpe.fragments.TodayFragment;
import br.deinfo.ufrpe.fragments.WeekFragment;
import br.deinfo.ufrpe.listeners.MainTitle;
import br.deinfo.ufrpe.models.User;
import br.deinfo.ufrpe.start.TipsActivity;
import br.deinfo.ufrpe.utils.Data;
import br.deinfo.ufrpe.utils.Session;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainTitle {

    private User mUser;

    private static boolean sTodaySelectWeek = false;
    private static int sSelectedMenu = -1;

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

        mUser = Data.getUser(this);
        Session.setUser(mUser);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        CircularImageView imageView = (CircularImageView) headerView.findViewById(R.id.picture);
        Picasso.with(this).load(mUser.getPicture()).into(imageView);

        TextView name = (TextView) headerView.findViewById(R.id.name);
        TextView email = (TextView) headerView.findViewById(R.id.email);

        name.setText(String.format(Locale.ENGLISH, "%s %s", mUser.getFirstName(), mUser.getLastName()));
        email.setText(mUser.getEmail());

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.home));
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

                changeFragment(new TodayFragment(), true);
                break;
            case R.id.week:
                sTodaySelectWeek = true;
                invalidateOptionsMenu();

                changeFragment(new WeekFragment(), true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        boolean first = sSelectedMenu == -1;

        sSelectedMenu = id;

        if (item.isChecked())
            item.setChecked(false);
        else
            item.setChecked(true);

        if (id == R.id.home) {
            setTitle(getString(R.string.title_activity_main));

            invalidateOptionsMenu();
            changeFragment(new TodayFragment(), first);
        } else if (id == R.id.calendar) {
            setTitle(getString(R.string.calendar));

            invalidateOptionsMenu();
            CalendarFragment calendarFragment = new CalendarFragment();
            calendarFragment.setMainTitle(this);
            changeFragment(calendarFragment, first);
        } else if (id == R.id.logout) {
            Data.saveUser(this, null);

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, TipsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment, boolean first) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        if (!first)
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    @Override
    public void updateTitle(String title) {
        setTitle(title);
    }
}
