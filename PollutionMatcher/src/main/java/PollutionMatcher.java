import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import io.nats.client.*;
import java.time.Duration;

import java.nio.charset.StandardCharsets;

@RestController
@EnableAutoConfiguration

public class PollutionMatcher {
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}
	
	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(PollutionMatcher.class, args);
		Connection nats = Nats.connect("nats://nats:IoslProject2018@iosl2018hxqma76gup7si-vm0.westeurope.cloudapp.azure.com:4222");
		System.out.println("Connected to " + nats.getConnectedUrl());
		
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
 * */