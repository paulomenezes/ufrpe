package br.ufrpe.conectada.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import br.ufrpe.conectada.R;
import br.ufrpe.conectada.listeners.RestaurantListener;
import br.ufrpe.conectada.models.Restaurant;

/**
 * Created by phgm on 12/03/2017.
 */

public class RestaurantAsync extends AsyncTask<Void, Void, Restaurant> {
    private Activity mActivity;
    private RestaurantListener mListener;
    private ProgressDialog mLoading;

    public RestaurantAsync(Activity activiy, RestaurantListener listener) {
        mActivity = activiy;
        mListener = listener;
        mLoading = ProgressDialog.show(mActivity, null, mActivity.getString(R.string.loading), true);
    }

    @Override
    protected Restaurant doInBackground(Void... voids) {
        Restaurant restaurant = null;

        try {
            String url = "http://restaurante.6te.net/restaurante.xml";

            URLConnection connection = (new URL(url)).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            // Read and store the result line by line then return the entire string.
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "ISO-8859-1"));

            Serializer serializer = new Persister();
            restaurant = serializer.read(Restaurant.class, reader);

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restaurant;
    }

    @Override
    protected void onPostExecute(Restaurant restaurant) {
        super.onPostExecute(restaurant);

        mLoading.dismiss();
        mListener.getRestaurant(restaurant);
    }
}
