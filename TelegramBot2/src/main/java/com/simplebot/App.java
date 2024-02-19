package com.simplebot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import TelegramBot2.TelegramBot2.MyBotHandler;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try
        {
        	
        	Bot bot = new Bot(new XmlBotConfig(App.class.getResource("/").getPath()+"bot_config.xml"),new MyBotHandler());
            telegramBotsApi.registerBot(bot);
            System.out.println("Bot started successfully.");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
