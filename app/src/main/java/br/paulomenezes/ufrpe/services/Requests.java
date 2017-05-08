package br.paulomenezes.ufrpe.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paulo on 02/10/2016.
 */

public class Requests {
    private static Requests requests;

    private Retrofit retrofitAVA;

    private static String AVA_URL = "http://ava.ufrpe.br";

    public static String LOGIN_SERVICE = "moodle_mobile_app";

    public static String FUNCTION_GET_USER = "core_user_get_users_by_id";
    public static String FUNCTION_GET_USER_COURSES = "core_enrol_get_users_courses";
    public static String FUNCTION_GET_SITE_INFO = "core_webservice_get_site_info";
    public static String FUNCTION_GET_COURSE_CONTENTS = "core_course_get_contents";
    public static String FUNCTION_GET_CALENDAR_EVENTS = "core_calendar_get_calendar_events";
    public static String FUNCTION_GET_MESSAGES = "core_message_get_messages";
    public static String FUNCTION_SEND_MESSAGE = "core_message_send_instant_messages";
    public static String FUNCTION_GET_PARTICIPANTS = "core_enrol_get_enrolled_users";
    public static String FUNCTION_GET_ASSIGNMENTS = "mod_assign_get_assignments";
    public static String FUNCTION_GET_DISCUSSIONS = "mod_forum_get_forum_discussions_paginated";
    public static String FUNCTION_GET_DISCUSSIONS_POSTS = "mod_forum_get_forum_discussion_posts";
    public static String FUNCTION_GET_GRADES = "gradereport_user_get_grades_table";

    private Requests() {
        retrofitAVA = new Retrofit.Builder()
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
        return retrofitAVA.create(AVAService.class);
    }
}
