package br.ufrpe.conectada.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.ufrpe.conectada.R;
import br.ufrpe.conectada.listeners.LoadScheduleListener;
import br.ufrpe.conectada.models.Classes;

/**
 * Created by paulo on 29/01/2017.
 */

public class LoadScheduleAsync extends AsyncTask<Void, Void, List<Classes>> {
    private LoadScheduleListener mListener;
    private ProgressDialog mLoading;
    private Context mContext;

    public LoadScheduleAsync(Context context, LoadScheduleListener listener) {
        mListener = listener;
        mContext = context;
        mLoading = ProgressDialog.show(context, null, context.getString(R.string.loading), true);
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected List<Classes> doInBackground(Void... voids) {
        List<Classes> listClasses = new ArrayList<>();

        try {
            InputStream is = mContext.getResources().openRawResource(R.raw.schedules);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String bufferString = new String(buffer);

            JSONArray jsonArray = new JSONArray(bufferString);

            for (int i = 0; i < jsonArray.length(); i++) {
                Classes classes = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), Classes.class);
                listClasses.add(classes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listClasses;
    }

    @Override
    protected void onPostExecute(List<Classes> classes) {
        super.onPostExecute(classes);

        mListener.schedule(classes);
        mLoading.dismiss();
    }
}

