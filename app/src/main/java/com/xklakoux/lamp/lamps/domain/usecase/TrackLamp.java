package com.xklakoux.lamp.lamps.domain.usecase;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.UseCase;
import com.xklakoux.lamp.data.source.LampsDataSource;
import com.xklakoux.lamp.data.source.LampsRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 04/05/16.
 */
public class TrackLamp extends UseCase<TrackLamp.RequestValues, TrackLamp.ResponseValue> {

    private final LampsRepository mLampsRepository;

    public TrackLamp(@NonNull LampsRepository lampsRepository) {
        mLampsRepository = checkNotNull(lampsRepository, "tasksRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        mLampsRepository.trackLamp(requestValues.getLampId(), new LampsDataSource.GenericCallback() {
            @Override
            public void onSuccess() {
                getUseCaseCallback().onSuccess(new ResponseValue());
            }

            @Override
            public void onFailure() {
                getUseCaseCallback().onError();

            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues{
        private final String lampId;

        public RequestValues(String lampId) {
            this.lampId = lampId;
        }

        public String getLampId() {
            return lampId;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue{
        public ResponseValue() {
        }
    }
}
