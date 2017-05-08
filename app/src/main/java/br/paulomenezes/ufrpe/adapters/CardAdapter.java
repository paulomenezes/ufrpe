package br.paulomenezes.ufrpe.adapters;

import android.support.v7.widget.CardView;

/**
 * Created by paulo on 11/02/2017.
 */

public interface CardAdapter {
    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}