package com.genymobile.android_slack.internal;

import com.genymobile.android_slack.SlackException;
import com.genymobile.android_slack.internal.rtm.RtmEvent;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Parse event received by the Slack realtime messaging API.
 */
public class EventParser {

    private final Gson gson;

    public EventParser() {
        gson = new Gson();
    }

    public RtmEvent parseEvent(String jsonEvent) throws SlackException {
        try {
            RtmEvent baseEvent = gson.fromJson(jsonEvent, RtmEvent.class);
            return specializeEvent(baseEvent, jsonEvent);
        } catch (JsonSyntaxException e) {
            throw new SlackException("Error when parsing slack event", e);
        }
    }

    private RtmEvent specializeEvent(RtmEvent baseEvent, String jsonEvent) throws SlackException {
        if (baseEvent.type == null) {
            throw new SlackException("Unknown slack event type for : " + jsonEvent);
        }
        return gson.fromJson(jsonEvent, baseEvent.type.getEventClass());
    }
}
