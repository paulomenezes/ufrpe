package br.paulomenezes.ufrpe.models;

/**
 * Created by phgm on 17/10/2016.
 */

public class Introattachments
{
    private String mimetype;

    private String fileurl;

    private String filename;

    public String getMimetype ()
    {
        return mimetype;
    }

    public void setMimetype (String mimetype)
    {
        this.mimetype = mimetype;
    }

    public String getFileurl ()
    {
        return fileurl;
    }

    public void setFileurl (String fileurl)
    {
        this.fileurl = fileurl;
    }

    public String getFilename ()
    {
        return filename;
    }

    public void setFilename (String filename)
    {
        this.filename = filename;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [mimetype = "+mimetype+", fileurl = "+fileurl+", filename = "+filename+"]";
    }
}