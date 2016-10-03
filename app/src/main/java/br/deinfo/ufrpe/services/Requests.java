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
