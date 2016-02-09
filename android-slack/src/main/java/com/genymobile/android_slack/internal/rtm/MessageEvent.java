package com.genymobile.android_slack.internal.rtm;

import com.google.gson.annotations.SerializedName;

/**
 * A message event received on the RTM api.
 */
public class MessageEvent extends RtmEvent {

    @SerializedName("channel")
    public String channelId;

    @SerializedName("user")
    public String userId;

    public String text;

    @SerializedName("ts")
    public String timestamp;

    public String subtype;

}
