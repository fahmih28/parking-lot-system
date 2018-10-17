package urn.gojek;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import urn.gojek.console.ExitException;
import urn.gojek.console.ParkingSystem;

/**
 * Hello world!
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		ParkingSystem parkingSystem = new ParkingSystem();
		if (args.length == 1) {
			File file = new File(args[0]);
			if (!file.exists()) {
				throw new ExitException("File Not Found '" + args[0] + "'");
				
			}

			BufferedReader reader = null;
			try  {
				reader = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = reader.readLine()) != null) {
					try {
						parkingSystem.command(line);
					}
					catch(ExitException ex) {
						//Ignore This Exception, Its Only Terminate Shell If User Uses Interactive Mode
					}
				}
			}
			finally {
				try {
					if(reader != null)
						reader.close();
				}
				catch(Exception ex)
				{
					
				}
			}
		} else if (args.length == 0) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				try {
					parkingSystem.command(reader.readLine());
				}
				catch(ExitException ex) {
					break;
				}
			}
		}
	}
}
