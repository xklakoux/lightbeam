package com.xklakoux.lamp.schedules;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xklakoux.lamp.R;
import com.xklakoux.lamp.data.Lamp;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SchedulesFragment extends Fragment implements SchedulesContract.View {

    public SchedulesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedules, container, false);
    }

    @Override
    public void showScheduleUI(String scheduleId) {

    }

    @Override
    public void showLoadingIndicator(boolean active) {

    }

    @Override
    public void showSchedules(List<Lamp> lamps) {

    }

    @Override
    public void showScheduleDeactivated(String id) {

    }

    @Override
    public void showScheduleActivated(String id) {

    }

    @Override
    public void showLoadingSchedulesError() {

    }

    @Override
    public void showChangeActivatedError(String scheduleId) {

    }

    @Override
    public void showNoSchedules() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
