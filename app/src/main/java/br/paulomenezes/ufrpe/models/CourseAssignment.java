package br.paulomenezes.ufrpe.models;

import org.parceler.Parcel;

/**
 * Created by phgm on 17/10/2016.
 */
@Parcel
public class CourseAssignment
{
    private Courses[] courses;

    private Warning[] warnings;

    public Courses[] getCourses ()
    {
        return courses;
    }

    public void setCourses (Courses[] courses)
    {
        this.courses = courses;
    }

    public Warning[] getWarnings ()
    {
        return warnings;
    }

    public void setWarnings (Warning[] warnings)
    {
        this.warnings = warnings;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [courses = "+courses+", warnings = "+warnings+"]";
    }
}