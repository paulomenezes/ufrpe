package br.paulomenezes.ufrpe;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.parceler.Parcels;

import java.util.Calendar;

import br.paulomenezes.ufrpe.async.RestaurantAsync;
import br.paulomenezes.ufrpe.fragments.RestaurantFragment;
import br.paulomenezes.ufrpe.listeners.RestaurantListener;
import br.paulomenezes.ufrpe.models.Restaurant;
import br.paulomenezes.ufrpe.utils.Functions;

public class RestaurantActivity extends AppCompatActivity implements RestaurantListener {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private Restaurant mRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 2;

        mViewPager.setCurrentItem(dayOfWeek, true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if (savedInstanceState == null) {
            new RestaurantAsync(this, this).execute();
        } else {
            mRestaurant = Parcels.unwrap(savedInstanceState.getParcelable("restaurant"));
            mSectionsPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("restaurant", Parcels.wrap(mRestaurant));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setView(R.layout.dialog_restaurant_info);
            dialog.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return RestaurantFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof RestaurantFragment)
                ((RestaurantFragment) object).update(mRestaurant);

            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            boolean isTablet = getResources().getBoolean(R.bool.isTablet) &&
                    Functions.getScreenOrientation(RestaurantActivity.this) == Configuration.ORIENTATION_LANDSCAPE;

            switch (position) {
                case 0:
                    return getString(R.string.monday) + (mRestaurant != null ? (isTablet ? "(" : "\n(") +
                            mRestaurant.getSegunda().getData().substring(0, 5) + ")" : "");
                case 1:
                    return getString(R.string.tuesday) + (mRestaurant != null ? (isTablet ? "(" : "\n(") +
                            mRestaurant.getTerca().getData().substring(0, 5) + ")" : "");
                case 2:
                    return getString(R.string.wednesday) + (mRestaurant != null ? (isTablet ? "(" : "\n(") +
                            mRestaurant.getQuarta().getData().substring(0, 5) + ")" : "");
                case 3:
                    return getString(R.string.thursday) + (mRestaurant != null ? (isTablet ? "(" : "\n(") +
                            mRestaurant.getQuinta().getData().substring(0, 5) + ")" : "");
                case 4:
                    return getString(R.string.friday) + (mRestaurant != null ? (isTablet ? "(" : "\n(") +
                            mRestaurant.getSexta().getData().substring(0, 5) + ")" : "");
            }
            return null;
        }
    }
}
