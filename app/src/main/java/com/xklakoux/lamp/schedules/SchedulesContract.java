package com.xklakoux.lamp.schedules;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.BasePresenter;
import com.xklakoux.lamp.BaseView;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.data.Schedule;

import java.util.List;

/**
 * Created by artur on 17/05/16.
 */
public interface SchedulesContract {

    interface View extends BaseView{

        void showScheduleUI(String scheduleId);

        void showLoadingIndicator(boolean active);

        void showSchedules(List<Lamp> lamps);

        void showScheduleDeactivated(String id);

        void showScheduleActivated(String id);

        void showLoadingSchedulesError();

        void showChangeActivatedError(String scheduleId);

        void showNoSchedules();

        boolean isActive();

    }
    interface Presenter extends BasePresenter{

        void openScheduleDetails(@NonNull Schedule schedule);

        void loadSchedules();

        void activateSchedule(@NonNull Schedule schedule);

        void deactivateSchedule(@NonNull Schedule schedule);

        void addSchedule();
    }
}
