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

    @POST("chat.postMessage")
    @FormUrlEncoded
    Call<SlackResponse> postMessage(@Field("token") String token, @Field("channel") String channelId,
                                     @Field("text") String text,
                                     // these are all optionals pass null if not needed
                                     @Field("username") String username,
                                     @Field("as_user") Boolean asUser, @Field("parse") String parseMode,
                                     @Field("link_names") Boolean linkNames, @Field("unfurl_links") Boolean unfurlLinks,
                                     @Field("unfurl_media") Boolean unfurlMedia, @Field("icon_url") String iconUrl,
                                     @Field("icon_emoji") String iconEmoji);

    @POST("rtm.start")
    @FormUrlEncoded
    Call<RtmStartSlackResponse> startRtm(@Field("token") String token,
                                 @Field("simple_latest") Boolean simpleLatest,
                                 @Field("no_unreads") Boolean noUnreads,
                                 @Field("mpim_aware") Boolean mpimAware);

}
