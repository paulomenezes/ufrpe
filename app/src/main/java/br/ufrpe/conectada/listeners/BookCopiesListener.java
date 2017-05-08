package br.ufrpe.conectada.listeners;

import java.util.List;

import br.ufrpe.conectada.models.BookCopies;

/**
 * Created by paulo on 29/01/2017.
 */

public interface BookCopiesListener {
    void copiesBook(List<BookCopies> book);
}
