package br.ufrpe.conectada.models;

import org.parceler.Parcel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by paulo on 11/02/2017.
 */
@Parcel
@Root(name = "restaurante")
public class Restaurant {
    @Element(name = "segunda")
    private RestaurantDayOfWeek segunda;

    @Element(name = "terca")
    private RestaurantDayOfWeek terca;

    @Element(name = "quarta")
    private RestaurantDayOfWeek quarta;

    @Element(name = "quinta")
    private RestaurantDayOfWeek quinta;

    @Element(name = "sexta")
    private RestaurantDayOfWeek sexta;

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

    public RestaurantDayOfWeek[] getDaysOfWeek() {
        RestaurantDayOfWeek[] days = new RestaurantDayOfWeek[5];
        days[0] = getSegunda();
        days[1] = getTerca();
        days[2] = getQuarta();
        days[3] = getQuinta();
        days[4] = getSexta();

        return days;
    }
}
