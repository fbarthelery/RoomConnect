package com.genymobile.android_slack;

import com.genymobile.android_slack.internal.ChannelsListSlackResponse;
import com.genymobile.android_slack.internal.RtmStartSlackResponse;
import com.genymobile.android_slack.internal.SlackResponse;
import com.genymobile.android_slack.internal.SlackService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.ws.WebSocket;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    // Token issued when creating a bot user in slack interface
    public static final String TOKEN = "xoxb-18190368645-ij2XqxuaOnXkeDtXKuG9tYpB";
    public static final String SLACK_API_BASE_URL = "https://slack.com/api/";
    private Retrofit retrofit;

    @Before
    public void setUp() throws Exception {
        retrofit = new Retrofit.Builder()
                .baseUrl(SLACK_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSimpleConnection() throws Exception {
        SlackService service = retrofit.create(SlackService.class);
        Call<SlackResponse> testCall = service.test(null);
        Response<SlackResponse> response = testCall.execute();
        System.out.println("Response is ok : " + response.body().ok);
        assertThat(response.body().ok).isTrue();
    }

    @Test
    public void testInvalidAuth() throws Exception {
        SlackService service = retrofit.create(SlackService.class);
        Call<SlackResponse> testCall = service.authTest("FAKETOKEN");
        Response<SlackResponse> response = testCall.execute();
        SlackResponse body = response.body();
        System.out.println("Response is ok : " + body.ok);
        assertThat(body.ok).isFalse();
        System.out.println("error is  " + body.error);
    }

    @Test
    public void testSimpleAuth() throws Exception {
        SlackService service = retrofit.create(SlackService.class);
        Call<SlackResponse> testCall = service.authTest(TOKEN);
        Response<SlackResponse> response = testCall.execute();
        SlackResponse body = response.body();
        System.out.println("Response is ok : " + body.ok);
        assertThat(body.ok).isTrue();
        System.out.println("error is  " + body.error);
    }

    @Test
    public void testListChannels() throws Exception {
        SlackService service = retrofit.create(SlackService.class);
        Call<ChannelsListSlackResponse> testCall = service.listChannels(TOKEN, 1);
        Response<ChannelsListSlackResponse> response = testCall.execute();
        ChannelsListSlackResponse body = response.body();
        System.out.println("Response is ok : " + body.ok);
        assertThat(body.ok).isTrue();
        System.out.println("error is  " + body.error);
        System.out.println("Available channels:  " + body.error);
        for (ChannelsListSlackResponse.Channel channel : body.channels) {
            System.out.println("name : " + channel.name);
        }
    }

    @Test
    public void testPostMessage() throws Exception {
        SlackService service = retrofit.create(SlackService.class);
        ChannelsListSlackResponse.Channel channel = findChannel(service, "test");
        Call<SlackResponse> postMessageCall = service.postMessage(TOKEN, channel.id, "Test of an API", "SLACK API ANDROID BOT",
                null, null, null, null, null, null, ":bug:");
        Response<SlackResponse> response = postMessageCall.execute();
        SlackResponse body = response.body();
        System.out.println("Response is ok : " + body.ok);
        assertThat(body.ok).isTrue();
        System.out.println("error is  " + body.error);
    }

    @Test
    public void testWebSocket() throws Exception {
        SlackService service = retrofit.create(SlackService.class);
        Call<RtmStartSlackResponse> rtmStartCall = service.startRtm(TOKEN, null, null, null);
        Response<RtmStartSlackResponse> response = rtmStartCall.execute();
        RtmStartSlackResponse body = response.body();
        System.out.println("Response is ok : " + body.ok);
        assertThat(body.ok).isTrue();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(body.url).build();
        WebSocketCall webSocketCall = WebSocketCall.create(okHttpClient, request);
        webSocketCall.enqueue(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                System.out.println("Web socket is open");
            }

            @Override
            public void onFailure(IOException e, okhttp3.Response response) {
                System.out.println("Web socket error " + e);
            }

            @Override
            public void onMessage(ResponseBody responseBody) throws IOException {
                System.out.println("new websocket message: " + responseBody.string());
                responseBody.close();
            }

            @Override
            public void onPong(Buffer buffer) {

            }

            @Override
            public void onClose(int i, String s) {
                System.out.println("Web socket is close");
            }
        });
        Thread.sleep(5000);
    }

    private ChannelsListSlackResponse.Channel findChannel(SlackService service, String name) throws IOException {
        Call<ChannelsListSlackResponse> testCall = service.listChannels(TOKEN, 1);
        Response<ChannelsListSlackResponse> response = testCall.execute();
        ChannelsListSlackResponse body = response.body();
        System.out.println("Response is ok : " + body.ok);
        for (ChannelsListSlackResponse.Channel channel : body.channels) {
            if (name.equals(channel.name)) {
                return channel;
            }
        }
        return null;
    }

}
