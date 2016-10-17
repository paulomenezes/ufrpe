package br.deinfo.ufrpe.utils;

import java.util.Calendar;

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

    public static boolean thisSemester(String shortName) {
        String semester = Calendar.getInstance().get(Calendar.YEAR) + "." + (Calendar.getInstance().get(Calendar.YEAR) > 6 ? 2 : 1);

        return shortName.contains(semester);
    }
}
