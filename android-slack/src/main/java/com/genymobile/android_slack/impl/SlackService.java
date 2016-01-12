package com.genymobile.android_slack.impl;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * Created by darisk on 11/01/16.
 */
public interface SlackService {
    @POST("api.test")
    @FormUrlEncoded
    Call<SlackResponse> test(@FieldMap Map<String, String> optionalParameters);

    @POST("auth.test")
    @FormUrlEncoded
    Call<SlackResponse> authTest(@Field("token") String token);

    @POST("channels.list")
    @FormUrlEncoded
    Call<ChannelsListSlackResponse> listChannels(@Field("token") String token, @Field("exclude_archived") int excludeArchived);



}
