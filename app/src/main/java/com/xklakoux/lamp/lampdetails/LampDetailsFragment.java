package com.xklakoux.lamp.lampdetails;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.xklakoux.lamp.R;
import com.xklakoux.lamp.data.Lamp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class LampDetailsFragment extends Fragment implements LampDetailsContract.View, CompoundButton.OnCheckedChangeListener {

    public static final String ARGUMENT_LAMP_ID = "LAMP_ID";
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.sTracking)
    Switch mSwitchTracking;
    @BindView(R.id.sOn)
    Switch mSwitchOn;
    @BindView(R.id.llColor)
    LinearLayout mLayoutColor;
    @BindView(R.id.spinnerMode)
    Spinner mSpinnerMode;
    @BindView(R.id.sbIntensity)
    SeekBar mSeekbarIntensity;
    @BindView(R.id.sbRotationZ)
    SeekBar mSeekbarRotZ;
    @BindView(R.id.sbRotationY)
    SeekBar mSeekbarRotY;
    @BindView(R.id.spinnerGroup)
    Spinner mSpinnerGroup;
    @BindView(R.id.llNoSuchLamp)
    LinearLayout mNoSuchLamp;
    @BindView(R.id.svSettings)
    ScrollView mSettings;
    @BindView(R.id.tvIntensity)
    TextView mTvIntensity;
    @BindView(R.id.tvRotZ)
    TextView mTvRotZ;
    @BindView(R.id.tvRotY)
    TextView mTvRotY;
    private ColorPicker mColorPicker;
    private LampDetailsContract.Presenter mPresenter;


    public LampDetailsFragment() {
    }

    public static LampDetailsFragment newInstance(String lampId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_LAMP_ID, lampId);
        LampDetailsFragment fragment = new LampDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lamps_details_frag, container, false);
        ButterKnife.bind(this, v);
        setSeekBarChangeListeners();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lamp_details, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            mPresenter.saveSettings();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSeekBarChangeListeners() {
        mSeekbarIntensity.setOnSeekBarChangeListener(new OnCheckChanged() {
            @Override
            void onProgressChanged(int progress) {
                mPresenter.changeIntensity((short) progress);
            }
        });
        mSeekbarRotY.setOnSeekBarChangeListener(new OnCheckChanged() {
            @Override
            void onProgressChanged(int progress) {
                mPresenter.changeRotationY((short) progress);
            }
        });
        mSeekbarRotZ.setOnSeekBarChangeListener(new OnCheckChanged() {
            @Override
            void onProgressChanged(int progress) {
                mPresenter.changeRotationZ((short) progress);
            }
        });
    }

    @Override
    public void showOtherSettingsUI(String lampId) {
        //
    }

    @Override
    public void showSettingsSaved() {
        showMessage(getString(R.string.settings_sent_to_lamp));

    }

    @Override
    public void showSaveSettingsDialog() {
        new MaterialDialog.Builder(getActivity())
                .title(getResources().getString(R.string.do_you_want_to_save))
                .positiveText(getResources().getString(R.string.yes))
                .negativeText(getResources().getString(R.string.no))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mPresenter.saveSettings();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                })
                .show();
    }

    @Override
    public void showSettingsUpdateError() {
        showMessage(getString(R.string.settings_save_error));

    }

    @Override
    public void showLampLoadingError() {
        showMessage(getString(R.string.error_loading_lamp));
    }

    @Override
    public void showChangeNameDialog(String name) {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.set_name)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(getResources().getString(R.string.new_name), name, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        mPresenter.changeName(input.toString());
                    }
                }).show();
    }

    @Override
    public void showChangeColorDialog(int color) {
        mColorPicker = new ColorPicker(getActivity(), Color.red(color), Color.green(color), Color.blue(color));
        mColorPicker.show();


    /* On Click listener for the dialog, when the user select the color */
        Button okColor = (Button) mColorPicker.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int intColor = mColorPicker.getColor();
                final String stringColor = String.format("#%06X", (0xFFFFFF & intColor));
                mPresenter.changeColor(stringColor);
                mColorPicker.dismiss();
            }
        });
    }

    @Override
    public void showNameChanged(String name) {

        mTvName.setText(name);
    }

    @Override
    public void showColorChanged(String color) {
        mLayoutColor.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void showTrackingChanged(boolean on) {
        mSwitchTracking.setEnabled(true);
    }

    @Override
    public void showActiveChanged(boolean on) {
        mSwitchOn.setEnabled(true);
    }

    @Override
    public void showTrackingChangedError() {
        mSwitchTracking.setEnabled(true);
        mSwitchTracking.setChecked(mSwitchTracking.isChecked());
        showMessage(getContext().getString(R.string.couldnt_change_tracking));
    }

    @Override
    public void showActiveChangedError() {
        mSwitchOn.setEnabled(true);
        mSwitchOn.setChecked(mSwitchOn.isChecked());
        showMessage(getContext().getString(R.string.couldnt_change_active_state));
    }

    @Override
    public void showIntensityChanged(short intensity) {
        final String text = "" + intensity;
        mTvIntensity.setText(text);

    }

    @Override
    public void showModeChanged(short mode) {

    }

    @Override
    public void showRotationZChanged(short rotationZ) {
        final String text = "" + rotationZ;
        mTvRotZ.setText(text);
    }

    @Override
    public void showRotationYChanged(short rotationY) {
        final String text = "" + rotationY;
        mTvRotY.setText(text);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showNoSuchLamp() {
        mSettings.setVisibility(View.GONE);
        mNoSuchLamp.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingIndicator(boolean show) {
//
    }

    @Override
    public void showSettings(Lamp lamp) {
        mTvName.setText(lamp.getName());
        mSwitchTracking.setChecked(lamp.isTracking());
        mSwitchOn.setChecked(lamp.isOn());
        mLayoutColor.setBackgroundColor(Color.parseColor(lamp.getLightSetting().getColor()));
        mSpinnerMode.setSelection(lamp.getLightSetting().getMode());
        mSeekbarIntensity.setProgress(lamp.getLightSetting().getIntensity());
        mSeekbarRotZ.setProgress(lamp.getRotation().getZ());
        mSeekbarRotY.setProgress(lamp.getRotation().getY() + 90);

        final String intensity = "" + lamp.getLightSetting().getIntensity();
        mTvIntensity.setText(intensity);

        final String rotY = "" + (lamp.getRotation().getY());
        mTvRotY.setText(rotY);

        final String rotZ = "" + lamp.getRotation().getZ();
        mTvRotZ.setText(rotZ);

        //mSpinnerGroup to be done

        mSwitchTracking.setOnCheckedChangeListener(this);
        mSwitchOn.setOnCheckedChangeListener(this);
    }

    @Override
    public void showGroupIdHasChanged(String groupId) {

    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(LampDetailsContract.Presenter presenter) {
        checkNotNull(presenter);
        mPresenter = presenter;
    }

    @OnClick({R.id.tvName, R.id.llColor, R.id.otherSettings})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvName:
                showChangeNameDialog(mTvName.getText().toString());
                break;
            case R.id.llColor:
                final int color = ((ColorDrawable) mLayoutColor.getBackground()).getColor();
                showChangeColorDialog(color);
                break;
            case R.id.otherSettings:
                mPresenter.openOtherSettingsUI();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean on) {
        if(buttonView.getId()==R.id.sTracking){
            mPresenter.changeTracking(on);
            mSwitchTracking.setEnabled(false);
        }else if(buttonView.getId()==R.id.sOn){
            mPresenter.changeActive(on);
            mSwitchOn.setEnabled(false);
        }
    }

    enum Mode {FLASH, STROBE, FADE, SMOOTH}

    private abstract class OnCheckChanged implements SeekBar.OnSeekBarChangeListener {

        abstract void onProgressChanged(int progress);

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // nothing
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //nothing
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            onProgressChanged(seekBar.getProgress());
        }
    }

}
