package com.genymobile.roomconnect.roomengine;

import java.util.List;

public interface ServiceModule {

    void connect(ConnectionListener listener);

    List<Room> listAvailableRooms();

    void joinRoom(Room room, JoinRoomListener listener);

    void registerMessageListener(MessageListener listener);

    void sendMessage(String message);

    String getNotificationStringForUser(String username);

}
