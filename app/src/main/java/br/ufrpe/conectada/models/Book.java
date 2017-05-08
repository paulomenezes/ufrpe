package br.ufrpe.conectada.models;

/**
 * Created by paulo on 15/01/2017.
 */
@org.parceler.Parcel
public class Book {
    private int id;
    private String title;
    private String image;
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Book() {}

    public Book(int id, String title, String image, String author) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.author = author;
    }
}
