package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBody;

import java.util.Objects;

public class Message {
    public static final String C_OPERATION_PROP_NAME = "operation";
    public static final String C_BODY_PROP_NAME = "body";

    private final OperationEnum m_operation;
    private final MessageBody m_messageBody;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Message message = (Message) o;

        return m_operation == message.m_operation &&
                Objects.equals(m_messageBody, message.m_messageBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_operation, m_messageBody);
    }
}
