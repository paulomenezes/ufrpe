package br.paulomenezes.ufrpe.models;

import org.parceler.Parcel;

/**
 * Created by phgm on 26/10/2016.
 */
@Parcel
public class Forum
{
    private String[] warnings;

    private Discussions[] discussions;

    public String[] getWarnings ()
    {
        return warnings;
    }

    public void setWarnings (String[] warnings)
    {
        this.warnings = warnings;
    }

    public Discussions[] getDiscussions ()
    {
        return discussions;
    }

    public void setDiscussions (Discussions[] discussions)
    {
        this.discussions = discussions;
    }

}
