
package br.ufrpe.conectada.models;

import org.parceler.Parcel;

@Parcel
public class Tabledatum {

    private Itemname itemname;
    private Leader leader;
    private Weight weight;
    private Grade grade;
    private Range range;
    private Percentage percentage;
    private Feedback feedback;
    private Contributiontocoursetotal contributiontocoursetotal;

    /**
     * 
     * @return
     *     The itemname
     */
    public Itemname getItemname() {
        return itemname;
    }

    /**
     * 
     * @param itemname
     *     The itemname
     */
    public void setItemname(Itemname itemname) {
        this.itemname = itemname;
    }

    /**
     * 
     * @return
     *     The leader
     */
    public Leader getLeader() {
        return leader;
    }

    /**
     * 
     * @param leader
     *     The leader
     */
    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    /**
     * 
     * @return
     *     The weight
     */
    public Weight getWeight() {
        return weight;
    }

    /**
     * 
     * @param weight
     *     The weight
     */
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    /**
     * 
     * @return
     *     The grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * 
     * @param grade
     *     The grade
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /**
     * 
     * @return
     *     The range
     */
    public Range getRange() {
        return range;
    }

    /**
     * 
     * @param range
     *     The range
     */
    public void setRange(Range range) {
        this.range = range;
    }

    /**
     * 
     * @return
     *     The percentage
     */
    public Percentage getPercentage() {
        return percentage;
    }

    /**
     * 
     * @param percentage
     *     The percentage
     */
    public void setPercentage(Percentage percentage) {
        this.percentage = percentage;
    }

    /**
     * 
     * @return
     *     The feedback
     */
    public Feedback getFeedback() {
        return feedback;
    }

    /**
     * 
     * @param feedback
     *     The feedback
     */
    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    /**
     * 
     * @return
     *     The contributiontocoursetotal
     */
    public Contributiontocoursetotal getContributiontocoursetotal() {
        return contributiontocoursetotal;
    }

    /**
     * 
     * @param contributiontocoursetotal
     *     The contributiontocoursetotal
     */
    public void setContributiontocoursetotal(Contributiontocoursetotal contributiontocoursetotal) {
        this.contributiontocoursetotal = contributiontocoursetotal;
    }

}
