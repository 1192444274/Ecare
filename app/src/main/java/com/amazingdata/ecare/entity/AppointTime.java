package com.amazingdata.ecare.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xiong
 * @date 2019/1/23 - 10:37
 */
// 预约时间
public class AppointTime implements Parcelable, Serializable {

    private Date time; // 预约时间
    private int capacity; // 剩余容量

    public AppointTime() {
    }

    public AppointTime(Date time, int capacity) {
        this.time = time;
        this.capacity = capacity;
    }

    protected AppointTime(Parcel in) {
        capacity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(capacity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppointTime> CREATOR = new Creator<AppointTime>() {
        @Override
        public AppointTime createFromParcel(Parcel in) {
            return new AppointTime(in);
        }

        @Override
        public AppointTime[] newArray(int size) {
            return new AppointTime[size];
        }
    };

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "AppointTime{" +
                "time=" + time +
                ", capacity=" + capacity +
                '}';
    }
}
