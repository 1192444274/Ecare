package com.amazingdata.ecare.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xiong
 * @date 2019/1/24 - 13:59
 */
public class DrugTaking implements Parcelable, Serializable {
    private int oderNum; // 订单号
    private int mode; // 0为未可取,1为未取,2为已取
    private Date time; // mode为0时无意义,为1时标记为过期时间,为2时标记为领取时间
    private String QRcodeUrl; // 二维码Url地址

    public DrugTaking() {
    }

    public DrugTaking(int oderNum, int mode, Date time, String QRcodeUrl) {
        this.oderNum = oderNum;
        this.mode = mode;
        this.time = time;
        this.QRcodeUrl = QRcodeUrl;
    }

    protected DrugTaking(Parcel in) {
        oderNum = in.readInt();
        mode = in.readInt();
        QRcodeUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(oderNum);
        dest.writeInt(mode);
        dest.writeString(QRcodeUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DrugTaking> CREATOR = new Creator<DrugTaking>() {
        @Override
        public DrugTaking createFromParcel(Parcel in) {
            return new DrugTaking(in);
        }

        @Override
        public DrugTaking[] newArray(int size) {
            return new DrugTaking[size];
        }
    };

    public int getOderNum() {
        return oderNum;
    }

    public void setOderNum(int oderNum) {
        this.oderNum = oderNum;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getQRcodeUrl() {
        return QRcodeUrl;
    }

    public void setQRcodeUrl(String QRcodeUrl) {
        this.QRcodeUrl = QRcodeUrl;
    }

    @Override
    public String toString() {
        return "DrugTaking{" +
                "oderNum=" + oderNum +
                ", mode=" + mode +
                ", time=" + time +
                ", QRcodeUrl='" + QRcodeUrl + '\'' +
                '}';
    }
}
