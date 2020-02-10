package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.Init;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

public class Request implements HttpServletRequest {
	private HashMap<String, Object> attributeMap;
	private HashMap<String, String[]> parameterMap;
	private LinkedHashSet<Locale> locales;
	private HashMap<String, String> headers;
	private ArrayList<Cookie> cookieList;
	private RequestDispatcher requestDispatcher;
	private HttpSession session;
	private String characterEncoding;
	private String contextPath;
	protected Request(HashMap<String, String> headers, 
							ArrayList<Cookie> cookieList, 
							HashMap<String, String[]> parameterMap){
		this.headers = headers;
		this.cookieList = cookieList;
		this.parameterMap = parameterMap;	
	}
	public boolean hasCookie(){
		if(cookieList.size() == 0)
			return false;
		return true;
	}
	public void setSession(HttpSession session){
		this.session = session;
	}
	public String getSessionId(){
		int i = 0;
		while( ! cookieList.get(i).getName().equals("JSESSIONID")){
			i++;
		}
		return cookieList.get(i).getValue();
	}
	@Override
	public AsyncContext getAsyncContext() {
		return null;
	}

	@Override
	public Object getAttribute(String name) {
		return attributeMap.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		Set<String> nameSet = attributeMap.keySet();
		return Collections.enumeration(nameSet);
	}

	@Override
	public String getCharacterEncoding() {
		return characterEncoding;
	}

	@Override
	public int getContentLength() {
		return Integer.parseInt(headers.get("Content-Length"));
	}

	@Override
	public long getContentLengthLong() {
		return Long.parseLong(headers.get("Content-Length"));
	}

	@Override
	public String getContentType() {
		return headers.get("Content-Type");
	}

	@Override
	public DispatcherType getDispatcherType() {
		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public String getLocalAddr() {
		return null;
	}

	@Override
	public String getLocalName() {
		return null;
	}

	@Override
	public int getLocalPort() {
		return 0;
	}

	@Override
	public Locale getLocale() {
		return locales.iterator().next();
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return Collections.enumeration(locales);
	}

	@Override
	public String getParameter(String name) {
		String[] temp = parameterMap.get(name);
		return temp[0];
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(parameterMap.keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		return parameterMap.get(name);
	}

	@Override
	public String getProtocol() {
		return headers.get("protocol");
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return null;
	}

	@Override
	public String getRealPath(String path) {
		return null;
	}

	@Override
	public String getRemoteAddr() {
		return null;
	}

	@Override
	public String getRemoteHost() {
		return null;
	}

	@Override
	public int getRemotePort() {
		return 0;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return requestDispatcher;
	}

	@Override
	public String getScheme() {
		return null;
	}

	@Override
	public String getServerName() {
		return headers.get("Host").split(":")[0];
	}

	@Override
	public int getServerPort() {
		String[] temp = headers.get("Host").split(":");
		if(temp.length == 2) return Integer.parseInt(temp[1]);
		else return -1;
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public boolean isAsyncStarted() {
		return false;
	}

	@Override
	public boolean isAsyncSupported() {
		return false;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public void removeAttribute(String name) {
		attributeMap.remove(name);
	}

	@Override
	public void setAttribute(String name, Object o) {
		attributeMap.put(name, o);
	}

	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
		characterEncoding = env;
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
			throws IllegalStateException {
		return null;
	}

	@Override
	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		return false;
	}

	@Override
	public String changeSessionId() {
		return null;
	}

	@Override
	public String getAuthType() {
		return null;
	}

	@Override
	public String getContextPath() {
		return contextPath;
	}

	@Override
	public Cookie[] getCookies() {
		return	(Cookie[])cookieList.toArray();
	}

	@Override
	public long getDateHeader(String name) {
		return 0;
	}

	@Override
	public String getHeader(String name) {
		return headers.get(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		Set<String> set = headers.keySet();
		return Collections.enumeration(set);
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		return null;
	}

	@Override
	public int getIntHeader(String name) {
		return 0;
	}

	@Override
	public String getMethod() {
		return headers.get("method");
	}

	@Override
	public Part getPart(String name) throws IOException, ServletException {
		return null;
	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		return null;
	}

	@Override
	public String getPathInfo() {
		return null;
	}

	@Override
	public String getPathTranslated() {
		return null;
	}

	@Override
	public String getQueryString() {
		return null;
	}

	@Override
	public String getRemoteUser() {
		return null;
	}

	@Override
	public String getRequestURI() {
		return null;
	}

	@Override
	public StringBuffer getRequestURL() {
		return new StringBuffer(headers.get("Referer"));
	}

	@Override
	public String getRequestedSessionId() {
		return null;
	}

	@Override
	public String getServletPath() {
		return headers.get("servletPath");
	}

	@Override
	public HttpSession getSession() {
		return session;
	}

	@Override
	public HttpSession getSession(boolean create) {
		return getSession();
	}

	@Override
	public Principal getUserPrincipal() {
		return null;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return false;
	}

	@Override
	public boolean isUserInRole(String role) {
		return false;
	}

	@Override
	public void login(String username, String password) throws ServletException {

	}

	@Override
	public void logout() throws ServletException {

	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
		return null;
	}

}
