package br.deinfo.ufrpe.models;

import org.parceler.Parcel;

/**
 * Created by paulo on 30/10/2016.
 */
@Parcel
public class Message {

    private int id;
    private int useridfrom;
    private int useridto;
    private String subject;
    private String text;
    private String fullmessage;
    private int fullmessageformat;
    private String fullmessagehtml;
    private String smallmessage;
    private int notification;
    private String contexturl;
    private String contexturlname;
    private long timecreated;
    private int timeread;
    private String usertofullname;
    private String userfromfullname;

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The useridfrom
     */
    public int getUseridfrom() {
        return useridfrom;
    }

    /**
     * @param useridfrom The useridfrom
     */
    public void setUseridfrom(int useridfrom) {
        this.useridfrom = useridfrom;
    }

    /**
     * @return The useridto
     */
    public int getUseridto() {
        return useridto;
    }

    /**
     * @param useridto The useridto
     */
    public void setUseridto(int useridto) {
        this.useridto = useridto;
    }

    /**
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The fullmessage
     */
    public String getFullmessage() {
        return fullmessage;
    }

    /**
     * @param fullmessage The fullmessage
     */
    public void setFullmessage(String fullmessage) {
        this.fullmessage = fullmessage;
    }

    /**
     * @return The fullmessageformat
     */
    public int getFullmessageformat() {
        return fullmessageformat;
    }

    /**
     * @param fullmessageformat The fullmessageformat
     */
    public void setFullmessageformat(int fullmessageformat) {
        this.fullmessageformat = fullmessageformat;
    }

    /**
     * @return The fullmessagehtml
     */
    public String getFullmessagehtml() {
        return fullmessagehtml;
    }

    /**
     * @param fullmessagehtml The fullmessagehtml
     */
    public void setFullmessagehtml(String fullmessagehtml) {
        this.fullmessagehtml = fullmessagehtml;
    }

    /**
     * @return The smallmessage
     */
    public String getSmallmessage() {
        return smallmessage;
    }

    /**
     * @param smallmessage The smallmessage
     */
    public void setSmallmessage(String smallmessage) {
        this.smallmessage = smallmessage;
    }

    /**
     * @return The notification
     */
    public int getNotification() {
        return notification;
    }

    /**
     * @param notification The notification
     */
    public void setNotification(int notification) {
        this.notification = notification;
    }

    /**
     * @return The contexturl
     */
    public String getContexturl() {
        return contexturl;
    }

    /**
     * @param contexturl The contexturl
     */
    public void setContexturl(String contexturl) {
        this.contexturl = contexturl;
    }

    /**
     * @return The contexturlname
     */
    public String getContexturlname() {
        return contexturlname;
    }

    /**
     * @param contexturlname The contexturlname
     */
    public void setContexturlname(String contexturlname) {
        this.contexturlname = contexturlname;
    }

    /**
     * @return The timecreated
     */
    public long getTimecreated() {
        return timecreated;
    }

    /**
     * @param timecreated The timecreated
     */
    public void setTimecreated(long timecreated) {
        this.timecreated = timecreated;
    }

    /**
     * @return The timeread
     */
    public int getTimeread() {
        return timeread;
    }

    /**
     * @param timeread The timeread
     */
    public void setTimeread(int timeread) {
        this.timeread = timeread;
    }

    /**
     * @return The usertofullname
     */
    public String getUsertofullname() {
        return usertofullname;
    }

    /**
     * @param usertofullname The usertofullname
     */
    public void setUsertofullname(String usertofullname) {
        this.usertofullname = usertofullname;
    }

    /**
     * @return The userfromfullname
     */
    public String getUserfromfullname() {
        return userfromfullname;
    }

    /**
     * @param userfromfullname The userfromfullname
     */
    public void setUserfromfullname(String userfromfullname) {
        this.userfromfullname = userfromfullname;
    }
}