package br.ufrpe.conectada.models;

import org.parceler.Parcel;

/**
 * Created by paulo on 11/02/2017.
 */
@Parcel
public class Restaurante {
    private RestaurantDayOfWeek quarta;
    private RestaurantDayOfWeek terca;
    private RestaurantDayOfWeek sexta;
    private RestaurantDayOfWeek quinta;
    private RestaurantDayOfWeek segunda;

    public RestaurantDayOfWeek getQuarta ()
    {
        return quarta;
    }

    public void setQuarta (RestaurantDayOfWeek quarta)
    {
        this.quarta = quarta;
    }

    public RestaurantDayOfWeek getTerca ()
    {
        return terca;
    }

    public void setTerca (RestaurantDayOfWeek terca)
    {
        this.terca = terca;
    }

    public RestaurantDayOfWeek getSexta ()
    {
        return sexta;
    }

    public void setSexta (RestaurantDayOfWeek sexta)
    {
        this.sexta = sexta;
    }

    public RestaurantDayOfWeek getQuinta ()
    {
        return quinta;
    }

    public void setQuinta (RestaurantDayOfWeek quinta)
    {
        this.quinta = quinta;
    }

    public RestaurantDayOfWeek getSegunda () {
        return segunda;
    }

    public void setSegunda (RestaurantDayOfWeek segunda) {
        this.segunda = segunda;
    }
}
