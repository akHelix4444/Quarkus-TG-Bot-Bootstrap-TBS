package org.helix4444.quarkus.tgbot.userform.handler;

import org.helix4444.quarkus.tgbot.bot.BotUtils;
import org.helix4444.quarkus.tgbot.userform.entity.UserForm;
import org.helix4444.quarkus.tgbot.userform.repository.UserFormRepository;
import org.helix4444.quarkus.tgbot.userform.state.UserFormState;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import io.quarkus.logging.Log;
import static org.helix4444.quarkus.tgbot.userform.state.UserFormState.*;

public class UserFormStartHandler
        extends UserFormHandler {

    public UserFormStartHandler(UserFormHandler nextFieldHandler) {
        super(nextFieldHandler);
    }

    @Override
    public UserFormState handleNextField(
            TelegramLongPollingBot bot,
            UserFormRepository repository,
            Update update,
            UserFormState state) {
        Log.infov("STEP - {0}", state);

        if (START.equals(state)) {
            var chatId = update.getMessage().getChatId();

            var newUserForm = new UserForm();
            newUserForm.setChatId(chatId);
            repository.saveUserForm(newUserForm);

            var message = "Создана новая анкета с номером - " + newUserForm.getId();
            BotUtils.sendSimpleTextMessage(bot, chatId, message);

            return super.handleNextField(bot, repository, update, REQUEST_LAST_NAME);
        }

        return super.handleNextField(bot, repository, update, state);
    }

}
