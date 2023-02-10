package org.helix4444.quarkus.tgbot.userform.handler;

import org.helix4444.quarkus.tgbot.userform.repository.UserFormRepository;
import org.helix4444.quarkus.tgbot.userform.state.UserFormState;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class UserFormHandler {

    private UserFormHandler nextFieldHandler;

    protected UserFormHandler(UserFormHandler nextFieldHandler) {
        this.nextFieldHandler = nextFieldHandler;
    }

    public UserFormState handleNextField(
            TelegramLongPollingBot bot,
            UserFormRepository repository,
            Update update,
            UserFormState state) {
        if (null != this.nextFieldHandler) {
            return this.nextFieldHandler.handleNextField(bot, repository, update, state);
        }

        return state;
    }

}
