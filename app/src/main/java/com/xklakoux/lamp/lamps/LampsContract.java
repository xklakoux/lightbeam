package com.xklakoux.lamp.lamps;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.BasePresenter;
import com.xklakoux.lamp.BaseView;
import com.xklakoux.lamp.data.Lamp;

import java.util.List;

/**
 * Created by artur on 04/05/16.
 */
public interface LampsContract {

    interface View extends BaseView<Presenter> {

        void showLoadingIndicator(boolean active);

        void showLamps(List<Lamp> lamps);

        void showLampDetailsUi(String lampId);

        void showLampNotTracked(String id);

        void showLampTracked(String id);

        void showLoadingLampsError();

        void showChangeTrackingStateError(String id);

        void showNoLamps();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadLamps(boolean forceUpdate);

        void openLampDetails(@NonNull Lamp requestedLamp);

        void trackLamp(@NonNull Lamp activatedLamp);

        void dontTrackLamp(@NonNull Lamp deactivatedLamp);

    }
}
