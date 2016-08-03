package com.xklakoux.lamp.lamps.domain.usecase;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.UseCase;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.data.source.LampsDataSource;
import com.xklakoux.lamp.data.source.LampsRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 04/05/16.
 */
public class GetLamps extends UseCase<GetLamps.RequestValues, GetLamps.ResponseValue> {

    private final LampsRepository mLampsRepository;

    public GetLamps(@NonNull LampsRepository lampsRepository) {
        mLampsRepository = checkNotNull(lampsRepository, "lamps repository cannot be null!");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        if(requestValues.isForceUpdate()){
            mLampsRepository.refreshLamps();
        }
        mLampsRepository.getLamps(new LampsDataSource.LoadLampsCallback() {
            @Override
            public void onLampsLoaded(List<Lamp> lamps) {
                getUseCaseCallback().onSuccess(new ResponseValue(lamps));
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues{

        public boolean isForceUpdate() {
            return mForceUpdate;
        }

        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate) {
            mForceUpdate = forceUpdate;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final List<Lamp> mLamps;

        public ResponseValue(@NonNull List<Lamp> lamps) {
            mLamps = checkNotNull(lamps, "lamps cannot be null!");
        }

        public List<Lamp> getLamps() {
            return mLamps;
        }
    }
}
