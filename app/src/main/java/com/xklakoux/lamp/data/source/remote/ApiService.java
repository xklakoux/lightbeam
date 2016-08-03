package com.xklakoux.lamp.data.source.remote;

import com.xklakoux.lamp.data.Lamp;
import com.xklakoux.lamp.data.source.Id;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by artur on 05/05/16.
 */
public interface ApiService {

    //lamps

    @GET("lamps")
    Call<List<Lamp>> getLamps();

    @GET("lamps/{id}")
    Call<Lamp> getLamp(@Path("id") String lampId);

    @FormUrlEncoded
    @PUT("lamps/{id}")
    Call<Id> updateLamp(@Path("id") String lampId, @Field("name") String name, @FieldMap Map<String, String> lightSettings, @Field("on") Boolean on, @Field("tracking") Boolean tracking, @Field("z") Short z, @Field("y") Short y);

    //events

    @FormUrlEncoded
    @POST("lamps/{id}/event")
    Call<Void> event(@Path("id") String lampId, @Field("length") int length, @FieldMap HashMap<String, String> lightSettings);

    //schedules

    @FormUrlEncoded
    @GET("lamps/{id}/schedules")
    Call<Void> getSchedules(@Path("id") String lampId);

    @FormUrlEncoded
    @POST("lamps/{id}/schedules")
    Call<Void> addSchedule(@Path("id") String lampId, @FieldMap HashMap<String, String> lightSettings);

    @FormUrlEncoded
    @PUT("lamps/{id}/schedules/{scheduleId}")
    Call<Void> updateSchedule(@Path("id") String lampId, @Path("scheduleId") String scheduleId, @FieldMap HashMap<String, String> lightSettings, @Field("on") Boolean on);

    @FormUrlEncoded
    @DELETE("lamps/{id}/schedules/{scheduleId}")
    Call<Void> removeSchedule(@Path("id") String lampId, @Path("scheduleId") String scheduleId);

    //groups

//    @POST("groups/{id}/donttrack")
//    Call<Void> donttrackGroup(@Path("id") String groupId);
//
//    @POST("groups/{id}/track")
//    Call<Void> trackGroup(@Path("id") String groupId);
//
//    @GET("groups")
//    Call<List<Group>> getGroups();
//
//    @GET("groups/{id}")
//    Call<Group> getGroup(@Path("id") String groupId);
//
//    @FormUrlEncoded
//    @POST("groups/{id}/settings")
//    Call<Void> setGroupSettings(@Path("id") String groupId, @Field("name") String name, @FieldMap HashMap<String, String> lightSettings, @Field("on") boolean on);

}
