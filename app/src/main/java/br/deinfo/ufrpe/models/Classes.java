package br.deinfo.ufrpe.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phgm on 10/10/2016.
 */
@Parcel
public class Classes {
    private String cod;
    private String name;
    private String _class;
    private List<Schedule> schedules = new ArrayList<Schedule>();
    private String period;
    private String type;
    private String departament;
    private String place;
    private String vacancies;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getVacancies() {
        return vacancies;
    }

    public void setVacancies(String vacancies) {
        this.vacancies = vacancies;
    }
}
