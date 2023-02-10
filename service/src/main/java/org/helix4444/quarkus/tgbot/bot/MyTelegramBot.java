package org.helix4444.quarkus.tgbot.bot;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.helix4444.quarkus.tgbot.userform.flow.UserFormWorkflow;
import org.helix4444.quarkus.tgbot.userform.repository.UserFormRepository;
import org.helix4444.quarkus.tgbot.userform.state.UserFormState;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import io.quarkus.logging.Log;
import static java.util.Collections.synchronizedMap;
import static org.helix4444.quarkus.tgbot.userform.state.UserFormState.*;

@ApplicationScoped
public class MyTelegramBot
        extends TelegramLongPollingBot {

    private static final String USER_FORM_WORD = "АНКЕТА";

    @ConfigProperty(name = "bot.telegram.my-telegram-bot.username")
    String username;

    @ConfigProperty(name = "bot.telegram.my-telegram-bot.token")
    String token;

    private final Map<Long, UserFormState> chatId2StateMap = synchronizedMap(new HashMap<>());
    private final Map<Long, String> chatId2Command = synchronizedMap(new HashMap<>());

    @Inject
    UserFormWorkflow userFormWorkflow;

    @Inject
    UserFormRepository userFormRepository;

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chatId = update.getMessage().getChatId();
            var userMessage = update.getMessage().getText();

            var prevState = this.chatId2StateMap.get(chatId);
            var command = this.chatId2Command.get(chatId);

            Log.infov("Before update: state - {0}, command - {1}", prevState, command);

            if (null == prevState && USER_FORM_WORD.equals(userMessage)) {
                command = USER_FORM_WORD;
                this.chatId2Command.put(chatId, command);

                prevState = UserFormState.START;
                this.chatId2StateMap.put(chatId, prevState);
            }

            if (USER_FORM_WORD.equals(command)) {
                var nextState = this.userFormWorkflow
                        .getUserFormHandler()
                        .handleNextField(this, this.userFormRepository, update, prevState);
                this.resetFinalState(chatId, nextState);
            }

            if (null == command && null == prevState) {
                var commandRequestMsg = "Введите команду - " + USER_FORM_WORD;
                BotUtils.sendSimpleTextMessage(this, chatId, commandRequestMsg);
            }
        }
    }

    private void resetFinalState(Long chatId, UserFormState state) {
        if (FINISH.equals(state)) {
            this.chatId2StateMap.remove(chatId);
            var lastCommand = this.chatId2Command.remove(chatId);
            Log.infov("Clear data: command - {0}", lastCommand);
        } else {
            this.chatId2StateMap.computeIfPresent(chatId, (k, v) -> state);
        }
    }

}
