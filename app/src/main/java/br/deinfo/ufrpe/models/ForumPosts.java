package br.deinfo.ufrpe.models;

/**
 * Created by paulo on 30/10/2016.
 */

public class ForumPosts
{
    private Posts[] posts;

    private String[] warnings;

    public Posts[] getPosts ()
    {
        return posts;
    }

    public void setPosts (Posts[] posts)
    {
        this.posts = posts;
    }

    public String[] getWarnings ()
    {
        return warnings;
    }

    public void setWarnings (String[] warnings)
    {
        this.warnings = warnings;
    }
}