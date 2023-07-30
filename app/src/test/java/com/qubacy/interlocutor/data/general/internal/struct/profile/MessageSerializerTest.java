package com.qubacy.interlocutor.data.general.internal.struct.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.MessageSerializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.OperationEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage.NewMessageClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice.UsersChosenClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.start.StartSearchingClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.stop.StopSearchingClientMessageBody;
import com.qubacy.interlocutor.data.general.export.struct.profile.LanguageEnum;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MessageSerializerTest {
    private Gson m_gson = null;

    @Before
    public void setUp() {
        m_gson =
                new GsonBuilder().
                        registerTypeAdapter(Message.class, new MessageSerializer()).
                        create();
    }

    @Test
    public void testNullMessageSerialization() {
        Message message = null;
        JsonElement result = m_gson.toJsonTree(message);

        Assert.assertTrue(result.isJsonNull());
    }

    @Test
    public void testStartSearchingMessageSerialization() {
        Profile profile = Profile.getInstance("user1", "contact", LanguageEnum.EN);

        MessageBody messageBody = StartSearchingClientMessageBody.getInstance(profile);
        Message message = Message.getInstance(OperationEnum.SEARCHING_START, messageBody);

        String gottenJsonString = m_gson.toJson(message);
        StringBuilder expectedJsonStringBuilder = new StringBuilder("{\"");

        expectedJsonStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        expectedJsonStringBuilder.append("\":");
        expectedJsonStringBuilder.append(OperationEnum.SEARCHING_START.getId());
        expectedJsonStringBuilder.append(",\"body\":{\"");
        expectedJsonStringBuilder.append(StartSearchingClientMessageBody.C_PROFILE_PROP_NAME);
        expectedJsonStringBuilder.append("\":{\"");
        expectedJsonStringBuilder.append(Profile.C_USERNAME_PROP_NAME);
        expectedJsonStringBuilder.append("\":\"");
        expectedJsonStringBuilder.append(profile.getUsername());
        expectedJsonStringBuilder.append("\",\"");
        expectedJsonStringBuilder.append(Profile.C_CONTACT_PROP_NAME);
        expectedJsonStringBuilder.append("\":\"");
        expectedJsonStringBuilder.append(profile.getContact());
        expectedJsonStringBuilder.append("\",\"");
        expectedJsonStringBuilder.append(Profile.C_LANG_PROP_NAME);
        expectedJsonStringBuilder.append("\":");
        expectedJsonStringBuilder.append(profile.getLang().getId());
        expectedJsonStringBuilder.append("}}}");

        Assert.assertEquals(expectedJsonStringBuilder.toString(), gottenJsonString);
    }

    @Test
    public void testStopSearchingMessageSerialization() {
        MessageBody messageBody = new StopSearchingClientMessageBody();
        Message message = Message.getInstance(OperationEnum.SEARCHING_STOP, messageBody);

        String gottenJsonString = m_gson.toJson(message);
        StringBuilder expectedJsonStringBuilder = new StringBuilder("{\"");

        expectedJsonStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        expectedJsonStringBuilder.append("\":");
        expectedJsonStringBuilder.append(OperationEnum.SEARCHING_STOP.getId());
        expectedJsonStringBuilder.append(",\"body\":{}}");

        Assert.assertEquals(expectedJsonStringBuilder.toString(), gottenJsonString);
    }

    @Test
    public void testNewMessageMessageSerialization() {
        String messageText = "hi";
        com.qubacy.interlocutor.data.game.export.struct.message.Message message =
                com.qubacy.interlocutor.data.game.export.struct.message.Message.
                        getInstance(messageText);
        MessageBody messageBody =
                NewMessageClientMessageBody.getInstance(message);
        Message messageToSerialize =
                Message.getInstance(OperationEnum.CHATTING_NEW_MESSAGE, messageBody);

        String gottenJsonString = m_gson.toJson(messageToSerialize);
        StringBuilder expectedJsonStringBuilder = new StringBuilder("{\"");

        expectedJsonStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        expectedJsonStringBuilder.append("\":");
        expectedJsonStringBuilder.append(OperationEnum.CHATTING_NEW_MESSAGE.getId());
        expectedJsonStringBuilder.append(",\"body\":{\"");
        expectedJsonStringBuilder.append(NewMessageClientMessageBody.C_MESSAGE_PROP_NAME);
        expectedJsonStringBuilder.append("\":{\"");
        expectedJsonStringBuilder.append(
                com.qubacy.interlocutor.data.game.export.struct.message.Message.C_TEXT_PROP_NAME);
        expectedJsonStringBuilder.append("\":\"");
        expectedJsonStringBuilder.append(messageText);
        expectedJsonStringBuilder.append("\"}}}");

        Assert.assertEquals(expectedJsonStringBuilder.toString(), gottenJsonString);
    }

    @Test
    public void testUsersChosenMessageSerialization() {
        List<Integer> chosenUserIdList =
                new ArrayList<Integer>()
                {
                    {
                        add(1);
                        add(2);
                    }
                };
        MessageBody messageBody =
                UsersChosenClientMessageBody.getInstance(chosenUserIdList);
        Message messageToSerialize =
                Message.getInstance(OperationEnum.CHOOSING_USERS_CHOSEN, messageBody);

        String gottenJsonString = m_gson.toJson(messageToSerialize);
        StringBuilder expectedJsonStringBuilder = new StringBuilder("{\"");

        expectedJsonStringBuilder.append(Message.C_OPERATION_PROP_NAME);
        expectedJsonStringBuilder.append("\":");
        expectedJsonStringBuilder.append(OperationEnum.CHOOSING_USERS_CHOSEN.getId());
        expectedJsonStringBuilder.append(",\"body\":{\"");
        expectedJsonStringBuilder.append(UsersChosenClientMessageBody.C_USER_ID_LIST_PROP_NAME);
        expectedJsonStringBuilder.append("\":[");

        int chosenUserIdCount = chosenUserIdList.size();

        for (int i = 0; i < chosenUserIdCount; ++i) {
            if (i == (chosenUserIdCount - 1))
                expectedJsonStringBuilder.append(chosenUserIdList.get(i));
            else {
                expectedJsonStringBuilder.append(chosenUserIdList.get(i));
                expectedJsonStringBuilder.append(',');
            }
        }

        expectedJsonStringBuilder.append("]}}");

        Assert.assertEquals(expectedJsonStringBuilder.toString(), gottenJsonString);
    }
}
