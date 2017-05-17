package br.paulomenezes.ufrpe;

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

import br.paulomenezes.ufrpe.adapters.CardPagerAdapter;
import br.paulomenezes.ufrpe.models.CardItem;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;

    //private CardFragmentPagerAdapter mFragmentCardAdapter;

    private List<Marker> mMakers = new ArrayList<>();

    private String[] mPlacesNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mPlacesNames = getResources().getStringArray(R.array.places_names);
        String[] address = getResources().getStringArray(R.array.places_addresses);
        String[] phones = getResources().getStringArray(R.array.places_numbers);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapter(mPlacesNames, address, phones);
        for (int i = 0; i < mPlacesNames.length; i++) {
            mCardAdapter.addCardItem(new CardItem(i));
        }

        //mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), Functions.dpToPx(this, 2));

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mMakers.size() > position) {
                    for (int i = 0; i < mMakers.size(); i++) {
                        mMakers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMakers.get(position).getPosition(), position > 0 ? 18 : 15));
                    mMakers.get(position).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng[] places = new LatLng[35];
        places[0] = new LatLng(-8.014497, -34.950267);
        places[1] = new LatLng(-8.0137558, -34.9486202);
        places[2] = new LatLng(-8.016048000000001, -34.9511978);
        places[3] = new LatLng(-8.0136065, -34.9503667);
        places[4] = new LatLng(-8.0148713, -34.94865499999999);
        places[5] = new LatLng(-8.0191842, -34.9497917);
        places[6] = new LatLng(-8.015979599999998, -34.9513225);
        places[7] = new LatLng(-8.013874099999999, -34.9479738);
        places[8] = new LatLng(-8.0175631, -34.95043569999999);
        places[9] = new LatLng(-8.014119700000002, -34.9476787);
        places[10] = new LatLng(-8.0160347, -34.9510798);
        places[11] = new LatLng(-8.0172936, -34.9500182);
        places[12] = new LatLng(-8.0161263, -34.9510427);
        places[13] = new LatLng(-8.014041299999999, -34.9511186);
        places[14] = new LatLng(-8.018802599999999, -34.9533573);
        places[15] = new LatLng(-8.0185563, -34.9498832);
        places[16] = new LatLng(-8.0137328, -34.9506194);
        places[17] = new LatLng(-8.0160347, -34.9510798);
        places[18] = new LatLng(-8.014653599999999, -34.94742389999999);
        places[19] = new LatLng(-8.0158339, -34.9508877);
        places[20] = new LatLng(-8.016666599999999, -34.9511517);
        places[21] = new LatLng(-8.0143787, -34.947253);
        places[22] = new LatLng(-8.0148142, -34.94850479999999);
        places[23] = new LatLng(-8.013559299999999, -34.95034150000001);
        places[24] = new LatLng(-8.014904, -34.951294);
        places[25] = new LatLng(-8.0145739, -34.9485086);
        places[26] = new LatLng(-8.0160347, -34.9510798);
        places[27] = new LatLng(-8.0158873, -34.9501467);
        places[28] = new LatLng(-8.016889899999999, -34.9452996);
        places[29] = new LatLng(-8.0181668, -34.94916);
        places[30] = new LatLng(-8.016573600000001, -34.9476737);
        places[31] = new LatLng(-8.014558, -34.95083290000001);
        places[32] = new LatLng(-8.0141519, -34.95033329999999);
        places[33] = new LatLng(-8.019203, -34.9495796);
        places[34] = new LatLng(-8.014626199999999, -34.9473549);

        for (int i = 0; i < places.length; i++) {
            if (i == 0)
                mMakers.add(mMap.addMarker(new MarkerOptions().position(places[i]).title(mPlacesNames[i]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
            else
                mMakers.add(mMap.addMarker(new MarkerOptions().position(places[i]).title(mPlacesNames[i])));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(places[0], 15));
        
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
