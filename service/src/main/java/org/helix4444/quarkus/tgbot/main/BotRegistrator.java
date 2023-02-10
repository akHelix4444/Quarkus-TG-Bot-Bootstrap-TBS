package org.helix4444.quarkus.tgbot.main;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.helix4444.quarkus.tgbot.bot.MyTelegramBot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;

@Startup
@ApplicationScoped
public class BotRegistrator {

    @Inject
    MyTelegramBot myTelegramBot;

    @PostConstruct
    protected void telegramBotRegistration() {
        this.registerTelegramBot(this.myTelegramBot);
    }

    private void registerTelegramBot(TelegramLongPollingBot bot) {
        try {
            var telegramBotsAPI = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsAPI.registerBot(bot);
            Log.infov("{0} - Telegram bot registration complete!", bot.getBotUsername());
        } catch (TelegramApiException e) {
            Log.errorv("{0} - Telegram bot registration failed.", bot.getBotUsername());
            e.printStackTrace();
        }
    }

}
