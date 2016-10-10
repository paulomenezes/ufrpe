package br.deinfo.ufrpe.utils;

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
}
