package com.qubacy.interlocutor.data.general.export.utility.time;

public class TimeUtility {
    public final static long C_MILLISECONDS_IN_SECOND = 1000;
    public final static long C_SECONDS_IN_MINUTE = 60;

    public static String millisecondsToMinutesString(
            final long timeMilliseconds)
    {
        int seconds = (int) (timeMilliseconds / C_MILLISECONDS_IN_SECOND);

        int solidMinutes = (int) (seconds / C_SECONDS_IN_MINUTE);
        int remainedSeconds = (int) (seconds % C_SECONDS_IN_MINUTE);

        StringBuilder minutesStringBuilder = new StringBuilder();

        minutesStringBuilder.append(timePieceToString(solidMinutes));
        minutesStringBuilder.append(':');
        minutesStringBuilder.append(timePieceToString(remainedSeconds));

        return minutesStringBuilder.toString();
    }

    private static String timePieceToString(
            final int timePiece)
    {
        return (timePiece > 9 ? String.valueOf(timePiece) : ("0" + timePiece));
    }
}
