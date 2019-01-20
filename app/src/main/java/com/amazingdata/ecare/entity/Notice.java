package com.amazingdata.ecare.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xiong
 * @date 2019/1/19 - 11:25
 */
public class Notice extends SimpleBannerInfo implements Parcelable, Serializable {
    private String title;
    private Date time;
    private String content;
    private String iconUrl;


    protected Notice(Parcel in) {
        title = in.readString();
        content = in.readString();
        iconUrl = in.readString();
    }

    public static final Creator<Notice> CREATOR = new Creator<Notice>() {
        @Override
        public Notice createFromParcel(Parcel in) {
            return new Notice(in);
        }

        @Override
        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };

    public Notice(String title, Date time, String content, String iconUrl) {
        this.title = title;
        this.time = time;
        this.content = content;
        this.iconUrl = iconUrl;
    }

    public Notice() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(iconUrl);
    }

    @Override
    public Object getXBannerUrl() {
        return iconUrl;
    }
}
