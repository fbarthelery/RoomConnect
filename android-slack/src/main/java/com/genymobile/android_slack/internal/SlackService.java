package com.genymobile.android_slack.internal;

import android.support.annotation.Nullable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * Endpoints to Slack Web API.
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
                                     @Nullable @Field("username") String username,
                                     @Nullable @Field("as_user") Boolean asUser, @Nullable @Field("parse") String parseMode,
                                     @Nullable @Field("link_names") Boolean linkNames, @Nullable @Field("unfurl_links") Boolean unfurlLinks,
                                     @Nullable @Field("unfurl_media") Boolean unfurlMedia, @Nullable @Field("icon_url") String iconUrl,
                                     @Nullable @Field("icon_emoji") String iconEmoji);

    @POST("rtm.start")
    @FormUrlEncoded
    Call<RtmStartSlackResponse> startRtm(@Field("token") String token,
                                         @Nullable @Field("simple_latest") Boolean simpleLatest,
                                         @Nullable @Field("no_unreads") Boolean noUnreads,
                                         @Nullable @Field("mpim_aware") Boolean mpimAware);

}
