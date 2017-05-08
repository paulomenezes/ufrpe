package br.paulomenezes.ufrpe.listeners;

import java.util.List;

import br.paulomenezes.ufrpe.models.Book;

/**
 * Created by paulo on 16/01/2017.
 */

public interface BookListener {
    void downloadBooks(List<Book> books);
}
