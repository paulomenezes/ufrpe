package br.deinfo.ufrpe.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.parceler.Parcels;

import java.util.Calendar;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.BR;
import br.deinfo.ufrpe.models.Restaurant;

/**
 * Created by paulo on 29/01/2017.
 */

public class RestaurantFragment extends Fragment {
    private ViewDataBinding mDataBinding;
    private int mDayOfWeek = 0;
    private static Restaurant mRestaurant;

    public static RestaurantFragment newInstance(int position) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putInt("dayOfWeek", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant, container, false);
        mDayOfWeek = getArguments().getInt("dayOfWeek");

        if (mRestaurant != null)
            mDataBinding.setVariable(BR.day, mRestaurant.getDaysOfWeek()[mDayOfWeek]);

        final View view = mDataBinding.getRoot();

        final LinearLayout lunchContent = (LinearLayout) view.findViewById(R.id.lunchContent);
        final LinearLayout dinnerContent = (LinearLayout) view.findViewById(R.id.dinnerContent);

        RelativeLayout lunchTitle = (RelativeLayout) view.findViewById(R.id.lunchTitle);
        RelativeLayout dinnerTitle = (RelativeLayout) view.findViewById(R.id.dinnerTitle);

        final ImageView lunchArrow = (ImageView) view.findViewById(R.id.lunchArrow);
        final ImageView dinnerArrow = (ImageView) view.findViewById(R.id.dinnerArrow);

        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) > 15) {
            dinnerContent.setVisibility(View.VISIBLE);
            dinnerArrow.setImageDrawable(getActivity().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        } else {
            lunchContent.setVisibility(View.VISIBLE);
            lunchArrow.setImageDrawable(getActivity().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        }

        lunchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lunchContent.getVisibility() == View.GONE) {
                    lunchContent.setVisibility(View.VISIBLE);
                    lunchArrow.setImageDrawable(getActivity().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                } else {
                    lunchContent.setVisibility(View.GONE);
                    lunchArrow.setImageDrawable(getActivity().getDrawable(R.drawable.ic_keyboard_arrow_down_grey_24dp));
                }
            }
        });

        dinnerTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dinnerContent.getVisibility() == View.GONE) {
                    dinnerContent.setVisibility(View.VISIBLE);
                    dinnerArrow.setImageDrawable(getActivity().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                } else {
                    dinnerContent.setVisibility(View.GONE);
                    dinnerArrow.setImageDrawable(getActivity().getDrawable(R.drawable.ic_keyboard_arrow_down_grey_24dp));
                }
            }
        });


        return view;
    }

    public void update(Restaurant restaurant) {
        if (restaurant != null) {
            mDataBinding.setVariable(BR.day, restaurant.getDaysOfWeek()[mDayOfWeek]);
            mRestaurant = restaurant;
        }
    }
}
