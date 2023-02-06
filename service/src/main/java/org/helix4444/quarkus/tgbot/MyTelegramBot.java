package org.helix4444.quarkus.tgbot;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import io.quarkus.logging.Log;

@ApplicationScoped
public class MyTelegramBot
        extends TelegramLongPollingBot {

    @ConfigProperty(name = "bot.telegram.my-telegram-bot.username")
    String username;

    @ConfigProperty(name = "bot.telegram.my-telegram-bot.token")
    String token;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chatId = update.getMessage().getChatId();

            var message = new SendMessage();
            message.setChatId(chatId);
            message.setText(update.getMessage().getText());

            try {
                this.execute(message);
                Log.infov("Message send to chat with id = {0}", chatId);
            } catch (TelegramApiException e) {
                Log.errorv("Error to send message to chat with id = {0}", chatId);
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

}
