package com.xklakoux.lamp.data;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by artur on 12/05/16.
 */
public final class Event extends SugarRecord {

    @SerializedName("eventId")
    private int mEventId;

    @SerializedName("type")
    private short mType;

    @SerializedName("lightSetting")
    private Lamp.LightSetting mLightSetting;

    @SerializedName("length")
    private int mLength;

    public Event() {

    }

    public Event(int eventId, short type, Lamp.LightSetting lightSetting, int length) {
        mEventId = eventId;
        mType = type;
        mLightSetting = lightSetting;
        mLength = length;
    }
}
