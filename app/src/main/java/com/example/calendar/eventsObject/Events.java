package com.example.calendar.eventsObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Events implements Parcelable {
    private String EVENT_NAME, EVENT_DETAILS, TIME, DATE, MONTH, YEAR;

    public Events(String EVENT_NAME, String EVENT_DETAILS, String TIME, String DATE, String MONTH, String YEAR) {
        this.EVENT_NAME = EVENT_NAME;
        this.EVENT_DETAILS = EVENT_DETAILS;
        this.TIME = TIME;
        this.DATE = DATE;
        this.MONTH = MONTH;
        this.YEAR = YEAR;
    }

    protected Events(Parcel in) {
        EVENT_NAME = in.readString( );
        EVENT_DETAILS = in.readString( );
        TIME = in.readString( );
        DATE = in.readString( );
        MONTH = in.readString( );
        YEAR = in.readString( );
    }

    public static final Creator<Events> CREATOR = new Creator<Events>( ) {
        @Override
        public Events createFromParcel(Parcel in) {
            return new Events(in);
        }

        @Override
        public Events[] newArray(int size) {
            return new Events[size];
        }
    };

    public String getEVENT_NAME() {
        return EVENT_NAME;
    }

    public void setEVENT_NAME(String EVENT_NAME) {
        this.EVENT_NAME = EVENT_NAME;
    }

    public String getEVENT_DETAILS() {
        return EVENT_DETAILS;
    }

    public void setEVENT_DETAILS(String EVENT_DETAILS) {
        this.EVENT_DETAILS = EVENT_DETAILS;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(EVENT_NAME);
        dest.writeString(EVENT_DETAILS);
        dest.writeString(TIME);
        dest.writeString(DATE);
        dest.writeString(MONTH);
        dest.writeString(YEAR);
    }
}
