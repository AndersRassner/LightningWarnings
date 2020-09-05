package se.rassner.LightningWarnings;
// TODO: Replace with java.time apparently
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
		// set​(int year, int month, int date, int hourOfDay, int minute, int second)
		// "year":2020,"month":9,"day":5,"hours":10,"minutes":42,"seconds":37,"
		timeOfStrike.set​(strike.get("year").getAsInt(), strike.get("month").getAsInt(), strike.get("day").getAsInt(), strike.get("hours").getAsInt(), strike.get("minutes").getAsInt(), strike.get("seconds").getAsInt());
	}

	LatLonCoordinate getCoordinates() {
		return coords;
	}

	Calendar getTimeOfStrike() {
		return timeOfStrike;
	}
}