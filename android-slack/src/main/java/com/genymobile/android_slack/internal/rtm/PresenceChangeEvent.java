package com.genymobile.android_slack.internal.rtm;

import com.google.gson.annotations.SerializedName;

/**
 * A presence_change event received on the RTM api.
 */
public class PresenceChangeEvent extends RtmEvent {
    @SerializedName("user")
    public String userId;

    public String presence;
}
