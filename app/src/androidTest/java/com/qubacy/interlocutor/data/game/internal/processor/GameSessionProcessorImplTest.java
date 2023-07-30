package com.qubacy.interlocutor.data.game.internal.processor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.processor.impl.GameSessionProcessorImpl;
import com.qubacy.interlocutor.data.game.internal.processor.impl.GameSessionProcessorImplFactory;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommand;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.WebSocketServerMock;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state.ServerMockRoomChattingState;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state.ServerMockRoomChoosingState;
import com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state.ServerMockRoomSearchingState;
import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateType;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.profile.LanguageEnum;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RunWith(JUnit4.class)
public class GameSessionProcessorImplTest {
    private Context m_context = null;
    private GameSessionProcessorCallback m_callback = null;

    @Before
    public void setUp() {
        m_context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        m_callback = new GameSessionProcessorCallback() {
            @Override
            public void gameFound(@NonNull final RemoteFoundGameData foundGameData) {
                return;
            }

            @Override
            public void errorOccurred(@NonNull final Error error) {
                return;
            }

            @Override
            public void messageReceived(@NonNull final RemoteMessage message) {
                return;
            }

            @Override
            public void onChattingPhaseIsOver() {

            }

            @Override
            public void onChoosingPhaseIsOver(
                    @NonNull final ArrayList<MatchedUserProfileData> userProfileContactDataList)
            {

            }

            @Override
            public void onDisconnection(final boolean isIncorrect) {

            }
        };
    }

    @Test
    public void testLaunchingStopping() {
        GameSessionProcessor gameSessionProcessor =
                new GameSessionProcessorImplFactory().generateProcessor();
        gameSessionProcessor.launch(m_callback, m_context);

        Assert.assertTrue(gameSessionProcessor.isRunning());

        gameSessionProcessor.stop();

        Assert.assertFalse(gameSessionProcessor.isRunning());
    }

    @Test
    public void testSearchingStartAndAbortion() {
        GameSessionProcessor gameSessionProcessor =
                new GameSessionProcessorImplFactory().generateProcessor();

        gameSessionProcessor.launch(m_callback, m_context);

        Assert.assertTrue(gameSessionProcessor.isRunning());

        Profile profile = Profile.getInstance("username", "contact", LanguageEnum.EN);

        Assert.assertTrue(gameSessionProcessor.startSearching(profile));
        Assert.assertTrue(gameSessionProcessor.stopSearching());

        gameSessionProcessor.stop();

        Assert.assertFalse(gameSessionProcessor.isRunning());
    }

    private WebSocketServerMock startWebSocketServer(
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommands,
            final boolean isGameFound,
            final boolean isAboutToDisconnect)
    {
        if (networkCallbackCommands == null) return null;

        WebSocketServerMock webSocketServerMock =
                WebSocketServerMock.getInstance(
                        networkCallbackCommands, isGameFound, isAboutToDisconnect);

        webSocketServerMock.launch();

        return webSocketServerMock;
    }

    @Test
    public void testUnexpectedConnectionClosing() throws InterruptedException {
        BlockingQueue<NetworkCallbackCommand> callbackCommands =
                new LinkedBlockingQueue<>();
        WebSocketServerMock webSocketServerMock =
                startWebSocketServer(callbackCommands, true, true);
        GameSessionProcessor gameSessionProcessor =
                GameSessionProcessorImpl.getInstance(
                        callbackCommands, webSocketServerMock);

        final boolean[] isDisconnected = {false};

        GameSessionProcessorCallback gameSessionProcessorCallback =
                new GameSessionProcessorCallback() {
                    @Override
                    public void gameFound(@NonNull final RemoteFoundGameData foundGameData) {

                    }

                    @Override
                    public void errorOccurred(@NonNull Error error) {

                    }

                    @Override
                    public void messageReceived(@NonNull RemoteMessage message) {
                    }

                    @Override
                    public void onChattingPhaseIsOver() {

                    }

                    @Override
                    public void onChoosingPhaseIsOver(@NonNull ArrayList<MatchedUserProfileData> userProfileContactDataList) {

                    }

                    @Override
                    public void onDisconnection(final boolean isIncorrect) {
                        isDisconnected[0] = true;
                    }
                };

        gameSessionProcessor.launch(gameSessionProcessorCallback, m_context);

        Assert.assertTrue(gameSessionProcessor.isRunning());

        Profile profile = Profile.getInstance("username", "contact", LanguageEnum.EN);

        Assert.assertTrue(gameSessionProcessor.startSearching(profile));

        Thread.sleep(
                ServerMockRoomSearchingState.C_DEFAULT_DURATION +
                ServerMockRoomChattingState.C_DEFAULT_DURATION);

        Assert.assertTrue(isDisconnected[0]);

        webSocketServerMock.stop();
    }

