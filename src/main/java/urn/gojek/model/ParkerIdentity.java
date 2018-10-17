package urn.gojek.model;

public class ParkerIdentity {
	private String registrationNumber;
	private String color;
	private int slot;
	public ParkerIdentity(String registrationNumber,String color,int slot) {
		this.registrationNumber = registrationNumber;
		this.color = color;
		this.slot = slot;
	}
	
	public String getColor() {
		return color;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public int getSlot() {
		return slot;
	}
}
