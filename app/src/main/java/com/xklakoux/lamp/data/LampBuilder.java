package com.xklakoux.lamp.data;

public class LampBuilder {
    private String mLampId;
    private String mName;
    private Boolean mTracking;
    private String mTokenId;
    private Boolean mOn;
    private Lamp.LightSetting mLightSetting;
    private Lamp.Rotation mRotation;
    private String mGroupId;
    private String mColor;
    private Short mIntensity;
    private Short mMode;
    private Short mRotY;
    private Short mRotZ;

    public LampBuilder setLampId(String lampId) {
        mLampId = lampId;
        return this;
    }

    public LampBuilder setName(String name) {
        mName = name;
        return this;
    }

    public LampBuilder setTracking(Boolean tracking) {
        mTracking = tracking;
        return this;
    }

    public LampBuilder setTokenId(String tokenId) {
        mTokenId = tokenId;
        return this;
    }

    public LampBuilder setOn(Boolean on) {
        mOn = on;
        return this;
    }

    public LampBuilder setLightSetting(Lamp.LightSetting lightSetting) {
        mLightSetting = lightSetting;
        return this;
    }

    public LampBuilder setRotation(Lamp.Rotation rotation) {
        mRotation = rotation;
        return this;
    }

    public LampBuilder setGroupId(String groupId) {
        mGroupId = groupId;
        return this;
    }

    public LampBuilder setColor(String color) {
        mColor = color;
        return this;
    }

    public LampBuilder setIntensity(Short intensity) {
        mIntensity = intensity;
        return this;
    }

    public LampBuilder setMode(Short mode) {
        mMode = mode;
        return this;
    }

    public LampBuilder setRotY(Short rotY) {
        mRotY = rotY;
        return this;
    }

    public LampBuilder setRotZ(Short rotZ) {
        mRotZ = rotZ;
        return this;
    }

    public Lamp createLamp() {
        return new Lamp(mLampId, mName, mTracking, mTokenId, mOn, mColor, mIntensity, mMode, mRotY, mRotZ, mGroupId);
    }
}