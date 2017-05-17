package br.paulomenezes.ufrpe.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulo on 31/10/2016.
 */
@Parcel
public class AVAUser {
    private int id;
    private String fullname;
    private int firstaccess;
    private int lastaccess;
    private String description;
    private int descriptionformat;
    private String city;
    private String url;
    private String country;
    private String profileimageurlsmall;
    private String profileimageurl;
    private List<Enrolledcourse> enrolledcourses = new ArrayList<Enrolledcourse>();
    private String email;

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
     * The firstaccess
     */
    public int getFirstaccess() {
        return firstaccess;
    }

    /**
     *
     * @param firstaccess
     * The firstaccess
     */
    public void setFirstaccess(int firstaccess) {
        this.firstaccess = firstaccess;
    }

    /**
     *
     * @return
     * The lastaccess
     */
    public int getLastaccess() {
        return lastaccess;
    }

    /**
     *
     * @param lastaccess
     * The lastaccess
     */
    public void setLastaccess(int lastaccess) {
        this.lastaccess = lastaccess;
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
     * The descriptionformat
     */
    public int getDescriptionformat() {
        return descriptionformat;
    }

    /**
     *
     * @param descriptionformat
     * The descriptionformat
     */
    public void setDescriptionformat(int descriptionformat) {
        this.descriptionformat = descriptionformat;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The profileimageurlsmall
     */
    public String getProfileimageurlsmall() {
        return profileimageurlsmall;
    }

    /**
     *
     * @param profileimageurlsmall
     * The profileimageurlsmall
     */
    public void setProfileimageurlsmall(String profileimageurlsmall) {
        this.profileimageurlsmall = profileimageurlsmall;
    }

    /**
     *
     * @return
     * The profileimageurl
     */
    public String getProfileimageurl() {
        return profileimageurl;
    }

    /**
     *
     * @param profileimageurl
     * The profileimageurl
     */
    public void setProfileimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }

    /**
     *
     * @return
     * The enrolledcourses
     */
    public List<Enrolledcourse> getEnrolledcourses() {
        return enrolledcourses;
    }

    /**
     *
     * @param enrolledcourses
     * The enrolledcourses
     */
    public void setEnrolledcourses(List<Enrolledcourse> enrolledcourses) {
        this.enrolledcourses = enrolledcourses;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
