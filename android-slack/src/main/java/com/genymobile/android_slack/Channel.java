package com.genymobile.android_slack;

/**
 * A Slack channel.
 * TODO: to be complete when needed
 */
public class Channel {

    private String id;
    private String name;

    public Channel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
