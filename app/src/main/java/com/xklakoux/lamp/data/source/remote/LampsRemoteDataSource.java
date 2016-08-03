package com.xklakoux.lamp.data.source.remote;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.data.source.Id;
import com.xklakoux.lamp.data.source.LampsDataSource;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by artur on 04/05/16.
 */
public class LampsRemoteDataSource implements LampsDataSource {

    private static LampsDataSource INSTANCE = null;

    private static final String ENDPOINT = "https://demo2426029.mockable.io/v1/mobile/";
    private final ApiService mService;

        private LampsRemoteDataSource(){
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(ENDPOINT).build();

            mService = retrofit.create(ApiService.class);


    }

    public static LampsDataSource getInstance(){
        if(INSTANCE==null){
            INSTANCE = new LampsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void deleteAllLamps() {

    }

    @Override
    public void deleteLamp(@NonNull String lampId) {

    }

    @Override
    public void getLamps(@NonNull final LoadLampsCallback callback) {
        Call<List<Lamp>> call = mService.getLamps();
        call.enqueue(new Callback<List<Lamp>>() {
            @Override
            public void onResponse(Call<List<Lamp>> call, Response<List<Lamp>> response) {
                if(response.isSuccessful()){
                    List<Lamp> lamps = response.body();
                    callback.onLampsLoaded(lamps);
                }else{
                    ResponseBody body = response.errorBody();
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<Lamp>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getLamp(@NonNull String lampId, @NonNull final GetLampCallback callback) {
        final Call<Lamp> call = mService.getLamp(lampId);
        call.enqueue(new Callback<Lamp>() {
            @Override
            public void onResponse(Call<Lamp> call, Response<Lamp> response) {
                if(response.isSuccessful()){
                    Lamp lamp = response.body();
                    callback.onLampLoaded(lamp);
                }else{
                    ResponseBody body = response.errorBody();
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<Lamp> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }


    @Override
    public void saveLamp(@NonNull Lamp lamp) {

    }

    @Override
    public void trackLamp(@NonNull Lamp lamp, @NonNull GenericCallback callback) {
        trackLamp(lamp.getLampId(), callback);

    }

    @Override
    public void trackLamp(@NonNull String lampId, @NonNull final GenericCallback callback) {
        Call<Id> call = mService.updateLamp(lampId,null,null,null,true,null,null);
        call.enqueue(new Callback<Id>() {
            @Override
            public void onResponse(Call<Id> call, Response<Id> response) {
                if(response.isSuccessful()){
                    callback.onSuccess();
                }else{
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<Id> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void dontTrackLamp(@NonNull Lamp lamp, @NonNull GenericCallback callback) {
        dontTrackLamp(lamp.getLampId(), callback);

    }

    @Override
    public void dontTrackLamp(@NonNull String lampId, @NonNull final GenericCallback callback) {
        Call<Id> call = mService.updateLamp(lampId,null,null,null,false,null,null);
        call.enqueue(new Callback<Id>() {
            @Override
            public void onResponse(Call<Id> call, Response<Id> response) {
                if(response.isSuccessful()){
                    callback.onSuccess();
                }else{
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<Id> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void updateLamp(@NonNull Lamp lamp, @NonNull final IdCallback callback) {

        Call<Id> call = mService.updateLamp(lamp.getLampId(), lamp.getName(), lamp.getLightSettingHashMap(), lamp.isOn(), lamp.isTracking(), lamp.getRotation().getZ(), lamp.getRotation().getY());
        call.enqueue(new Callback<Id>() {
            @Override
            public void onResponse(Call<Id> call, Response<Id> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else{
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<Id> call, Throwable t) {
                callback.onFailure();
            }
        });
    }


    @Override
    public void refreshLamps() {

    }
}
