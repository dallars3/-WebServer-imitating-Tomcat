package value;

import java.io.File;
import java.io.IOException;

import service.Container;
import service.Request;
import service.Response;

public class StandardContext implements StandardValue{
	private final Container container;
	public StandardContext(Container container){
		this.container = container;
	}
	
	public void run(Value value, Request request, Response response){
		response.setContext(((Context)value).getPath().getAbsolutePath());
		String file = findFile((Context)value, request.getServletPath());
		if(file != null){
			try {
				response.sendRedirect(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		((Context)value).getPipeline().getFirst().invoke(request, response);
		
	}
	private String findFile(Context context, String filePath){
		File file = new File(context.getPath().getParent() + filePath.replace('/', '\\'));
		if(file.exists()){
			String[] temp = filePath.replace('/', '\\').split("\\\\");
			StringBuilder sb = new StringBuilder();
			
			for(int i = 2; i < temp.length; i ++){
				sb.append("\\");
				sb.append(temp[i]);
			}
			return new String(sb);
		}
		return null;
	}

}
