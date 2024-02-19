package com.simplebot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Bot extends TelegramLongPollingBot 
{
	private BotConfig cfg;
	private Map<Long,BotClient> clientMap=new HashMap<Long,BotClient>();
	private BotHandler handler;
	
	/*private ReplyKeyboardMarkup keyMarkup=new ReplyKeyboardMarkup();*/
	
	
	public Bot(BotConfig cfg,BotHandler handler)
	{
		this.cfg=cfg;
		this.handler=handler;
		
	}
	

	public void onUpdateReceived(Update update) {
		
		if(clientMap.size()>100000)
			clientMap.clear();
		
		long chatId = update.getMessage().getChatId();
		BotClient client=clientMap.get(chatId);
		if(client==null)
		{
			client=new BotClient(cfg.getRootRequest());
			clientMap.put(chatId, client);
		}
		
		try {
			
			if(!handler.handle(this,update, client))
				execute(client.onRevMessage(update));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
			
		
	}

	public String getBotUsername() {
		return cfg.getUserName();
	}

	@Override
	public String getBotToken() {
		return cfg.getToken();
	}
	
	
	
}
