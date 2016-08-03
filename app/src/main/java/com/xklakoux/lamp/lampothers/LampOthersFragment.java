package com.xklakoux.lamp.lampothers;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xklakoux.lamp.R;
import com.xklakoux.lamp.data.Lamp;

/**
 * A placeholder fragment containing a simple view.
 */
public class LampOthersFragment extends Fragment implements LampOthersContract.View{

    public LampOthersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lamp_others, container, false);
    }

    @Override
    public void showSchedulesUI(String lampId) {

    }

    @Override
    public void showSettings(Lamp lamp) {

    }

    @Override
    public void showNoSuchLamp() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(LampOthersContract.Presenter presenter) {

    }
}
