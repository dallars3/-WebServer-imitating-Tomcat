package value;

import service.Request;
import service.Response;

public interface Value {
	public Value getNext();
	public void setNext(Value next);
	public void invoke(Request request, Response response);
}
