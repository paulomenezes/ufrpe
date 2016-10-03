package br.deinfo.ufrpe.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.util.UUID;

import br.deinfo.ufrpe.R;

/**
 * Created by paulo on 25/09/2016.
 */
public class User extends BaseObservable implements Parcelable {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String picture;
    private String token;
    private int unit;

    public User(GoogleSignInAccount googleSignInAccount) {
        setId(googleSignInAccount.getId());
        setFirstName(googleSignInAccount.getGivenName());
        setLastName(googleSignInAccount.getFamilyName());
        setPicture(googleSignInAccount.getPhotoUrl() != null ? googleSignInAccount.getPhotoUrl().toString() : null);
        setEmail(googleSignInAccount.getEmail());
    }

    public User(FirebaseUser firebaseUser) {
        if (firebaseUser.getDisplayName() != null) {
            String[] name = firebaseUser.getDisplayName().split(" ");

            if (name.length == 1) {
                setFirstName(firebaseUser.getDisplayName());
            } else {
                setFirstName(name[0]);
                setLastName(name[name.length - 1]);
            }

            setId(firebaseUser.getUid());
            setPicture(firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : null);
        } else {
            setId(UUID.randomUUID().toString());
            setPicture(String.valueOf(R.drawable.ic_person_dark_48dp));
        }

        setEmail(firebaseUser.getEmail());
    }

    protected User(Parcel in) {
        id = in.readString();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        userName = in.readString();
        password = in.readString();
        picture = in.readString();
        token = in.readString();
        unit = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(picture);
        dest.writeString(token);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
        notifyPropertyChanged(BR.picture);
    }

    @Bindable
    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
        notifyPropertyChanged(BR.unit);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.unit);
    }

    @Exclude
    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
