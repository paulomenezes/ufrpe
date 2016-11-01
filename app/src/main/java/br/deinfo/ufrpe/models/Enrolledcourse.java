package br.deinfo.ufrpe.models;

/**
 * Created by paulo on 31/10/2016.
 */

public class Enrolledcourse {
    private int id;
    private String fullname;
    private String shortname;

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
     * The fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     *
     * @param fullname
     * The fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     *
     * @return
     * The shortname
     */
    public String getShortname() {
        return shortname;
    }

    /**
     *
     * @param shortname
     * The shortname
     */
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
}
