package value;

import javax.servlet.http.HttpServlet;

import service.Request;
import service.Response;

public class StandardWrapper implements StandardValue {
	@Override
	public void run(Value value, Request request, Response response){
		Class<?> servletClass = ((ServletWrapper)value).getServletClass();
		Object a;
		try {
			a = servletClass.newInstance();
			((HttpServlet)a).service(request, response);
			//Method method = servletClass.getDeclaredMethod("service", ServletRequest.class, ServletResponse.class);
			//method.invoke(a, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
