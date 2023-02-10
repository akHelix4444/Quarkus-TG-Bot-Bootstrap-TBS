package org.helix4444.quarkus.tgbot.userform.handler;

import org.helix4444.quarkus.tgbot.bot.BotUtils;
import org.helix4444.quarkus.tgbot.userform.repository.UserFormRepository;
import org.helix4444.quarkus.tgbot.userform.state.UserFormState;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import io.quarkus.logging.Log;

import static org.helix4444.quarkus.tgbot.userform.state.UserFormState.*;

public class UserFormFirstNameHandler
        extends UserFormHandler {

    public UserFormFirstNameHandler(UserFormHandler nextFieldHandler) {
        super(nextFieldHandler);
    }

    @Override
    public UserFormState handleNextField(
            TelegramLongPollingBot bot,
            UserFormRepository repository,
            Update update,
            UserFormState state) {
        Log.infov("STEP - {0}", state);

        if (REQUEST_FIRST_NAME.equals(state)) {
            var chatId = update.getMessage().getChatId();
            BotUtils.sendSimpleTextMessage(bot, chatId, "Введите имя:");

            return READ_FIRST_NAME;
        }

        if (READ_FIRST_NAME.equals(state)) {
            var chatId = update.getMessage().getChatId();
            var newFirstName = update.getMessage().getText();
            repository.updateUserFormFirstName(newFirstName, chatId);

            return super.handleNextField(bot, repository, update, REQUEST_MIDDLE_NAME);
        }

        return super.handleNextField(bot, repository, update, state);
    }

}
