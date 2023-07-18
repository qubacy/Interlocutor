package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson;

public enum OperationEnum {
    SEARCHING_START(0),
    SEARCHING_STOP(1),
    SEARCHING_GAME_FOUND(2),

    CHATTING_NEW_MESSAGE(3),
    CHATTING_STAGE_IS_OVER(4),

    CHOOSING_USERS_CHOSEN(5),
    CHOOSING_STAGE_IS_OVER(6),

    RESULTS_MATCHED_USERS_GOTTEN(7);

    private final int m_id;

    private OperationEnum(final int id) {
        m_id = id;
    }

    public int getId() {
        return m_id;
    }

    public static OperationEnum getOperationById(final int id) {
        if (id < 0) return null;

        for (final OperationEnum operationEnum : OperationEnum.values())
            if (operationEnum.getId() == id) return operationEnum;

        return null;
    }
}
