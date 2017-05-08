package br.ufrpe.conectada.models;

import org.parceler.Parcel;

/**
 * Created by phgm on 19/10/2016.
 */
@Parcel
public class Modules
{
    private String id;

    private String indent;

    private String modicon;

    private String visible;

    private Contents[] contents;

    private String description;

    private String name;

    private String modname;

    private int instance;

    private String modplural;

    private String url;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIndent ()
    {
        return indent;
    }

    public void setIndent (String indent)
    {
        this.indent = indent;
    }

    public String getModicon ()
    {
        return modicon;
    }

    public void setModicon (String modicon)
    {
        this.modicon = modicon;
    }

    public String getVisible ()
    {
        return visible;
    }

    public void setVisible (String visible)
    {
        this.visible = visible;
    }

    public Contents[] getContents ()
    {
        return contents;
    }

    public void setContents (Contents[] contents)
    {
        this.contents = contents;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getModname ()
    {
        return modname;
    }

    public void setModname (String modname)
    {
        this.modname = modname;
    }

    public int getInstance ()
    {
        return instance;
    }

    public void setInstance (int instance)
    {
        this.instance = instance;
    }

    public String getModplural ()
    {
        return modplural;
    }

    public void setModplural (String modplural)
    {
        this.modplural = modplural;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

}
