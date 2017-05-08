package br.paulomenezes.ufrpe.models;

/**
 * Created by paulo on 30/10/2016.
 */

public class Posts {

    private String discussion;

    private String messageformat;

    private String subject;

    private String postread;

    private String userid;

    private String messagetrust;

    private String[] children;

    private String parent;

    private String userpictureurl;

    private String attachment;

    private String mailnow;

    private String modified;

    private String id;

    private String message;

    private int created;

    private String canreply;

    private String userfullname;

    private String totalscore;

    private String mailed;

    public String getDiscussion ()
    {
        return discussion;
    }

    public void setDiscussion (String discussion)
    {
        this.discussion = discussion;
    }

    public String getMessageformat ()
    {
        return messageformat;
    }

    public void setMessageformat (String messageformat)
    {
        this.messageformat = messageformat;
    }

    public String getSubject ()
    {
        return subject;
    }

    public void setSubject (String subject)
    {
        this.subject = subject;
    }

    public String getPostread ()
    {
        return postread;
    }

    public void setPostread (String postread)
    {
        this.postread = postread;
    }

    public String getUserid ()
    {
        return userid;
    }

    public void setUserid (String userid)
    {
        this.userid = userid;
    }

    public String getMessagetrust ()
    {
        return messagetrust;
    }

    public void setMessagetrust (String messagetrust)
    {
        this.messagetrust = messagetrust;
    }

    public String[] getChildren ()
    {
        return children;
    }

    public void setChildren (String[] children)
    {
        this.children = children;
    }

    public String getParent ()
    {
        return parent;
    }

    public void setParent (String parent)
    {
        this.parent = parent;
    }

    public String getUserpictureurl ()
    {
        return userpictureurl;
    }

    public void setUserpictureurl (String userpictureurl)
    {
        this.userpictureurl = userpictureurl;
    }

    public String getAttachment ()
    {
        return attachment;
    }

    public void setAttachment (String attachment)
    {
        this.attachment = attachment;
    }

    public String getMailnow ()
    {
        return mailnow;
    }

    public void setMailnow (String mailnow)
    {
        this.mailnow = mailnow;
    }

    public String getModified ()
    {
        return modified;
    }

    public void setModified (String modified)
    {
        this.modified = modified;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public int getCreated ()
    {
        return created;
    }

    public void setCreated (int created)
    {
        this.created = created;
    }

    public String getCanreply ()
    {
        return canreply;
    }

    public void setCanreply (String canreply)
    {
        this.canreply = canreply;
    }

    public String getUserfullname ()
    {
        return userfullname;
    }

    public void setUserfullname (String userfullname)
    {
        this.userfullname = userfullname;
    }

    public String getTotalscore ()
    {
        return totalscore;
    }

    public void setTotalscore (String totalscore)
    {
        this.totalscore = totalscore;
    }

    public String getMailed ()
    {
        return mailed;
    }

    public void setMailed (String mailed)
    {
        this.mailed = mailed;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [discussion = "+discussion+", messageformat = "+messageformat+", subject = "+subject+", postread = "+postread+", userid = "+userid+", messagetrust = "+messagetrust+", children = "+children+", parent = "+parent+", userpictureurl = "+userpictureurl+", attachment = "+attachment+", mailnow = "+mailnow+", modified = "+modified+", id = "+id+", message = "+message+", created = "+created+", canreply = "+canreply+", userfullname = "+userfullname+", totalscore = "+totalscore+", mailed = "+mailed+"]";
    }
}
