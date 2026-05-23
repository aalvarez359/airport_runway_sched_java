import java.io.*;
import java.util.*;

public class FlightLL <E> implements BareBonesFlightLL<E>{

	// Node class
	private class Node<F>{
		private F data;	//Data at Node
		private Node<F> next; //link to next node
		
		//Constructors to create node
		//This creates node with data and next value
		public Node(F data, Node<F> next) {
			this.data = data;
			this.next = next;
		}
		//This creates node when only data supplied and next is null
		public Node(F data) {
			this(data, null); //this calls the other constructor to create the node
		}
	}
	
	//Data and Method for the Linked List
	
	private Node<E> head;	//Reference to the Head of LL
	private int size;		//How many nodes are in the LL
	
	//Constructor for LL
	public FlightLL() {
		this.head = new Node<E>(null);
		//the head has no data, or next value at the beginning
		this.size = 0;		//When LL created, there are no other nodes
	}
	
	@Override
	public void add(int index, E item) {
		//this method adds an element to the LL
		//Depending on index, it adds to 1st location using addFirst()
		//or adds after a node using addAfter()
		
		if(index < 0 || index > size) {
			System.out.println("Invalid Index");
			return;
		}
		else if(index == 0) {
			//add at first location
			addFirst(item);
		}
		else {
			//We are adding after node
			//We need the reference of that node first
			Node <E> node = getNode(index); //Get reference of prev node
			addAfter(node, item);
		}
		
	}

	private void addAfter(Node<E> node, E item) {
		// Adds item after reference node
		node.next = new Node<E>(item, node.next);
		size++;
		
	}

	private Node<E> getNode(int index) {
		if(index < 0 || index > size) {
			System.out.println("Invalid Index");
			return null;
		}
		// This method loops over LL and returns reference of Node at index
		Node<E> node = head;
		for(int i =0;i < index && node != null;i++) {
			node = node.next;
		}
		return node; // returns reference of node at index
	}

	private void addFirst(E item) {
		//Creates node with item as data and head.next as next
		//We update head.next to point to this newly created node
		//Finally, we increment the size
		//Node<E> temp = new Node<E>(item, head.next);
		//head.next = temp;
		head.next = new Node<E>(item, head.next);
		size++;
	}

	@Override
	public E remove(int index) {
		//This method removes an element from given index
		if(index < 0 || index > size) {
			System.out.println("Invalid Index");
			return null;
		}
		else if(index == 0) {
			return removeFirst();
		}
		else {
			Node<E> node = getNode(index);
			return removeAfter(node);
		}
	}

	private E removeAfter(Node<E> node) {
		//Deletes node after 
		Node <E> temp = node.next;
		if(temp != null) {
			node.next = temp.next;
			size --;
			return temp.data;
		}
		return null;
	}

	private E removeFirst() {
		//Deletes first node
		Node <E> temp = head;
		if(temp != null) {
			head = head.next;
			size --;
			return temp.data;
		}
		return null;
	}

	@Override
	public E get(int index) {
		if(index < 0 || index > size-1) {
			System.out.println("Invalid Index");
			return null;
		}
		//Node<E> node = getNode(index);
		Node<E> node = head;
		for(int i =0;i <= index && node != null;i++) {
			node = node.next;
		}
		return node.data;
	}

	@Override
	public E set(int index, E newValue) {
		if(index < 0 || index > size-1) {
			System.out.println("Invalid Index");
			return null;
		}
		Node <E> node = head;
		for(int i =0;i <= index && node != null;i++) {
			node = node.next;
		}
		E value = node.data;
		node.data = newValue;
		return value;
	}

	@Override
	public int size() {
		// Returns size of LL
		return this.size;
	}

	//Implement toString() to print LL dat
	public String toString() {
		String s = "[";
		Node<E> p = head;	// This reference will be used to iterate over the LL
		if(p != null) {
			while(p.next != null){
				//Iterate over the nodes one by one
				s += p.next.data + " ; "; 	//Adds data to the string
				p = p.next;					//Go to next node
			}
		}
		//end of LL
		s += "]";
		return s;
	}
}

class Flight {
	private String flightID;
	private String status;
	private String priority;
	private int timestampHr;
	private int timestampMin;
	