    @Test
    public void testServerErrorOccurred() throws InterruptedException {
        BlockingQueue<NetworkCallbackCommand> callbackCommands =
                new LinkedBlockingQueue<>();
        WebSocketServerMock webSocketServerMock =
                startWebSocketServer(callbackCommands, true, true);
        GameSessionProcessor gameSessionProcessor =
                GameSessionProcessorImpl.getInstance(
                        callbackCommands, webSocketServerMock);

        final Error[] gottenError = {null};

        GameSessionProcessorCallback gameSessionProcessorCallback =
                new GameSessionProcessorCallback() {
                    @Override
                    public void gameFound(@NonNull final RemoteFoundGameData foundGameData) {

                    }

                    @Override
                    public void errorOccurred(@NonNull Error error) {
                        gottenError[0] = error;
                    }

                    @Override
                    public void messageReceived(@NonNull RemoteMessage message) {
                    }

                    @Override
                    public void onChattingPhaseIsOver() {

                    }

                    @Override
                    public void onChoosingPhaseIsOver(@NonNull ArrayList<MatchedUserProfileData> userProfileContactDataList) {

                    }

                    @Override
                    public void onDisconnection(final boolean isIncorrect) {

                    }
                };

        gameSessionProcessor.launch(gameSessionProcessorCallback, m_context);

        Assert.assertTrue(gameSessionProcessor.isRunning());

        Profile profile = Profile.getInstance("", "contact", LanguageEnum.EN);

        Assert.assertTrue(gameSessionProcessor.startSearching(profile));

        Thread.sleep(ServerMockRoomSearchingState.C_DEFAULT_DURATION);

        Assert.assertNotNull(gottenError);

        webSocketServerMock.stop();
    }

    @Test
    public void testSearchingGameNotFound() throws InterruptedException {
        BlockingQueue<NetworkCallbackCommand> callbackCommands =
                new LinkedBlockingQueue<>();
        WebSocketServerMock webSocketServerMock =
                startWebSocketServer(callbackCommands, false, false);
        GameSessionProcessor gameSessionProcessor =
                GameSessionProcessorImpl.getInstance(
                        callbackCommands, webSocketServerMock);

        gameSessionProcessor.launch(m_callback, m_context);

        Assert.assertTrue(gameSessionProcessor.isRunning());

        Profile profile = Profile.getInstance("username", "contact", LanguageEnum.EN);

        Assert.assertTrue(gameSessionProcessor.startSearching(profile));

        Thread.sleep(1500); // it should work. unfortunately it depends on the device;

        Assert.assertEquals(
                GameSessionStateType.SEARCHING,
                gameSessionProcessor.m_gameSessionState.getType());

        Thread.sleep(3000); // it should work. unfortunately it depends on the device;

        Assert.assertEquals(
                GameSessionStateType.SEARCHING,
                gameSessionProcessor.m_gameSessionState.getType());

        webSocketServerMock.stop();
    }

    @Test
    public void testSearchingStartWithFoundGame() throws InterruptedException {
        BlockingQueue<NetworkCallbackCommand> callbackCommands =
                new LinkedBlockingQueue<>();
        WebSocketServerMock webSocketServerMock =
                startWebSocketServer(callbackCommands, true, false);
        GameSessionProcessor gameSessionProcessor =
                GameSessionProcessorImpl.getInstance(
                        callbackCommands, webSocketServerMock);

        gameSessionProcessor.launch(m_callback, m_context);

        Assert.assertTrue(gameSessionProcessor.isRunning());

        Profile profile = Profile.getInstance("username", "contact", LanguageEnum.EN);

        Assert.assertTrue(gameSessionProcessor.startSearching(profile));

        Thread.sleep(ServerMockRoomSearchingState.C_DEFAULT_DURATION - 500); // it should work. unfortunately it depends on the device;

        Assert.assertEquals(
                GameSessionStateType.SEARCHING,
                gameSessionProcessor.m_gameSessionState.getType());

        Thread.sleep(ServerMockRoomChattingState.C_DEFAULT_DURATION - 1000); // it should work. unfortunately it depends on the device;

        Assert.assertEquals(
                GameSessionStateType.CHATTING,
                gameSessionProcessor.m_gameSessionState.getType());

        webSocketServerMock.stop();
    }

