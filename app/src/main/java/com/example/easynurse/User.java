package com.example.easynurse;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public String name;
    public String email;
    public String password;
    public String dob;
    public String phone;
    public String gender;
    public String accountType;

    public User() {

    }

    public User(String name, String email, String password, String dob, String phone, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.phone = phone;
        this.gender = gender;
    }

    public User(String name, String email, String password, String dob, String phone, String gender, String accountType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.phone = phone;
        this.gender = gender;
        this.accountType = accountType;
    }

    public void setAccountType(String accountType){
        this.accountType = accountType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(dob);
        parcel.writeString(phone);
        parcel.writeString(gender);
        parcel.writeString(gender);
        parcel.writeString(accountType);
    }

    private User(Parcel in){
        this.name = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.dob = in.readString();
        this.phone = in.readString();
        this.gender = in.readString();
        this.accountType = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[0];
        }
    };
}


