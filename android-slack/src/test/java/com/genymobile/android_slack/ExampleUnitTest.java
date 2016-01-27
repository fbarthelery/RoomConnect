package com.genymobile.android_slack;

import com.genymobile.android_slack.impl.ChannelsListSlackResponse;
import com.genymobile.android_slack.impl.SlackResponse;
import com.genymobile.android_slack.impl.SlackService;

import org.junit.Test;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

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

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSimpleConnection() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SLACK_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SlackService service = retrofit.create(SlackService.class);
        Call<SlackResponse> testCall = service.test(null);
        Response<SlackResponse> response = testCall.execute();
        System.out.println("Response is ok : " + response.body().ok);
        assertThat(response.body().ok).isTrue();
    }


    @Test
    public void testInvalidAuth() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SLACK_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SLACK_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SLACK_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SLACK_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
