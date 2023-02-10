package org.helix4444.quarkus.tgbot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import io.quarkus.logging.Log;

public class BotUtils {

    private BotUtils() {
        // This class can't be instantiated
    }

    public static void sendSimpleTextMessage(
            TelegramLongPollingBot bot,
            Long chatId,
            String textMessage) {
        var message = new SendMessage();

        message.setChatId(chatId);
        message.setText(textMessage);
        message.enableMarkdown(true);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            Log.errorv("Error to send message to chat with id = {0}", chatId);
            e.printStackTrace();
        }
    }

}
