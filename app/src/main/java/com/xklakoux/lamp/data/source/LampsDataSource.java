package com.xklakoux.lamp.data.source;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.data.Lamp;

import java.util.List;

/**
 * Created by artur on 04/05/16.
 */
public interface LampsDataSource {

    interface LoadLampsCallback{
        void onLampsLoaded(List<Lamp> lamps);

        void onDataNotAvailable();
    }

    interface GetLampCallback{
        void onLampLoaded(Lamp lamp);

        void onDataNotAvailable();
    }

    interface GenericCallback{
        void onSuccess();

        void onFailure();
    }

    interface IdCallback{
        void onSuccess(Id id);

        void onFailure();
    }

    void deleteAllLamps();

    void deleteLamp(@NonNull String lampId);

    void getLamps(@NonNull LoadLampsCallback callback);

    void getLamp(@NonNull String lampId, @NonNull GetLampCallback callback);

    void saveLamp(@NonNull Lamp lamp);

    void trackLamp(@NonNull Lamp lamp, @NonNull GenericCallback callback);

    void trackLamp(@NonNull String lampId, @NonNull GenericCallback callback);

    void dontTrackLamp(@NonNull Lamp lamp, @NonNull GenericCallback callback);

    void dontTrackLamp(@NonNull String lampId, @NonNull GenericCallback callback);

    void updateLamp(@NonNull Lamp lamp, @NonNull IdCallback callback);

    void refreshLamps();
}
