package value;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.omg.CORBA.portable.InvokeHandler;

import pipeline.Pipeline;
import pipeline.WrapperPipeline;
import service.Request;
import service.Response;
import util.MyClassLoader;
import util.XMLParser;

public class Context implements Value{
	private final File path;
	private Context nextContext;
	private final WrapperPipeline wrapperPipeline;
	private final StandardContext standardContext;
	public Context(File path, StandardContext standardContext){
		this.path = path;
		this.standardContext = standardContext;
		wrapperPipeline = new WrapperPipeline();
		initWrapper();
	}
	private void initWrapper(){
		wrapperPipeline.setURLMap(XMLParser.parser(new File(path.getAbsolutePath() + "\\web.xml")));
		ConcurrentHashMap<String, Class<?>> classMap = MyClassLoader.load(path);
		Iterator<String> it = classMap.keySet().iterator();
		while(it.hasNext()){
			String name = it.next();
			wrapperPipeline.addWrapper(name, classMap.get(name));
		}
		
		
	}
	public Pipeline getPipeline(){
		return wrapperPipeline;
	}
	public File getPath() {
		return path;
	}
	public Value getNext() {
		return nextContext;
	}
	public void setNext(Value next) {
		this.nextContext = (Context)next;
	}
	public void invoke(Request request, Response response) {
		String reqPath = request.getServletPath().split("/")[1];
		String contextPath = path.getName();
		if(reqPath.equals(contextPath)){
			standardContext.run(this, request, response);
			return;
		}
		nextContext.invoke(request, response);
		
	}
	
}
