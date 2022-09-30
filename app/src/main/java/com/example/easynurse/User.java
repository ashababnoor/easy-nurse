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


//import android.os.Parcel;
//        import android.os.Parcelable;
//        import android.util.Log;
//
//public class Student implements Parcelable{
//
//    String mSName;
//    int mSAge;
//    String mSAddress;
//    String mSCourse;
//
//    @Override
//    public int describeContents() {
//        // TODO Auto-generated method stub
//        return 0;
//    }
//
//    /**
//     * Storing the Student data to Parcel object
//     **/
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(mSName);
//        dest.writeInt(mSAge);
//        dest.writeString(mSAddress);
//        dest.writeString(mSCourse);
//    }
//
//    /**
//     * A constructor that initializes the Student object
//     **/
//    public Student(String sName, int sAge, String sAddress, String sCourse){
//        this.mSName = sName;
//        this.mSAge = sAge;
//        this.mSAddress = sAddress;
//        this.mSCourse = sCourse;
//    }
//
//    /**
//     * Retrieving Student data from Parcel object
//     * This constructor is invoked by the method createFromParcel(Parcel source) of
//     * the object CREATOR
//     **/
//    private Student(Parcel in){
//        this.mSName = in.readString();
//        this.mSAge = in.readInt();
//        this.mSAddress = in.readString();
//        this.mSCourse = in.readString();
//    }
//
//    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
//
//        @Override
//        public Student createFromParcel(Parcel source) {
//            return new Student(source);
//        }
//
//        @Override
//        public Student[] newArray(int size) {
//            return new Student[size];
//        }
//    };
//}
