package com.xklakoux.lamp.lamps;

import com.google.common.collect.Lists;
import com.xklakoux.lamp.TestUseCaseScheduler;
import com.xklakoux.lamp.UseCaseHandler;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.data.LampBuilder;
import com.xklakoux.lamp.data.source.LampsDataSource;
import com.xklakoux.lamp.data.source.LampsRepository;
import com.xklakoux.lamp.lamps.domain.usecase.DontTrackLamp;
import com.xklakoux.lamp.lamps.domain.usecase.GetLamps;
import com.xklakoux.lamp.lamps.domain.usecase.TrackLamp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by artur on 27/05/16.
 */
public class LampsPresenterTest {

    private static List<Lamp> LAMPS;

    @Mock
    private LampsRepository mLampsRepository;

    @Mock
    private LampsContract.View mLampsView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<LampsDataSource.LoadLampsCallback> mLoadLampsCallbackCaptor;

    @Captor
    private ArgumentCaptor<LampsDataSource.GenericCallback> mGenericCallbackArgumentCaptor;


    private LampsPresenter mLampsPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mLampsPresenter = givenLampsPresenter();

        when(mLampsView.isActive()).thenReturn(true);

        LAMPS = Lists.newArrayList(
                new Lamp("0", "Lampa pierwsza", true, "0", true, "#FF0000", (short) 100, (short) 0, (short) -90, (short) 270, null),
                new Lamp("1", "Lampa druga", false, "0", true, "#00FF00", (short) 90, (short) 0, (short) 90, (short) 0, null),
                new Lamp("2", "Lampa trzecia", true, "0", false, "#0000FF", (short) 40, (short) 0, (short) 20, (short) 270, null),
                new Lamp("3", "Lampa czwarta", false, "0", false, "#FFFFFF", (short) 10, (short) 0, (short) 10, (short) 20, null)
        );
    }

    private LampsPresenter givenLampsPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        GetLamps getLamps = new GetLamps(mLampsRepository);
        TrackLamp trackLamp = new TrackLamp(mLampsRepository);
        DontTrackLamp dontTrackLamp = new DontTrackLamp(mLampsRepository);

        return new LampsPresenter(useCaseHandler,mLampsView, getLamps, dontTrackLamp, trackLamp);
    }

    @Test
    public void testLoadLamps() throws Exception {

        //when loading of lamps is requested
        mLampsPresenter.loadLamps(true);

        //callback is captured and invoked with stubbed lamps

        verify(mLampsRepository).getLamps(mLoadLampsCallbackCaptor.capture());
        mLoadLampsCallbackCaptor.getValue().onLampsLoaded(LAMPS);
        //loading indicator is shown
        verify(mLampsView).showLoadingIndicator(true);
        //loading indicator is hidden and all tasks are shown in UI
        verify(mLampsView).showLoadingIndicator(false);
        ArgumentCaptor<List> showLampsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mLampsView).showLamps(showLampsArgumentCaptor.capture());
        assertTrue(showLampsArgumentCaptor.getValue().size() == 4);
    }

    @Test
    public void testEmptyLampList() throws Exception {

        //when loading of lamps is requested
        mLampsPresenter.loadLamps(true);

        //callback is captured and invoked with stubbed lamps
        verify(mLampsRepository).getLamps(mLoadLampsCallbackCaptor.capture());
        mLoadLampsCallbackCaptor.getValue().onLampsLoaded(new ArrayList<Lamp>());

        //loading indicator is shown
        verify(mLampsView).showLoadingIndicator(true);
        //loading indicator is hidden and all tasks are shown in UI
        verify(mLampsView).showLoadingIndicator(false);

        verify(mLampsView).showNoLamps();
    }

    @Test
    public void testLoadLampsError() throws Exception {

        //when loading of lamps is requested
        mLampsPresenter.loadLamps(true);

        //callback is captured and invoked with stubbed lamps

        verify(mLampsRepository).getLamps(mLoadLampsCallbackCaptor.capture());
        mLoadLampsCallbackCaptor.getValue().onDataNotAvailable();
        //loading indicator is shown
        verify(mLampsView).showLoadingIndicator(true);
        //loading indicator is hidden and all tasks are shown in UI
        verify(mLampsView).showLoadingLampsError();
        verify(mLampsView).showLoadingIndicator(false);
    }

    @Test
    public void testOpenLampDetails() throws Exception {
        //given any lamp
        final Lamp firstLamp = LAMPS.get(0);
        //when lamp is tapped
        mLampsPresenter.openLampDetails(firstLamp);
        //show lamp details UI
        verify(mLampsView).showLampDetailsUi(firstLamp.getLampId());

    }

    @Test
    public void testTrackLamp() throws Exception {
        //given any lamp
        final Lamp nottrackedlamp = LAMPS.get(0);
        nottrackedlamp.setTracking(true);
        //when lamp switched to not track
        mLampsPresenter.trackLamp(nottrackedlamp);

        verify(mLampsRepository).trackLamp(any(String.class),mGenericCallbackArgumentCaptor.capture());
        mGenericCallbackArgumentCaptor.getValue().onSuccess();

        //show lamp details UI
        verify(mLampsView).showLampTracked(any(String.class));

    }

    @Test
    public void testDontTrackLamp() throws Exception {
        //given any lamp
        final Lamp trackedLamp = LAMPS.get(0);
        trackedLamp.setTracking(true);
        //when lamp switched to track


        mLampsPresenter.dontTrackLamp(trackedLamp);
        verify(mLampsRepository).dontTrackLamp(any(String.class),mGenericCallbackArgumentCaptor.capture());
        mGenericCallbackArgumentCaptor.getValue().onSuccess();
        //show lamp details UI

        verify(mLampsView).showLampNotTracked(any(String.class));

    }

    @Test
    public void testTrackLampError() throws Exception {
        //given any lamp
        final Lamp nottrackedlamp = new LampBuilder().setLampId("0").setTracking(false).createLamp();

        //when lamp switched to not track
        mLampsPresenter.trackLamp(nottrackedlamp);
        verify(mLampsRepository).trackLamp(any(String.class),mGenericCallbackArgumentCaptor.capture());
        mGenericCallbackArgumentCaptor.getValue().onFailure();

        //show lamp details UI
        verify(mLampsView).showChangeTrackingStateError(any(String.class));

    }

    @Test
    public void testDontTrackLampError() throws Exception {
        //given any lamp
        final Lamp trackedLamp = new LampBuilder().setLampId("0").setTracking(true).createLamp();

        //when lamp switched to track
        mLampsPresenter.dontTrackLamp(trackedLamp);
        verify(mLampsRepository).dontTrackLamp(any(String.class),mGenericCallbackArgumentCaptor.capture());
        mGenericCallbackArgumentCaptor.getValue().onFailure();

        //show lamp details UI
        verify(mLampsView).showChangeTrackingStateError(any(String.class));

    }
}