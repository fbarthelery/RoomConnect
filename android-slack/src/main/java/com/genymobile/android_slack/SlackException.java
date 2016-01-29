package com.genymobile.android_slack;

/**
 * An exception that occurs when interacting with Slack.
 */
public class SlackException extends Exception {

    public SlackException() {
    }

    public SlackException(String detailMessage) {
        super(detailMessage);
    }

    public SlackException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SlackException(Throwable throwable) {
        super(throwable);
    }
}
