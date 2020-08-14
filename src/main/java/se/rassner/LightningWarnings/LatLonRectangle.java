package se.rassner.LightningWarnings;

public class LatLonRectangle {
	private double latMin;
	private double latMax;
	private double lonMin;
	private double lonMax;

	public LatLonRectangle(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
		latMin = minLatitude;
		latMax = maxLatitude;
		lonMin = minLongitude;
		lonMax = maxLongitude;
	}

	public void setArea(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
		latMin = minLatitude;
		latMax = maxLatitude;
		lonMin = minLongitude;
		lonMax = maxLongitude;
	}

	public boolean contains(LatLonCoordinate coordinate) {
		double lat = coordinate.getLat();
		double lon = coordinate.getLon();
		return ((lat <= latMax && lat >= latMin)
			 && (lon <= lonMax && lon >= lonMin));
	}

}