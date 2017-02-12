package br.deinfo.ufrpe;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.deinfo.ufrpe.adapters.CardFragmentPagerAdapter;
import br.deinfo.ufrpe.adapters.CardPagerAdapter;
import br.deinfo.ufrpe.models.CardItem;
import br.deinfo.ufrpe.utils.Functions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;

    private CardFragmentPagerAdapter mFragmentCardAdapter;

    private List<Marker> mMakers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.ufrpe, R.string.ufrpe));
        mCardAdapter.addCardItem(new CardItem(R.string.ceagri1, R.string.ufrpe));
        mCardAdapter.addCardItem(new CardItem(R.string.ceagri2, R.string.ufrpe));
        mCardAdapter.addCardItem(new CardItem(R.string.cegoe, R.string.ufrpe));
        mCardAdapter.addCardItem(new CardItem(R.string.cegen, R.string.ufrpe));

        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), Functions.dpToPx(this, 2));

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mMakers.size() > position) {
                    for (int i = 0; i < mMakers.size(); i++) {
                        mMakers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMakers.get(position).getPosition(), position > 0 ? 18 : 15));
                    mMakers.get(position).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ufrpe = new LatLng(-8.0154518, -34.9495108);
        LatLng ceagri1 = new LatLng(-8.01779, -34.94410);
        LatLng ceagri2 = new LatLng(-8.01767, -34.94443);
        LatLng cegoe = new LatLng(-8.0172937, -34.9515659);
        LatLng cegen = new LatLng(-8.0170824, -34.9511938);

        mMakers.add(mMap.addMarker(new MarkerOptions().position(ufrpe).title(getString(R.string.ufrpe)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
        mMakers.add(mMap.addMarker(new MarkerOptions().position(ceagri1).title(getString(R.string.ceagri1))));
        mMakers.add(mMap.addMarker(new MarkerOptions().position(ceagri2).title(getString(R.string.ceagri2))));
        mMakers.add(mMap.addMarker(new MarkerOptions().position(cegoe).title(getString(R.string.cegoe))));
        mMakers.add(mMap.addMarker(new MarkerOptions().position(cegen).title(getString(R.string.cegen))));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ufrpe, 15));
        
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (int i = 0; i < mMakers.size(); i++) {
                    if (mMakers.get(i).getPosition().latitude == marker.getPosition().latitude &&
                        mMakers.get(i).getPosition().longitude == marker.getPosition().longitude) {
                        mMakers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        mViewPager.setCurrentItem(i, true);
                    } else {
                        mMakers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }
                }

                return true;
            }
        });
    }
}
