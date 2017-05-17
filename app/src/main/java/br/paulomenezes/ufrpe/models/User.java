package br.paulomenezes.ufrpe.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.util.List;
import java.util.UUID;

import br.paulomenezes.ufrpe.BR;
import br.paulomenezes.ufrpe.R;

/**
 * Created by paulo on 25/09/2016.
 */
@org.parceler.Parcel
public class User extends BaseObservable {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String picture;
    private String token;
    private int avaID;
    private int unit;
    private String currentSemester;

    private List<Course> courses;

    public User() {}

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

    public int getAvaID() {
        return avaID;
    }

    public void setAvaID(int avaID) {
        this.avaID = avaID;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }
}
