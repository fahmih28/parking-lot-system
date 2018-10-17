package urn.gojek.service;

import java.util.ArrayList;
import java.util.List;

import urn.gojek.console.table.Table;
import urn.gojek.model.ParkerIdentity;


public class ParkingService {
	private ParkerIdentity[] currentParkedIntentites;
	private boolean parkingLotCreated;
	private int pointerCurrentEmptySlot = 0;
	private Table tableStatus;

	public ParkingService() {
		tableStatus = new Table(new String[] { "Slot No.", "Registration No.", "Color" });
	}

	public int createParkingLot(int sizeOfCars) {
		if (sizeOfCars < 1) {
			throw new ParkingServiceException("Size Of Cars For Parking Lot Must Be Greater Than Zero");
		}
		
		if(parkingLotCreated)
		{
			throw new ParkingServiceException("Parking Lot Already Created");
		}
		parkingLotCreated = true;
		currentParkedIntentites = new ParkerIdentity[sizeOfCars];
		return sizeOfCars;
	}

	private void checkIfParkingLotIsCreated() {
		if (!parkingLotCreated)
			throw new ParkingServiceException("You Must Create Parking Lot First");
	}

	/**
	 * 
	 * @param registrationNumber
	 *            of car
	 * @param color
	 *            of car
	 * @return no of allocated slot
	 */
	public int park(String registrationNumber, String color) {
		checkIfParkingLotIsCreated();
		if (pointerCurrentEmptySlot == currentParkedIntentites.length)
			throw new ParkingServiceException("Sorry, Parking Lot Is Full");

		ParkerIdentity parker = new ParkerIdentity(registrationNumber, color,
				pointerCurrentEmptySlot + 1);
		currentParkedIntentites[pointerCurrentEmptySlot] = parker;
		
		boolean found = false;
		for(int i = pointerCurrentEmptySlot;i < currentParkedIntentites.length;i++)
		{
			if(currentParkedIntentites[i] == null)
			{
				pointerCurrentEmptySlot = i;
				found = true;
				break;
			}
		}
		
		if(!found)
		{
			pointerCurrentEmptySlot = currentParkedIntentites.length;
		}
		return parker.getSlot();
	}

	public int leave(int slotNumber) {
		checkIfParkingLotIsCreated();
		if (slotNumber < 1) {
			throw new ParkingServiceException("Slot Number Must Be Grater Than Zero");
		}

		if (slotNumber > currentParkedIntentites.length) {
			throw new ParkingServiceException("Slot Number Must Be Between 1 To " + currentParkedIntentites.length);
		}

		int removedArrayIndex = slotNumber - 1;
		ParkerIdentity removedCard = currentParkedIntentites[removedArrayIndex];
		if (removedCard == null) {
			throw new ParkingServiceException("No Car Parking Here");
		} else {
			if (removedArrayIndex < pointerCurrentEmptySlot) {
				pointerCurrentEmptySlot = removedArrayIndex;
			}
			currentParkedIntentites[removedArrayIndex] = null;
		}
		return slotNumber;
	}

	public String getStatus() {
		checkIfParkingLotIsCreated();
		for (ParkerIdentity parkerIdentitiy : currentParkedIntentites) {
			if (parkerIdentitiy != null) {
				tableStatus.addRow(new String[] { String.valueOf(parkerIdentitiy.getSlot()),
						parkerIdentitiy.getRegistrationNumber(), parkerIdentitiy.getColor() });
			}
		}
		String ret = tableStatus.toString();
		tableStatus.clearContent();
		return ret;
	}

	public List<ParkerIdentity> getParkerIdentityByColor(String color)
	{
		checkIfParkingLotIsCreated();
		if (color == null || color.trim().isEmpty()) {
			throw new ParkingServiceException("color Param Must Not Be Empty");
		}
		
		List<ParkerIdentity> listParkerIdentity = new ArrayList<ParkerIdentity>();
		for (ParkerIdentity parkerIdentity : currentParkedIntentites) {
			if (parkerIdentity != null && color.equalsIgnoreCase(parkerIdentity.getColor())) {
				listParkerIdentity.add(parkerIdentity);
			}
		}
		return listParkerIdentity;
	}
	
	public int getSlotNumberByRegistrationNumber(String registrationNumber)
	{
		checkIfParkingLotIsCreated();
		if(registrationNumber == null || registrationNumber.trim().isEmpty())
		{
			throw new ParkingServiceException("registrationNumber Must Not Be Empty");
		}
		
		for(ParkerIdentity parkerIdentity:currentParkedIntentites)
		{
			if(parkerIdentity != null && parkerIdentity.getRegistrationNumber().equals(registrationNumber))
			{
				return parkerIdentity.getSlot();
			}
		}
		return -1;
	}
	
	

}
