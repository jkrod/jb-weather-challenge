
package jb.umbrella;

import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.webclient.WebClient;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;

// TODO: Refactor

@ApplicationScoped
public class WeatherService {

    private WebClient webClient;

    public WeatherService() {
        this.webClient = WebClient.builder().addReader(JsonpSupport.reader()).addWriter(JsonpSupport.writer())
                .addMediaSupport(JsonpSupport.create()).build();
    }

    public JsonObject getWeatherObject(String longitude, String latitude) throws Exception {
        System.out.println("FROM Service " + longitude + " " + latitude);
        try {
           return webClient.get().uri("https://api.met.no/weatherapi/locationforecast/2.0/complete?" + "lat=" + latitude + "&" + "lon=" + longitude).request(JsonObject.class).get();
        } catch (Exception e) {
            throw new Exception("Weather Service failed: No precipitation found");
        }
    }
}
