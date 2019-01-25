package com.amazingdata.ecare.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Xiong
 * @date 2019/1/24 - 16:23
 */
// 药品信息
public class DrugInfo implements Parcelable, Serializable {
    private String drugName;
    private String drugType;
    private String simpleDesc;
    private String detailedDesc;
    private float price;
    private String iconUrl;

    public DrugInfo() {
    }

    public DrugInfo(String drugName, String drugType, String simpleDesc, String detailedDesc, float price, String iconUrl) {
        this.drugName = drugName;
        this.drugType = drugType;
        this.simpleDesc = simpleDesc;
        this.detailedDesc = detailedDesc;
        this.price = price;
        this.iconUrl = iconUrl;
    }

    protected DrugInfo(Parcel in) {
        drugName = in.readString();
        drugType = in.readString();
        simpleDesc = in.readString();
        detailedDesc = in.readString();
        price = in.readFloat();
        iconUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(drugName);
        dest.writeString(drugType);
        dest.writeString(simpleDesc);
        dest.writeString(detailedDesc);
        dest.writeFloat(price);
        dest.writeString(iconUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DrugInfo> CREATOR = new Creator<DrugInfo>() {
        @Override
        public DrugInfo createFromParcel(Parcel in) {
            return new DrugInfo(in);
        }

        @Override
        public DrugInfo[] newArray(int size) {
            return new DrugInfo[size];
        }
    };

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getSimpleDesc() {
        return simpleDesc;
    }

    public void setSimpleDesc(String simpleDesc) {
        this.simpleDesc = simpleDesc;
    }

    public String getDetailedDesc() {
        return detailedDesc;
    }

    public void setDetailedDesc(String detailedDesc) {
        this.detailedDesc = detailedDesc;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "DrugInfo{" +
                "drugName='" + drugName + '\'' +
                ", drugType='" + drugType + '\'' +
                ", simpleDesc='" + simpleDesc + '\'' +
                ", detailedDesc='" + detailedDesc + '\'' +
                ", price=" + price +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
