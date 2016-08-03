package com.xklakoux.lamp.data.source.local;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.data.source.LampsDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 04/05/16.
 */
public class LampsLocalDataSource implements LampsDataSource {


    private static LampsLocalDataSource INSTANCE = null;

    private LampsLocalDataSource() {

    }

    public static LampsLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LampsLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void deleteAllLamps() {
        Lamp.deleteAll(Lamp.class);
    }

    @Override
    public void deleteLamp(@NonNull String lampId) {
        Lamp.delete(checkNotNull(getLamp(lampId)));
    }

    @Override
    public void getLamps(@NonNull LoadLampsCallback callback) {
        List<Lamp> lamps = Lamp.listAll(Lamp.class);
        if (lamps.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onLampsLoaded(lamps);
        }
    }

    @Override
    public void getLamp(@NonNull String lampId, @NonNull GetLampCallback callback) {
        List<Lamp> lamps = Lamp.find(Lamp.class, "mLampId = ?", lampId);
        if (lamps.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onLampLoaded(lamps.get(0));
        }
    }

    @Override
    public void saveLamp(@NonNull Lamp lamp) {
        lamp.getRotation().save();
        lamp.getLightSetting().save();
        lamp.save();
    }


    @Override
    public void trackLamp(@NonNull Lamp lamp, @NonNull GenericCallback callback) {
        trackLamp(lamp.getLampId(),callback);
    }

    @Override
    public void trackLamp(@NonNull String lampId, @NonNull GenericCallback callback) {
        checkNotNull(lampId);
        Lamp lamp = getLamp(lampId);
        if (lamp != null) {
            lamp.setTracking(true);
            lamp.save();
            callback.onSuccess();
        } else {
            callback.onFailure();
        }
    }

    @Override
    public void dontTrackLamp(@NonNull Lamp lamp, @NonNull GenericCallback callback) {
        dontTrackLamp(lamp.getLampId(),callback);
    }

    @Override
    public void dontTrackLamp(@NonNull String lampId, @NonNull GenericCallback callback) {
        checkNotNull(lampId);
        Lamp lamp = getLamp(lampId);
        if (lamp != null) {
            lamp.setTracking(false);
            lamp.save();
            callback.onSuccess();
        } else {
            callback.onFailure();
        }
    }

    @Override
    public void updateLamp(@NonNull Lamp lamp, @NonNull IdCallback callback) {
        checkNotNull(lamp);
        final Lamp updateLamp = Lamp.updateLamp(getLamp(lamp.getLampId()), lamp);
        updateLamp.getRotation().save();
        updateLamp.getLightSetting().save();
        updateLamp.save();
    }

    @Override
    public void refreshLamps() {

    }

    private Lamp getLamp(@NonNull String lampId) {
        List<Lamp> lamps = Lamp.find(Lamp.class, "M_LAMP_ID = ?", lampId);
        if (lamps.isEmpty()) {
            return null;
        } else {
            return lamps.get(0);
        }
    }
}
