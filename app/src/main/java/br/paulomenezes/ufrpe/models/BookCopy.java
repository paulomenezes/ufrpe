package br.paulomenezes.ufrpe.models;

/**
 * Created by paulo on 29/01/2017.
 */

public class BookCopy {
    private String volume;
    private String typeOfLoan;
    private String location;
    private String dateOfLoan;
    private String dueReturnDate;
    private String copy;
    private String collection;

    public BookCopy(String volume, String typeOfLoan, String location, String dateOfLoan, String dueReturnDate, String copy, String collection) {
        this.volume = volume;
        this.typeOfLoan = typeOfLoan;
        this.location = location;
        this.dateOfLoan = dateOfLoan;
        this.dueReturnDate = dueReturnDate;
        this.copy = copy;
        this.collection = collection;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTypeOfLoan() {
        return typeOfLoan;
    }

    public void setTypeOfLoan(String typeOfLoan) {
        this.typeOfLoan = typeOfLoan;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateOfLoan() {
        return dateOfLoan;
    }

    public void setDateOfLoan(String dateOfLoan) {
        this.dateOfLoan = dateOfLoan;
    }

    public String getDueReturnDate() {
        return dueReturnDate;
    }

    public void setDueReturnDate(String dueReturnDate) {
        this.dueReturnDate = dueReturnDate;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
