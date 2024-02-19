package com.simplebot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class BotClient extends Client {

	private String keyPath="__default";

	public BotClient(Request rootRequest) {
		super(rootRequest);
		
	}

	@Override
	public SendMessage onRevMessage(Update update) {
		

		Request request=rootRequest;
		
        long chatId = update.getMessage().getChatId();
        
        
        try{
        	/*SendPhoto message = new SendPhoto().setPhoto("photo", new FileInputStream(new File("g://static1.png")));
        	message.setChatId(chat_id);*/
        	boolean excuted=false;
        	String reqmsg=update.getMessage().getText();
        	Request curReq=request.findByKeyPath(keyPath);
        	Iterator<Simple> sit=curReq.getSimples().iterator();
        	SendMessage msg=new SendMessage();
        	msg.setChatId(chatId);
        	
        	while(sit.hasNext())
        	{
        		Simple s=sit.next();
        		if(s.getKey().contains(reqmsg))
        		{
        			
                	msg.setText(s.getValue());
                	excuted=true;
                	break;
        		}
        	}
        	
        	if(!excuted)
        	{
        		Iterator<Request> requs=curReq.getRequests().iterator();
        		while(requs.hasNext())
        		{
        			Request rq=requs.next();
        			if(rq.getKey().contains(reqmsg))
        			{
        				if(rq.getLink()!=null)
        					keyPath=rq.getLink();
        				else	
        					keyPath=rq.getKeyPath();
        				
                    	msg.setText(rq.getResponse());
                    	excuted=true;
                    	break;
        			}
        		}
        		
        		
        	}
        	
        	if(!excuted)
        	{
            	KeyBoard keyBd=curReq.getKeyBoard();
            	if(keyBd!=null)
            	{
                	Iterator<Row> rit=keyBd.getRows().iterator();
                	while(rit.hasNext())
                	{
                		Row row=rit.next();
                		Iterator<Request> cols=row.getRequests().iterator();
                		while(cols.hasNext())
                		{
                			Request rq=cols.next();
                			if(rq.getKey().contains(reqmsg))
                			{
                				if(rq.getLink()!=null)
                					keyPath=rq.getLink();
                				else	
                					keyPath=rq.getKeyPath();
                            	msg.setText(rq.getResponse());
                            	excuted=true;
                            	break;
                			}
                		}
                		if(excuted)
                			break;

                	}
            	}

        		
        		
        	}
        	

        	
/*        	if(!excuted)
        	{
        		if(request.findByKeyPath(reqmsg)!=null)
        		{
        			keyPath=reqmsg;
        			excuted=true;
        		}
        			
        	}*/
        	
        	ReplyKeyboardMarkup keyMarkup=new ReplyKeyboardMarkup();
        	msg.setReplyMarkup(keyMarkup);
        	keyMarkup.setSelective(true);
        	keyMarkup.setResizeKeyboard(true);
        	keyMarkup.setOneTimeKeyboard(false);
        	
        	List<KeyboardRow> keyBoard=new ArrayList<KeyboardRow>();
        	curReq=request.findByKeyPath(keyPath);
        	
        	KeyBoard keyBd=curReq.getKeyBoard();
        	if(keyBd!=null)
        	{
        		Iterator<Row> rit=keyBd.getRows().iterator();
            	while(rit.hasNext())
            	{
            		Row row=rit.next();
            		KeyboardRow kr=new KeyboardRow();
            		
            		Iterator<Request> cols=row.getRequests().iterator();
            		while(cols.hasNext())
            		{
            			Request cr=cols.next();
            			
            			KeyboardButton btn=new KeyboardButton();
            			btn.setText(cr.getKey());
            			kr.add(btn);
            		}
            		
            		keyBoard.add(kr);
            	}
            	
            	keyMarkup.setKeyboard(keyBoard);
            	msg.setReplyMarkup(keyMarkup);
        	}
        	else
        	{
        		msg.setReplyMarkup(new ReplyKeyboardRemove());
        	}
        	
        	
        	
        	if(msg.getText()==null || msg.getText().trim().equals(""))
        		msg.setText(reqmsg);
        	
        	return msg;
        	
        	
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return null;
	}

	public String getKeyPath() {
		return keyPath;
	}

}
