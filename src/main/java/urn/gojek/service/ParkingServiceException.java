package urn.gojek.service;

public class ParkingServiceException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ParkingServiceException(String message)
	{
		super(message);
	}
}
