package com.genymobile.roomconnect.roomengine;

import java.util.List;

public interface ServiceModule {

    void connect(ConnectionListener listener);

    List<Channel> listAvailableChannels();

    void joinChannel(Channel channel, ChannelJoinListener listener);

    void registerMessageListener(MessageListener listener);

    void sendMessage(String message);

    String getNotificationStringForUser(String username);

}
