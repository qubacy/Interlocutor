package com.qubacy.interlocutor.data.game.internal.processor.network.websocket;

import android.os.SystemClock;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommand;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandMessageReceived;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.OperationEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found.GameFoundServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.start.StartSearchingServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.stop.StopSearchingServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.start.StartSearchingClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket.WebSocketClient;
import com.qubacy.interlocutor.data.game.internal.processor.network.gson.ServerMockMessageDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.network.gson.ServerMockMessageSerializer;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.command.Command;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.command.CommandProcessMessage;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.ServerMockRoom;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state.ServerMockRoomChattingState;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state.ServerMockRoomChoosingState;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state.ServerMockRoomSearchingState;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state.ServerMockRoomState;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state.ServerMockRoomStateTypeEnum;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.user.ServerMockUser;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfile;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class WebSocketServerMock extends WebSocketClient {
    public static final long C_TIMEOUT_MILLISECONDS = 400;
    public static final int C_DEFAULT_USER_ID = 0;
    public static final String C_DEFAULT_TOPIC = "Some topic..";

    private final Gson m_gson;
    private Thread m_thread = null;

    private final BlockingQueue<Command> m_commandQueue;

    private final List<ServerMockRoom> m_roomList;

    private final boolean m_isGameFound;

    protected WebSocketServerMock(
            final OkHttpClient httpClient,
            final WebSocket webSocket,
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue,
            final Gson gson,
            final boolean isGameFound)
    {
        super(httpClient, webSocket, networkCallbackCommandQueue);

        m_gson = gson;
        m_commandQueue = new LinkedBlockingQueue<>();
        m_roomList = new LinkedList<>();
        m_isGameFound = isGameFound;
    }

    public static WebSocketServerMock getInstance(
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue,
            final boolean isGameFound)
    {
        if (networkCallbackCommandQueue == null) return null;

        Gson gson =
                new GsonBuilder().
                        registerTypeAdapter(Message.class, new ServerMockMessageDeserializer()).
                        registerTypeAdapter(Message.class, new ServerMockMessageSerializer()).
                        create();

        if (gson == null) return null;

        return new WebSocketServerMock(
                null, null, networkCallbackCommandQueue, gson, isGameFound);
    }

    public void launch() {
        m_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                exec();
            }
        });

        m_thread.start();
    }

    public void stop() {
        if (m_thread == null) return;

        m_thread.interrupt();
    }

    protected void exec() {
        while (!Thread.interrupted()) {
            SystemClock.sleep(C_TIMEOUT_MILLISECONDS);

            if (!execIteration()) break;
        }
    }

    protected boolean execIteration() {
        Command command = m_commandQueue.poll();

        if (command != null) {
            if (!processCommand(command)) return false;
        }

        if (!execRoomsState()) return false;

        return true;
    }

    protected boolean execRoomsState() {
        for (final ServerMockRoom room : m_roomList)
            if (!execRoomState(room)) return false;

        return true;
    }

    protected boolean processCommand(final Command command) {
        switch (command.getType()) {
            case PROCESS_MESSAGE:
                return processProcessMessageCommand((CommandProcessMessage) command);
        }

        return false;
    }

    protected boolean processProcessMessageCommand(
            final CommandProcessMessage commandProcessMessage)
    {
        if (commandProcessMessage == null) return false;

        Message message =
                m_gson.fromJson(commandProcessMessage.getMessage(), Message.class);

        return processMessage(message);
    }

    protected boolean processMessage(final Message message) {
        if (message == null) return false;

        MessageBody messageBody = message.getMessageBody();

        if (messageBody == null) return false;

        switch (message.getOperation()) {
            case SEARCHING_START:
                return processSearchingStartMessage((StartSearchingClientMessageBody) messageBody);
        }

        return false;
    }

    protected boolean processSearchingStartMessage(
            final StartSearchingClientMessageBody startSearchingClientMessageBody)
    {
        if (startSearchingClientMessageBody == null) return false;

        Profile userProfile = startSearchingClientMessageBody.getProfile();

        ServerMockRoom room = null;

        for (final ServerMockRoom curRoom : m_roomList) {
            if (room.getState().getType() == ServerMockRoomStateTypeEnum.SEARCHING) {
                room = curRoom;

                break;
            }
        }

        if (room == null) {
            RemoteProfile remoteProfile =
                    RemoteProfile.getInstance(
                            C_DEFAULT_USER_ID,
                            userProfile.getUsername(),
                            userProfile.getContact());
            ServerMockUser serverMockUser = ServerMockUser.getInstance(remoteProfile);

            List<ServerMockUser> userList =
                    new LinkedList<ServerMockUser>() {
                        {
                            add(serverMockUser);
                        }
                    };
            room = ServerMockRoom.getInstance(userList);

            m_roomList.add(room);

        } else {
            RemoteProfile remoteProfile =
                    RemoteProfile.getInstance(
                            room.getMinAvailableUserId(),
                            userProfile.getUsername(),
                            userProfile.getContact());
            ServerMockUser serverMockUser = ServerMockUser.getInstance(remoteProfile);

            if (!room.addUser(serverMockUser)) return false;
        }

        ServerMessageBody messageBody = StartSearchingServerMessageBody.getInstance();
        Message message = Message.getInstance(OperationEnum.SEARCHING_START, messageBody);

        if (message == null) return false;

        String serializedMessage = m_gson.toJson(message);
        NetworkCallbackCommandMessageReceived messageReceived =
                NetworkCallbackCommandMessageReceived.getInstance(serializedMessage);

        try {
            m_networkCallbackCommandQueue.put(messageReceived);

        } catch (InterruptedException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    @Override
    public boolean sendMessage(final String message) {
        try {
            m_commandQueue.put(CommandProcessMessage.getInstance(message));

        } catch (InterruptedException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    protected boolean execRoomState(final ServerMockRoom room) {
        if (room == null) return false;

        ServerMockRoomState roomState = room.getState();

        if (roomState == null) return false;

        switch (roomState.getType()) {
            case SEARCHING: return execSearchingState(room, (ServerMockRoomSearchingState) roomState);
            case CHATTING: return execChattingState(room, (ServerMockRoomChattingState) roomState);
            case CHOOSING: return execChoosingState(room, (ServerMockRoomChoosingState) roomState);
        }

        return false;
    }

    protected boolean execSearchingState(
            final ServerMockRoom room,
            final ServerMockRoomSearchingState searchingState)
    {
        if (searchingState == null) return false;
        if (searchingState.isOver(System.currentTimeMillis())) {
            Message message = null;

            if (!m_isGameFound) {
                ServerMessageBody messageBody = StopSearchingServerMessageBody.getInstance();

                message = Message.getInstance(OperationEnum.SEARCHING_STOP, messageBody);

                m_roomList.remove(room);

            } else {
                final long startSessionTime = System.currentTimeMillis();

                RemoteFoundGameData remoteFoundGameData =
                        RemoteFoundGameData.getInstance(
                                C_DEFAULT_USER_ID,
                                startSessionTime,
                                ServerMockRoomChattingState.C_DEFAULT_DURATION,
                                ServerMockRoomChoosingState.C_DEFAULT_DURATION,
                                C_DEFAULT_TOPIC,
                                getProfilePublicList(room.getUserList()));

                if (remoteFoundGameData == null) return false;

                ServerMessageBody messageBody =
                        GameFoundServerMessageBody.getInstance(remoteFoundGameData);

                message = Message.getInstance(OperationEnum.SEARCHING_GAME_FOUND, messageBody);

                if (!room.setState(ServerMockRoomChattingState.getInstance(
                        startSessionTime)))
                {
                    return false;
                }
            }

            if (message == null) return false;

            String serializedMessage = m_gson.toJson(message);

            NetworkCallbackCommandMessageReceived messageReceived =
                    NetworkCallbackCommandMessageReceived.getInstance(serializedMessage);

            try {
                m_networkCallbackCommandQueue.put(messageReceived);

            } catch (InterruptedException e) {
                e.printStackTrace();

                return false;
            }

            return true;
        }

        return true;
    }

    protected List<RemoteProfilePublic> getProfilePublicList(final List<ServerMockUser> userList) {
        if (userList == null) return null;

        List<RemoteProfilePublic> profilePublicList = new ArrayList<>();

        for (final ServerMockUser user : userList) {
            RemoteProfilePublic remoteProfilePublic =
                    RemoteProfilePublic.getInstance(
                            user.getProfile().getId(), user.getProfile().getUsername());

            if (remoteProfilePublic == null) return null;

            profilePublicList.add(remoteProfilePublic);
        }

        return profilePublicList;
    }

    protected boolean execChattingState(
            final ServerMockRoom room,
            final ServerMockRoomChattingState chattingState)
    {
        if (chattingState == null) return false;



        return true;
    }

    protected boolean execChoosingState(
            final ServerMockRoom room,
            final ServerMockRoomChoosingState choosingState)
    {
        if (choosingState == null) return false;



        return true;
    }
}
