package br.paulomenezes.ufrpe.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.paulomenezes.ufrpe.models.User;

/**
 * Created by phgm on 03/10/2016.
 */

public class Data {
    private static final String KEY_SHARED = "br.deinfo.ufrpe";
    public static final String KEY_USER = "user";
    public static final String KEY_ANONYMOUS = "anonymous";
    public static final String KEY_CALENDAR = "calendar";

    public static void saveUser(Activity activity, User user) {
        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String json = new Gson().toJson(user);
            editor.putString(KEY_USER, json);
            editor.apply();
        }
    }

    public static User getUser(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString(KEY_USER, null);

        if (json != null)
            return new Gson().fromJson(json, User.class);
        else
            return null;
    }

    public static void saveAnonymous(Activity activity, boolean anonymous) {
        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean(KEY_ANONYMOUS, anonymous);
            editor.apply();
        }
    }

    public static boolean getAnonymous(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean(KEY_ANONYMOUS, false);
    }

    public static void saveCalendar(Activity activity, List<String> academicEvents) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String json = new Gson().toJson(academicEvents);
        editor.putString(KEY_CALENDAR, json);
        editor.apply();
    }

    public static List<String> getCalendar(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString(KEY_CALENDAR, null);

        if (json != null)
            return new Gson().fromJson(json, (new TypeToken<List<String>>(){}).getType());
        else
            return null;
    }
}
