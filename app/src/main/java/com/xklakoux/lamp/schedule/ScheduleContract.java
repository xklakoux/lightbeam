package com.xklakoux.lamp.schedule;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.BasePresenter;
import com.xklakoux.lamp.BaseView;
import com.xklakoux.lamp.data.Schedule;

/**
 * Created by artur on 16/05/16.
 */
public interface ScheduleContract {

    interface View extends BaseView{

        void showSettingsNotChangedError();

        void showNoScheduleFound();

        void showSettingsChanged();

        void showTimePicker();

        void showSchedule(@NonNull Schedule schedule);

        boolean isActive();
    }
    interface Presenter extends BasePresenter{

        void saveSettings();
    }
}
