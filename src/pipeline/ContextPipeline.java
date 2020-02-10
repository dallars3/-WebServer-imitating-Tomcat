package pipeline;

import java.io.File;
import java.net.URLClassLoader;

import service.Container;
import value.Context;
import value.StandardContext;
import value.Value;


public class ContextPipeline implements Pipeline {
	private final Container container;
	private Context firstContext;
	private StandardContext standardContext;
	public ContextPipeline(Container container) {
		this.container = container;
		standardContext = new StandardContext(container);
	}
	@Override
	public Value getFirst(){
		return firstContext;
	}
	public void addContext(File path){
		
		if(firstContext == null){
			firstContext = new Context(path, standardContext);
		}else{
			Context temp = firstContext;
			while(temp.getNext() == null){
				temp.setNext(new Context(path, standardContext));
			}
		}
	}
}
