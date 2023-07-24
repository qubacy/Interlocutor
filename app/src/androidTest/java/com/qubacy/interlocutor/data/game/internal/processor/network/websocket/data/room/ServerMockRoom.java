package com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room;

import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state.ServerMockRoomSearchingState;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state.ServerMockRoomState;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.user.ServerMockUser;

import java.util.List;

public class ServerMockRoom {
    private final List<ServerMockUser> m_userList;
    private ServerMockRoomState m_state = null;
    private boolean m_isDestroyed = false;

    protected ServerMockRoom(
            final List<ServerMockUser> userList,
            final ServerMockRoomState initState)
    {
        m_userList = userList;
        m_state = initState;
    }

    public static ServerMockRoom getInstance(
            final List<ServerMockUser> userList)
    {
        if (userList == null) return null;

        ServerMockRoomSearchingState serverMockRoomSearchingState =
                ServerMockRoomSearchingState.getInstance(System.currentTimeMillis());

        if (serverMockRoomSearchingState == null) return null;

        return new ServerMockRoom(userList, serverMockRoomSearchingState);
    }

    public ServerMockUser getUserById(final int userId) {
        if (userId < 0) return null;

        for (final ServerMockUser user : m_userList)
            if (user.getProfile().getId() == userId)
                return user;

        return null;
    }

    public int getUserCount() {
        return m_userList.size();
    }

    public List<ServerMockUser> getUserList() {
        return m_userList;
    }

    public int getMinAvailableUserId() {
        int userId = 0;
        int userCount = m_userList.size();

        for (int i = 0; i < userCount; ++i) {
            boolean isFound = false;

            for (final ServerMockUser user : m_userList) {
                if (user.getProfile().getId() == userId) {
                    isFound = true;

                    break;
                }
            }

            if (!isFound) return userId;

            ++userId;
        }

        return userCount;
    }

    public boolean addUser(final ServerMockUser newUser) {
        if (newUser == null) return false;

        for (final ServerMockUser user : m_userList)
            if (user.getProfile().getId() == newUser.getProfile().getId())
                return false;

        m_userList.add(newUser);

        return true;
    }

    public boolean removeUserById(final int id) {
        if (id < 0) return false;

        for (ServerMockUser user : m_userList)
            if (user.getProfile().getId() == id) {
                m_userList.remove(user);

                return true;
            }

        return false;
    }

    public ServerMockRoomState getState() {
        return m_state;
    }

    public boolean setState(final ServerMockRoomState state) {
        if (state == null) return false;

        m_state = state;

        return true;
    }

    public void setDestroyed() {
        m_isDestroyed = true;
    }

    public boolean isDestroyed() {
        return m_isDestroyed;
    }
}
