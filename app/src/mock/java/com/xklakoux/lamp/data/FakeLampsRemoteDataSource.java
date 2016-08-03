package com.xklakoux.lamp.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.xklakoux.lamp.data.source.Id;
import com.xklakoux.lamp.data.source.LampsDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by artur on 07/05/16.
 */
public class FakeLampsRemoteDataSource implements LampsDataSource {

    private static FakeLampsRemoteDataSource INSTANCE = null;

    private static final Map<String, Lamp> LAMPS_SERVICE_DATA = new LinkedHashMap<>();

    private FakeLampsRemoteDataSource() {

                LAMPS_SERVICE_DATA.put("0",new Lamp("0", "Lampa pierwsza", true, "0", true, "#FF0000", (short) 100, (short) 0, (short) -90, (short) 270, null));
                LAMPS_SERVICE_DATA.put("1",new Lamp("1", "Lampa druga", false, "0", true, "#00FF00", (short) 90, (short) 0, (short) 90, (short) 0, null));
                LAMPS_SERVICE_DATA.put("2",new Lamp("2", "Lampa trzecia", true, "0", false, "#0000FF", (short) 40, (short) 0, (short) 20, (short) 270, null));
                LAMPS_SERVICE_DATA.put("3",new Lamp("3", "Lampa czwarta", false, "0", false, "#FFFFFF", (short) 10, (short) 0, (short) 10, (short) 20, null));

    }

    public static FakeLampsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeLampsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void deleteAllLamps() {
        LAMPS_SERVICE_DATA.clear();
    }

    @Override
    public void deleteLamp(@NonNull String lampId) {
        LAMPS_SERVICE_DATA.remove(lampId);
    }

    @Override
    public void getLamps(@NonNull LoadLampsCallback callback) {
        callback.onLampsLoaded(new ArrayList<Lamp>(LAMPS_SERVICE_DATA.values()));

    }

    @Override
    public void getLamp(@NonNull String lampId, @NonNull GetLampCallback callback) {
        callback.onLampLoaded(LAMPS_SERVICE_DATA.get(lampId));
    }

    @Override
    public void saveLamp(@NonNull Lamp lamp) {
        LAMPS_SERVICE_DATA.put(lamp.getLampId(), lamp);
    }

    @Override
    public void trackLamp(@NonNull Lamp lamp, @NonNull GenericCallback callback) {
        LAMPS_SERVICE_DATA.get(lamp.getLampId()).setTracking(true);
        callback.onSuccess();

    }

    @Override
    public void trackLamp(@NonNull String lampId, @NonNull GenericCallback callback) {
        LAMPS_SERVICE_DATA.get(lampId).setTracking(true);
        callback.onSuccess();

    }

    @Override
    public void dontTrackLamp(@NonNull Lamp lamp, @NonNull GenericCallback callback) {
        LAMPS_SERVICE_DATA.get(lamp.getLampId()).setTracking(false);
        callback.onSuccess();

    }

    @Override
    public void dontTrackLamp(@NonNull String lampId, @NonNull GenericCallback callback) {
        LAMPS_SERVICE_DATA.get(lampId).setTracking(false);
        callback.onSuccess();

    }

    @Override
    public void updateLamp(@NonNull Lamp lamp, @NonNull IdCallback callback) {
        final Lamp updatedLamp = Lamp.updateLamp(LAMPS_SERVICE_DATA.get(lamp.getLampId()), lamp);
        LAMPS_SERVICE_DATA.put(lamp.getLampId(), updatedLamp);
        callback.onSuccess(new Id(lamp.getLampId()));
    }

    @Override
    public void refreshLamps() {
        //nothing
    }

    @VisibleForTesting
    public void addLamps(Lamp... lamps) {
        for (Lamp lamp : lamps) {
            LAMPS_SERVICE_DATA.put(lamp.getLampId(), lamp);
        }
    }
}
