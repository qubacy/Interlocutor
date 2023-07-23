package com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ServerMockRoomChoosingState extends ServerMockRoomState {
    public static final long C_DEFAULT_DURATION = 5000;

    private final ArrayList<List<Integer>> m_userChoiceMatrix;

    protected ServerMockRoomChoosingState(
            final long startTime,
            final long duration,
            final int userCount)
    {
        super(startTime, duration);

        m_userChoiceMatrix = new ArrayList<>(userCount);
    }

    public static ServerMockRoomChoosingState getInstance(
            final long startTime,
            final int userCount)
    {
        if (startTime <= 0 || userCount <= 0) return null;

        return new ServerMockRoomChoosingState(
                startTime, C_DEFAULT_DURATION, userCount);
    }

    @Override
    public ServerMockRoomStateTypeEnum getType() {
        return ServerMockRoomStateTypeEnum.CHOOSING;
    }

    public boolean setUserChoice(
            final int userId,
            final List<Integer> userChoiceList)
    {
        if (m_userChoiceMatrix.get(userId) == null)
            return false;

        m_userChoiceMatrix.set(userId, userChoiceList);

        return true;
    }

    public List<Pair<Integer, Integer>> generateUserMatchMatrix() {
        List<Pair<Integer, Integer>> userMatchMatrix = new ArrayList<>();

        int curUserId = 0;

        for (final List<Integer> userChoice : m_userChoiceMatrix) {
            for (final Integer curChoice : userChoice) {
                List<Integer> oppositeUserChoice =
                        m_userChoiceMatrix.get(curChoice);

                if (oppositeUserChoice.contains(curUserId)) {
                    userMatchMatrix.add(new Pair<>(curUserId, curChoice));
                    oppositeUserChoice.remove(curUserId);
                }
            }

            ++curUserId;
        }

        return userMatchMatrix;
    }
}
