package org.helix4444.quarkus.tgbot.userform.handler;

import org.helix4444.quarkus.tgbot.userform.repository.UserFormRepository;
import org.helix4444.quarkus.tgbot.userform.state.UserFormState;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import io.quarkus.logging.Log;

import static org.helix4444.quarkus.tgbot.userform.state.UserFormState.*;
import org.helix4444.quarkus.tgbot.bot.BotUtils;

public class UserFormFinishHandler
        extends UserFormHandler {

    public UserFormFinishHandler(UserFormHandler nextFieldHandler) {
        super(nextFieldHandler);
    }

    @Override
    public UserFormState handleNextField(
            TelegramLongPollingBot bot,
            UserFormRepository repository,
            Update update,
            UserFormState state) {
        Log.infov("STEP - {0}", state);

        if (FINISH.equals(state)) {
            var chatId = update.getMessage().getChatId();

            var userForm = repository.getUserFormByChatId(chatId);

            var message = "Данные анкеты с номером - " + userForm.getId();
            BotUtils.sendSimpleTextMessage(bot, chatId, message);

            var lastNameMsg = "Фамилия: " + userForm.getLastName();
            BotUtils.sendSimpleTextMessage(bot, chatId, lastNameMsg);

            var firstNameMsg = "Имя: " + userForm.getFirstName();
            BotUtils.sendSimpleTextMessage(bot, chatId, firstNameMsg);

            var middleNameMsg = "Отчество: " + userForm.getMiddleName();
            BotUtils.sendSimpleTextMessage(bot, chatId, middleNameMsg);
        }

        return super.handleNextField(bot, repository, update, state);
    }

}
