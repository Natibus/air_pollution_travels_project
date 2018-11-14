import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import io.nats.client.*;

import java.nio.charset.StandardCharsets;

@RestController
@EnableAutoConfiguration

public class Main {
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(Main.class, args);
		Connection nats = Nats.connect("nats://nats:IoslProject2018@iosl2018hxqma76gup7si-vm0.westeurope.cloudapp.azure.com:4222");
		System.out.println("Connected to " + nats.getConnectedUrl());
		//String test = "hello world";
		nats.publish("Spring_jobQueue1", "All is Well".getBytes(StandardCharsets.UTF_8));

		nats.close();
		System.out.println("Connected to " + nats.getConnectedUrl());
	}
	
	
}
/* liens
 * https://nats.io/documentation/writing_applications/publishing/?lang=java
 * 
 * 
 * */
