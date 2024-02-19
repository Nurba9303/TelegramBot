package com.simplebot;
import java.util.*;
import org.dom4j.Node;
public class Request {

	private Request parent;
	/*private String path;*/
	private String key;
	private String keyPath;
	private String response;
	private List<Simple> simples=new ArrayList<Simple>();
	private List<Request> requests=new ArrayList<Request>();
	private KeyBoard keyBoard;
	private String link;
	
	public Request(Request parent,Node doc,String keyPath)
	{
		this.parent=parent;
		this.key=doc.valueOf("@key");
		this.keyPath=keyPath;
		System.out.println("Load keyPath:"+keyPath);

		Node resp=doc.selectSingleNode("response");
		if(resp!=null)
			response=resp.getText();
		else
			response="";
		
		Node linkNode=doc.selectSingleNode("link");
		if(linkNode!=null)
		{
			link=linkNode.getText();
			return;
		}
		
		List<Node> smN=doc.selectNodes("simple");
		Iterator<Node> it=smN.iterator();
		while(it.hasNext())
		{
			Node sm=it.next();
			Simple simple=new Simple();
			simple.setKey(sm.valueOf("@key"));
			simple.setValue(sm.getText());
			simples.add(simple);
		}
		
		List<Node> rqN=doc.selectNodes("request");
		it=rqN.iterator();
		while(it.hasNext())
		{
			Node n=it.next();
			requests.add(new Request(this,n,keyPath+"/"+n.valueOf("@key")));
		}

		Node kbn=doc.selectSingleNode("keyboard");
		if(kbn!=null)
		{
			keyBoard=new KeyBoard();
			List<Node> rows=doc.selectNodes("keyboard/row");
			it=rows.iterator();
			while(it.hasNext())
			{
				it.next();
				Row r=new Row();
				
				List<Node> cols=doc.selectNodes("keyboard/row/request");
				Iterator<Node> cit=cols.iterator();
				while(cit.hasNext())
				{
					Node n=cit.next();
					r.getRequests().add(new Request(this,n,keyPath+"/"+n.valueOf("@key")));
				}
				
				keyBoard.getRows().add(r);
			}
			
		}
		
		
	}
	
	
	public Request findByKeyPath(String keyPath)
	{
		if(this.keyPath.equals(keyPath)) return this;
		
		Iterator<Request> it=requests.iterator();
		while(it.hasNext())
		{
			Request rq=it.next();
			Request f=rq.findByKeyPath(keyPath);
			if(f!=null) return f;
		}
		if(keyBoard!=null)
		{
			Iterator<Row> rit=keyBoard.getRows().iterator();
			while(rit.hasNext())
			{
				Iterator<Request> rqit=rit.next().getRequests().iterator();
				while(rqit.hasNext())
				{
					Request rq=rqit.next();
					Request f=rq.findByKeyPath(keyPath);
					if(f!=null) return f;
				}
			}
		}
		
		return null;
		
	}
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public List<Simple> getSimples() {
		return simples;
	}
	public void setSimples(List<Simple> simples) {
		this.simples = simples;
	}
	public List<Request> getRequests() {
		return requests;
	}
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
	public KeyBoard getKeyBoard() {
		return keyBoard;
	}
	public void setKeyBoard(KeyBoard keyBoard) {
		this.keyBoard = keyBoard;
	}

	public Request getParent() {
		return parent;
	}


	public void setParent(Request parent) {
		this.parent = parent;
	}


	public String getKeyPath() {
		return keyPath;
	}


	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}
	
	
	
}
