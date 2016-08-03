package com.xklakoux.lamp.lampothers;

import com.xklakoux.lamp.BasePresenter;
import com.xklakoux.lamp.BaseView;
import com.xklakoux.lamp.data.Lamp;

/**
 * Created by artur on 17/05/16.
 */
public interface LampOthersContract {
    interface View extends BaseView<LampOthersContract.Presenter>{

        void showSchedulesUI(String lampId);


        void showSettings(Lamp lamp);

        void showNoSuchLamp();

        boolean isActive();
    }

    interface Presenter extends BasePresenter{

        void openSchedules();

    }
}
