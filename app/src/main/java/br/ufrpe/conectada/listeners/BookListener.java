package br.ufrpe.conectada.listeners;

import java.util.List;

import br.ufrpe.conectada.models.Book;

/**
 * Created by paulo on 16/01/2017.
 */

public interface BookListener {
    void downloadBooks(List<Book> books);
}