	//Flight constructor
	public Flight(String flightID, String status, String priority, int timestampHr, int timestampMin) {
		this.flightID = flightID;
		this.status = status;
		this.priority = priority;
		this.timestampHr = timestampHr;
		this.timestampMin = timestampMin;
	}
	
	public String getFlightID() {
		return flightID;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int gettimestampHr() {
		return timestampHr;
	}
	
	public int gettimestampMin() {
		return timestampMin;
	}
	
	public String getpriority() {
		return priority;
	}
	
	@Override
    public String toString() {
        return "Flight ID: " + flightID +
                ", Status: " + status +
                ", Priority: " + priority +
                ", Time: " + timestampHr + ":" + timestampMin;
    }
}
	
class RunwayScheduler {
	private FlightLL<Flight> runwayA;
	private FlightLL<Flight> runwayB;
		
	//Runway Constructors
	public RunwayScheduler() {
		this.runwayA = new FlightLL<Flight>();
		this.runwayB = new FlightLL<Flight>();
	}
		
	public RunwayScheduler(FlightLL<Flight> runwayA, FlightLL<Flight> runwayB) {
		this.runwayA = runwayA;
		this.runwayB = runwayB;
	}
	
	public void scheduleFlight(Flight flight) {
	    FlightLL<Flight> bestRunwayA = runwayA;
	    FlightLL<Flight> bestRunwayB = runwayB;
	    int flighttimestampHr = flight.gettimestampHr();
	    int flighttimestampMin = flight.gettimestampMin();
        boolean scheduleOnRunwayB = true;
	    if (bestRunwayA.size() < 10) {	//checks if there's room on runwayA
	        boolean scheduleOnRunwayA = true;
	        for (int i = 0; i < bestRunwayA.size(); i++) {	//sets boolean false if there are flights within 10 min of each other
	            Flight flightcompare = bestRunwayA.get(i);
	            int hrDiff = flighttimestampHr - flightcompare.gettimestampHr();
	            int minDiff = flighttimestampMin - flightcompare.gettimestampMin();
	            if (Math.abs(hrDiff * 60 + minDiff) < 10) {
	                scheduleOnRunwayA = false;
	                break;
	            }
	        }
	        if (scheduleOnRunwayA) {
	            int bestIndex = -1;
	            for (int i = 0; i < bestRunwayA.size(); i++) {
	                Flight flightcompare = bestRunwayA.get(i);
	                if (flighttimestampHr < flightcompare.gettimestampHr() ||	//sets bestIndex between two flights in time order
	                    (flighttimestampHr == flightcompare.gettimestampHr() && flighttimestampMin < flightcompare.gettimestampMin())) {
	                    bestIndex = i;
	                    break;
	                }
	            }
	            if (bestIndex == -1) {	//adds to runwayA at begining or end
	                bestRunwayA.add(bestRunwayA.size(), flight);
	            } 
	            else {					//adds to runwayA at chronological order
	                bestRunwayA.add(bestIndex, flight);
	            }
	        } 
	        else {
	        	for (int i = 0; i < bestRunwayB.size(); i++) {	//sets runwayB boolean false if there are flights within 10 min of each other except for emergencies
		            Flight flightcompareB = bestRunwayB.get(i);
		            int hrDiffB = flighttimestampHr - flightcompareB.gettimestampHr();
		            int minDiffB = flighttimestampMin - flightcompareB.gettimestampMin();
		            if (Math.abs(hrDiffB * 60 + minDiffB) < 10 && !"Emergency".equals(flight.getpriority())) {
		                scheduleOnRunwayB = false;
		            }
	        	}
		        if(scheduleOnRunwayB && bestRunwayB.size() < 10) {	//adds flight to runwayB if there are flights within 10 min on runwayA
	            bestRunwayB.add(bestRunwayB.size(), flight);
	        	}
		        else {
		        	System.out.println("There is no room for this flight on either runway");
		        }
	        } 
	    }
	    else {
	    	for (int i = 0; i < bestRunwayB.size(); i++) {	//sets runwayB boolean false if there are flights within 10 min of each other except for Emergencies
	            Flight flightcompareB = bestRunwayB.get(i);
	            int hrDiffB = flighttimestampHr - flightcompareB.gettimestampHr();
	            int minDiffB = flighttimestampMin - flightcompareB.gettimestampMin();
	            if (Math.abs(hrDiffB * 60 + minDiffB) < 10 && !"Emergency".equals(flight.getpriority())) {
	                scheduleOnRunwayB = false;
	            }
        	}
	        if(scheduleOnRunwayB && bestRunwayB.size() < 10) { //adds flight to runwayB if no room on runwayA
            bestRunwayB.add(bestRunwayB.size(), flight);
        	}
	        else {
	        	System.out.println("There is no room for this flight on either runway");
	        }
	    }
	}

