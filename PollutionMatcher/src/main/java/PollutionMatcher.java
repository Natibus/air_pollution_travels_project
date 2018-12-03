import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Connection;
import org.springframework.web.bind.annotation.RestController;

import io.nats.client.Connection;
import io.nats.client.Nats;


@RestController
@EnableAutoConfiguration
public class PollutionMatcher {
	/*@RequestMapping("/")
	String home() {
		return "Hello World!";
	}*/

	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(MapMatcher.class, args);
		String natsUrl = System.getenv().get("NATS_URI");
		Connection nats = Nats.connect(natsUrl);
		
		// Connection nats = Nats.connect("demo.nats.io");

		System.out.println("Connected to " + nats.getConnectedUrl());

		nats.close();

		System.out.println("Connected to " + nats.getConnectedUrl());
	}
	
	
}
/* liens
 * https://nats.io/documentation/writing_applications/publishing/?lang=java
 * 
 * 
 * */
