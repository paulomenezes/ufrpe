package br.ufrpe.conectada.utils;

import java.util.Comparator;

import br.ufrpe.conectada.models.Message;

/**
 * Created by paulo on 01/11/2016.
 */

public class CompareMessages implements Comparator<Message> {
    @Override
    public int compare(Message t1, Message t2) {
        return (int) (t2.getTimecreated() - t1.getTimecreated());
    }
}
