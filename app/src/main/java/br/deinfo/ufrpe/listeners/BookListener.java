package br.deinfo.ufrpe.listeners;

import java.util.List;

import br.deinfo.ufrpe.models.Book;

/**
 * Created by paulo on 16/01/2017.
 */

public interface BookListener {
    void downloadBooks(List<Book> books);
}
