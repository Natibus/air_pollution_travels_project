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
import io.nats.client.Subscription;


@RestController
@EnableAutoConfiguration
public class MapMatcher {
	/*@RequestMapping("/")
	String home() {
		return "Hello World!";
	}*/

	public static void main(String[] args) throws Exception
	{ //nats://nats:IoslProject2018@iosl2018hxqma76gup7si-vm0.westeurope.cloudapp.azure.com:4222
		SpringApplication.run(MapMatcher.class, args);
		String natsUrl = System.getenv().get("NATS_URL");
		Connection nats = Nats.connect(natsUrl);
		// Subscribe
		Subscription sub = nats.subscribe("toll-simulator");
		
		System.out.println("subscribed to : " + sub.getSubject());
		// Read a message
		io.nats.client.Message msg = sub.nextMessage(Duration.ZERO);
		String str = new String(msg.getData(), StandardCharsets.UTF_8);
		
		getJson(str);
		
		
		/*RouteGson route = new RouteGson(
			1,
			Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers")
		);*/
		// ArrayList test = "hello world";
		// Gson gson = new GsonBuilder().create();
		//String serializedRoute = new Gson().toJson(route);

		/*nats.publish("Spring_jobQueue1", serializedRoute.getBytes(StandardCharsets.UTF_8));
		nats.flush(Duration.ZERO);
		System.out.println("message sent : <"+serializedRoute+">");*/

		nats.close();
		System.out.println("Connected to " + nats.getConnectedUrl());
		// Properties props = new Properties();
		// props.load(this.getClass().getResourceAsStream("project.properties"));
		// String basedir = props.get("project.basedir");
		//getJson("/data/test.json");
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
