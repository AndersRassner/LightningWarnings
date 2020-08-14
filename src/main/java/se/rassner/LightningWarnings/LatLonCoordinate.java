package se.rassner.LightningWarnings;

public class LatLonCoordinate {
	private double latitude;
	private double longitude;

	public LatLonCoordinate(double lat, double lon) {
		latitude = lat;
		longitude = lon;
	}

	public double getLat() {
		return latitude;
	}
	public double getLon() {
		return longitude;
	}
	public void setLat(double lat) {
		latitude = (lat >= 0) ? lat : 0;
	}
	public void setLon(double lon) {
		longitude = (lon >= 0) ? lon : 0;
	}

}