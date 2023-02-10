package org.helix4444.quarkus.tgbot.userform.handler;

import org.helix4444.quarkus.tgbot.bot.BotUtils;
import org.helix4444.quarkus.tgbot.userform.repository.UserFormRepository;
import org.helix4444.quarkus.tgbot.userform.state.UserFormState;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import io.quarkus.logging.Log;

import static org.helix4444.quarkus.tgbot.userform.state.UserFormState.*;

public class UserFormLastNameHandler
        extends UserFormHandler {

    public UserFormLastNameHandler(UserFormHandler nextFieldHandler) {
        super(nextFieldHandler);
    }

    @Override
    public UserFormState handleNextField(
            TelegramLongPollingBot bot,
            UserFormRepository repository,
            Update update,
            UserFormState state) {
        Log.infov("STEP - {0}", state);

        if (REQUEST_LAST_NAME.equals(state)) {
            var chatId = update.getMessage().getChatId();
            BotUtils.sendSimpleTextMessage(bot, chatId, "Введите фамилию:");

            return READ_LAST_NAME;
        }

        if (READ_LAST_NAME.equals(state)) {
            var chatId = update.getMessage().getChatId();
            var newLastName = update.getMessage().getText();
            repository.updateUserFormLastName(newLastName, chatId);

            return super.handleNextField(bot, repository, update, REQUEST_FIRST_NAME);
        }

        return super.handleNextField(bot, repository, update, state);
    }

}
