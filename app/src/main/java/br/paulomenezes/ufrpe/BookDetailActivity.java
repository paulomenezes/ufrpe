package br.paulomenezes.ufrpe;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import org.parceler.Parcels;

import br.paulomenezes.ufrpe.fragments.BookCopiesFragment;
import br.paulomenezes.ufrpe.fragments.BookDetailFragment;
import br.paulomenezes.ufrpe.fragments.BookReferencesFragment;
import br.paulomenezes.ufrpe.models.Book;

public class BookDetailActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        mBook = Parcels.unwrap(getIntent().getParcelableExtra("book"));
        setTitle(mBook.getTitle());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private Fragment[] mPages;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

            mPages = new Fragment[this.getCount()];
            mPages[0] = BookDetailFragment.newInstance(mBook);
            mPages[1] = BookCopiesFragment.newInstance(mBook);
            mPages[2] = BookReferencesFragment.newInstance(mBook);
        }

        @Override
        public Fragment getItem(int position) {
            return mPages[position];
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.book_detail);
                case 1:
                    return getString(R.string.copies);
                case 2:
                    return getString(R.string.references);
            }
            return null;
        }
    }
}
