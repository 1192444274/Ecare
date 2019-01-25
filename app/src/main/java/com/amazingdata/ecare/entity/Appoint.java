package com.amazingdata.ecare.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.amazingdata.ecare.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xiong
 * @date 2019/1/22 - 11:09
 */
// 预约信息
public class Appoint implements Parcelable, Serializable {
    private int mode; // 1为挂号,2为体检
    private int number; // 预约号
    private String dept; // 部门,科室,当为挂号时默认为Null
    private Date time; // 预约时间,默认为开始时间;终止时间挂号为30min,体检为4小时
    private String pos; // 地点

    public Appoint() {
    }

    public Appoint(int mode, int number, String dept, Date time, String pos) {
        this.mode = mode;
        this.number = number;
        this.dept = dept;
        this.time = time;
        this.pos = pos;
    }

    protected Appoint(Parcel in) {
        mode = in.readInt();
        number = in.readInt();
        dept = in.readString();
        pos = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mode);
        dest.writeInt(number);
        dest.writeString(dept);
        dest.writeString(pos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Appoint> CREATOR = new Creator<Appoint>() {
        @Override
        public Appoint createFromParcel(Parcel in) {
            return new Appoint(in);
        }

        @Override
        public Appoint[] newArray(int size) {
            return new Appoint[size];
        }
    };

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getNumber() {
        return "预约号： " + number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDept() {
        return "科室：" + dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getTime() {
        return "时间：" + DateUtils.getNextHalf(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPos() {
        return "地点：" + pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "Appoint{" +
                "mode=" + mode +
                ", number=" + number +
                ", dept='" + dept + '\'' +
                ", time=" + time +
                ", pos='" + pos + '\'' +
                '}';
    }
}
