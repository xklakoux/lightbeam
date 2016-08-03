package com.xklakoux.lamp.data.source;

import com.google.gson.annotations.SerializedName;

/**
 * Created by artur on 14/05/16.
 */
public class Id {

    @SerializedName("id")
    private String id;

    public Id(String id) {
        this.id = id;
    }
}
