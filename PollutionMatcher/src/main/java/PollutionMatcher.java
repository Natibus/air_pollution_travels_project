import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Connection;
import org.springframework.web.bind.annotation.RestController;

import io.nats.client.*;

import java.time.Duration;

import java.nio.charset.StandardCharsets;

@RestController
@EnableAutoConfiguration
public class PollutionMatcher {
	/*@RequestMapping("/")
	String home() {
		return "Hello World!";
	}*/

	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(PollutionMatcher.class, args);
		String natsUrl = System.getenv().get("NATS_URL");
		Connection nats = Nats.connect(natsUrl);
		
		// Connection nats = Nats.connect("demo.nats.io");

		Subscription sub = nats.subscribe("Spring_jobQueue1");
		
		Message msg = sub.nextMessage(Duration.ZERO);
		String str = new String(msg.getData(), StandardCharsets.UTF_8);
		
		System.out.println("received <"+str+">");
		
		nats.close();
		System.out.println("Connected to " + nats.getConnectedUrl());
	}
	
	
}
/* liens
 * https://nats.io/documentation/writing_applications/publishing/?lang=java
 * 
 * 
 * */
