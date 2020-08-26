package se.rassner.LightningWarnings;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

import com.google.gson.*;

public class LightningStrike {
	private LatLonCoordinate coords;
	private Calendar timeOfStrike;

	LightningStrike(JsonObject strike) {
		double lat = strike.get("lat").getAsDouble();
		double lon = strike.get("lon").getAsDouble();
		coords = new LatLonCoordinate(lat,lon);

		//TODO: get time of strike from JsonObject
		timeOfStrike = new GregorianCalendar(new SimpleTimeZone(0, "UTC"));
	}

	LatLonCoordinate getCoordinates() {
		return coords;
	}

	Calendar getTimeOfStrike() {
		return timeOfStrike;
	}
}