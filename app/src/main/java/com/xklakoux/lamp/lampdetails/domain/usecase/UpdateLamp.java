package com.xklakoux.lamp.lampdetails.domain.usecase;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.UseCase;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.data.source.Id;
import com.xklakoux.lamp.data.source.LampsDataSource;
import com.xklakoux.lamp.data.source.LampsRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 14/05/16.
 */
public class UpdateLamp extends UseCase<UpdateLamp.RequestValues, UpdateLamp.ResponseValue> {

    private final LampsRepository mLampsRepository;

    public UpdateLamp(@NonNull LampsRepository lampsRepository) {
        mLampsRepository = checkNotNull(lampsRepository);
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

        mLampsRepository.updateLamp(requestValues.mLamp, new LampsDataSource.IdCallback() {

            @Override
            public void onSuccess(Id id) {
                getUseCaseCallback().onSuccess(new UpdateLamp.ResponseValue(id));
            }

            @Override
            public void onFailure() {
                getUseCaseCallback().onError();
            }

        });
    }

    public static final class RequestValues implements UseCase.RequestValues{
        private Lamp mLamp;

        public RequestValues(@NonNull Lamp lamp){
            mLamp = lamp;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private Id mId;
        public ResponseValue(@NonNull Id id) {
            mId = checkNotNull(id, "null id!");
        }

        public Id getId() {
            return mId;
        }
    }
}
