package com.xklakoux.lamp.lampdetails.domain.usecase;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.UseCase;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.data.source.LampsDataSource;
import com.xklakoux.lamp.data.source.LampsRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 15/05/16.
 */
public class GetLamp extends UseCase<GetLamp.RequestValues, GetLamp.ResponseValue> {


    private final LampsRepository mLampsRepository;

    public GetLamp(@NonNull LampsRepository lampsRepository) {
        mLampsRepository = checkNotNull(lampsRepository);
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        mLampsRepository.getLamp(requestValues.mLampId, new LampsDataSource.GetLampCallback() {
            @Override
            public void onLampLoaded(Lamp lamp) {
                getUseCaseCallback().onSuccess(new GetLamp.ResponseValue(lamp));
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private String mLampId;

        public RequestValues(@NonNull String lampId){
            mLampId = lampId;
        }
    }
    public static class ResponseValue implements UseCase.ResponseValue {

        private Lamp mLamp;

        public ResponseValue(@NonNull Lamp lamp) {
            mLamp = checkNotNull(lamp, "null lamp!");
        }

        public Lamp getLamp() {
            return mLamp;
        }
    }
}
