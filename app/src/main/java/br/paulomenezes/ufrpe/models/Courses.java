package br.paulomenezes.ufrpe.models;

import org.parceler.Parcel;

/**
 * Created by phgm on 17/10/2016.
 */
@Parcel
public class Courses
{
    private String id;

    private String timemodified;

    private String shortname;

    private Assignments[] assignments;

    private String fullname;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTimemodified ()
    {
        return timemodified;
    }

    public void setTimemodified (String timemodified)
    {
        this.timemodified = timemodified;
    }

    public String getShortname ()
    {
        return shortname;
    }

    public void setShortname (String shortname)
    {
        this.shortname = shortname;
    }

    public Assignments[] getAssignments ()
    {
        return assignments;
    }

    public void setAssignments (Assignments[] assignments)
    {
        this.assignments = assignments;
    }

    public String getFullname ()
    {
        return fullname;
    }

    public void setFullname (String fullname)
    {
        this.fullname = fullname;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", timemodified = "+timemodified+", shortname = "+shortname+", assignments = "+assignments+", fullname = "+fullname+"]";
    }
}