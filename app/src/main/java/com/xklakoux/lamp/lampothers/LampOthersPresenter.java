package com.xklakoux.lamp.lampothers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xklakoux.lamp.UseCase;
import com.xklakoux.lamp.UseCaseHandler;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.lampdetails.domain.usecase.GetLamp;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 17/05/16.
 */
public class LampOthersPresenter implements LampOthersContract.Presenter {

    private LampOthersContract.View mView;
    private GetLamp mGetLamp;
    private UseCaseHandler mUseCaseHandler;


    @Nullable
    private String mLampId;

    public LampOthersPresenter(LampOthersContract.View view, @Nullable String lampId, @NonNull GetLamp getLamp, @NonNull UseCaseHandler useCaseHandler) {
        mView = view;
        mLampId = lampId;
        mGetLamp  = checkNotNull(getLamp);
        mUseCaseHandler  = checkNotNull(useCaseHandler);
        mView.setPresenter(this);
    }

    @Override
    public void openSchedules() {
        mView.showSchedulesUI(mLampId);
    }

    @Override
    public void start() {
        if (mLampId == null || mLampId.isEmpty()) {
            mView.showNoSuchLamp();
            return;
        }
//        mView.showLoadingIndicator(true);
        mUseCaseHandler.execute(mGetLamp, new GetLamp.RequestValues(mLampId), new UseCase.UseCaseCallback<GetLamp.ResponseValue>() {

            @Override
            public void onSuccess(GetLamp.ResponseValue response) {
                Lamp lamp = response.getLamp();

                if (mView.isActive()) {
//                    mView.showLoadingIndicator(false);
                    mView.showSettings(lamp);
                }
            }

            @Override
            public void onError()
            {
//                mView.showLampLoadingError();
            }
        });
    }
}
