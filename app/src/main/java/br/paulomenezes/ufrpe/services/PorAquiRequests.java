package br.paulomenezes.ufrpe.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paulomenezes on 25/02/18.
 */

public class PorAquiRequests {
    private static PorAquiRequests requests;

    private Retrofit retrofitAVA;

    private PorAquiRequests() {
        String PORAQUI_URL = "https://poraqui.news/";

        retrofitAVA = new Retrofit.Builder()
                .baseUrl(PORAQUI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static PorAquiRequests getInstance() {
        if (requests == null) {
            requests = new PorAquiRequests();
        }

        return requests;
    }

    public PorAquiService getService() {
        return retrofitAVA.create(PorAquiService.class);
    }
}
