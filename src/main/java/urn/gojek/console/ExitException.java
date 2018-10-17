package urn.gojek.console;

public class ExitException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ExitException(String message)
	{
		super(message);
	}
	
	public ExitException()
	{
		
	}
}
