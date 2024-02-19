package com.simplebot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class BotHandler {

	public abstract boolean handle(TelegramLongPollingBot bot,Update update,BotClient client);
	
	
}
