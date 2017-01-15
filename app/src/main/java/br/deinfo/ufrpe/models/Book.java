package br.deinfo.ufrpe.models;

/**
 * Created by paulo on 15/01/2017.
 */

public class Book {
    private String title;
    private String image;
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book(String title, String image, String author) {
        this.title = title;
        this.image = image;
        this.author = author;
    }
}
