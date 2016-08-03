package com.xklakoux.lamp.schedule;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xklakoux.lamp.R;
import com.xklakoux.lamp.data.Schedule;

/**
 * A placeholder fragment containing a simple view.
 */
public class ScheduleFragment extends Fragment implements ScheduleContract.View {

    public ScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lamp_schedule, container, false);
    }

    @Override
    public void showSettingsNotChangedError() {

    }

    @Override
    public void showNoScheduleFound() {

    }

    @Override
    public void showSettingsChanged() {

    }

    @Override
    public void showTimePicker() {

    }

    @Override
    public void showSchedule(@NonNull Schedule schedule) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
