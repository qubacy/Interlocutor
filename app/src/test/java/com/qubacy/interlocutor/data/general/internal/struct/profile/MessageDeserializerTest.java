package com.qubacy.interlocutor.data.general.internal.struct.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.MessageDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.OperationEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageError;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage.NewChatMessageServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.stageover.ChattingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.userschosen.UsersChosenServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.stageover.ChoosingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found.GameFoundServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.start.StartSearchingServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MessageDeserializerTest {
    private Gson m_gson = null;

    @Before
    public void setUp() {
        m_gson =
                new GsonBuilder().
                        registerTypeAdapter(Message.class, new MessageDeserializer()).
                        create();
    }

    @Test
    public void testNullMessageDeserialization() {
        Message deserializedMessage =
                m_gson.fromJson("", Message.class);

        Assert.assertEquals(null, deserializedMessage);
    }

    @Test
    public void testIncorrectMessageDeserialization() {
        StringBuilder messageStringBuilder = new StringBuilder("{\"");

        messageStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append("\"fasfag\"");
        messageStringBuilder.append(",\"body\":{}}");

        Message deserializedMessage =
                m_gson.fromJson(messageStringBuilder.toString(), Message.class);

        Assert.assertEquals(null, deserializedMessage);
    }

    @Test
    public void testErrorMessageDeserialization() {
        ServerMessageError serverMessageError =
                ServerMessageError.getInstance("some error");

        StringBuilder messageStringBuilder = new StringBuilder("{\"");

        messageStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(OperationEnum.SEARCHING_START.getId());
        messageStringBuilder.append(",\"body\":{\"");
        messageStringBuilder.append(ServerMessageBody.C_ERROR_PROP_NAME);
        messageStringBuilder.append("\":{\"");
        messageStringBuilder.append(ServerMessageError.C_MESSAGE_PROP_NAME);
        messageStringBuilder.append("\":\"");
        messageStringBuilder.append(serverMessageError.getMessage());
        messageStringBuilder.append("\"}}}");

        MessageBody expectedMessageBody =
                ServerMessageBody.getInstance(serverMessageError);
        Message expectedMessage =
                Message.getInstance(OperationEnum.SEARCHING_START, expectedMessageBody);
        Message deserializedMessage =
                m_gson.fromJson(messageStringBuilder.toString(), Message.class);

        Assert.assertEquals(expectedMessage, deserializedMessage);
    }

    @Test
    public void testStartSearchingMessageDeserialization() {
        StringBuilder messageStringBuilder = new StringBuilder("{\"");

        messageStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(OperationEnum.SEARCHING_START.getId());
        messageStringBuilder.append(",\"body\":{}}");

        MessageBody expectedMessageBody = StartSearchingServerMessageBody.getInstance();
        Message expectedMessage =
                Message.getInstance(OperationEnum.SEARCHING_START, expectedMessageBody);
        Message deserializedMessage =
                m_gson.fromJson(messageStringBuilder.toString(), Message.class);

        Assert.assertEquals(expectedMessage, deserializedMessage);
    }

    @Test
    public void testGameFoundMessageDeserialization() {
        List<RemoteProfilePublic> remoteProfilePublicList =
                new ArrayList<RemoteProfilePublic>() {
                    {
                        add(RemoteProfilePublic.getInstance(1, "user1"));
                        add(RemoteProfilePublic.getInstance(2, "user2"));
                    }
                };
        RemoteFoundGameData remoteFoundGameData =
                RemoteFoundGameData.getInstance(
                        0,
                        System.currentTimeMillis(),
                        1000,
                        1000,
                        "topic",
                        remoteProfilePublicList);

        StringBuilder messageStringBuilder = new StringBuilder("{\"");

        messageStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(OperationEnum.SEARCHING_GAME_FOUND.getId());
        messageStringBuilder.append(",\"body\":{\"");
        messageStringBuilder.append(GameFoundServerMessageBody.C_FOUND_GAME_DATA_PROP_NAME);
        messageStringBuilder.append("\":{\"");
        messageStringBuilder.append(RemoteFoundGameData.C_LOCAL_PROFILE_ID_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(remoteFoundGameData.getLocalProfileId());
        messageStringBuilder.append(",\"");
        messageStringBuilder.append(RemoteFoundGameData.C_START_SESSION_TIME_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(remoteFoundGameData.getStartSessionTime());
        messageStringBuilder.append(",\"");
        messageStringBuilder.append(RemoteFoundGameData.C_CHATTING_STAGE_DURATION_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(remoteFoundGameData.getChattingStageDuration());
        messageStringBuilder.append(",\"");
        messageStringBuilder.append(RemoteFoundGameData.C_CHOOSING_STAGE_DURATION_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(remoteFoundGameData.getChoosingStageDuration());
        messageStringBuilder.append(",\"");
        messageStringBuilder.append(RemoteFoundGameData.C_CHATTING_TOPIC_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(remoteFoundGameData.getChattingTopic());
        messageStringBuilder.append(",\"");
        messageStringBuilder.append(RemoteFoundGameData.C_PROFILE_PUBLIC_LIST);
        messageStringBuilder.append("\":[");

        int remoteProfilePublicListSize = remoteProfilePublicList.size();

        for (int i = 0; i < remoteProfilePublicListSize; ++i) {
            messageStringBuilder.append("{\"");
            messageStringBuilder.append(RemoteProfilePublic.C_ID_PROP_NAME);
            messageStringBuilder.append("\":");
            messageStringBuilder.append(remoteProfilePublicList.get(i).getId());
            messageStringBuilder.append(",\"");
            messageStringBuilder.append(RemoteProfilePublic.C_USERNAME_PROP_NAME);
            messageStringBuilder.append("\":\"");
            messageStringBuilder.append(remoteProfilePublicList.get(i).getUsername());
            messageStringBuilder.append("\"}");
            messageStringBuilder.append((i == remoteProfilePublicListSize - 1 ? "" : ","));
        }

        messageStringBuilder.append("]}}}");

        MessageBody expectedMessageBody =
                GameFoundServerMessageBody.getInstance(remoteFoundGameData);
        Message expectedMessage =
                Message.getInstance(OperationEnum.SEARCHING_GAME_FOUND, expectedMessageBody);
        Message deserializedMessage =
                m_gson.fromJson(messageStringBuilder.toString(), Message.class);

        Assert.assertEquals(expectedMessage, deserializedMessage);
    }

    @Test
    public void testNewMessageMessageDeserialization() {
        RemoteMessage remoteMessage = RemoteMessage.getInstance(1, "hi");

        StringBuilder messageStringBuilder = new StringBuilder("{\"");

        messageStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(OperationEnum.CHATTING_NEW_MESSAGE.getId());
        messageStringBuilder.append(",\"body\":{\"");
        messageStringBuilder.append(NewChatMessageServerMessageBody.C_MESSAGE_PROP_NAME);
        messageStringBuilder.append("\":{\"");
        messageStringBuilder.append(RemoteMessage.C_SENDER_ID_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(remoteMessage.getSenderId());
        messageStringBuilder.append(",\"");
        messageStringBuilder.append(RemoteMessage.C_TEXT_PROP_NAME);
        messageStringBuilder.append("\":\"");
        messageStringBuilder.append(remoteMessage.getText());
        messageStringBuilder.append("\"}}}");

        MessageBody expectedMessageBody =
                NewChatMessageServerMessageBody.getInstance(remoteMessage);
        Message expectedMessage =
                Message.getInstance(OperationEnum.CHATTING_NEW_MESSAGE, expectedMessageBody);
        Message deserializedMessage =
                m_gson.fromJson(messageStringBuilder.toString(), Message.class);

        Assert.assertEquals(expectedMessage, deserializedMessage);
    }

    @Test
    public void testChattingStageIsOverMessageDeserialization() {
        StringBuilder messageStringBuilder = new StringBuilder("{\"");

        messageStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(OperationEnum.CHATTING_STAGE_IS_OVER.getId());
        messageStringBuilder.append(",\"body\":{}}");

        MessageBody expectedMessageBody = ChattingStageIsOverServerMessageBody.getInstance();
        Message expectedMessage =
                Message.getInstance(OperationEnum.CHATTING_STAGE_IS_OVER, expectedMessageBody);
        Message deserializedMessage =
                m_gson.fromJson(messageStringBuilder.toString(), Message.class);

        Assert.assertEquals(expectedMessage, deserializedMessage);
    }

    @Test
    public void testUsersChosenMessageDeserialization() {
        StringBuilder messageStringBuilder = new StringBuilder("{\"");

        messageStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(OperationEnum.CHOOSING_USERS_CHOSEN.getId());
        messageStringBuilder.append(",\"body\":{}}");

        MessageBody expectedMessageBody = UsersChosenServerMessageBody.getInstance();
        Message expectedMessage =
                Message.getInstance(OperationEnum.CHOOSING_USERS_CHOSEN, expectedMessageBody);
        Message deserializedMessage =
                m_gson.fromJson(messageStringBuilder.toString(), Message.class);

        Assert.assertEquals(expectedMessage, deserializedMessage);
    }

    @Test
    public void testResultsGottenMessageDeserialization() {
        List<MatchedUserProfileData> matchedUserProfileDataList =
                new ArrayList<MatchedUserProfileData>() {
                    {
                        add(MatchedUserProfileData.getInstance(1, "user1's contact"));
                        add(MatchedUserProfileData.getInstance(2, "user2's contact"));
                    }
                };

        StringBuilder messageStringBuilder = new StringBuilder("{\"");

        messageStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        messageStringBuilder.append("\":");
        messageStringBuilder.append(OperationEnum.CHOOSING_STAGE_IS_OVER.getId());
        messageStringBuilder.append(",\"body\":{\"");
        messageStringBuilder.append(ChoosingStageIsOverServerMessageBody.C_MATCHED_USERS_PROP_NAME);
        messageStringBuilder.append("\":[");

        int matchedUserProfileDataListSize = matchedUserProfileDataList.size();

        for (int i = 0; i < matchedUserProfileDataListSize; ++i) {
            messageStringBuilder.append("{\"");
            messageStringBuilder.append(MatchedUserProfileData.C_ID_PROP_NAME);
            messageStringBuilder.append("\":");
            messageStringBuilder.append(matchedUserProfileDataList.get(i).getId());
            messageStringBuilder.append(",\"");
            messageStringBuilder.append(MatchedUserProfileData.C_CONTACT_PROP_NAME);
            messageStringBuilder.append("\":\"");
            messageStringBuilder.append(matchedUserProfileDataList.get(i).getContact());
            messageStringBuilder.append("\"}");
            messageStringBuilder.append((i == matchedUserProfileDataListSize - 1 ? "" : ","));
        }

        messageStringBuilder.append("]}}");

        MessageBody expectedMessageBody =
                ChoosingStageIsOverServerMessageBody.getInstance(matchedUserProfileDataList);
        Message expectedMessage =
                Message.getInstance(OperationEnum.CHOOSING_STAGE_IS_OVER, expectedMessageBody);
        Message deserializedMessage =
                m_gson.fromJson(messageStringBuilder.toString(), Message.class);

        Assert.assertEquals(expectedMessage, deserializedMessage);
    }
}
