package com.genymobile.android_slack.internal;

import com.genymobile.android_slack.SlackException;
import com.genymobile.android_slack.internal.rtm.HelloEvent;
import com.genymobile.android_slack.internal.rtm.MessageEvent;
import com.genymobile.android_slack.internal.rtm.RtmEvent;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class EventParserTest {

    private EventParser eventParser;

    @Before
    public void setUp() throws Exception {
        eventParser = new EventParser();
    }

    @Test
    public void testThatHelloEventIsParsed() throws Exception {
        InputStream jsonStream = getClass().getResourceAsStream("/events.hello.json");
        String json = readFully(jsonStream);
        HelloEvent helloEvent = (HelloEvent) eventParser.parseEvent(json);
        assertThat(helloEvent.type).isEqualTo(RtmEvent.Type.HELLO);
    }

    @Test
    public void testThatMessageEventIsParsed() throws Exception {
        InputStream jsonStream = getClass().getResourceAsStream("/events.message.json");
        String json = readFully(jsonStream);
        MessageEvent messageEvent = (MessageEvent) eventParser.parseEvent(json);
        assertThat(messageEvent.type).isEqualTo(RtmEvent.Type.MESSAGE);
        assertThat(messageEvent.channelId).isEqualTo("C2147483705");
        assertThat(messageEvent.userId).isEqualTo("U2147483697");
        assertThat(messageEvent.text).isEqualTo("Hello, world!");
        assertThat(messageEvent.timestamp).isEqualTo("1355517523.000005");
    }

    @Test(expected = SlackException.class)
    public void testThatExceptionIsThrownOnInvalidJson() throws Exception {
        String json = "{";
        eventParser.parseEvent(json);
    }

    @Test(expected = SlackException.class)
    public void testThatExceptionIsThrownOnUnknownEventType() throws Exception {
        InputStream jsonStream = getClass().getResourceAsStream("/events.unknown.json");
        String json = readFully(jsonStream);
        eventParser.parseEvent(json);
    }

    private static String readFully(InputStream inputStream) throws IOException {
        return readFully(new InputStreamReader(inputStream));
    }

    /**
     * Returns the remainder of 'reader' as a string, closing it when done.
     * Slightly improved version from Android libcore.io.Streams class
     */
    private static String readFully(Reader r) throws IOException {
        try (Reader reader = r;
             StringWriter writer = new StringWriter()) {
            char[] buffer = new char[1024];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, count);
            }
            return writer.toString();
        }
    }

}
