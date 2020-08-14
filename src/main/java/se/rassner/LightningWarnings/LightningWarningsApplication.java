package se.rassner.LightningWarnings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Iterator;

import com.google.gson.*;

@SpringBootApplication
public class LightningWarningsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LightningWarningsApplication.class, args);	
		/*
		Make http request to get lightning strike data from SMHI
		*/
		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder(URI.create("https://opendata-download-lightning.smhi.se/api/version/latest/year/2020/month/8/day/11/data.json"))
			.header("Content-Type", "application/json")
			.build();
		HttpResponse<String> response = null;
		try{
			response = httpClient.send(request, BodyHandlers.ofString());
			System.out.println("Status of response is: " + response.statusCode());
		} catch(Exception ex) {ex.printStackTrace();}
		if(response != null && response.statusCode() != 200) {
			System.out.println("Response's not OK, ending program");
			return;
		}

		/*
		Transform received data to workable format, if needed
		*/
		JsonElement parser = JsonParser.parseString(response.body());
		JsonArray strikes = parser.getAsJsonObject().getAsJsonArray("values");
		System.out.println(strikes.get(0).toString());

		/*
		Set area to search, starting with default, then by asking through interface
		*/
		LatLonRectangle malmoArea = new LatLonRectangle(55.48, 55.65, 12.75, 13.23);
		
		/*
		Filter data using given area
		*/
		JsonArray filteredStrikes = new JsonArray();
		Iterator<JsonElement> strikesIt = strikes.iterator();
		while(strikesIt.hasNext()) {
			JsonObject strike = (JsonObject) strikesIt.next();
			double lat = strike.get("lat").getAsDouble();
			double lon = strike.get("lon").getAsDouble();;
			if(malmoArea.contains(new LatLonCoordinate(lat,lon))) { 
				filteredStrikes.add(strike);
			}
		}
		System.out.println(filteredStrikes.toString());

		/*
		Show result, starting with println, then by showing in interface
		*/
		System.out.println("Number of strikes in given area: " + filteredStrikes.size());
		if(filteredStrikes.size() > 0) {
			String latestStrike = "00:00"; // replace with GregorianCalendar using datetime in JsonObjects in filteredStrikes
			System.out.println("Latest strike occured at: " + latestStrike);
		}
		System.out.println("End of program reached");
	
	}

}
