package com.genymobile.android_slack.impl;

import java.util.List;

/**
 * Created by darisk on 12/01/16.
 */
public class ChannelsListSlackResponse extends SlackResponse {

    public List<Channel> channels;

    public class Channel {
        public String id;
        public String name;
    }
}
