/*
 Copyright (c) 2019 Ekaterina Alexeevna Ivanova. All rights reserved.
 PROPRIETARY. For demo purposes only, not for redistribution or any commercial 
 use.
 */
package iceja.moscow;

import org.junit.jupiter.api.Test;
import iceja.moscow.ParkingServer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ekaterina Ivanova (iceja.moscow)
 *
 */
public class ParkingServerTest {

	
    @Test
    public void getFreeParkingUnits() throws Exception {
    	
    	ParkingServer server = new ParkingServer(40, 4, false, false);

    	assertEquals("36",server.getFreePlaces() );
    	server.spaceUtilized();
    	server.spaceUtilized();
    	assertEquals("34",server.getFreePlaces() );
    	server.spaceReleased();
    	assertEquals("35",server.getFreePlaces() );
    	for(int i=5;i< 40;i++) {
    		server.spaceUtilized();
    	}
    	assertEquals("0",server.getFreePlaces() );
    	assertEquals("overload",server.spaceUtilized());
    }

}
