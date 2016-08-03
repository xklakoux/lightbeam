package com.xklakoux.lamp;

import com.xklakoux.lamp.data.FakeLampsRemoteDataSource;
import com.xklakoux.lamp.data.source.LampsRepository;
import com.xklakoux.lamp.data.source.local.LampsLocalDataSource;
import com.xklakoux.lamp.lampdetails.domain.usecase.GetLamp;
import com.xklakoux.lamp.lampdetails.domain.usecase.UpdateLamp;
import com.xklakoux.lamp.lamps.domain.usecase.DontTrackLamp;
import com.xklakoux.lamp.lamps.domain.usecase.GetLamps;
import com.xklakoux.lamp.lamps.domain.usecase.TrackLamp;

/**
 * Created by artur on 07/05/16.
 * Enables injection of mock implementations for
 * {@link TasksDataSource} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {
    public static LampsRepository provideLampsRepository() {
        return LampsRepository.getInstance(FakeLampsRemoteDataSource.getInstance(),
                LampsLocalDataSource.getInstance());
    }

    public static GetLamps provideGetLamps() {
            return new GetLamps(provideLampsRepository());
        }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static TrackLamp provideActivateLamp() {
        return new TrackLamp(Injection.provideLampsRepository());
    }

    public static DontTrackLamp provideDeactivateLamp() {
        return new DontTrackLamp(Injection.provideLampsRepository());
    }

    public static UpdateLamp provideUpdateLamp() {
        return new UpdateLamp(Injection.provideLampsRepository());
    }

    public static GetLamp provideGetLamp() {
        return new GetLamp(Injection.provideLampsRepository());
    }
}
