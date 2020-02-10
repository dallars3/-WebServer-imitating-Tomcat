package value;

import service.Request;
import service.Response;

public class ServletWrapper implements Value{
	private final String url;
	private final Class<?> servletClass;
	private ServletWrapper nextWrapper;
	private final StandardWrapper standardWrapper;
	public ServletWrapper(String url, Class<?> servletClass, StandardWrapper standardWrapper){
		this.url = url;
		this.servletClass = servletClass;
		this.standardWrapper = standardWrapper;
	}
	protected Class<?> getServletClass() {
		return servletClass;
	}
	@Override
	public Value getNext() {
		return nextWrapper;
	}

	@Override
	public void setNext(Value next) {
		nextWrapper = (ServletWrapper)next;
	}

	@Override
	public void invoke(Request request, Response response) {
		String[] servletPath = request.getServletPath().split("/");
		StringBuilder sb = new StringBuilder();
		for(int i = 2; i < servletPath.length; i ++){
			sb.append("/");
			sb.append(servletPath[i]);
			
		}
		if(new String(sb).equals(url)){
			standardWrapper.run(this, request, response);
			return;
		}
		System.out.println(sb + "  " + url);
		nextWrapper.invoke(request, response);
	}
	
}
