package com.xklakoux.lamp.lamps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.xklakoux.lamp.R;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.lampdetails.LampDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by artur on 04/05/16.
 */
public class LampsFragment extends Fragment implements LampsContract.View {

    LampsContract.Presenter mPresenter;
    private LampsAdapter mListAdapter;
    private LinearLayout mEmptyList;
    private ListView mLampsList;

    public LampsFragment() {
    }

    public static LampsFragment newInstance() {
        return new LampsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new LampsAdapter(new ArrayList<Lamp>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.lamps_frag, container, false);

        mLampsList = (ListView) root.findViewById(R.id.lvLampsList);
        mLampsList.setAdapter(mListAdapter);

        mEmptyList = (LinearLayout) root.findViewById(R.id.llListEmpty);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(mLampsList);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadLamps(false);
            }
        });

        return root;
    }

    @Override
    public void showLoadingIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showLamps(List<Lamp> lamps) {
        mListAdapter.replaceData(lamps);
        mLampsList.setVisibility(View.VISIBLE);
        mEmptyList.setVisibility(View.GONE);
    }

    @Override
    public void showLampDetailsUi(String lampId) {
        Intent intent = new Intent(getContext(), LampDetailsActivity.class);
        intent.putExtra(LampDetailsActivity.EXTRA_LAMP_ID,lampId);
        startActivity(intent);
    }

    @Override
    public void showLampTurnedOff(String id) {
        enableSwitch(id);
        showMessage(getString(R.string.lamp_turned_off));
    }

    @Override
    public void showLampTurnedOn(String id) {
        enableSwitch(id);
        showMessage(getString(R.string.lamp_turned_on));
    }

    private void enableSwitch(String id) {
        for (int i = 0; i < mListAdapter.getCount(); i++) {
            Lamp lamp = mListAdapter.getItem(i);
            if(lamp.getLampId().equals(id)){
                View item = mLampsList.getChildAt(i);
                Switch active = (Switch) item.findViewById(R.id.swActive);
                active.setEnabled(true);
            }

        }
    }

    @Override
    public void showLoadingLampsError() {
        showMessage(getString(R.string.error_while_fetching_lamps));
    }

    @Override
    public void showChangeTurnOnOffStateError(String id) {
        enableSwitch(id);
        showMessage(getString(R.string.error_while_changing_lamp_state));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNoLamps() {
        mLampsList.setVisibility(View.GONE);
        mEmptyList.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(@NonNull LampsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    private class LampsAdapter extends BaseAdapter {

        private final LampItemListener mListener;
        private List<Lamp> mLamps;

        public LampsAdapter(List<Lamp> lamps, LampItemListener listener) {
            setLamps(lamps);
            this.mListener = listener;
        }

        public void replaceData(List<Lamp> lamps) {
            setLamps(lamps);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mLamps.size();
        }

        @Override
        public Lamp getItem(int position) {
            return mLamps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return Long.parseLong(mLamps.get(position).getLampId());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if(rowView==null){
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                rowView = inflater.inflate(R.layout.lamp_item,parent, false);
            }
            final Lamp lamp = getItem(position);
            final TextView name = (TextView) rowView.findViewById(R.id.tvName);
            name.setText(lamp.getName());

            final Switch activate = (Switch) rowView.findViewById(R.id.swActive);
            activate.setChecked(lamp.isTracking());

            activate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    activate.setEnabled(false);
                    if (isChecked) {
                        mListener.onActivateLampClick(lamp);
                    }else{
                        mListener.onDeactivateLampClick(lamp);
                    }
                }
            });

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onLampClick(lamp);
                }
            });
            return rowView;

        }

        public void setLamps(List<Lamp> mLamps) {
            this.mLamps = mLamps;
        }
    }

    /**
     * Listener for clicks on lamps in the ListView.
     */
    LampItemListener mItemListener = new LampItemListener() {
        @Override
        public void onLampClick(Lamp clickedLamp) {
            mPresenter.openLampDetails(clickedLamp);
        }

        @Override
        public void onDeactivateLampClick(Lamp deactivatedLamp) {

            mPresenter.turnOffLamp(deactivatedLamp);

        }

        @Override
        public void onActivateLampClick(Lamp activatedLamp) {
            mPresenter.turnOnLamp(activatedLamp);
        }
    };


    public interface LampItemListener {

        void onLampClick(Lamp clickedLamp);

        void onDeactivateLampClick(Lamp deactivatedLamp);

        void onActivateLampClick(Lamp activatedLamp);
    }

}
