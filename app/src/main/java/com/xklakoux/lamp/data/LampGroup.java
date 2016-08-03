package com.xklakoux.lamp.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by artur on 01/05/16.
 */

public final class LampGroup extends SugarRecord {

    @SerializedName("on")
    private boolean mOn;
    @SerializedName("groupId")
    private String mGroupId;
    @SerializedName("name")
    private String mName;
    @SerializedName("tracking")
    private boolean mTracking;
    @SerializedName("lightSetting")
    private Lamp.LightSetting mLightSetting;

    public LampGroup() {
    }

    public LampGroup(String groupId, String name, boolean tracking, Lamp.LightSetting lightSetting, boolean on) {
        mGroupId = groupId;
        mName = name;
        mTracking = tracking;
        mLightSetting = lightSetting;
        mOn = on;
    }

    public boolean isOn() {
        return mOn;
    }

    public void setOn(boolean on) {
        mOn = on;
    }

    public boolean isTracking() {
        return mTracking;
    }

    public void setTracking(boolean tracking) {
        mTracking = tracking;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


    public void setGroupId(@Nullable String groupId) {
        mGroupId = groupId;
    }


    public Lamp.LightSetting getLightSetting() {
        return mLightSetting;
    }

    public void setLightSetting(Lamp.LightSetting lightSetting) {
        mLightSetting = lightSetting;
    }

    @Override
    public String toString() {
        return "Group named " + mName;
    }

}
