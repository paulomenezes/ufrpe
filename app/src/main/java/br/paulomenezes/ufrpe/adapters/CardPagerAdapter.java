package br.paulomenezes.ufrpe.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.models.CardItem;

/**
 * Created by paulo on 11/02/2017.
 */

public class CardPagerAdapter extends RecyclerView.Adapter<CardPagerAdapter.CoursePagerViewHolder> {
    private String[] mNames;
    private String[] mAddress;
    private String[] mPhones;

    public CardPagerAdapter(String[] names, String[] address, String[] phones) {
        mNames = names;
        mAddress = address;
        mPhones = phones;
    }

    @Override
    public CoursePagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_map_department, parent, false);

        return new CardPagerAdapter.CoursePagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CoursePagerViewHolder holder, int position) {
        holder.mName.setText(mNames[position]);
        holder.mAddress.setText(mAddress[position]);
        holder.mPhone.setText(mPhones[position]);
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }


    public class CoursePagerViewHolder extends RecyclerView.ViewHolder {
        private TextView mName, mAddress, mPhone;

        public CoursePagerViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.name);
            mAddress = (TextView) itemView.findViewById(R.id.address);
            mPhone = (TextView) itemView.findViewById(R.id.phone);
        }
    }

}