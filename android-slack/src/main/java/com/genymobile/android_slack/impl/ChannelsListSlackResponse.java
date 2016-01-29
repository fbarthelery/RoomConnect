package com.genymobile.android_slack.impl;

import java.util.List;

/**
 * Slack response to the channels.list Web api call.
 */
public class ChannelsListSlackResponse extends SlackResponse {

    public List<Channel> channels;

    public class Channel {
        public String id;
        public String name;
    }
}
