package br.ufrpe.conectada.models;

import org.parceler.Parcel;

/**
 * Created by paulo on 30/10/2016.
 */
@Parcel
public class Event {
    private int id;
    private String name;
    private String description;
    private int format;
    private int courseid;
    private int groupid;
    private int userid;
    private int repeatid;
    private String modulename;
    private int instance;
    private String eventtype;
    private int timestart;
    private int timeduration;
    private int visible;
    private String uuid;
    private int sequence;
    private int timemodified;
    private String subscriptionid;
   // private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The format
     */
    public int getFormat() {
        return format;
    }

    /**
     *
     * @param format
     * The format
     */
    public void setFormat(int format) {
        this.format = format;
    }

    /**
     *
     * @return
     * The courseid
     */
    public int getCourseid() {
        return courseid;
    }

    /**
     *
     * @param courseid
     * The courseid
     */
    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    /**
     *
     * @return
     * The groupid
     */
    public int getGroupid() {
        return groupid;
    }

    /**
     *
     * @param groupid
     * The groupid
     */
    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    /**
     *
     * @return
     * The userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     *
     * @param userid
     * The userid
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }

    /**
     *
     * @return
     * The repeatid
     */
    public int getRepeatid() {
        return repeatid;
    }

    /**
     *
     * @param repeatid
     * The repeatid
     */
    public void setRepeatid(int repeatid) {
        this.repeatid = repeatid;
    }

    /**
     *
     * @return
     * The modulename
     */
    public String getModulename() {
        return modulename;
    }

    /**
     *
     * @param modulename
     * The modulename
     */
    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    /**
     *
     * @return
     * The instance
     */
    public int getInstance() {
        return instance;
    }

    /**
     *
     * @param instance
     * The instance
     */
    public void setInstance(int instance) {
        this.instance = instance;
    }

    /**
     *
     * @return
     * The eventtype
     */
    public String getEventtype() {
        return eventtype;
    }

    /**
     *
     * @param eventtype
     * The eventtype
     */
    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    /**
     *
     * @return
     * The timestart
     */
    public int getTimestart() {
        return timestart;
    }

    /**
     *
     * @param timestart
     * The timestart
     */
    public void setTimestart(int timestart) {
        this.timestart = timestart;
    }

    /**
     *
     * @return
     * The timeduration
     */
    public int getTimeduration() {
        return timeduration;
    }

    /**
     *
     * @param timeduration
     * The timeduration
     */
    public void setTimeduration(int timeduration) {
        this.timeduration = timeduration;
    }

    /**
     *
     * @return
     * The visible
     */
    public int getVisible() {
        return visible;
    }

    /**
     *
     * @param visible
     * The visible
     */
    public void setVisible(int visible) {
        this.visible = visible;
    }

    /**
     *
     * @return
     * The uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid
     * The uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     * The sequence
     */
    public int getSequence() {
        return sequence;
    }

    /**
     *
     * @param sequence
     * The sequence
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    /**
     *
     * @return
     * The timemodified
     */
    public int getTimemodified() {
        return timemodified;
    }

    /**
     *
     * @param timemodified
     * The timemodified
     */
    public void setTimemodified(int timemodified) {
        this.timemodified = timemodified;
    }

    /**
     *
     * @return
     * The subscriptionid
     */
    public String getSubscriptionid() {
        return subscriptionid;
    }

    /**
     *
     * @param subscriptionid
     * The subscriptionid
     */
    public void setSubscriptionid(String subscriptionid) {
        this.subscriptionid = subscriptionid;
    }

//    public Map<String, Object> getAdditionalProperties() {
//        return this.additionalProperties;
//    }
//
//    public void setAdditionalProperty(String name, Object value) {
//        this.additionalProperties.put(name, value);
//    }
}
