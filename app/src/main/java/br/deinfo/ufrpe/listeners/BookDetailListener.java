package br.deinfo.ufrpe.listeners;

import java.util.List;
import java.util.Map;

import br.deinfo.ufrpe.models.Book;

/**
 * Created by phgm on 17/01/2017.
 */

public interface BookDetailListener {
    void detailBook(Map<String, String> book);
}