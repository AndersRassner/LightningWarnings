package se.rassner.LightningWarnings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LatLonCoordinateTest {
	@Test
	void ConstructLatLonCoordinate() {
		LatLonCoordinate latLonTest = new LatLonCoordinate(1.5, 2.5);
		assertEquals(latLonTest.getLat(), 1.5);
		assertEquals(latLonTest.getLon(), 2.5);
	}
	
	@Test
	void setLatLonCoordinate() {
		LatLonCoordinate latLonTest = new LatLonCoordinate(1.5, 2.5);		
		latLonTest.setLat(-1.5);
		latLonTest.setLon(-2.5);
		assertEquals(latLonTest.getLat(), 0);
		assertEquals(latLonTest.getLon(), 0);
	}
}