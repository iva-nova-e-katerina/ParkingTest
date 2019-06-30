package iceja.moscow;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/parkingtest")
public class ParkingServer {
	
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private int capacity = 38;
	private int used = 0;
	private boolean firstGateIsOpen = false;
	private boolean secondGateIsOpen = false;
	
	public ParkingServer(int cap, int usedSpace, boolean firstGate, boolean secondGate) {
		this.capacity = cap;
		this.used = usedSpace;
		this.firstGateIsOpen = firstGate;
		this.secondGateIsOpen = secondGate;
		
	}
	
	// default constructor
	public ParkingServer() {
		this(38, 4, false, false);
	}
	
 
	@GET
	@Path("/utilize")
	@Produces(MediaType.TEXT_PLAIN)  
	public synchronized String spaceUtilized() {
		if(getUsed() == capacity) {
			return "overload";
		}
		this.used++;
		return String.valueOf(getUsed());
	}
	
	
	@GET
	@Path("/release")
	@Produces(MediaType.TEXT_PLAIN)  
	public synchronized String spaceReleased()  throws Exception{
		if(getUsed() <= 0) {
			throw new Exception ("SYSTEM ERROR! ERROR! system halted. Used " + getUsed());
		} 
		this.used--;
		return String.valueOf(getUsed());
	}


	@GET
	@Path("/openfirst")
	@Produces(MediaType.TEXT_PLAIN)  
	public  synchronized String askForOpenFirstGate() throws Exception {
		if(getUsed() < capacity) {
			return "allow";
		}else if(getUsed() < 0){
			throw new Exception ("SYSTEM ERROR! ERROR! system halted");
		} else {
			return "reject";
		}
	}
	

	@GET
	@Path("/opensecond")
	@Produces(MediaType.TEXT_PLAIN)  
	public  synchronized String askForOpenSecondGate() throws Exception {
		if(getUsed() < capacity) {
			return "allow";
		}else if(getUsed() < 0){
			throw new Exception ("SYSTEM ERROR! ERROR! system halted");
		} else {
			return "reject";
		}
	}
	
	
	@GET
	@Path("/freespace")
	@Produces(MediaType.TEXT_PLAIN)  
	public  synchronized  String getFreePlaces() throws Exception{
		if(getUsed() <= capacity) {
			return String.valueOf(capacity - used);
		}else if(getUsed() < 0){
			throw new Exception ("SYSTEM ERROR! ERROR! system halted");
		} else {
			throw new Exception ("SYSTEM ERROR! ERROR! system halted");
		}
	}

	

	/**
	 * @return the used
	 */
	@Column(name = "used_space")
	public int getUsed() {
		return used;
	}

	/**
	 * @param used the used to set
	 */
	public void setUsed(int used) {
		this.used = used;
	}

	/**
	 * @return the firstGateIsOpen
	 */
	@Column(name = "first_gate")
	public boolean isFirstGateIsOpen() {
		return firstGateIsOpen;
	}

	/**
	 * @param firstGateIsOpen the firstGateIsOpen to set
	 */
	public void setFirstGateIsOpen(boolean firstGateIsOpen) {
		this.firstGateIsOpen = firstGateIsOpen;
	}

	/**
	 * @return the secondGateIsOpen
	 */
	@Column(name = "second_gate")
	public boolean isSecondGateIsOpen() {
		return secondGateIsOpen;
	}

	/**
	 * @param secondGateIsOpen the secondGateIsOpen to set
	 */
	public void setSecondGateIsOpen(boolean secondGateIsOpen) {
		this.secondGateIsOpen = secondGateIsOpen;
	}

	/**
	 * @return the capacity
	 */
	@Column(name = "capacity")
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
    @GET
    @Path("/message2")
    public String getMsg()
    {
         return "Hello World !! - Jersey 2";
    }
}
