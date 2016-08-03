package com.xklakoux.lamp.data;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by artur on 03/05/16.
 */
public final class Schedule extends SugarRecord {

    @SerializedName("scheduleId")
    private int mScheduleId;

    @SerializedName("objectId")
    private int mObjectId;

    @SerializedName("lightSetting")
    private String mLightSetting;

    @SerializedName("on")
    private boolean mOn;

    @SerializedName("time")
    private Date mTime;

    @SerializedName("days")
    private String mDays;

    @SerializedName("active")
    private boolean mActive;

    public int getScheduleId() {
        return mScheduleId;
    }

    public void setScheduleId(int scheduleId) {
        mScheduleId = scheduleId;
    }

    public Schedule(int scheduleId, int objectId, String lightSetting, boolean on, Date time, String days, boolean active) {
        mScheduleId = scheduleId;
        mObjectId = objectId;
        mLightSetting = lightSetting;
        mOn = on;
        mTime = time;
        mDays = days;
        mActive = active;
    }

    public int getObjectId() {
        return mObjectId;
    }

    public void setObjectId(int objectId) {
        mObjectId = objectId;
    }

    public String getLightSetting() {
        return mLightSetting;
    }

    public void setLightSetting(String lightSetting) {
        mLightSetting = lightSetting;
    }

    public boolean isOn() {
        return mOn;
    }

    public void setOn(boolean on) {
        mOn = on;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public String getDays() {
        return mDays;
    }

    public void setDays(String days) {
        mDays = days;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }


    public Schedule() {
    }

}
