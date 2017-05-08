package br.ufrpe.conectada.models;

import org.parceler.Parcel;

/**
 * Created by phgm on 17/10/2016.
 */
@Parcel
public class Warning {
    private String item;
    private int itemid;
    private String warningcode;
    private String message;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getWarningcode() {
        return warningcode;
    }

    public void setWarningcode(String warningcode) {
        this.warningcode = warningcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
