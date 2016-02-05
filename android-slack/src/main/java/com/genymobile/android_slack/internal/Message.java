package com.genymobile.android_slack.internal;

import com.google.gson.annotations.SerializedName;

/**
 * Represent a Message event from Slack
 */
public class Message {
    private String type;

    @SerializedName("channel")
    private String channelId;

    @SerializedName("user")
    private String userId;

    private String text;

    private String timestamp;

    private String subtype;

    private boolean hidden;

    public String getType() {
        return type;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSubtype() {
        return subtype;
    }

    public boolean isHidden() {
        return hidden;
    }
}
