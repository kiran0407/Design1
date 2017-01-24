package com.example.harikrishna.design1;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ListView;


public class ListItem implements Parcelable {

    private int id;
    private String name;
    private String number;

    protected ListItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        number = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(number);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };
}