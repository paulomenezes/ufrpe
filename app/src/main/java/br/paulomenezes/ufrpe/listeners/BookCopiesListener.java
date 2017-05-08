package br.paulomenezes.ufrpe.listeners;

import java.util.List;

import br.paulomenezes.ufrpe.models.BookCopies;

/**
 * Created by paulo on 29/01/2017.
 */

public interface BookCopiesListener {
    void copiesBook(List<BookCopies> book);
}
