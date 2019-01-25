package com.amazingdata.ecare.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Xiong
 * @date 2019/1/25 - 10:57
 */
// 购物车item
public class ShoppingCartItem implements Parcelable, Serializable {
    private String goodsName; // 商品名称
    private float goodsPrice; // 商品单价
    private int goodsNum; // 商品数目
    private String goodsIconUrl; // 商品图标Url

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(String goodsName, float goodsPrice, int goodsNum, String goodsIconUrl) {
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
        this.goodsIconUrl = goodsIconUrl;
    }

    protected ShoppingCartItem(Parcel in) {
        goodsName = in.readString();
        goodsPrice = in.readFloat();
        goodsNum = in.readInt();
        goodsIconUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goodsName);
        dest.writeFloat(goodsPrice);
        dest.writeInt(goodsNum);
        dest.writeString(goodsIconUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShoppingCartItem> CREATOR = new Creator<ShoppingCartItem>() {
        @Override
        public ShoppingCartItem createFromParcel(Parcel in) {
            return new ShoppingCartItem(in);
        }

        @Override
        public ShoppingCartItem[] newArray(int size) {
            return new ShoppingCartItem[size];
        }
    };

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsIconUrl() {
        return goodsIconUrl;
    }

    public void setGoodsIconUrl(String goodsIconUrl) {
        this.goodsIconUrl = goodsIconUrl;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsNum=" + goodsNum +
                ", goodsIconUrl='" + goodsIconUrl + '\'' +
                '}';
    }
}
