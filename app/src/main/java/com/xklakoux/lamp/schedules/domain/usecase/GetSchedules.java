package com.xklakoux.lamp.schedules.domain.usecase;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.UseCase;
import com.xklakoux.lamp.data.source.LampsRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 19/05/16.
 */
public class GetSchedules extends UseCase<GetSchedules.RequestValues, GetSchedules.ResponseValue> {



    private final LampsRepository mLampsRepository;

    public GetSchedules(@NonNull LampsRepository lampsRepository) {
        mLampsRepository = checkNotNull(lampsRepository, "lamps repository cannot be null!");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

    }

    public static class RequestValues implements UseCase.RequestValues {

    }

    public static class ResponseValue implements UseCase.ResponseValue {

    }
}
