package com.genymobile.android_slack.internal.rtm;

import com.google.gson.annotations.SerializedName;

/**
 * Base event received on the Rtm websocket
 */
public class RtmEvent {

    public Type type;

    public enum Type {
        @SerializedName("hello")
        HELLO(HelloEvent.class),

        @SerializedName("message")
        MESSAGE(MessageEvent.class),

        @SerializedName("presence_change")
        PRESENCE_CHANGE(PresenceChangeEvent.class),

        @SerializedName("reconnect_url")
        RECONNECT_URL(ReconnectUrlEvent.class);

        private Class<? extends RtmEvent> eventClass;

        Type(Class<? extends RtmEvent> eventClass) {
            this.eventClass = eventClass;
        }

        public Class<? extends RtmEvent> getEventClass() {
            return eventClass;
        }
    }

}
