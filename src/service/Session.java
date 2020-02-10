package service;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class Session implements HttpSession {
	private HashMap<String, Object> attributesMap;
	private String sessionId;
	public Session(String id) {
		sessionId = id;
		attributesMap = new HashMap<String, Object>();
	}
	@Override
	public Object getAttribute(String name) {
		return attributesMap.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return Collections.enumeration(attributesMap.keySet());
	}

	@Override
	public long getCreationTime() {
		return 0;
	}

	@Override
	public String getId() {
		return sessionId;
	}

	@Override
	public long getLastAccessedTime() {
		return 0;
	}

	@Override
	public int getMaxInactiveInterval() {
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getValueNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invalidate() {

	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public void putValue(String name, Object value) {

	}

	@Override
	public void removeAttribute(String name) {
		attributesMap.remove(name);
	}

	@Override
	public void removeValue(String name) {

	}

	@Override
	public void setAttribute(String name, Object value) {
		attributesMap.put(name, value);
	}

	@Override
	public void setMaxInactiveInterval(int interval) {

	}

}
