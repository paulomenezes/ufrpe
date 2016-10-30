package br.deinfo.ufrpe.utils;

import java.util.Comparator;

import br.deinfo.ufrpe.models.Posts;

/**
 * Created by paulo on 30/10/2016.
 */

public class ComparePosts implements Comparator<Posts> {
    @Override
    public int compare(Posts t1, Posts t2) {
        return t1.getId().compareTo(t2.getId());
    }
}
