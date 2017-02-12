package br.deinfo.ufrpe.services;

import br.deinfo.ufrpe.models.Restaurant;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

/**
 * Created by paulo on 11/02/2017.
 */

public interface RestaurantService {
    @GET("restaurante.xml")
    Call<Restaurant> getRestaurant();
}
