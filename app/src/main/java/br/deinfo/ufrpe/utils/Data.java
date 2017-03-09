package br.deinfo.ufrpe.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.deinfo.ufrpe.models.User;

/**
 * Created by phgm on 03/10/2016.
 */

public class Data {
    public static final String KEY_SHARED = "br.deinfo.ufrpe";
    public static final String KEY_USER = "user";

    public static void saveUser(Activity activity, User user) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String json = new Gson().toJson(user);
        editor.putString(KEY_USER, json);
        editor.apply();
    }

    public static User getUser(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString(KEY_USER, null);

        if (json != null)
            return new Gson().fromJson(json, User.class);
        else
            return null;
    }
}
