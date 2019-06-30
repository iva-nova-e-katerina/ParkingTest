package iceja.moscow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;



@Entity(name = "parking")
@Table(name = "parkings")
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
	public synchronized String spaceUtilized(@Context HttpServletRequest req) {		
		if(getUsed(req.getSession(true)) >= capacity) {
			return "overload";
		}
		this.used++;
		req.getSession(true).setAttribute("parkingserver.used", this.used);
		return String.valueOf(this.used);
	}
	
	
	@GET
	@Path("/release")
	@Produces(MediaType.TEXT_PLAIN)  
	public synchronized String spaceReleased(@Context HttpServletRequest req)  throws Exception{
		if(getUsed(req.getSession(true)) <= 0) {
			return String.valueOf(this.used);
			//throw new Exception ("SYSTEM ERROR! ERROR! system halted. Used " + getUsed(req.getSession(true)));
		} 
		this.used--;
		req.getSession(true).setAttribute("parkingserver.used", this.used);
		return String.valueOf(this.used);
	}
//
//
	@GET
	@Path("/openfirst")
	@Produces(MediaType.TEXT_PLAIN)  
	public  synchronized String askForOpenFirstGate(@Context HttpServletRequest req) throws Exception {
		if(getUsed(req.getSession(true)) < capacity) {
			return "allow";
		}else if(getUsed(req.getSession(true)) < 0){
			throw new Exception ("SYSTEM ERROR! ERROR! system halted");
		} else {
			return "reject";
		}
	}
//	
//
	@GET
	@Path("/opensecond")
	@Produces(MediaType.TEXT_PLAIN)  
	public  synchronized String askForOpenSecondGate(@Context HttpServletRequest req) throws Exception {
		if(getUsed(req.getSession(true)) < capacity) {
			return "allow";
		}else if(getUsed(req.getSession(true)) < 0){
			throw new Exception ("SYSTEM ERROR! ERROR! system halted");
		} else {
			return "reject";
		}
	}
	
	@GET
	@Path("/freespace")
	@Produces(MediaType.TEXT_PLAIN)  
	public  synchronized  String getFreePlaces(@Context HttpServletRequest req) throws Exception{
		if(getUsed(req.getSession(true)) <= capacity) {
			return String.valueOf(capacity - used);
		}else if(getUsed(req.getSession(true)) < 0){
			throw new Exception ("SYSTEM ERROR! ERROR! system halted");
		} else {
			throw new Exception ("SYSTEM ERROR! ERROR! system halted");
		}
	}

	/**
	 * @return the used
	 */

	public int getUsed(HttpSession session) {
		Integer u = (Integer)session.getAttribute("parkingserver.used");
		this.used = (u == null ? 0 : u);
		return this.used;
	}

	@Column(name = "used_space")	
	public int getUsed() {
		return this.used;
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
	
    
}