    @Test
    public void testChatting() throws InterruptedException {
        BlockingQueue<NetworkCallbackCommand> callbackCommands =
                new LinkedBlockingQueue<>();
        WebSocketServerMock webSocketServerMock =
                startWebSocketServer(callbackCommands, true, false);
        GameSessionProcessor gameSessionProcessor =
                GameSessionProcessorImpl.getInstance(
                        callbackCommands, webSocketServerMock);

        final RemoteMessage[] gottenMessage = {null};

        GameSessionProcessorCallback gameSessionProcessorCallback =
                new GameSessionProcessorCallback() {
                    @Override
                    public void gameFound(@NonNull final RemoteFoundGameData foundGameData) {

                    }

                    @Override
                    public void errorOccurred(@NonNull Error error) {

                    }

                    @Override
                    public void messageReceived(@NonNull RemoteMessage message) {
                        gottenMessage[0] = message;
                    }

                    @Override
                    public void onChattingPhaseIsOver() {

                    }

                    @Override
                    public void onChoosingPhaseIsOver(@NonNull ArrayList<MatchedUserProfileData> userProfileContactDataList) {

                    }

                    @Override
                    public void onDisconnection(final boolean isIncorrect) {

                    }
                };

        gameSessionProcessor.launch(gameSessionProcessorCallback, m_context);

        Assert.assertTrue(gameSessionProcessor.isRunning());

        Profile profile = Profile.getInstance("username", "contact", LanguageEnum.EN);

        Assert.assertTrue(gameSessionProcessor.startSearching(profile));

        Thread.sleep(ServerMockRoomSearchingState.C_DEFAULT_DURATION); // it should work. unfortunately it depends on the device;

        Assert.assertEquals(
                GameSessionStateType.SEARCHING,
                gameSessionProcessor.m_gameSessionState.getType());

        Thread.sleep(ServerMockRoomChattingState.C_DEFAULT_DURATION - 2000); // it should work. unfortunately it depends on the device;

        Assert.assertEquals(
                GameSessionStateType.CHATTING,
                gameSessionProcessor.m_gameSessionState.getType());

        Message userMessage = Message.getInstance("some message");

        Assert.assertTrue(gameSessionProcessor.sendMessage(userMessage));

        Thread.sleep(1000);

        Assert.assertEquals(
                userMessage.getText(),
                gottenMessage[0].getText());

        Assert.assertEquals(
                gameSessionProcessor.m_gameSessionState.getType(),
                GameSessionStateType.CHOOSING);

        webSocketServerMock.stop();
    }

    @Test
    public void testChoosing() throws InterruptedException {
        BlockingQueue<NetworkCallbackCommand> callbackCommands =
                new LinkedBlockingQueue<>();
        WebSocketServerMock webSocketServerMock =
                startWebSocketServer(callbackCommands, true, false);
        GameSessionProcessor gameSessionProcessor =
                GameSessionProcessorImpl.getInstance(
                        callbackCommands, webSocketServerMock);

        final List<MatchedUserProfileData>[] matchedUserProfileDataList = new List[]{null};

        GameSessionProcessorCallback gameSessionProcessorCallback =
                new GameSessionProcessorCallback() {
                    @Override
                    public void gameFound(@NonNull final RemoteFoundGameData foundGameData) {

                    }

                    @Override
                    public void errorOccurred(@NonNull Error error) {

                    }

                    @Override
                    public void messageReceived(@NonNull RemoteMessage message) {

                    }

                    @Override
                    public void onChattingPhaseIsOver() {

                    }

                    @Override
                    public void onChoosingPhaseIsOver(
                            @NonNull ArrayList<MatchedUserProfileData> userProfileContactDataList)
                    {
                        matchedUserProfileDataList[0] = userProfileContactDataList;
                    }

                    @Override
                    public void onDisconnection(final boolean isIncorrect) {

                    }
                };

        gameSessionProcessor.launch(gameSessionProcessorCallback, m_context);

        Assert.assertTrue(gameSessionProcessor.isRunning());

        Profile profile = Profile.getInstance("username", "contact", LanguageEnum.EN);

        Assert.assertTrue(gameSessionProcessor.startSearching(profile));

        Thread.sleep(ServerMockRoomSearchingState.C_DEFAULT_DURATION); // it should work. unfortunately it depends on the device;

        Assert.assertEquals(
                GameSessionStateType.SEARCHING,
                gameSessionProcessor.m_gameSessionState.getType());

        Thread.sleep(ServerMockRoomChattingState.C_DEFAULT_DURATION - 2000); // it should work. unfortunately it depends on the device;

        Assert.assertEquals(
                GameSessionStateType.CHATTING,
                gameSessionProcessor.m_gameSessionState.getType());

        Thread.sleep(ServerMockRoomChoosingState.C_DEFAULT_DURATION - 3000);

        Assert.assertEquals(
                gameSessionProcessor.m_gameSessionState.getType(),
                GameSessionStateType.CHOOSING);

        List<Integer> chosenUserIdList = new ArrayList<Integer>() {
            {
                add(0);
            }
        };

        Assert.assertTrue(gameSessionProcessor.chooseUsers(chosenUserIdList));

        Thread.sleep(5000);

        MatchedUserProfileData matchedUserProfileData =
                matchedUserProfileDataList[0].get(0);

        Assert.assertNotNull(matchedUserProfileData);
        Assert.assertEquals(
                WebSocketServerMock.C_DEFAULT_USER_ID,
                matchedUserProfileDataList[0].get(0).getId());
        Assert.assertEquals(
                profile.getContact(),
                matchedUserProfileData.getContact());

        webSocketServerMock.stop();
    }
}
