package br.ufrpe.conectada.models;

import org.parceler.Parcel;

/**
 * Created by phgm on 17/10/2016.
 */
@Parcel
public class Assignments {
    private String cutoffdate;

    private String submissiondrafts;

    private String blindmarking;

    private String nosubmissions;

    private String markingworkflow;

    private String introformat;

    private String requiresubmissionstatement;

    private String course;

    private String id;

    private String revealidentities;

    private String name;

    private String duedate;

    private String attemptreopenmethod;

    private String grade;

    private String requireallteammemberssubmit;

    private String maxattempts;

    private String markingallocation;

    private String sendstudentnotifications;

    private String sendnotifications;

    private String sendlatenotifications;

    private String timemodified;

    private String intro;

    private Configs[] configs;

    private String teamsubmission;

    private String allowsubmissionsfromdate;

    private String cmid;

    private String completionsubmit;

    private String teamsubmissiongroupingid;

    public String getCutoffdate ()
    {
        return cutoffdate;
    }

    public void setCutoffdate (String cutoffdate)
    {
        this.cutoffdate = cutoffdate;
    }

    public String getSubmissiondrafts ()
    {
        return submissiondrafts;
    }

    public void setSubmissiondrafts (String submissiondrafts)
    {
        this.submissiondrafts = submissiondrafts;
    }

    public String getBlindmarking ()
    {
        return blindmarking;
    }

    public void setBlindmarking (String blindmarking)
    {
        this.blindmarking = blindmarking;
    }

    public String getNosubmissions ()
    {
        return nosubmissions;
    }

    public void setNosubmissions (String nosubmissions)
    {
        this.nosubmissions = nosubmissions;
    }

    public String getMarkingworkflow ()
    {
        return markingworkflow;
    }

    public void setMarkingworkflow (String markingworkflow)
    {
        this.markingworkflow = markingworkflow;
    }

    public String getIntroformat ()
    {
        return introformat;
    }

    public void setIntroformat (String introformat)
    {
        this.introformat = introformat;
    }

    public String getRequiresubmissionstatement ()
    {
        return requiresubmissionstatement;
    }

    public void setRequiresubmissionstatement (String requiresubmissionstatement)
    {
        this.requiresubmissionstatement = requiresubmissionstatement;
    }

    public String getCourse ()
    {
        return course;
    }

    public void setCourse (String course)
    {
        this.course = course;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getRevealidentities ()
    {
        return revealidentities;
    }

    public void setRevealidentities (String revealidentities)
    {
        this.revealidentities = revealidentities;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDuedate ()
    {
        return duedate;
    }

    public void setDuedate (String duedate)
    {
        this.duedate = duedate;
    }

    public String getAttemptreopenmethod ()
    {
        return attemptreopenmethod;
    }

    public void setAttemptreopenmethod (String attemptreopenmethod)
    {
        this.attemptreopenmethod = attemptreopenmethod;
    }

    public String getGrade ()
    {
        return grade;
    }

    public void setGrade (String grade)
    {
        this.grade = grade;
    }

    public String getRequireallteammemberssubmit ()
    {
        return requireallteammemberssubmit;
    }

    public void setRequireallteammemberssubmit (String requireallteammemberssubmit)
    {
        this.requireallteammemberssubmit = requireallteammemberssubmit;
    }

    public String getMaxattempts ()
    {
        return maxattempts;
    }

    public void setMaxattempts (String maxattempts)
    {
        this.maxattempts = maxattempts;
    }

    public String getMarkingallocation ()
    {
        return markingallocation;
    }

    public void setMarkingallocation (String markingallocation)
    {
        this.markingallocation = markingallocation;
    }

    public String getSendstudentnotifications ()
    {
        return sendstudentnotifications;
    }

    public void setSendstudentnotifications (String sendstudentnotifications)
    {
        this.sendstudentnotifications = sendstudentnotifications;
    }

    public String getSendnotifications ()
    {
        return sendnotifications;
    }

    public void setSendnotifications (String sendnotifications)
    {
        this.sendnotifications = sendnotifications;
    }

    public String getSendlatenotifications ()
    {
        return sendlatenotifications;
    }

    public void setSendlatenotifications (String sendlatenotifications)
    {
        this.sendlatenotifications = sendlatenotifications;
    }

    public String getTimemodified ()
    {
        return timemodified;
    }

    public void setTimemodified (String timemodified)
    {
        this.timemodified = timemodified;
    }

    public String getIntro ()
    {
        return intro;
    }

    public void setIntro (String intro)
    {
        this.intro = intro;
    }

    public Configs[] getConfigs ()
    {
        return configs;
    }

    public void setConfigs (Configs[] configs)
    {
        this.configs = configs;
    }

    public String getTeamsubmission ()
    {
        return teamsubmission;
    }

    public void setTeamsubmission (String teamsubmission)
    {
        this.teamsubmission = teamsubmission;
    }

    public String getAllowsubmissionsfromdate ()
    {
        return allowsubmissionsfromdate;
    }

    public void setAllowsubmissionsfromdate (String allowsubmissionsfromdate)
    {
        this.allowsubmissionsfromdate = allowsubmissionsfromdate;
    }

    public String getCmid ()
    {
        return cmid;
    }

    public void setCmid (String cmid)
    {
        this.cmid = cmid;
    }

    public String getCompletionsubmit ()
    {
        return completionsubmit;
    }

    public void setCompletionsubmit (String completionsubmit)
    {
        this.completionsubmit = completionsubmit;
    }

    public String getTeamsubmissiongroupingid ()
    {
        return teamsubmissiongroupingid;
    }

    public void setTeamsubmissiongroupingid (String teamsubmissiongroupingid)
    {
        this.teamsubmissiongroupingid = teamsubmissiongroupingid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cutoffdate = "+cutoffdate+", submissiondrafts = "+submissiondrafts+", blindmarking = "+blindmarking+", nosubmissions = "+nosubmissions+", markingworkflow = "+markingworkflow+", introformat = "+introformat+", requiresubmissionstatement = "+requiresubmissionstatement+", course = "+course+", id = "+id+", revealidentities = "+revealidentities+", name = "+name+", duedate = "+duedate+", attemptreopenmethod = "+attemptreopenmethod+", grade = "+grade+", requireallteammemberssubmit = "+requireallteammemberssubmit+", maxattempts = "+maxattempts+", markingallocation = "+markingallocation+", sendstudentnotifications = "+sendstudentnotifications+", sendnotifications = "+sendnotifications+", sendlatenotifications = "+sendlatenotifications+", timemodified = "+timemodified+", intro = "+intro+", configs = "+configs+", teamsubmission = "+teamsubmission+", allowsubmissionsfromdate = "+allowsubmissionsfromdate+", cmid = "+cmid+", completionsubmit = "+completionsubmit+", teamsubmissiongroupingid = "+teamsubmissiongroupingid+"]";
    }
}