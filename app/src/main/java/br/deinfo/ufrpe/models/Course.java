package br.deinfo.ufrpe.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.firebase.database.Exclude;

import br.deinfo.ufrpe.BR;

/**
 * Created by phgm on 10/10/2016.
 */
@org.parceler.Parcel
public class Course extends BaseObservable {

    private int id;
    private String shortname;
    private String fullname;
    private int enrolledusercount;
    private String idnumber;
    private int visible;
    private String summary;
    private int summaryformat;
    private String format;
    private boolean showgrades;
    private String lang;
    private boolean enablecompletion;

    private Classes classes;

    public Course() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    @Bindable
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
        notifyPropertyChanged(BR.fullname);
    }

    public int getEnrolledusercount() {
        return enrolledusercount;
    }

    public void setEnrolledusercount(int enrolledusercount) {
        this.enrolledusercount = enrolledusercount;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getSummaryformat() {
        return summaryformat;
    }

    public void setSummaryformat(int summaryformat) {
        this.summaryformat = summaryformat;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isShowgrades() {
        return showgrades;
    }

    public void setShowgrades(boolean showgrades) {
        this.showgrades = showgrades;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isEnablecompletion() {
        return enablecompletion;
    }

    public void setEnablecompletion(boolean enablecompletion) {
        this.enablecompletion = enablecompletion;
    }

    @Exclude
    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }
}