package br.deinfo.ufrpe.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import br.deinfo.ufrpe.MainActivity;
import br.deinfo.ufrpe.models.User;

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
