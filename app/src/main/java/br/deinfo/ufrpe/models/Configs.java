package br.deinfo.ufrpe.models;

import org.parceler.Parcel;

/**
 * Created by phgm on 17/10/2016.
 */
@Parcel
public class Configs
{
    private String id;

    private String assignment;

    private String name;

    private String subtype;

    private String value;

    private String plugin;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAssignment ()
    {
        return assignment;
    }

    public void setAssignment (String assignment)
    {
        this.assignment = assignment;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSubtype ()
    {
        return subtype;
    }

    public void setSubtype (String subtype)
    {
        this.subtype = subtype;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public String getPlugin ()
    {
        return plugin;
    }

    public void setPlugin (String plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", assignment = "+assignment+", name = "+name+", subtype = "+subtype+", value = "+value+", plugin = "+plugin+"]";
    }
}