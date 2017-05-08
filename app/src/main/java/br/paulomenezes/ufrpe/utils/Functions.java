package br.paulomenezes.ufrpe.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import br.paulomenezes.ufrpe.models.User;

/**
 * Created by phgm on 10/10/2016.
 */

public class Functions {
    public static String getCamelSentence(String sentence) {
        String[] words = sentence.split(" ");
        String result = "";

        for (String word : words) {
            result += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ";
        }

        return result;
    }

//    public static boolean thisSemester(String shortName) {
//        String semester = "2016.2"; // Calendar.getInstance().get(Calendar.YEAR) + "." + (Calendar.getInstance().get(Calendar.YEAR) > 6 ? 2 : 1);
//
//        return shortName.contains(semester);
//    }

    public static boolean thisSemester(User user, String shortName) {
        String semester = user.getCurrentSemester(); // Calendar.getInstance().get(Calendar.YEAR) + "." + (Calendar.getInstance().get(Calendar.YEAR) > 6 ? 2 : 1);

        return shortName.contains(semester);
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static void actionBarColor(AppCompatActivity activity, String normal, String dark) {
        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(normal)));

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor(dark));
    }

    public static int getScreenOrientation(Activity activity) {
        Display getOrient = activity.getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if (getOrient.getWidth() == getOrient.getHeight()) {
            orientation = Configuration.ORIENTATION_SQUARE;
        } else {
            if (getOrient.getWidth() < getOrient.getHeight()) {
                orientation = Configuration.ORIENTATION_PORTRAIT;
            } else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }

        return orientation;
    }
}
