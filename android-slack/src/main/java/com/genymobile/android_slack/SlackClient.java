package com.genymobile.android_slack;

import com.genymobile.android_slack.impl.ChannelsListSlackResponse;
import com.genymobile.android_slack.impl.SlackService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A client that allows you to interact with Slack.
 */
public class SlackClient {

    private static final String SLACK_API_BASE_URL = "https://slack.com/api/";

    private String token;
    private SlackService slackService;

    public SlackClient(String token) {
        this.token = token;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SLACK_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        slackService = retrofit.create(SlackService.class);
    }

    public List<Channel> getChannels(boolean excludeArchived) throws SlackException {
        int excludeArchivedInt = 0;
        if (excludeArchived) {
            excludeArchivedInt = 1;
        }
        Call<ChannelsListSlackResponse> channelsListSlackResponseCall = slackService.listChannels(token, excludeArchivedInt);
        ChannelsListSlackResponse response = callOrThrowException(channelsListSlackResponseCall);
        return createChannels(response);
    }

    private List<Channel> createChannels(ChannelsListSlackResponse response) {
        if (response.channels.size() <= 0) {
            return Collections.emptyList();
        }
        List<Channel> result = new ArrayList<>(response.channels.size());
        for (ChannelsListSlackResponse.Channel c : response.channels) {
            result.add(new Channel(c.id, c.name));
        }
        return result;
    }

    private <T> T callOrThrowException(Call<T> call) throws SlackException {
        Response<T> response = null;
        try {
            response = call.execute();
            if (response.isSuccess()) {
                return response.body();
            } else {
                throw new SlackException(
                        "Unsuccessfull slack request. \n"
                        + "error code: " + response.code() + " " + response.message() + "\n"
                        + "body: " + response.errorBody().string());
            }
        } catch (IOException e) {
            throw new SlackException("Unable to make slack request", e);
        }
    }

}
