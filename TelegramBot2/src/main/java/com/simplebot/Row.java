package com.simplebot;
import java.util.*;
public class Row {
	
	private List<Request> requests=new ArrayList<Request>();

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

}
