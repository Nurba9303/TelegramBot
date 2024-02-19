package TelegramBot2.TelegramBot2;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.simplebot.BotClient;
import com.simplebot.BotHandler;
public class MyBotHandler extends BotHandler {

	@Override
	public boolean handle(TelegramLongPollingBot bot, Update update, BotClient client) {
		System.out.println("My-Key-Path:"+client.getKeyPath());
	
		return false;
	}

}
