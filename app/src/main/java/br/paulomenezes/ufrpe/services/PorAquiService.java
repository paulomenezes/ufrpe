package br.paulomenezes.ufrpe.services;

import br.paulomenezes.ufrpe.models.NewsDTO;
import br.paulomenezes.ufrpe.models.SiteInfo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by paulomenezes on 25/02/18.
 */

public interface PorAquiService {
    @GET("api/v2/posts/search")
    Call<NewsDTO> getSiteInfo(
            @Query("stationIds") int station,
            @Query("page") int page);
}
