package com.xklakoux.lamp.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by artur on 01/05/16.
 */

public final class Lamp extends SugarRecord {

    @Unique
    @SerializedName("lampId")
    private String mLampId;

    @SerializedName("name")
    private String mName;
    @SerializedName("tracking")
    private Boolean mTracking;

    @SerializedName("tokenId")
    private String mTokenId;

    @SerializedName("on")
    private Boolean mOn;

    @NotNull
    @SerializedName("rotation")
    private Rotation mRotation = new Rotation();

    @SerializedName("groupId")
    @Nullable
    private String mGroupId;

    @NotNull
    @SerializedName("lightSetting")
    private LightSetting mLightSetting = new LightSetting();

    public Lamp() {

    }

    public Lamp(String lampId, String name, Boolean tracking, String tokenId, Boolean on, LightSetting lightSetting, Rotation rotation, String groupId) {
        mLampId = lampId;
        mName = name;
        mTracking = tracking;
        mTokenId = tokenId;
        mOn = on;
        mLightSetting = lightSetting;
        mRotation = rotation;
        mGroupId = groupId;
    }

    public Lamp(String lampId, String name, Boolean tracking, String tokenId, Boolean on, String color, Short intensity, Short mode, Short rotY, Short rotZ, String groupId) {
        mLampId = lampId;
        mName = name;
        mTracking = tracking;
        mTokenId = tokenId;
        mOn = on;
        mLightSetting.setColor(color);
        mLightSetting.setIntensity(intensity);
        mLightSetting.setMode(mode);
        mRotation.setY(rotY);
        mRotation.setZ(rotZ);
        mGroupId = groupId;
    }

    public Boolean isTracking() {
        return mTracking;
    }

    public void setTracking(Boolean tracking) {
        mTracking = tracking;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTokenId() {
        return mTokenId;
    }

    public void setTokenId(String tokenId) {
        mTokenId = tokenId;
    }

    public Rotation getRotation() {
        return mRotation;
    }

    public void setRotation(Rotation rotation) {
        mRotation = rotation;
    }

    @Nullable
    public String getGroupId() {
        return mGroupId;
    }

    public void setGroupId(@Nullable String groupId) {
        mGroupId = groupId;
    }

    public String getLampId() {
        return mLampId;
    }

    public void setLampId(String lampId) {
        mLampId = lampId;
    }

    @Override
    public String toString() {
        return "Lamp named " + mName;
    }

    public Boolean isOn() {
        return mOn;
    }

    public void setOn(Boolean on) {
        mOn = on;
    }

    public LightSetting getLightSetting() {
        return mLightSetting;
    }

    public void setLightSetting(LightSetting lightSetting) {
        mLightSetting = lightSetting;
    }

    public Map<String, String> getLightSettingHashMap() {
        Map<String, String> map = new HashMap<>();
        map.put("color", mLightSetting.getColor());
        map.put("intensity", "" + mLightSetting.getIntensity());
        map.put("mode", "" + mLightSetting.getMode());
        return map;
    }

    public static class LightSetting extends SugarRecord {

        @SerializedName("intensity")
        private Short mIntensity;

        @SerializedName("color")
        private String mColor;

        @SerializedName("mode")
        private Short mMode;

        public Short getMode() {
            return mMode;
        }

        public void setMode(Short mode) {
            mMode = mode;
        }

        public Short getIntensity() {
            return mIntensity;
        }

        public void setIntensity(Short intensity) {
            mIntensity = intensity;
        }

        public String getColor() {
            return mColor;
        }

        public void setColor(String color) {
            this.mColor = color;
        }

        public HashMap<String, String> getMapOfFields() {
            HashMap<String, String> map = new HashMap<>();
            map.put("intensity", "" + mIntensity);
            map.put("mode", "" + mMode);
            map.put("color", mColor);
            return map;
        }

    }

    public static class Rotation extends SugarRecord{
        @SerializedName("z")
        private Short mZ;

        public void setY(Short y) {
            mY = y;
        }

        public void setZ(Short z) {
            mZ = z;
        }

        @SerializedName("y")
        private Short mY;

        public Rotation() {
        }

        public Short getZ() {
            return mZ;
        }

        public Short getY() {
            return mY;
        }
    }

    public static Lamp updateLamp(Lamp oldLamp, Lamp updatesLamp){
        if(updatesLamp.getName()!=null){
            oldLamp.setName(updatesLamp.getName());
        }
        if(updatesLamp.getGroupId()!=null){
            oldLamp.setGroupId(updatesLamp.getGroupId());
        }
        if(updatesLamp.getLightSetting().getColor()!=null){
            oldLamp.getLightSetting().setColor(updatesLamp.getLightSetting().getColor());
        }
        if(updatesLamp.getTokenId()!=null){
            oldLamp.setTokenId(updatesLamp.getTokenId());
        }
        if(updatesLamp.getRotation().getY()!=null){
            oldLamp.getRotation().setY(updatesLamp.getRotation().getY());
        }
        if(updatesLamp.getRotation().getZ()!=null){
            oldLamp.getRotation().setZ(updatesLamp.getRotation().getZ());
        }
        if(updatesLamp.getLightSetting().getIntensity()!=null){
            oldLamp.getLightSetting().setIntensity(updatesLamp.getLightSetting().getIntensity());
        }
        if(updatesLamp.getLightSetting().getMode()!=null){
            oldLamp.getLightSetting().setMode(updatesLamp.getLightSetting().getMode());
        }
        if(updatesLamp.isOn()!=null){
            oldLamp.setOn(updatesLamp.isOn());
        }
        if(updatesLamp.isTracking()!=null){
            oldLamp.setTracking(updatesLamp.isTracking());
        }
        return oldLamp;
    }
}
