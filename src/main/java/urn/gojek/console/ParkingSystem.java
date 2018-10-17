package urn.gojek.console;

import java.io.PrintStream;
import java.util.List;

import urn.gojek.model.ParkerIdentity;
import urn.gojek.service.ParkingService;
import urn.gojek.service.ParkingServiceException;

public class ParkingSystem {
	private ParkingService parkingService;
	private PrintStream outputStream;

	public ParkingSystem(PrintStream outputStream) {
		parkingService = new ParkingService();
		this.outputStream = (outputStream == null) ? System.out : outputStream;
	}

	public ParkingSystem() {
		this(null);
	}

	public void command(String inputLine) {
		String[] inputSplit = inputLine.split("\\s+");

		String command = inputSplit[0];
		if (ParkingSystemCommandConstant.CREATE_PARKING_LOT.equals(command)) {
			createParkingLot(inputSplit);
		} else if (ParkingSystemCommandConstant.PARK.equals(command)) {
			park(inputSplit);
		} else if (ParkingSystemCommandConstant.LEAVE.equals(command)) {
			leave(inputSplit);
		} else if (ParkingSystemCommandConstant.STATUS.equals(command)) {
			proceedStatus(inputSplit);
		} else if (ParkingSystemCommandConstant.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR.equals(command)) {
			showRegistrationNumberByColor(inputSplit);
		} else if (ParkingSystemCommandConstant.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR.equals(command)) {
			showSlotNumbersByColor(inputSplit);
		} else if (ParkingSystemCommandConstant.SLOT_NUMBER_FOR_REGISTRATION_NUMBER.equals(command)) {
			showSlotNumberByRegistrationNumber(inputSplit);
		} else if(ParkingSystemCommandConstant.EXIT.equals(command))
		{
			throw new ExitException();
		}
		else{
			unknownCommand(inputSplit);
		}
	}

	private void unknownCommand(String[] inputSplit)
	{
		outputStream.println("Unknown Command '"+inputSplit[0]+"'");
	}
	private void createParkingLot(String[] inputSplit) {
		if (inputSplit.length != 2) {
			raiseIllegalParam(ParkingSystemCommandConstant.CREATE_PARKING_LOT, "Should Take 2 Params");
			return;
		}

		try {
			outputStream.printf("Created parking lot with %d slots%n",
					parkingService.createParkingLot(Integer.parseInt(inputSplit[1])));
		} catch (NumberFormatException ex) {
			raiseIllegalParam(ParkingSystemCommandConstant.CREATE_PARKING_LOT, "Param 2 Should Be Number");
		} catch (ParkingServiceException ex) {
			outputStream.println(ex.getMessage());
		}
	}

	private void park(String[] inputSplit) {
		if (inputSplit.length != 3) {
			raiseIllegalParam(ParkingSystemCommandConstant.PARK, "Should Take " + inputSplit.length);
			return;
		}

		try {
			String registrationNumber = inputSplit[1];
			String color = inputSplit[2];
			int slot = parkingService.park(registrationNumber, color);
			outputStream.printf("Allocated slot number : %d%n", slot);
		} catch (ParkingServiceException ex) {
			outputStream.println(ex.getMessage());
		}
	}

	private void leave(String[] inputSplit) {
		if (inputSplit.length != 2) {
			raiseIllegalParam(ParkingSystemCommandConstant.LEAVE, "Should Take 2 Params");
			return;
		}

		try {
			outputStream.printf("Slot number %d is free%n", parkingService.leave(Integer.parseInt(inputSplit[1])));
		} catch (NumberFormatException ex) {
			raiseIllegalParam(ParkingSystemCommandConstant.CREATE_PARKING_LOT, "Param 2 Should Be Number");
		} catch (ParkingServiceException ex) {
			outputStream.println(ex.getMessage());
		}
	}

	private void proceedStatus(String[] inputSplit) {
		if (inputSplit.length != 1) {
			raiseIllegalParam(ParkingSystemCommandConstant.STATUS, "Should Take No Param");
			return;
		}

		try {
			outputStream.println(parkingService.getStatus());
		} catch (ParkingServiceException ex) {
			outputStream.println(ex.getMessage());
		}
	}

	private void showRegistrationNumberByColor(String[] inputSplit) {
		if (inputSplit.length != 2) {
			raiseIllegalParam(ParkingSystemCommandConstant.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR,
					"Should Take 2 Params");
			return;
		}

		try {
			List<ParkerIdentity> selectedParkerIdentities = parkingService.getParkerIdentityByColor(inputSplit[1]);
			if (selectedParkerIdentities.isEmpty()) {
				outputStream.println("Not Found");
			} else {
				StringBuilder listRegNumber = new StringBuilder();
				for (ParkerIdentity parkerIdentity : selectedParkerIdentities) {
					if (listRegNumber.length() > 0) {
						listRegNumber.append(",");
					}

					listRegNumber.append(parkerIdentity.getRegistrationNumber());
				}

				outputStream.println(listRegNumber);
			}
		} catch (ParkingServiceException ex) {
			outputStream.println(ex.getMessage());
		}
	}

	private void showSlotNumbersByColor(String[] inputSplit) {
		if (inputSplit.length != 2) {
			raiseIllegalParam(ParkingSystemCommandConstant.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR, "Should Take 2 Params");
			return;
		}

		try {
			List<ParkerIdentity> selectedParkerIdentities = parkingService.getParkerIdentityByColor(inputSplit[1]);
			if (selectedParkerIdentities.isEmpty()) {
				outputStream.println("Not Found");
			} else {
				StringBuilder listSlotNumber = new StringBuilder();
				for (ParkerIdentity parkerIdentity : selectedParkerIdentities) {
					if (listSlotNumber.length() > 0) {
						listSlotNumber.append(",");
					}

					listSlotNumber.append(parkerIdentity.getSlot());
				}

				outputStream.println(listSlotNumber);
			}
		} catch (ParkingServiceException ex) {
			outputStream.println(ex.getMessage());
		}
	}

	private void showSlotNumberByRegistrationNumber(String[] inputSplit) {
		if (inputSplit.length != 2) {
			raiseIllegalParam(ParkingSystemCommandConstant.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR, "Should Take 2 Params");
			return;
		}

		try {
			int slot = parkingService.getSlotNumberByRegistrationNumber(inputSplit[1]);
			if (slot == -1) {
				outputStream.println("Not Found");
			} else {
				outputStream.println(slot);
			}
		} catch (ParkingServiceException ex) {
			outputStream.println(ex.getMessage());
		}
	}

	private void raiseIllegalParam(String command, String error) {
		outputStream.println(command + " " + error);
	}
}
