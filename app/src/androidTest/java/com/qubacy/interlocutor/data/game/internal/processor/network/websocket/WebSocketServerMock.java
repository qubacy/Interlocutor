package com.qubacy.interlocutor.data.game.internal.processor.network.websocket;

import android.os.SystemClock;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommand;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandDisconnected;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandMessageReceived;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.error.NetworkServerErrorEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.OperationEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageError;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage.NewChatMessageServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.stageover.ChattingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.stageover.ChoosingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.userschosen.UsersChosenServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found.GameFoundServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.start.StartSearchingServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage.NewMessageClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice.UsersChosenClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.start.StartSearchingClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.stop.StopSearchingClientMessageBody;
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
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfile;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class WebSocketServerMock extends WebSocketClient {
    public static final long C_TIMEOUT_MILLISECONDS = 400;
    public static final int C_DEFAULT_USER_ID = 0;
    public static final long C_MAX_SEARCHING_STAGE_DURATION = 3000;
    public static final String C_DEFAULT_TOPIC = "Some topic..";

    private final Gson m_gson;
    private Thread m_thread = null;

    private final BlockingQueue<Command> m_commandQueue;

    private final List<ServerMockRoom> m_roomList;

    private final boolean m_isAboutToDisconnect;
    private final boolean m_isGameFound;

    protected WebSocketServerMock(
            final OkHttpClient httpClient,
            final WebSocket webSocket,
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue,
            final Gson gson,
            final AtomicBoolean isClosed,
            final boolean isGameFound,
            final boolean isAboutToDisconnect)
    {
        super(httpClient, webSocket, networkCallbackCommandQueue, isClosed);

        m_gson = gson;
        m_commandQueue = new LinkedBlockingQueue<>();
        m_roomList = new LinkedList<>();
        m_isGameFound = isGameFound;
        m_isAboutToDisconnect = isAboutToDisconnect;
    }

    public static WebSocketServerMock getInstance(
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue,
            final boolean isGameFound,
            final boolean isAboutToDisconnect)
    {
        if (networkCallbackCommandQueue == null) return null;

        Gson gson =
                new GsonBuilder().
                        registerTypeAdapter(Message.class, new ServerMockMessageDeserializer()).
                        registerTypeAdapter(Message.class, new ServerMockMessageSerializer()).
                        create();

        if (gson == null) return null;

        AtomicBoolean isClosed = new AtomicBoolean(false);

        return new WebSocketServerMock(
                null,
                null,
                networkCallbackCommandQueue,
                gson,
                isClosed,
                isGameFound,
                isAboutToDisconnect);
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

        for (int i = 0; i < m_roomList.size(); ++i) {
            for (final ServerMockRoom room : m_roomList) {
                if (room.isDestroyed()) {
                    m_roomList.remove(room);

                    break;
                }
            }
        }

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
            case SEARCHING_STOP:
                return processSearchingStopMessage((StopSearchingClientMessageBody) messageBody);
            case CHATTING_NEW_MESSAGE:
                return processNewMessageMessage((NewMessageClientMessageBody) messageBody);
            case CHOOSING_USERS_CHOSEN:
                return processUsersChosenMessage((UsersChosenClientMessageBody) messageBody);
        }

        return false;
    }

    protected boolean processSearchingStartMessage(
            final StartSearchingClientMessageBody startSearchingClientMessageBody) {
        if (startSearchingClientMessageBody == null) return false;

        Profile userProfile = startSearchingClientMessageBody.getProfile();

        boolean isProfileCorrect = true;

        if (userProfile.getUsername() == null || userProfile.getContact() == null) {
            isProfileCorrect = false;
        } else {
            if (userProfile.getUsername() != null) {
                if (userProfile.getUsername().isEmpty())
                    isProfileCorrect = false;
            }

            if (userProfile.getContact() != null && isProfileCorrect) {
                if (userProfile.getContact().isEmpty())
                    isProfileCorrect = false;
            }
        }

        if (!isProfileCorrect) {
            ServerMessageError serverMessageError =
                    ServerMessageError.getInstance(NetworkServerErrorEnum.INCORRECT_PROFILE.getId());
            ServerMessageBody serverMessageBody =
                    ServerMessageBody.getInstance(serverMessageError);

            return generateAndDeliverNetworkCallbackMessage(
                    OperationEnum.SEARCHING_START, serverMessageBody);
        }

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

        return generateAndDeliverNetworkCallbackMessage(
                OperationEnum.SEARCHING_START, messageBody);
    }

    protected boolean processSearchingStopMessage(
            final StopSearchingClientMessageBody messageBody)
    {
        if (messageBody == null) return false;
        if (m_roomList.size() <= 0) return false;

        ServerMockRoom room = m_roomList.get(0);

        if (!room.removeUserById(C_DEFAULT_USER_ID))
            return false;

        return true;
    }

    protected boolean processNewMessageMessage(
            final NewMessageClientMessageBody messageBody)
    {
        if (messageBody == null) return false;

        com.qubacy.interlocutor.data.game.export.struct.message.Message clientMessage =
                messageBody.getMessage();
        RemoteMessage remoteMessage =
                RemoteMessage.getInstance(C_DEFAULT_USER_ID, clientMessage.getText());

        if (remoteMessage == null) return false;

        ServerMessageBody outgoingMessageBody
                = NewChatMessageServerMessageBody.getInstance(remoteMessage);

        return generateAndDeliverNetworkCallbackMessage(
                OperationEnum.CHATTING_NEW_MESSAGE, outgoingMessageBody);
    }

    protected boolean processUsersChosenMessage(
            final UsersChosenClientMessageBody messageBody)
    {
        if (messageBody == null) return false;
        if (m_roomList.size() <= 0) return false;

        ServerMockRoom room = m_roomList.get(0);

        ServerMockRoomChoosingState state = (ServerMockRoomChoosingState) room.getState();

        if (state == null) return false;
        if (!state.setUserChoice(C_DEFAULT_USER_ID, messageBody.getChosenUserIdList()))
            return false;

        ServerMessageBody outgoingMessageBody =
                UsersChosenServerMessageBody.getInstance();

        return generateAndDeliverNetworkCallbackMessage(
                OperationEnum.CHOOSING_USERS_CHOSEN,
                outgoingMessageBody);
    }

    private boolean generateAndDeliverNetworkCallbackMessage(
            final OperationEnum operation,
            final ServerMessageBody messageBody)
    {
        if (messageBody == null) return false;

        Message message = Message.getInstance(operation, messageBody);

        if (message == null) return false;

        String serializedMessage = m_gson.toJson(message, Message.class);
        NetworkCallbackCommandMessageReceived messageReceived =
                NetworkCallbackCommandMessageReceived.getInstance(serializedMessage);

        if (messageReceived == null) return false;

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
                if (searchingState.getStartTime() + C_MAX_SEARCHING_STAGE_DURATION >=
                    System.currentTimeMillis())
                {
                    return true;
                }

                NetworkCallbackCommandDisconnected disconnected =
                        NetworkCallbackCommandDisconnected.getInstance();

                try {
                    m_networkCallbackCommandQueue.put(disconnected);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                    return false;
                }

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

        long endTime = chattingState.getStartTime() + chattingState.getDuration();

        if (endTime >= System.currentTimeMillis()) {
            if (m_isAboutToDisconnect) {
                NetworkCallbackCommandDisconnected networkCallbackCommandDisconnected =
                        NetworkCallbackCommandDisconnected.getInstance();

                try {
                    m_networkCallbackCommandQueue.put(networkCallbackCommandDisconnected);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                    return false;
                }

                return true;
            }

            ServerMessageBody messageBody =
                    ChattingStageIsOverServerMessageBody.getInstance();
            Message message =
                    Message.getInstance(OperationEnum.CHATTING_STAGE_IS_OVER, messageBody);

            if (message == null) return false;

            String serializedMessage = m_gson.toJson(message, Message.class);
            NetworkCallbackCommandMessageReceived messageReceived =
                    NetworkCallbackCommandMessageReceived.getInstance(serializedMessage);

            if (messageReceived == null) return false;

            try {
                m_networkCallbackCommandQueue.put(messageReceived);

            } catch (InterruptedException e) {
                e.printStackTrace();

                return false;
            }

            long startChoosingTime = System.currentTimeMillis();

            ServerMockRoomChoosingState choosingState =
                    ServerMockRoomChoosingState.getInstance(
                            startChoosingTime, room.getUserCount());

            if (choosingState == null) return false;
            if (!room.setState(choosingState)) return false;
        }

        return true;
    }

    protected boolean execChoosingState(
            final ServerMockRoom room,
            final ServerMockRoomChoosingState choosingState)
    {
        if (choosingState == null) return false;

        long endTime = choosingState.getStartTime() + choosingState.getDuration();

        if (System.currentTimeMillis() >= endTime) {
            List<Pair<Integer, Integer>> userChoiceMatrix =
                    choosingState.generateUserMatchMatrix();
            List<MatchedUserProfileData> matchedUserProfileDataList =
                    new ArrayList<>();

            for (final Pair<Integer, Integer> userMatureChoice : userChoiceMatrix) {
                if (userMatureChoice.first == C_DEFAULT_USER_ID) {
                    ServerMockUser user = room.getUserById(userMatureChoice.second);

                    if (user == null) return false;

                    MatchedUserProfileData matchedUserProfileData =
                            MatchedUserProfileData.getInstance(
                                    userMatureChoice.second, user.getProfile().getContact());

                    matchedUserProfileDataList.add(matchedUserProfileData);
                }
            }


            ServerMessageBody messageBody =
                    ChoosingStageIsOverServerMessageBody.getInstance(matchedUserProfileDataList);
            Message message =
                    Message.getInstance(OperationEnum.CHOOSING_STAGE_IS_OVER, messageBody);

            if (message == null) return false;

            String serializedMessage = m_gson.toJson(message, Message.class);
            NetworkCallbackCommandMessageReceived messageReceived =
                    NetworkCallbackCommandMessageReceived.getInstance(serializedMessage);

            if (messageReceived == null) return false;

            try {
                m_networkCallbackCommandQueue.put(messageReceived);
            } catch (InterruptedException e) {
                e.printStackTrace();

                return false;
            }

            room.setDestroyed();
        }

        return true;
    }
}
