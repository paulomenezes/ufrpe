package br.ufrpe.conectada.models;

import java.util.List;

/**
 * Created by paulo on 29/01/2017.
 */

public class BookCopies {
    private String library;
    private int available;
    private int loaned;
    private List<BookCopy> copies;

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getLoaned() {
        return loaned;
    }

    public void setLoaned(int loaned) {
        this.loaned = loaned;
    }

    public List<BookCopy> getCopies() {
        return copies;
    }

    public void setCopies(List<BookCopy> copies) {
        this.copies = copies;
    }
}
