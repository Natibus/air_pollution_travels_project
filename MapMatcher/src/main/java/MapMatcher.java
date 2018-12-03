import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import io.nats.client.Connection;
import io.nats.client.Nats;


@RestController
@EnableAutoConfiguration
public class MapMatcher {
	/*@RequestMapping("/")
	String home() {
		return "Hello World!";
	}*/

	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(MapMatcher.class, args);
		String natsUrl = System.getenv().get("NATS_URI");
		Connection nats = Nats.connect(natsUrl);
		
		RouteGson route = new RouteGson(
			1,
			Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers")
		);
		// ArrayList test = "hello world";
		// Gson gson = new GsonBuilder().create();
		String serializedRoute = new Gson().toJson(route);

		nats.publish("Spring_jobQueue1", serializedRoute.getBytes(StandardCharsets.UTF_8));
		nats.flush(Duration.ZERO);
		System.out.println("message sent : <"+serializedRoute+">");

		nats.close();
		System.out.println("Connected to " + nats.getConnectedUrl());
		// Properties props = new Properties();
		// props.load(this.getClass().getResourceAsStream("project.properties"));
		// String basedir = props.get("project.basedir");
		getJson("/data/test.json");
	}
	
	private static void getJson(String filename)
	{
		System.out.println("test");
		JsonObject jsonObject = new JsonObject();
		Message message = null;
		try
		{
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(filename));
			jsonObject = jsonElement.getAsJsonObject();
			message = new Message(jsonObject);
		}
		catch(JsonSyntaxException e)
		{
			System.out.println("json format is wrong");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(message.toString());
	}
	
}
/* liens
 * https://nats.io/documentation/writing_applications/publishing/?lang=java
 * 
 * 
 * */
