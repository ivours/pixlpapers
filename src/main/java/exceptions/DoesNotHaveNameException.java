package exceptions;

public class DoesNotHaveNameException extends RuntimeException{
	
	public DoesNotHaveNameException(String message){
		super(message);
	}
}
