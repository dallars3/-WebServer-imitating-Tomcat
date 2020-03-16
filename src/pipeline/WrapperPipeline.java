package pipeline;

import java.util.HashMap;

import value.Context;
import value.ServletWrapper;
import value.StandardWrapper;
import value.Value;

public class WrapperPipeline implements Pipeline {
	private HashMap<String, String> urlMap;
	private ServletWrapper firstWrapper;
	private StandardWrapper standardWrapper;
	public WrapperPipeline(){
		standardWrapper = new StandardWrapper();
	}
	public void setUrlMap(HashMap<String, String> urlMap){
		this.urlMap = urlMap;
	}
	//将servlet封装入对应Wrapper
	public void addWrapper(String name, Class<?> servletClass){
		if(firstWrapper == null){
			firstWrapper = new ServletWrapper(urlMap.get(name), servletClass, standardWrapper);
		}else{
			ServletWrapper temp = firstWrapper;
			while(temp.getNext() == null){
				temp.setNext(new ServletWrapper(urlMap.get(name), servletClass, standardWrapper));
			}
		}
		
		
	}
	@Override
	public Value getFirst() {
		return firstWrapper;
	}

}
