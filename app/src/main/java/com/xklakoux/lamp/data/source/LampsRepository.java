package com.xklakoux.lamp.data.source;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.data.Lamp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 04/05/16.
 */
public class LampsRepository implements LampsDataSource {

    private static LampsRepository INSTANCE = null;

    private LampsDataSource mRemoteDataSource;
    private LampsDataSource mLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Lamp> mCachedLamps;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    private LampsRepository(@NonNull LampsDataSource remoteDataSource, @NonNull LampsDataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    public static LampsRepository getInstance(LampsDataSource lampsRemoteDataSource,
                                       LampsDataSource lampsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new LampsRepository(lampsRemoteDataSource, lampsLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void deleteAllLamps() {
        mLocalDataSource.deleteAllLamps();
        mRemoteDataSource.deleteAllLamps();

        if (mCachedLamps == null) {
            mCachedLamps = new LinkedHashMap<>();
        }
        mCachedLamps.clear();
    }

    @Override
    public void deleteLamp(@NonNull String lampId) {
        mLocalDataSource.deleteLamp(checkNotNull(lampId));
        mRemoteDataSource.deleteLamp(checkNotNull(lampId));

        mCachedLamps.remove(lampId);
    }

    @Override
    public void getLamp(@NonNull final String lampId, @NonNull final GetLampCallback callback) {
        checkNotNull(lampId);
        checkNotNull(callback);
        if (!mCacheIsDirty && mCachedLamps != null) {
            callback.onLampLoaded(mCachedLamps.get(lampId));
            return;
        }
        if (mCacheIsDirty) {
            getLampFromRemoteDataSource(lampId, callback);
        } else {
            mLocalDataSource.getLamp(lampId, new GetLampCallback() {
                @Override
                public void onLampLoaded(Lamp lamp) {
                    refreshCache(lamp);
                    callback.onLampLoaded(lamp);
                }

                @Override
                public void onDataNotAvailable() {
                    getLampFromRemoteDataSource(lampId, callback);
                }
            });
        }
    }

    private void getLampFromRemoteDataSource(final String lampId, final GetLampCallback callback) {
        mRemoteDataSource.getLamp(lampId, new GetLampCallback() {
            @Override
            public void onLampLoaded(Lamp lamp) {
                refreshCache(lamp);
                refreshLocalDataSource(lamp);
                callback.onLampLoaded(mCachedLamps.get(lampId));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getLamps(@NonNull final LoadLampsCallback callback) {
        checkNotNull(callback);

        if (mCachedLamps != null && !mCacheIsDirty) {
            callback.onLampsLoaded(new ArrayList<>(mCachedLamps.values()));
            return;
        }

        if (mCacheIsDirty) {
            getLampsFromRemoteDataSource(callback);
        } else {
            mLocalDataSource.getLamps(new LoadLampsCallback() {
                @Override
                public void onLampsLoaded(List<Lamp> lamps) {
                    refreshCache(lamps);
                    callback.onLampsLoaded(new ArrayList<>(mCachedLamps.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getLampsFromRemoteDataSource(callback);
                }
            });
        }


    }

    private void refreshLocalDataSource(Lamp lamp) {
        mLocalDataSource.deleteLamp(lamp.getLampId());
        mCachedLamps.put(lamp.getLampId(), lamp);
    }

    private void getLampsFromRemoteDataSource(final LoadLampsCallback callback) {
        mRemoteDataSource.getLamps(new LoadLampsCallback() {
            @Override
            public void onLampsLoaded(List<Lamp> lamps) {
                refreshCache(lamps);
                refreshLocalDataSource(lamps);
                callback.onLampsLoaded(new ArrayList<>(mCachedLamps.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Lamp> lamps) {
        mLocalDataSource.deleteAllLamps();
        for (Lamp lamp : lamps) {
            mCachedLamps.put(lamp.getLampId(), lamp);
            mLocalDataSource.saveLamp(lamp);
        }
    }

    private void refreshCache(List<Lamp> lamps) {
        if (mCachedLamps == null) {
            mCachedLamps = new LinkedHashMap<>();
        }
        mCachedLamps.clear();
        for (Lamp lamp : lamps) {
            mCachedLamps.put(lamp.getLampId(), lamp);
        }
        mCacheIsDirty = false;
    }

    private void refreshCache(Lamp lamp) {
        if (mCachedLamps.get(lamp.getLampId()) != null) {
            mCachedLamps.remove(lamp.getLampId());
        }
        if (mCachedLamps == null) {
            mCachedLamps = new LinkedHashMap<>();
        }
        mCachedLamps.put(lamp.getLampId(), lamp);
        mCacheIsDirty = false;
    }

    @Override
    public void saveLamp(@NonNull Lamp lamp) {
        checkNotNull(lamp);

        mLocalDataSource.saveLamp(lamp);
        mRemoteDataSource.saveLamp(lamp);

        if (mCachedLamps == null) {
            mCachedLamps = new LinkedHashMap<>();
        }
        mCachedLamps.put(lamp.getLampId(), lamp);
    }


    @Override
    public void trackLamp(@NonNull Lamp lamp, @NonNull GenericCallback callback) {
        checkNotNull(lamp);
        checkNotNull(callback);
        mRemoteDataSource.trackLamp(lamp,callback);
        mLocalDataSource.trackLamp(lamp, callback);

        lamp.setTracking(true);

        if (mCachedLamps == null) {
            mCachedLamps = new LinkedHashMap<>();
        }
        mCachedLamps.put(lamp.getLampId(), lamp);
    }

    @Override
    public void trackLamp(@NonNull String lampId, @NonNull GenericCallback callback) {
        checkNotNull(lampId);
        trackLamp(checkNotNull(getLampWithId(lampId)),callback);
    }

    @Override
    public void dontTrackLamp(@NonNull Lamp lamp, @NonNull GenericCallback callback) {
        checkNotNull(lamp);
        checkNotNull(callback);

        lamp.setTracking(false);

        mLocalDataSource.dontTrackLamp(lamp,callback);
        mRemoteDataSource.dontTrackLamp(lamp,callback);


        if (mCachedLamps == null) {
            mCachedLamps = new LinkedHashMap<>();
        }
        mCachedLamps.put(lamp.getLampId(), lamp);
    }

    @Override
    public void dontTrackLamp(@NonNull String lampId, @NonNull GenericCallback callback) {
        checkNotNull(lampId);
        dontTrackLamp(checkNotNull(getLampWithId(lampId)),callback);
    }

    @Override
    public void updateLamp(@NonNull Lamp lamp, @NonNull IdCallback callback) {
        checkNotNull(lamp);

        mLocalDataSource.updateLamp(lamp, callback);
        mRemoteDataSource.updateLamp(lamp, callback);

        if (mCachedLamps == null) {
            mCachedLamps = new LinkedHashMap<>();
        }
        final Lamp updatedLamp = Lamp.updateLamp(mCachedLamps.get(lamp.getLampId()), lamp);
        mCachedLamps.put(lamp.getLampId(), updatedLamp);

    }

    private Lamp getLampWithId(String lampId) {
        checkNotNull(lampId);
        if (mCachedLamps == null || mCachedLamps.isEmpty()) {
            return null;
        }
        return mCachedLamps.get(lampId);
    }

    @Override
    public void refreshLamps() {
        mCacheIsDirty = true;
    }
}
