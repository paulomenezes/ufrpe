package br.deinfo.ufrpe.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paulo on 02/10/2016.
 */

public class Requests {
    private static Requests requests;

    private Retrofit retrofit;

    private static String AVA_URL = "http://ava.ufrpe.br";

    public static String LOGIN_SERVICE = "moodle_mobile_app";

    public static String FUNCTION_GET_USER_COURSES = "core_enrol_get_users_courses";
    public static String FUNCTION_GET_SITE_INFO = "core_webservice_get_site_info";
    public static String FUNCTION_GET_ASSIGNMENTS = "mod_assign_get_assignments";
    public static String FUNCTION_GET_COURSE_CONTENTS = "core_course_get_contents";

    private Requests() {
        retrofit = new Retrofit.Builder()
                .baseUrl(AVA_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Requests getInstance() {
        if (requests == null) {
            requests = new Requests();
        }

        return requests;
    }

    public AVAService getAVAService() {
        return retrofit.create(AVAService.class);
    }


}
