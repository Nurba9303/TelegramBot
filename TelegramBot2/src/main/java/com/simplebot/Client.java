package com.simplebot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Client {
	protected Request rootRequest;
	public Client(Request rootRequest)
	{
		this.rootRequest=rootRequest;
	}
	public abstract SendMessage onRevMessage(Update update);
	public Request getRootRequest()
	{
		return rootRequest;
	}
}
