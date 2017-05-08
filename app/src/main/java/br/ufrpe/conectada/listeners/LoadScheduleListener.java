package br.ufrpe.conectada.listeners;

import java.util.List;

import br.ufrpe.conectada.models.Classes;

/**
 * Created by paulo on 29/01/2017.
 */

public interface LoadScheduleListener {
    void schedule(List<Classes> classes);
}
