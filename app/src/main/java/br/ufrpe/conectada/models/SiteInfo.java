package br.ufrpe.conectada.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phgm on 10/10/2016.
 */

public class SiteInfo {

    private String sitename;
    private String username;
    private String firstname;
    private String lastname;
    private String fullname;
    private String lang;
    private int userid;
    private String siteurl;
    private String userpictureurl;
    private List<Function> functions = new ArrayList<Function>();
    private int downloadfiles;
    private int uploadfiles;
    private String release;
    private String version;
    private String mobilecssurl;
    private List<Advancedfeature> advancedfeatures = new ArrayList<Advancedfeature>();
    private boolean usercanmanageownfiles;
    private int userquota;
    private int usermaxuploadfilesize;

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getSiteurl() {
        return siteurl;
    }

    public void setSiteurl(String siteurl) {
        this.siteurl = siteurl;
    }

    public String getUserpictureurl() {
        return userpictureurl;
    }

    public void setUserpictureurl(String userpictureurl) {
        this.userpictureurl = userpictureurl;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public int getDownloadfiles() {
        return downloadfiles;
    }

    public void setDownloadfiles(int downloadfiles) {
        this.downloadfiles = downloadfiles;
    }

    public int getUploadfiles() {
        return uploadfiles;
    }

    public void setUploadfiles(int uploadfiles) {
        this.uploadfiles = uploadfiles;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMobilecssurl() {
        return mobilecssurl;
    }

    public void setMobilecssurl(String mobilecssurl) {
        this.mobilecssurl = mobilecssurl;
    }

    public List<Advancedfeature> getAdvancedfeatures() {
        return advancedfeatures;
    }

    public void setAdvancedfeatures(List<Advancedfeature> advancedfeatures) {
        this.advancedfeatures = advancedfeatures;
    }

    public boolean isUsercanmanageownfiles() {
        return usercanmanageownfiles;
    }

    public void setUsercanmanageownfiles(boolean usercanmanageownfiles) {
        this.usercanmanageownfiles = usercanmanageownfiles;
    }

    public int getUserquota() {
        return userquota;
    }

    public void setUserquota(int userquota) {
        this.userquota = userquota;
    }

    public int getUsermaxuploadfilesize() {
        return usermaxuploadfilesize;
    }

    public void setUsermaxuploadfilesize(int usermaxuploadfilesize) {
        this.usermaxuploadfilesize = usermaxuploadfilesize;
    }
}