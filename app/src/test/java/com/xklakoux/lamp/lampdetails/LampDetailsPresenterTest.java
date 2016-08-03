package com.xklakoux.lamp.lampdetails;

import android.support.annotation.NonNull;

import com.xklakoux.lamp.TestUseCaseScheduler;
import com.xklakoux.lamp.UseCaseHandler;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.data.source.Id;
import com.xklakoux.lamp.data.source.LampsRepository;
import com.xklakoux.lamp.lampdetails.domain.usecase.GetLamp;
import com.xklakoux.lamp.lampdetails.domain.usecase.UpdateLamp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by artur on 27/05/16.
 */
public class LampDetailsPresenterTest {

    LampDetailsPresenter mPresenter;

    @Mock
    private LampDetailsFragment mView;

    @Mock
    private LampsRepository mRepository;

    @Captor
    private ArgumentCaptor<LampsRepository.IdCallback> mIdCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<LampsRepository.GetLampCallback> mGetLampCallbackArgumentCaptor;
    private Lamp LAMP;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        LAMP = new Lamp("0", "Lampa pierwsza", true, "0", true, "#FF0000", (short) 100, (short) 0, (short) -90, (short) 270, null);
        when(mView.isActive()).thenReturn(true);
        mPresenter = givenPresenter("0");
    }

    @NonNull
    private LampDetailsPresenter givenPresenter(String id) {
        return new LampDetailsPresenter(mView, id, new UpdateLamp(mRepository), new GetLamp(mRepository), new UseCaseHandler(new TestUseCaseScheduler()));

    }

    @Test
    public void testOpenOtherSettingsUI() throws Exception {
        start();
        mPresenter.openOtherSettingsUI();
        verify(mView).showOtherSettingsUI(any(String.class));
    }

    @Test
    public void testChangeName() throws Exception {
        start();
        mPresenter.changeName(LAMP.getName());
        callUpdateSuccess();
        verify(mView).showNameChanged(any(String.class));
    }

    @Test
    public void testChangeNameFailed() throws Exception {

        start();
        mPresenter.changeName(LAMP.getName());
        callUpdateFailureShowUpdateError();
    }

    @Test
    public void testChangeColor() throws Exception {
        start();
        mPresenter.changeColor(LAMP.getLightSetting().getColor());
        callUpdateSuccess();
        verify(mView).showColorChanged(any(String.class));
    }

    @Test
    public void testChangeColorFailed() throws Exception {
        start();
        mPresenter.changeColor(LAMP.getLightSetting().getColor());
        callUpdateFailureShowUpdateError();
    }

    @Test
    public void testChangeIntensity() throws Exception {
        start();
        mPresenter.changeIntensity(LAMP.getLightSetting().getIntensity());
        callUpdateSuccess();
        verify(mView).showIntensityChanged(any(Short.class));
    }

    private void callUpdateSuccess() {
        verify(mRepository).updateLamp(any(Lamp.class),mIdCallbackArgumentCaptor.capture());
        mIdCallbackArgumentCaptor.getValue().onSuccess(new Id("0"));
    }

    private void callUpdateFailureShowUpdateError() {
        verify(mRepository).updateLamp(any(Lamp.class),mIdCallbackArgumentCaptor.capture());
        mIdCallbackArgumentCaptor.getValue().onFailure();
        verify(mView).showSettingsUpdateError();

    }

    @Test
    public void testChangeMode() throws Exception {
        start();
        mPresenter.changeColor(LAMP.getLightSetting().getColor());
        callUpdateFailureShowUpdateError();
    }


    @Test
    public void testChangeGroup() throws Exception {
        start();
        mPresenter.changeGroup(LAMP.getGroupId());
        callUpdateSuccess();
        verify(mView).showGroupIdHasChanged(any(String.class));

    }
    @Test
    public void testChangeGroupFailed() throws Exception {
        start();
        mPresenter.changeGroup(LAMP.getGroupId());
        callUpdateFailureShowUpdateError();
    }

    @Test
    public void testSaveSettings() throws Exception {

    }

    @Test
    public void testSaveSettingsFailed() throws Exception {

    }

    @Test
    public void testChangeRotationZ() throws Exception {
        start();
        mPresenter.changeRotationZ(LAMP.getRotation().getZ());
        callUpdateSuccess();
        verify(mView).showRotationZChanged(any(Short.class));
    }


    @Test
    public void testChangeRotationZFailed() throws Exception {
        start();
        mPresenter.changeRotationZ(LAMP.getRotation().getZ());
        callUpdateFailureShowUpdateError();
    }

    @Test
    public void testChangeRotationY() throws Exception {
        start();
        mPresenter.changeRotationY(LAMP.getRotation().getY());
        callUpdateSuccess();
        verify(mView).showRotationYChanged(any(Short.class));
    }


    @Test
    public void testChangeRotationYFailed() throws Exception {
        start();
        mPresenter.changeRotationY(LAMP.getRotation().getY());
        callUpdateFailureShowUpdateError();
    }


    @Test
    public void testChangeTracking() throws Exception {
        start();
        mPresenter.changeTracking(LAMP.isTracking());
        callUpdateSuccess();
        verify(mView).showTrackingChanged(any(Boolean.class));
    }

    @Test
    public void testChangeTrackingFailed() throws Exception {
        start();
        mPresenter.changeTracking(LAMP.isTracking());
        callUpdateFailureShowUpdateError();
    }

    @Test
    public void testChangeActive() throws Exception {
        start();
        mPresenter.changeActive(LAMP.isOn());
        callUpdateSuccess();
        verify(mView).showActiveChanged(any(Boolean.class));
    }

    @Test
    public void testChangeActiveFailed() throws Exception {
        start();
        mPresenter.changeActive(LAMP.isOn());
        callUpdateFailureShowUpdateError();
    }

    @Test
    public void testStart() throws Exception{
        mPresenter.start();
        verify(mRepository).getLamp(any(String.class),mGetLampCallbackArgumentCaptor.capture());
        mGetLampCallbackArgumentCaptor.getValue().onLampLoaded(LAMP);
        verify(mView).showSettings(LAMP);
    }

    public void start() throws Exception{
        mPresenter.start();
        verify(mRepository).getLamp(any(String.class),mGetLampCallbackArgumentCaptor.capture());
        mGetLampCallbackArgumentCaptor.getValue().onLampLoaded(LAMP);
//        verify(mView).showSettings(LAMP);
    }

    @Test
    public void startLampNotFound() throws Exception{
        mPresenter.start();
        verify(mRepository).getLamp(any(String.class),mGetLampCallbackArgumentCaptor.capture());
        mGetLampCallbackArgumentCaptor.getValue().onDataNotAvailable();
        verify(mView).showLampLoadingError();
    }


    @Test
    public void testStartEmptyId() throws Exception{
        mPresenter = givenPresenter("");
        mPresenter.start();
        verify(mView).showNoSuchLamp();
    }

    @Test
    public void testStartNullId() throws Exception{
        mPresenter = givenPresenter(null);
        mPresenter.start();
        verify(mView).showNoSuchLamp();
    }

}