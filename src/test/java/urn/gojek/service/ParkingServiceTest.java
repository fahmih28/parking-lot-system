package urn.gojek.service;

import org.junit.Assert;
import org.junit.Test;



public class ParkingServiceTest {

	@Test(expected=ParkingServiceException.class)
	public void createParkingLotParamLessThanZeroTest()
	{
		new ParkingService().createParkingLot(-1);
	}
	
	@Test(expected=ParkingServiceException.class)
	public void createParkingLotAlreadyExistsTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(7);
		parkingService.createParkingLot(1);
	}
	
	@Test(expected=ParkingServiceException.class)
	public void checkIfParkingLotNotCreatedYet()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.getStatus().trim().isEmpty();
	}
	
	@Test()
	public void getStatus()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(3);
		Assert.assertTrue(!parkingService.getStatus().isEmpty());
	}
	
	@Test(expected=ParkingServiceException.class)
	public void parkThrowsFullTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(1);
		parkingService.park("KH-3333-Pk", "White");
		parkingService.park("KH-777-L", "White");
	}
	
	@Test
	public void leaveParkingLotTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(2);
		parkingService.park("KH-3333-Pk", "White");
		parkingService.park("KH-777-M", "Red");
		parkingService.leave(1);
		parkingService.park("KH-777-L", "White");
	}
	
	@Test(expected=ParkingServiceException.class)
	public void leaveParamLessThanOneTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(2);
		parkingService.park("KH-3333-Pk", "White");
		parkingService.leave(0);
	}
	
	@Test(expected=ParkingServiceException.class)
	public void leaveParamMoreThanSizeParkingLotTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(2);
		parkingService.park("KH-3333-Pk", "White");
		parkingService.leave(3);
	}
	
	@Test(expected=ParkingServiceException.class)
	public void leaveNotExistenceParkerTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(4);
		parkingService.park("KH-3333-Pk", "White");
		parkingService.park("KH-3331-Pk", "Red");
		parkingService.park("KH-3334-Pk", "Gray");
		parkingService.park("KH-3337-Pk", "Blue");
		parkingService.leave(4);
		parkingService.leave(3);
		parkingService.leave(1);
		parkingService.leave(2);
		parkingService.leave(3);
	}
	
	@Test(expected=ParkingServiceException.class)
	public void getListParkedIdentitiesByColorEmptyTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(4);
		parkingService.getParkerIdentityByColor("");
	}
	
	@Test(expected=ParkingServiceException.class)
	public void getListParkedIdentitiesByColorNullTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(4);
		parkingService.getParkerIdentityByColor(null);
	}
	
	@Test
	public void getParkerIdentityByColorSuccessTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(4);
		parkingService.park("KH-3333-Pk", "White");
		parkingService.park("KH-3331-Pk", "Red");
		parkingService.park("KH-3334-Pk", "Gray");
		parkingService.park("KH-3337-Pk", "Blue");
		parkingService.leave(2);
		Assert.assertEquals(parkingService.getParkerIdentityByColor("Gray").get(0).getRegistrationNumber(),"KH-3334-Pk");
		Assert.assertTrue(parkingService.getStatus().length() > 0);
	}
	
	@Test(expected=ParkingServiceException.class)
	public void getSlotNumberByRegistrationNumberEmptyParamTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(4);
		parkingService.getSlotNumberByRegistrationNumber("");
	}
	
	@Test(expected=ParkingServiceException.class)
	public void getSlotNumberByRegistrationNumberNullParamTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(4);
		parkingService.getSlotNumberByRegistrationNumber(null);
	}
	
	@Test
	public void getSlotNumberByRegistrationNumberNotFoundTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(4);
		parkingService.park("KH-3333-Pk", "White");
		parkingService.park("KH-3331-Pk", "Red");
		parkingService.park("KH-3334-Pk", "Gray");
		parkingService.park("KH-3337-Pk", "Blue");
		Assert.assertNotEquals(parkingService.getSlotNumberByRegistrationNumber("KH"), 2);
	}
	
	@Test
	public void getSlotNumberByRegistrationNumberFoundTest()
	{
		ParkingService parkingService = new ParkingService();
		parkingService.createParkingLot(4);
		parkingService.park("KH-3333-Pk", "White");
		parkingService.park("KH-3331-Pk", "Red");
		parkingService.park("KH-3334-Pk", "Gray");
		parkingService.park("KH-3337-Pk", "Blue");
		parkingService.leave(2);
		Assert.assertEquals(parkingService.getSlotNumberByRegistrationNumber("KH-3334-Pk"), 3);
	}
}
