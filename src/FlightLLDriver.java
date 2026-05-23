import java.io.*;
import java.util.*;

public class FlightLLDriver {

	public static void main(String[] args) throws FileNotFoundException {
		
		RunwayScheduler schedule1 = new RunwayScheduler();
		schedule1.flightInterface();
		/*schedule1.readFromFile();
		schedule1.displaySchedule();
		schedule1.writeToFile();*/
	}

}
