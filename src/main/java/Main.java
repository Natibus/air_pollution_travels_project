import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Connection;
import org.springframework.web.bind.annotation.RestController;

import io.nats.client.Connection;
import io.nats.client.Nats;


@RestController
@EnableAutoConfiguration
public class Main {
	/*@RequestMapping("/")
	String home() {
		return "Hello World!";
	}*/

	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(Main.class, args);
		Connection nats = Nats.connect("nats://nats:IoslProject2018@iosl2018hxqma76gup7si-vm0.westeurope.cloudapp.azure.com:4222");
		// String url = System.getenv().get("NATS_URI");
		// System.out.println("url :" + url);
		// Connection nats = Nats.connect("demo.nats.io");

		System.out.println("Connected to " + nats.getConnectedUrl());
		// String test = "hello world";
		
		nats.close();
		System.out.println("Connected to " + nats.getConnectedUrl());
	}
	
	
}
/* liens
 * https://nats.io/documentation/writing_applications/publishing/?lang=java
 * 
 * 
 * */
