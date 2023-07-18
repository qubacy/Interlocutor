package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson;

import com.google.gson.annotations.SerializedName;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBody;

public class Message {
    @SerializedName("operation") private final OperationEnum m_operation;
    @SerializedName("body") private final MessageBody m_messageBody;

    protected Message(
            final OperationEnum operation,
            final MessageBody messageBody)
    {
        m_operation = operation;
        m_messageBody = messageBody;
    }

    public static Message getInstance(
            final OperationEnum operation,
            final MessageBody messageBody)
    {
        if (operation == null || messageBody == null)
            return null;

        return new Message(operation, messageBody);
    }

    public OperationEnum getOperation() {
        return m_operation;
    }

    public MessageBody getMessageBody() {
        return m_messageBody;
    }
}
