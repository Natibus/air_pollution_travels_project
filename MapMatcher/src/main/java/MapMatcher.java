import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import io.nats.client.*;
import java.time.Duration;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;

@RestController
@EnableAutoConfiguration
public class MapMatcher {
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(MapMatcher.class, args);
		Connection nats = Nats.connect("nats://nats:IoslProject2018@iosl2018hxqma76gup7si-vm0.westeurope.cloudapp.azure.com:4222");
		System.out.println("Connected to " + nats.getConnectedUrl());
		//String test = "hello world";

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
	}
	
}
/* liens
 * https://nats.io/documentation/writing_applications/publishing/?lang=java
 * 
 * 
 * */
