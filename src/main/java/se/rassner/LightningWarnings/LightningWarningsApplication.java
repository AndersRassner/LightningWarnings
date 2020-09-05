package se.rassner.LightningWarnings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.LocalTime;

import com.google.gson.*;

@SpringBootApplication
public class LightningWarningsApplication {

	public static void main(String[] args) {
		/*
		Note that the intention is to move all these chunks into separate functions once the program stabilizes
		*/
		SpringApplication.run(LightningWarningsApplication.class, args);
		new LightningWarningsApplication().go();
	}
	
	public void go() {
		/*
		Make http request to get lightning strike data from SMHI
		*/
		HttpResponse<String> response = fetchStrikesFromSmhi();
		if(response != null && response.statusCode() != 200) {
			System.out.println("Response's not OK, ending program");
			return;
		}

		/*
		Transform received data to workable format, if needed.
		As far as I can tell I should be able to do what I want without transforming,
		however, if I want to sort the strikes it seems I need to move everything
		to an ArrayList<JsonObject>, create a Comparator<JsonObject> and in that
		override public int compare(JsonObject lhs, JsonObject rhs) so I can call
		Collections.sort with the ArrayList and the Comparator<JsonObject>
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
		ArrayList<LightningStrike> malmoStrikes = new ArrayList<LightningStrike>();
		ArrayList<LightningStrike> skaneStrikes = new ArrayList<LightningStrike>();

		Iterator<JsonElement> strikesIt = strikes.iterator();
		while(strikesIt.hasNext()) {
			LightningStrike strike = new LightningStrike((JsonObject) strikesIt.next());
			if(malmoArea.contains(strike.getCoordinates())) { 
				malmoStrikes.add(strike);
			}
			if(skaneArea.contains(strike.getCoordinates())) { 
				skaneStrikes.add(strike);
			}
		}

		/*
		Show result, starting with println, then by showing in interface
		*/
		System.out.println("Number of strikes in Malmö area: " + malmoStrikes.size());
		if(malmoStrikes.size() > 0) {
			Collections.sort(malmoStrikes);
			LocalTime latestStrike = malmoStrikes.get(malmoStrikes.size() - 1).getTimeOfStrike();
			System.out.println("Latest Malmö strike occured at: " + latestStrike.toString());

		}
		System.out.println("Number of strikes in Skåne area: " + skaneStrikes.size());
		if(skaneStrikes.size() > 0) {
			Collections.sort(skaneStrikes);
			LocalTime latestStrike = skaneStrikes.get(skaneStrikes.size() - 1).getTimeOfStrike();
			System.out.println("Latest Skåne strike occured at: " + latestStrike.toString());
		}
		

		System.out.println("End of program reached");
	
	}
	
	private HttpResponse<String> fetchStrikesFromSmhi() {
		HttpResponse<String> response = null;
		LocalDate currentDate = LocalDate.now();
		//System.out.println(currentDate.getTime().toString());
		int year = currentDate.getYear();
		int month = currentDate.getMonthValue(); //January is 0 because reasons...
		int day = currentDate.getDayOfMonth();
		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder(URI.create("https://opendata-download-lightning.smhi.se/api/version/latest/year/" + year + "/month/" + month + "/day/" + day + "/data.json"))
			.header("Content-Type", "application/json")
			.build();
		try{
			response = httpClient.send(request, BodyHandlers.ofString());
			System.out.println("Status of response is: " + response.statusCode());
		} catch(Exception ex) {ex.printStackTrace();}
		return response;	
	}

}
