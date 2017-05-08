package br.paulomenezes.ufrpe.models;

import org.parceler.Parcel;
import org.simpleframework.xml.Element;

/**
 * Created by paulo on 11/02/2017.
 */
@Parcel
public class RestaurantDayOfWeek {
    @Element(name = "data")
    private String data;

    @Element(name = "almoco")
    private RestaurantMeal almoco;

    @Element(name = "jantar")
    private RestaurantMeal jantar;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public RestaurantMeal getAlmoco() {
        return almoco;
    }

    public void setAlmoco(RestaurantMeal almoco) {
        this.almoco = almoco;
    }

    public RestaurantMeal getJantar() {
        return jantar;
    }

    public void setJantar(RestaurantMeal jantar) {
        this.jantar = jantar;
    }
}
