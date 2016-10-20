package br.deinfo.ufrpe.utils;

import br.deinfo.ufrpe.models.User;

/**
 * Created by phgm on 19/10/2016.
 */

public class Session {
    private static User sUser;

    public static void setUser(User user) {
        sUser = user;
    }

    public static User getUser() {
        return sUser;
    }
}
