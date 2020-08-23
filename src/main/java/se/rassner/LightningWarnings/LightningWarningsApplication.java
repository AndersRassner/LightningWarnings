package se.rassner.LightningWarnings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Iterator;
import java.util.SimpleTimeZone;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.google.gson.*;

@SpringBootApplication
public class LightningWarningsApplication {

	public static void main(String[] args) {
		/*
		Note that the intention is to move all these chunks into separate functions once the program stabilizes
		*/
		SpringApplication.run(LightningWarningsApplication.class, args);	
		/*
		Make http request to get lightning strike data from SMHI
		*/
		Calendar currentDate = new GregorianCalendar();
		//System.out.println(currentDate.getTime().toString());
		int year = currentDate.get(Calendar.YEAR);
		int month = currentDate.get(Calendar.MONTH) + 1; //January is 0 because reasons...
		int day = currentDate.get(Calendar.DAY_OF_MONTH);
		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder(URI.create("https://opendata-download-lightning.smhi.se/api/version/latest/year/" + year + "/month/" + month + "/day/" + day + "/data.json"))
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
		System.out.println("" + strikes.size() + " Strikes received");

		/*
		Set area to search, starting with default, then by asking through interface
		*/
		LatLonRectangle malmoArea = new LatLonRectangle(55.48, 55.65, 12.75, 13.23);
		LatLonRectangle skaneArea = new LatLonRectangle(55.29, 56.42, 12.35, 14.45);
		
		/*
		Filter data using given area
		*/
		JsonArray malmoStrikes = new JsonArray();
		JsonArray skaneStrikes = new JsonArray();
		Iterator<JsonElement> strikesIt = strikes.iterator();
		while(strikesIt.hasNext()) {
			JsonObject strike = (JsonObject) strikesIt.next();
			double lat = strike.get("lat").getAsDouble();
			double lon = strike.get("lon").getAsDouble();
			LatLonCoordinate strikeCurrent = new LatLonCoordinate(lat,lon);
			if(malmoArea.contains(strikeCurrent)) { 
				malmoStrikes.add(strike);
			}
			if(skaneArea.contains(strikeCurrent)) { 
				skaneStrikes.add(strike);
			}
		}

		/*
		Show result, starting with println, then by showing in interface
		*/
		Calendar latestStrike = new GregorianCalendar(new SimpleTimeZone(0, "UTC"));
		System.out.println("Number of strikes in Malmö area: " + malmoStrikes.size());
		if(malmoStrikes.size() > 0) {
			// Removing until I figure out how I should sort the strikes, if they aren't sorted already
			//System.out.println("Latest strike occured at: " + latestStrike.getTime().toString());
		}
		System.out.println("Number of strikes in Skåne area: " + skaneStrikes.size());
		if(skaneStrikes.size() > 0) {
			// Removing until I figure out how I should sort the strikes, if they aren't sorted already
			//System.out.println("Latest strike occured at: " + latestStrike.getTime().toString());
		}
		

		System.out.println("End of program reached");
	
	}

}
