package br.ufrpe.conectada.utils;

import android.content.Context;

import br.ufrpe.conectada.models.User;

/**
 * Created by phgm on 19/10/2016.
 */

public class Session {
    private static User sUser;

    public static void setUser(User user) {
        sUser = user;
    }

    public static User getUser(Context activity) {
        if (sUser == null) {
            sUser = Data.getUser(activity);
        }
        return sUser;
    }
}
