package com.genymobile.android_slack;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import okio.Okio;
import okio.Source;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SlackClientTest {

    private SlackClient slackClient;
    private MockWebServer mockWebServer;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        slackClient = new SlackClient(mockWebServer.url("/api/").toString(), "token");
    }

    @Test(expected = SlackException.class)
    public void testThatIOErrorThrowsSlackException() throws Exception {
        mockWebServer.shutdown();
        slackClient.getChannels(true);
    }

    @Test
    public void testThatGetChannelsReturnsCorrectResult() throws Exception {
        MockResponse response = new MockResponse();
        Buffer body = new Buffer();
        Source bodySource = Okio.source(getClass().getResourceAsStream("/channels.list.correct-answers.json"));
        body.writeAll(bodySource);
        response.setBody(body);
        mockWebServer.enqueue(response);

        List<Channel> channels = slackClient.getChannels(true);

        assertThat(channels.size()).isEqualTo(1);
        Channel channel = channels.get(0);
        assertThat(channel.getId()).isEqualTo("C024BE91L");
        assertThat(channel.getName()).isEqualTo("fun");
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}
