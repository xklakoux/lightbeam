package com.xklakoux.lamp.lamps;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.UseCase;
import com.xklakoux.lamp.UseCaseHandler;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.lamps.domain.usecase.DontTrackLamp;
import com.xklakoux.lamp.lamps.domain.usecase.TrackLamp;
import com.xklakoux.lamp.lamps.domain.usecase.GetLamps;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 04/05/16.
 */
public class LampsPresenter implements LampsContract.Presenter {

    private final LampsContract.View mLampsView;
    private final GetLamps mGetLamps;
    private final DontTrackLamp mDontTrackLamp;
    private final TrackLamp mTrackLamp;

    private boolean mFirstLoad = true;

    private final UseCaseHandler mUseCaseHandler;

    public LampsPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull LampsContract.View lampsView, @NonNull GetLamps getLamps, @NonNull DontTrackLamp dontTrackLamp, @NonNull TrackLamp trackLamp){
        mGetLamps = getLamps;
        mDontTrackLamp = dontTrackLamp;
        mTrackLamp = trackLamp;
        mUseCaseHandler = checkNotNull(useCaseHandler);
        mLampsView = checkNotNull(lampsView);
        mLampsView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        //nothing here for now
    }

    @Override
    public void loadLamps(boolean forceUpdate) {
        loadLamps(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadLamps(boolean forceUpdate, final boolean showLoadingUI) {
        if(showLoadingUI){
            mLampsView.showLoadingIndicator(true);
        }
        GetLamps.RequestValues requestValues = new GetLamps.RequestValues(forceUpdate);
        mUseCaseHandler.execute(mGetLamps, requestValues, new UseCase.UseCaseCallback<GetLamps.ResponseValue>() {
            @Override
            public void onSuccess(GetLamps.ResponseValue response) {
                List<Lamp> lamps = response.getLamps();
                if(!mLampsView.isActive()){
                    return;
                }
                if(showLoadingUI){
                    mLampsView.showLoadingIndicator(false);
                }
                processLamps(lamps);
            }



            @Override
            public void onError() {
                // The view may not be able to handle UI updates anymore
                if (!mLampsView.isActive()) {
                    return;
                }
                if(showLoadingUI){
                    mLampsView.showLoadingIndicator(false);
                }
                mLampsView.showLoadingLampsError();
            }
        });
    }

    private void processLamps(List<Lamp> lamps) {
        if(lamps.isEmpty()){
            mLampsView.showNoLamps();
        }else{
            mLampsView.showLamps(lamps);
        }
    }

    @Override
    public void openLampDetails(@NonNull Lamp requestedLamp) {
        checkNotNull(requestedLamp, "requestedLamp cannot be null!");
        mLampsView.showLampDetailsUi(requestedLamp.getLampId());
    }

    @Override
    public void trackLamp(@NonNull final Lamp activatedLamp) {
        checkNotNull(activatedLamp, "activatedLamp cannot be null");
        TrackLamp.RequestValues requestValues = new TrackLamp.RequestValues(activatedLamp.getLampId());
        mUseCaseHandler.execute(mTrackLamp, requestValues, new UseCase.UseCaseCallback<TrackLamp.ResponseValue>() {
            @Override
            public void onSuccess(TrackLamp.ResponseValue response) {
                if(!mLampsView.isActive()){
                    return;
                }
                mLampsView.showLampTracked(activatedLamp.getLampId());
            }

            @Override
            public void onError() {
                mLampsView.showChangeTrackingStateError(activatedLamp.getLampId());

            }
        });
    }

    @Override
    public void dontTrackLamp(@NonNull final Lamp deactivatedLamp) {
        checkNotNull(deactivatedLamp, "deactivatedLamp cannot be null");
        DontTrackLamp.RequestValues requestValues = new DontTrackLamp.RequestValues(deactivatedLamp.getLampId());
        mUseCaseHandler.execute(mDontTrackLamp, requestValues, new UseCase.UseCaseCallback<DontTrackLamp.ResponseValue>() {
            @Override
            public void onSuccess(DontTrackLamp.ResponseValue response) {
                if(!mLampsView.isActive()){
                    return;
                }
                mLampsView.showLampNotTracked(deactivatedLamp.getLampId());
            }

            @Override
            public void onError() {
                mLampsView.showChangeTrackingStateError(deactivatedLamp.getLampId());
            }
        });
    }

    @Override
    public void start() {
        loadLamps(false);
    }
}
