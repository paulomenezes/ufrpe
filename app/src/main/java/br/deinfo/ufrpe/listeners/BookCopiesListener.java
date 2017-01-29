package br.deinfo.ufrpe.listeners;

import java.util.List;

import br.deinfo.ufrpe.models.BookCopies;
import br.deinfo.ufrpe.models.BookCopy;

/**
 * Created by paulo on 29/01/2017.
 */

public interface BookCopiesListener {
    void copiesBook(List<BookCopies> book);
}
