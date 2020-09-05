package se.rassner.LightningWarnings;

import java.time.LocalTime;

import com.google.gson.*;

public class LightningStrike implements Comparable<LightningStrike> {
	private LatLonCoordinate coords;
	private LocalTime timeOfStrike;

	LightningStrike(JsonObject strike) {
		double lat = strike.get("lat").getAsDouble();
		double lon = strike.get("lon").getAsDouble();
		coords = new LatLonCoordinate(lat,lon);
		timeOfStrike = LocalTime.of(strike.get("hours").getAsInt(), strike.get("minutes").getAsInt(), strike.get("seconds").getAsInt());
		// set​(int year, int month, int date, int hourOfDay, int minute, int second)
		// "year":2020,"month":9,"day":5,"hours":10,"minutes":42,"seconds":37,"
	}

	LatLonCoordinate getCoordinates() {
		return coords;
	}

	LocalTime getTimeOfStrike() {
		return timeOfStrike;
	}

	@Override
	public int compareTo(LightningStrike other) {
		return timeOfStrike.compareTo(other.getTimeOfStrike());
	}
}