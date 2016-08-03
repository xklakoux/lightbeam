package com.xklakoux.lamp.lampdetails;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xklakoux.lamp.UseCase;
import com.xklakoux.lamp.UseCaseHandler;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.lampdetails.domain.usecase.GetLamp;
import com.xklakoux.lamp.lampdetails.domain.usecase.UpdateLamp;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 06/05/16.
 */
public class LampDetailsPresenter implements LampDetailsContract.Presenter {

    private LampDetailsContract.View mLampDetailsView;

    @Nullable
    private String mLampId;
    private Lamp mLamp;
    private UseCaseHandler mUseCaseHandler;
    private GetLamp mGetLamp;

    private UpdateLamp mUpdateLamp;

    public LampDetailsPresenter(@NonNull LampDetailsContract.View lampDetailsView, @Nullable String lampId, @NonNull UpdateLamp updateLamp, @NonNull GetLamp getLamp, @NonNull UseCaseHandler useCaseHandler) {

        mLampDetailsView = lampDetailsView;
        mLampId = lampId;
        mUseCaseHandler = checkNotNull(useCaseHandler);
        mGetLamp = checkNotNull(getLamp);
        mUpdateLamp = checkNotNull(updateLamp);
        mLampDetailsView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void openOtherSettingsUI() {
        mLampDetailsView.showOtherSettingsUI(mLamp.getLampId());
    }

    @Override
    public void changeName(@NonNull final String lampName) {
        mLamp.setName(lampName);
        Lamp updatedValues = new Lamp();
        updatedValues.setLampId(mLamp.getLampId());
        updatedValues.setName(mLamp.getName());

        mUseCaseHandler.execute(mUpdateLamp, new UpdateLamp.RequestValues(updatedValues), new UseCase.UseCaseCallback<UpdateLamp.ResponseValue>() {
            @Override
            public void onSuccess(UpdateLamp.ResponseValue response) {
                mLampDetailsView.showNameChanged(lampName);
            }

            @Override
            public void onError() {
                mLampDetailsView.showSettingsUpdateError();
            }
        });
    }

    @Override
    public void changeColor(@NonNull String color) {
        mLamp.getLightSetting().setColor(color);
        Lamp updatedValues = new Lamp();
        updatedValues.setLampId(mLamp.getLampId());
        updatedValues.getLightSetting().setColor((mLamp.getLightSetting().getColor()));

        mUseCaseHandler.execute(mUpdateLamp, new UpdateLamp.RequestValues(updatedValues), new UseCase.UseCaseCallback<UpdateLamp.ResponseValue>() {
            @Override
            public void onSuccess(UpdateLamp.ResponseValue response) {
                mLampDetailsView.showColorChanged(mLamp.getLightSetting().getColor());
            }

            @Override
            public void onError() {
                mLampDetailsView.showSettingsUpdateError();
            }
        });
    }

    @Override
    public void changeIntensity(@IntRange(from = 0, to = 100) final short intensity) {
        mLamp.getLightSetting().setIntensity(intensity);
        Lamp updatedValues = new Lamp();
        updatedValues.setLampId(mLamp.getLampId());
        updatedValues.getLightSetting().setIntensity((mLamp.getLightSetting().getIntensity()));

        mUseCaseHandler.execute(mUpdateLamp, new UpdateLamp.RequestValues(updatedValues), new UseCase.UseCaseCallback<UpdateLamp.ResponseValue>() {
            @Override
            public void onSuccess(UpdateLamp.ResponseValue response) {
                mLampDetailsView.showIntensityChanged(intensity);

            }

            @Override
            public void onError() {
                mLampDetailsView.showSettingsUpdateError();
            }
        });
    }

    @Override
    public void changeMode(final short mode) {
        mLamp.getLightSetting().setMode(mode);
        Lamp updatedValues = new Lamp();
        updatedValues.setLampId(mLamp.getLampId());
        updatedValues.getLightSetting().setMode((mLamp.getLightSetting().getMode()));

        mUseCaseHandler.execute(mUpdateLamp, new UpdateLamp.RequestValues(updatedValues), new UseCase.UseCaseCallback<UpdateLamp.ResponseValue>() {
            @Override
            public void onSuccess(UpdateLamp.ResponseValue response) {
                mLampDetailsView.showModeChanged(mode);

            }

            @Override
            public void onError() {
                mLampDetailsView.showSettingsUpdateError();
            }
        });
    }


    @Override
    public void changeGroup(@NonNull final String groupId) {
        mLamp.setGroupId(groupId);

        Lamp updatedValues = new Lamp();
        updatedValues.setLampId(mLamp.getLampId());
        updatedValues.setGroupId(mLamp.getGroupId());

        mUseCaseHandler.execute(mUpdateLamp, new UpdateLamp.RequestValues(updatedValues), new UseCase.UseCaseCallback<UpdateLamp.ResponseValue>() {
            @Override
            public void onSuccess(UpdateLamp.ResponseValue response) {
                mLampDetailsView.showGroupIdHasChanged(groupId);
            }

            @Override
            public void onError() {
                mLampDetailsView.showSettingsUpdateError();
            }
        });
    }

    @Override
    public void saveSettings() {
        mLampDetailsView.showSettingsSaved();
    }

    @Override
    public void changeRotationZ(@IntRange(from = 0, to = 360) final short z) {
        mLamp.getRotation().setZ(z);

        Lamp updatedValues = new Lamp();
        updatedValues.setLampId(mLamp.getLampId());
        updatedValues.getRotation().setZ(mLamp.getRotation().getZ());

        mUseCaseHandler.execute(mUpdateLamp, new UpdateLamp.RequestValues(updatedValues), new UseCase.UseCaseCallback<UpdateLamp.ResponseValue>() {
            @Override
            public void onSuccess(UpdateLamp.ResponseValue response) {
                mLampDetailsView.showRotationZChanged(z);
            }

            @Override
            public void onError() {
                mLampDetailsView.showSettingsUpdateError();
            }
        });
    }

    @Override
    public void changeRotationY(@IntRange(from = 0, to = 180) final short y) {
        mLamp.getRotation().setY((short) (y-90));

        Lamp updatedValues = new Lamp();
        updatedValues.setLampId(mLamp.getLampId());
        updatedValues.getRotation().setY(mLamp.getRotation().getY());

        mUseCaseHandler.execute(mUpdateLamp, new UpdateLamp.RequestValues(updatedValues), new UseCase.UseCaseCallback<UpdateLamp.ResponseValue>() {
            @Override
            public void onSuccess(UpdateLamp.ResponseValue response) {
                mLampDetailsView.showRotationYChanged((short) (y - 90));

            }

            @Override
            public void onError() {
                mLampDetailsView.showSettingsUpdateError();
            }
        });
    }

    @Override
    public void changeTracking(final boolean on) {
        mLamp.setTracking(on);

        Lamp updatedValues = new Lamp();
        updatedValues.setLampId(mLamp.getLampId());
        updatedValues.setTracking(mLamp.isTracking());

        mUseCaseHandler.execute(mUpdateLamp, new UpdateLamp.RequestValues(updatedValues), new UseCase.UseCaseCallback<UpdateLamp.ResponseValue>() {
            @Override
            public void onSuccess(UpdateLamp.ResponseValue response) {
                mLampDetailsView.showTrackingChanged(on);
            }

            @Override
            public void onError() {
                mLampDetailsView.showSettingsUpdateError();
            }
        });
    }

    @Override
    public void changeActive(final boolean on) {
        mLamp.setOn(on);

        Lamp updatedValues = new Lamp();
        updatedValues.setLampId(mLamp.getLampId());
        updatedValues.setOn(mLamp.isOn());

        mUseCaseHandler.execute(mUpdateLamp, new UpdateLamp.RequestValues(updatedValues), new UseCase.UseCaseCallback<UpdateLamp.ResponseValue>() {
            @Override
            public void onSuccess(UpdateLamp.ResponseValue response) {
                mLampDetailsView.showActiveChanged(on);
            }

            @Override
            public void onError() {
                mLampDetailsView.showSettingsUpdateError();
            }
        });
    }



    @Override
    public void start() {
        if (mLampId == null || mLampId.isEmpty()) {
            mLampDetailsView.showNoSuchLamp();
            return;
        }
        mLampDetailsView.showLoadingIndicator(true);
        mUseCaseHandler.execute(mGetLamp, new GetLamp.RequestValues(mLampId), new UseCase.UseCaseCallback<GetLamp.ResponseValue>() {

            @Override
            public void onSuccess(GetLamp.ResponseValue response) {
                Lamp lamp = response.getLamp();

                if (mLampDetailsView.isActive()) {
                    mLampDetailsView.showLoadingIndicator(false);
                    mLamp = lamp;
                    mLampDetailsView.showSettings(mLamp);
                }
            }

            @Override
            public void onError() {
                mLampDetailsView.showLampLoadingError();
            }
        });

    }
}
