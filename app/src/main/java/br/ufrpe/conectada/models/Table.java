
package br.ufrpe.conectada.models;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private int courseid;
    private int userid;
    private String userfullname;
    private int maxdepth;
    private List<Tabledatum> tabledata = new ArrayList<Tabledatum>();

    /**
     * 
     * @return
     *     The courseid
     */
    public int getCourseid() {
        return courseid;
    }

    /**
     * 
     * @param courseid
     *     The courseid
     */
    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    /**
     * 
     * @return
     *     The userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     * 
     * @param userid
     *     The userid
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }

    /**
     * 
     * @return
     *     The userfullname
     */
    public String getUserfullname() {
        return userfullname;
    }

    /**
     * 
     * @param userfullname
     *     The userfullname
     */
    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    /**
     * 
     * @return
     *     The maxdepth
     */
    public int getMaxdepth() {
        return maxdepth;
    }

    /**
     * 
     * @param maxdepth
     *     The maxdepth
     */
    public void setMaxdepth(int maxdepth) {
        this.maxdepth = maxdepth;
    }

    /**
     * 
     * @return
     *     The tabledata
     */
    public List<Tabledatum> getTabledata() {
        return tabledata;
    }

    /**
     * 
     * @param tabledata
     *     The tabledata
     */
    public void setTabledata(List<Tabledatum> tabledata) {
        this.tabledata = tabledata;
    }

}
