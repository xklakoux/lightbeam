package com.xklakoux.lamp.lampdetails;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.xklakoux.lamp.BasePresenter;
import com.xklakoux.lamp.BaseView;
import com.xklakoux.lamp.data.Lamp;

/**
 * Created by artur on 06/05/16.
 */
public interface LampDetailsContract {
    interface View extends BaseView<Presenter> {

        void showOtherSettingsUI(String lampId);

        void showSettingsSaved();

        void showSaveSettingsDialog();

        void showSettingsUpdateError();

        void showLampLoadingError();

        void showChangeNameDialog(String name);

        void showChangeColorDialog(int color);

        void showNameChanged(String name);

        void showColorChanged(String color);

        void showTrackingChanged(boolean on);

        void showActiveChanged(boolean on);

        void showTrackingChangedError();

        void showActiveChangedError();

        void showIntensityChanged(short intensity);

        void showModeChanged(short mode);

        void showRotationZChanged(short z);

        void showRotationYChanged(short y);

        boolean isActive();

        void showNoSuchLamp();

        void showLoadingIndicator(boolean show);

        void showSettings(Lamp lamp);

        void showGroupIdHasChanged(String groupId);
    }
    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void openOtherSettingsUI();

        void changeName(@NonNull String lampName);

        void changeColor(@NonNull String color);

        void changeIntensity(@IntRange(from = 0, to = 100) short intensity);

        void changeGroup(@NonNull String groupId);

        void saveSettings();

        void changeRotationZ(@IntRange(from = 0, to = 360) short z);

        void changeRotationY(@IntRange(from = -90, to = 90) short y);

        void changeTracking(boolean on);

        void changeActive (boolean on);

        void changeMode(short mode);

    }
}
