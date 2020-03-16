package pipeline;

import java.io.File;
import java.net.URLClassLoader;

import service.Container;
import value.Context;
import value.StandardContext;
import value.Value;


public class ContextPipeline implements Pipeline {
	private Context firstContext;
	//最终处理Context的对象
	private final StandardContext standardContext;
	public ContextPipeline(Container container) {
		standardContext = new StandardContext(container);
	}
	@Override
	public Value getFirst(){
		return firstContext;
	}
	public void addContext(File path){
		Context newContext = new Context(path, standardContext);
		synchronized (this) {
			if(firstContext == null){
				firstContext = newContext;
			}else{
				Context temp = firstContext;
				while(temp.getNext() == null){
					temp.setNext(newContext);
				}
			}
		}
	
	}
}
