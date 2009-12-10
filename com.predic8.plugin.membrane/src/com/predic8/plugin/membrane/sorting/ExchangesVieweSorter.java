package com.predic8.plugin.membrane.sorting;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.predic8.membrane.core.exchange.HttpExchange;

public class ExchangesVieweSorter extends ViewerSorter {

	public static final int SORT_TARGET_TIME = 0;
	
	public static final int SORT_TARGET_RULE = 1;
	
	public static final int SORT_TARGET_METHOD = 2;
	
	public static final int SORT_TARGET_PATH = 3;
	
	public static final int SORT_TARGET_CLIENT = 4;
	
	public static final int SORT_TARGET_SERVER = 5;
	
	public static final int SORT_TARGET_CONTENT_TYPE = 6;
	
	public static final int SORT_TARGET_STATUS_CODE = 7;
	
	public static final int SORT_TARGET_REQUEST_CONTENT_LENGTH = 8;
	
	public static final int SORT_TARGET_RESPONSE_CONTENT_LENGTH = 9;
	
	public static final int SORT_TARGET_DURATION = 10;
	
	private int sortTarget = -1;  

		
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		
		if (sortTarget < 0)
			return super.compare(viewer, e1, e2);
		
		try {
			
			HttpExchange obj1 = (HttpExchange)e1;
			HttpExchange obj2 = (HttpExchange)e2;
			
			switch (sortTarget) {
			case SORT_TARGET_TIME:
				return obj1.getTime().compareTo(obj2.getTime());
				
			case SORT_TARGET_RULE:
				return obj1.getRule().getRuleKey().getPort() - obj2.getRule().getRuleKey().getPort();
				
			case SORT_TARGET_METHOD:
				return obj1.getRequest().getMethod().compareTo(obj2.getRequest().getMethod());
				
			case SORT_TARGET_PATH:
				return obj1.getRequest().getUri().compareTo(obj2.getRequest().getUri());
				
				
			case SORT_TARGET_SERVER:
				return obj1.getRequest().getHeader().getHost().compareTo(obj2.getRequest().getHeader().getHost());
				
			case SORT_TARGET_STATUS_CODE:
				if (obj1.getResponse() == null && obj2.getResponse() == null)
					return 0;
				if (obj1.getResponse() != null && obj2.getResponse() == null)
					return 1;
				if (obj1.getResponse() == null && obj2.getResponse() != null)
					return -1;
				return obj1.getResponse().getStatusCode() - obj2.getResponse().getStatusCode();
				
			case SORT_TARGET_REQUEST_CONTENT_LENGTH:
				return obj1.getRequest().getHeader().getContentLength() - obj2.getRequest().getHeader().getContentLength();
				
			case SORT_TARGET_RESPONSE_CONTENT_LENGTH:
				if (obj1.getResponse() == null && obj2.getResponse() == null)
					return 0;
				if (obj1.getResponse() != null && obj2.getResponse() == null)
					return 1;
				if (obj1.getResponse() == null && obj2.getResponse() != null)
					return -1;
				return obj1.getResponse().getHeader().getContentLength() - obj2.getResponse().getHeader().getContentLength();
				
			case SORT_TARGET_DURATION:
				return (int)((obj1.getTimeResReceived() - obj1.getTimeReqSent()) -  (obj2.getTimeResReceived() - obj2.getTimeReqSent()));
				
			default:
					return super.compare(viewer, e1, e2);
				
			}
			
		} catch (Exception e) {
			System.out.println("sorting failed due to exception.");
		}
		return super.compare(viewer, e1, e2);
	}

	public int getSortTarget() {
		return sortTarget;
	}

	public void setSortTarget(int sortTarget) {
		this.sortTarget = sortTarget;
	}
	
	
	
	
}