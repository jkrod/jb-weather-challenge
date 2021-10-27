
package jb.umbrella;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Path("/api")
@RequestScoped
public class WeatherResource {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());
    private JsonArray weatherTimeSeries;

    @Inject()
    WeatherService weatherService;
    
    @GET
    @Path("{longitude}/{latitude}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getPrecipitation(@PathParam("longitude") String longitude, @PathParam("latitude") String latitude) throws Exception {
        weatherTimeSeries = weatherService.getWeatherObject(longitude, latitude).getJsonObject("properties").getJsonArray("timeseries");
        return createResponse(getPrecipitationNextSixHours(weatherTimeSeries));
    }

    /*
    Returns probability of precipation as a string, for the first date and time after current date and time.
    */
    private String getPrecipitationNextSixHours(JsonArray weatherTimeSeries) throws Exception {
        for(int i = 0; i < weatherTimeSeries.size(); i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDateTime dateTime = LocalDateTime.parse(weatherTimeSeries.getJsonObject(i).getString("time"), formatter);
            
            if(dateTime.isAfter(LocalDateTime.now())) {
                return weatherTimeSeries.getJsonObject(i).getJsonObject("data").getJsonObject("next_6_hours").getJsonObject("details").getJsonNumber("probability_of_precipitation").toString();
            } 
        }
        // TODO: Implement better exception on no date and time found
        throw new Exception("No date and time found in weather time series after current date and time");
    }

    private JsonObject createResponse(String precipitation) {
        return JSON.createObjectBuilder().add("precipitation", precipitation).build();
    }
}
