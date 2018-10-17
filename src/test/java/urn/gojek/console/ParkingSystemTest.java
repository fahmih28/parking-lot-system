package urn.gojek.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.BeforeClass;
import org.junit.Test;

public class ParkingSystemTest {
	
	private static ParkingSystem parkingSystem;
	
	@BeforeClass()
	public static void init() 
	{
		parkingSystem = new ParkingSystem();
		parkingSystem = new ParkingSystem(System.out);
	}
	
	@Test()
	public void runTest() throws Exception
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(ParkingSystemTest.class.getResourceAsStream("/Testing.txt")));
		String line = null;
		while((line = reader.readLine()) != null)
		{
			parkingSystem.command(line);
		}
	}
	
	@Test(expected = ExitException.class)
	public void exitTest()
	{
		parkingSystem.command("exit");
	}
	
}
