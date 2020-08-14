package se.rassner.LightningWarnings;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LatLonRectangleTest {

	@Test
	void constructLatLonRectangle() {
		LatLonRectangle latLonRectTest = new LatLonRectangle(1.5, 2.5, 3.5, 4.5);
	}

	@Test
	void latLonRectangleContains() {
		LatLonRectangle latLonRectTest = new LatLonRectangle(1.5, 2.5, 3.5, 4.5);
		LatLonCoordinate containedCoordinate = new LatLonCoordinate(2.0, 4.0);
		LatLonCoordinate uncontainedCoordinate1 = new LatLonCoordinate(1.0, 4.0);
		LatLonCoordinate uncontainedCoordinate2 = new LatLonCoordinate(2.0, 5.0);
		LatLonCoordinate uncontainedCoordinate3 = new LatLonCoordinate(3.0, 1.0);
		assertTrue(latLonRectTest.contains(containedCoordinate));
		assertFalse(latLonRectTest.contains(uncontainedCoordinate1));
		assertFalse(latLonRectTest.contains(uncontainedCoordinate2));
		assertFalse(latLonRectTest.contains(uncontainedCoordinate3));

	}
}