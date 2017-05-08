package br.ufrpe.conectada.models;

import org.parceler.Parcel;

/**
 * Created by phgm on 19/10/2016.
 */
@Parcel
public class CourseContent {
    private String summary;

    private String id;

    private String summaryformat;

    private String visible;

    private String name;

    private Modules[] modules;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummaryformat() {
        return summaryformat;
    }

    public void setSummaryformat(String summaryformat) {
        this.summaryformat = summaryformat;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Modules[] getModules() {
        return modules;
    }

    public void setModules(Modules[] modules) {
        this.modules = modules;
    }
}
