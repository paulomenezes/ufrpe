package br.deinfo.ufrpe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import br.deinfo.ufrpe.fragments.RestaurantFragment;
import br.deinfo.ufrpe.listeners.RestaurantListener;
import br.deinfo.ufrpe.models.Restaurant;
import br.deinfo.ufrpe.services.Requests;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity {
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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Call<Restaurant> restaurantCall = Requests.getInstance().getRestaurantService().getRestaurant();
        restaurantCall.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                mRestaurant = response.body();
                mSectionsPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
            switch (position) {
                case 0:
                    return getString(R.string.monday) + (mRestaurant != null ? "\n(" + mRestaurant.getSegunda().getData().substring(0, 5) + ")" : "");
                case 1:
                    return getString(R.string.tuesday) + (mRestaurant != null ? "\n(" + mRestaurant.getTerca().getData().substring(0, 5) + ")" : "");
                case 2:
                    return getString(R.string.wednesday) + (mRestaurant != null ? "\n(" + mRestaurant.getQuarta().getData().substring(0, 5) + ")" : "");
                case 3:
                    return getString(R.string.thursday) + (mRestaurant != null ? "\n(" + mRestaurant.getQuinta().getData().substring(0, 5) + ")" : "");
                case 4:
                    return getString(R.string.friday) + (mRestaurant != null ? "\n(" + mRestaurant.getSexta().getData().substring(0, 5) + ")" : "");
            }
            return null;
        }
    }
}
