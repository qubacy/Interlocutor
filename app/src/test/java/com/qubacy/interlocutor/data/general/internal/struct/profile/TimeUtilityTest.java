package com.qubacy.interlocutor.data.general.internal.struct.profile;

import com.qubacy.interlocutor.data.general.export.utility.time.TimeUtility;

import org.junit.Assert;
import org.junit.Test;

public class TimeUtilityTest {

    @Test
    public void testZeroMinutesZeroSecondsInMillisecondsToString() {
        long zeroMinutesMilliseconds = 0;

        Assert.assertEquals(
                "00:00",
                TimeUtility.millisecondsToMinutesString(zeroMinutesMilliseconds));
    }

    @Test
    public void testTwoFigureMinutesZeroSecondsInMillisecondsToString() {
        long tenMinutesMilliseconds = 10 * 60 * 1000;
        long twelveMinutesMilliseconds = 12 * 60 * 1000;
        long sixtyMinutesMilliseconds = 60 * 60 * 1000;
        long seventyMinutesMilliseconds = 70 * 60 * 1000;

        Assert.assertEquals(
                "10:00",
                TimeUtility.millisecondsToMinutesString(tenMinutesMilliseconds));
        Assert.assertEquals(
                "12:00",
                TimeUtility.millisecondsToMinutesString(twelveMinutesMilliseconds));
        Assert.assertEquals(
                "60:00",
                TimeUtility.millisecondsToMinutesString(sixtyMinutesMilliseconds));
        Assert.assertEquals(
                "70:00",
                TimeUtility.millisecondsToMinutesString(seventyMinutesMilliseconds));
    }

    @Test
    public void testOneFigureMinutesZeroSecondsInMillisecondsToString() {
        long oneMinuteMilliseconds = 1 * 60 * 1000;
        long fiveMinutesMilliseconds = 5 * 60 * 1000;

        Assert.assertEquals(
                "01:00",
                TimeUtility.millisecondsToMinutesString(oneMinuteMilliseconds));
        Assert.assertEquals(
                "05:00",
                TimeUtility.millisecondsToMinutesString(fiveMinutesMilliseconds));
    }

    @Test
    public void testZeroMinutesTwoFigureSecondsInMillisecondsToString() {
        long tenSecondsMilliseconds = 10 * 1000;
        long twelveSecondsMilliseconds = 12 * 1000;
        long sixtyOneSecondsMilliseconds = 61 * 1000;

        Assert.assertEquals(
                "00:10",
                TimeUtility.millisecondsToMinutesString(tenSecondsMilliseconds));
        Assert.assertEquals(
                "00:12",
                TimeUtility.millisecondsToMinutesString(twelveSecondsMilliseconds));
        Assert.assertEquals(
                "01:01",
                TimeUtility.millisecondsToMinutesString(sixtyOneSecondsMilliseconds));
    }

    @Test
    public void testZeroMinutesOneFigureSecondsInMillisecondsToString() {
        long oneSecondMilliseconds = 1 * 1000;
        long twoSecondsMilliseconds = 2 * 1000;
        long nineSecondsMilliseconds = 9 * 1000;

        Assert.assertEquals(
                "00:01",
                TimeUtility.millisecondsToMinutesString(oneSecondMilliseconds));
        Assert.assertEquals(
                "00:02",
                TimeUtility.millisecondsToMinutesString(twoSecondsMilliseconds));
        Assert.assertEquals(
                "00:09",
                TimeUtility.millisecondsToMinutesString(nineSecondsMilliseconds));
    }

    @Test
    public void testTwoFigureMinutesTwoFigureSecondsInMillisecondsToString() {
        long tenMinutesTenSecondsMilliseconds = 10 * 60 * 1000 + 10 * 1000;
        long twelveMinutesFiftySecondsMilliseconds = 12 * 60 * 1000 + 50 * 1000;
        long sixtyMinutesNineteenSecondsMilliseconds = 60 * 60 * 1000 + 19 * 1000;

        Assert.assertEquals(
                "10:10",
                TimeUtility.millisecondsToMinutesString(tenMinutesTenSecondsMilliseconds));
        Assert.assertEquals(
                "12:50",
                TimeUtility.millisecondsToMinutesString(twelveMinutesFiftySecondsMilliseconds));
        Assert.assertEquals(
                "60:19",
                TimeUtility.millisecondsToMinutesString(sixtyMinutesNineteenSecondsMilliseconds));
    }

    @Test
    public void testTwoFigureMinutesOneFigureSecondsInMillisecondsToString() {
        long tenMinutesOneSecondMilliseconds = 10 * 60 * 1000 + 1 * 1000;
        long twelveMinutesTwoSecondsMilliseconds = 12 * 60 * 1000 + 2 * 1000;
        long sixtyMinutesNineSecondsMilliseconds = 60 * 60 * 1000 + 9 * 1000;

        Assert.assertEquals(
                "10:01",
                TimeUtility.millisecondsToMinutesString(tenMinutesOneSecondMilliseconds));
        Assert.assertEquals(
                "12:02",
                TimeUtility.millisecondsToMinutesString(twelveMinutesTwoSecondsMilliseconds));
        Assert.assertEquals(
                "60:09",
                TimeUtility.millisecondsToMinutesString(sixtyMinutesNineSecondsMilliseconds));
    }

    @Test
    public void testOneFigureMinutesTwoFigureSecondsInMillisecondsToString() {
        long oneMinuteTenSecondsMilliseconds = 1 * 60 * 1000 + 10 * 1000;
        long twoMinutesFiftySecondsMilliseconds = 2 * 60 * 1000 + 50 * 1000;
        long nineMinutesNineteenSecondsMilliseconds = 9 * 60 * 1000 + 19 * 1000;

        Assert.assertEquals(
                "01:10",
                TimeUtility.millisecondsToMinutesString(oneMinuteTenSecondsMilliseconds));
        Assert.assertEquals(
                "02:50",
                TimeUtility.millisecondsToMinutesString(twoMinutesFiftySecondsMilliseconds));
        Assert.assertEquals(
                "09:19",
                TimeUtility.millisecondsToMinutesString(nineMinutesNineteenSecondsMilliseconds));
    }

    @Test
    public void testOneFigureMinutesOneFigureSecondsInMillisecondsToString() {
        long oneMinuteOneSecondMilliseconds = 1 * 60 * 1000 + 1 * 1000;
        long twoMinutesFiveSecondsMilliseconds = 2 * 60 * 1000 + 5 * 1000;
        long nineMinutesNineSecondsMilliseconds = 9 * 60 * 1000 + 9 * 1000;

        Assert.assertEquals(
                "01:01",
                TimeUtility.millisecondsToMinutesString(oneMinuteOneSecondMilliseconds));
        Assert.assertEquals(
                "02:05",
                TimeUtility.millisecondsToMinutesString(twoMinutesFiveSecondsMilliseconds));
        Assert.assertEquals(
                "09:09",
                TimeUtility.millisecondsToMinutesString(nineMinutesNineSecondsMilliseconds));
    }
}
