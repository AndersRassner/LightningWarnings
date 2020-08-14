package se.rassner.LightningWarnings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
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
		JsonArray Strikes = parser.getAsJsonObject().getAsJsonArray("values");
		//System.out.println(Strikes.toString());

		/*
		Set area to search, starting with default, then by asking through interface
		*/
		
		
		/*
		Filter data using given area
		*/
		
		/*
		Show result, starting with println, then by showing in interface
		*/
		System.out.println("End of program reached");
	
	}

}
