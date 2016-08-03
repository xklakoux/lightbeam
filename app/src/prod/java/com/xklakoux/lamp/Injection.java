package com.xklakoux.lamp;

import com.xklakoux.lamp.data.source.LampsRepository;
import com.xklakoux.lamp.data.source.local.LampsLocalDataSource;
import com.xklakoux.lamp.data.source.remote.LampsRemoteDataSource;
import com.xklakoux.lamp.lampdetails.domain.usecase.GetLamp;
import com.xklakoux.lamp.lampdetails.domain.usecase.UpdateLamp;
import com.xklakoux.lamp.lamps.domain.usecase.TrackLamp;
import com.xklakoux.lamp.lamps.domain.usecase.DontTrackLamp;
import com.xklakoux.lamp.lamps.domain.usecase.GetLamps;

/**
 * Created by artur on 11/05/16.
 */
public class Injection {
    public static LampsRepository provideLampsRepository() {
        return LampsRepository.getInstance(LampsRemoteDataSource.getInstance(), LampsLocalDataSource.getInstance());
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

    public static GetLamp provideGetLamp(){
        return new GetLamp(Injection.provideLampsRepository());
    }

    public static UpdateLamp provideUpdateLamp(){
        return new UpdateLamp(Injection.provideLampsRepository());
    }


    public static DontTrackLamp provideDeactivateLamp() {
        return new DontTrackLamp(Injection.provideLampsRepository());
    }
}
