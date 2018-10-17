package urn.gojek;

import org.junit.Test;

import urn.gojek.console.ExitException;

public class MainTest {
	@Test
	public void entryPointWithFileTest() throws Exception
	{
		Main.main(new String[] {"/Testing.txt"});
	}
	
	@Test(expected=ExitException.class)
	public void entryPointWithNotFoundFileTest() throws Exception
	{
		Main.main(new String[] {"Testing.txt"});
	}
}