	public void cancelFlight(String flightID) {
		for(int i = 0;i < runwayA.size();i++) { //cancels flight on runwayA
			Flight flightcurr = runwayA.get(i);
			if(flightcurr.getFlightID().equals(flightID)) {
				runwayA.remove(i);
			}
		}
		for(int i = 0;i < runwayB.size();i++) {  //cancels flight on runwayB
			Flight flightcurr = runwayB.get(i);
			if(flightcurr.getFlightID().equals(flightID)) {
				runwayB.remove(i);
			}
		}
	}
	
	public void displaySchedule() {
		System.out.println("RunwayA");	//iterates through runwayA to print flights
		for(int i=0;i < runwayA.size();i++) {
			System.out.println(runwayA.get(i));
		}
		System.out.println("\nRunwayB"); //iterates through runwayB to print flights
		for(int i=0;i < runwayB.size();i++) {
			System.out.println(runwayB.get(i));
		}
	}
	
	public void readFromFile() throws FileNotFoundException {
		try {
	        Scanner input = new Scanner(new File("filename.txt"));	//sets variables equal to inputs
	        while(input.hasNext()) {
	        	String flightID = input.next();
	        	String status = input.next();
	        	String priority = input.next();
	        	int timestampHr = input.nextInt();
	        	int timestampMin = input.nextInt();
	        	
	        	Flight flight = new Flight(flightID, status, priority, timestampHr, timestampMin); //creats flight object with variables
	        	
	        	scheduleFlight(flight);  //adds flight to the runways
	        }
	        input.close();
		} catch (FileNotFoundException e) {
			System.out.println("There is an error reading the file");
			e.printStackTrace();
		}

	}
	

	public void writeToFile() {
		try {
			File file = new File("flight.txt");
			PrintStream output = new PrintStream(file);
			for(int i = 0;i< runwayA.size();i++) { //prints runwayA to output file
				Flight flight = runwayA.get(i);
				writeFlightDataToFile(output, flight);
			}
			for(int i = 0;i< runwayB.size();i++) {  //prints runwayB to output file
				Flight flight = runwayB.get(i);
				writeFlightDataToFile(output, flight);
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("There is an error writing to the file");
			e.printStackTrace();
		}
	}
	private void writeFlightDataToFile(PrintStream output, Flight flight) {
		output.println(flight.getFlightID() + " " + flight.getStatus() + " " + flight.getpriority() + " " + flight.gettimestampHr() + " " + flight.gettimestampMin());
	}
	
	public void flightInterface() throws FileNotFoundException{
		RunwayScheduler schedule1 = new RunwayScheduler();
		Scanner input = new Scanner(System.in);
		int selection;
		do {
			System.out.println("Menu");
			System.out.println("1. Read Schedule From File");
			System.out.println("2. Schedule a Flight");
			System.out.println("3. Cancel a Flight");
			System.out.println("4. Display Schedule");
			System.out.println("5. Write Schedule to File");
			System.out.println("6. Exit");
			System.out.println("Make a Selection: ");
			selection = input.nextInt();
			
			switch(selection) {
				case 1:
					schedule1.readFromFile();
			        	break;
				case 2:
					//schedule1.scheduleFlight(flight);
				case 3:
					//schedule1.cancelFlight(flight);
			    case 4:
			        schedule1.displaySchedule();
			        break;
			    case 5:
			    	schedule1.writeToFile();
			    	break;
			}
		}while(selection !=6);
		input.close();
	}

}



