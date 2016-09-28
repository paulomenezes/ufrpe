package br.deinfo.ufrpe.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by paulo on 25/09/2016.
 */
public class User implements Parcelable {
    private String email;
    private String firstName;
    private String lastName;
    private String picture;
    private int unit;

    public User(GoogleSignInAccount googleSignInAccount) {
        setFirstName(googleSignInAccount.getGivenName());
        setLastName(googleSignInAccount.getFamilyName());
        setPicture(googleSignInAccount.getPhotoUrl() != null ? googleSignInAccount.getPhotoUrl().toString() : null);
        setEmail(googleSignInAccount.getEmail());
    }

    protected User(Parcel in) {
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        picture = in.readString();
        unit = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(picture);
        dest.writeInt(unit);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
