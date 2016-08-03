package com.xklakoux.lamp.lampdetails;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.xklakoux.lamp.Injection;
import com.xklakoux.lamp.R;
import com.xklakoux.lamp.util.ActivityUtils;
import com.xklakoux.lamp.util.EspressoIdlingResource;

public class LampDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_LAMP_ID = "extra_lamp_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_lamp_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the requested task id
        String lampId = getIntent().getStringExtra(EXTRA_LAMP_ID);

        LampDetailsFragment lampDetailFragment = (LampDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (lampDetailFragment == null) {
            lampDetailFragment = LampDetailsFragment.newInstance(lampId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    lampDetailFragment, R.id.contentFrame);
        }

        new LampDetailsPresenter(lampDetailFragment,lampId, Injection.provideUpdateLamp(),Injection.provideGetLamp(),Injection.provideUseCaseHandler());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
