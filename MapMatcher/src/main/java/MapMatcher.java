import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
		
//		System.out.println("subscribed to : " + sub.getSubject());
		// Read a message
//		io.nats.client.Message msg = sub.nextMessage(Duration.ZERO);
//		String str = new String(msg.getData(), StandardCharsets.UTF_8);
//
//		getJson(str);

		String osrmUrl = System.getenv().get("OSRM_URL");
		URL url = new URL(osrmUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		//http://router.project-osrm.org/route/v1/driving/13.388860,52.517037;13.397634,52.529407;13.428555,52.523219?overview=false

		Map<String, String> parameters = new HashMap<>();
		parameters.put("service", "route");
		parameters.put("version", "v1");
		parameters.put("profile", "driving");
		parameters.put("coordinates", "13.388860,52.517037;13.397634,52.529407;13.428555,52.523219");

		con.setDoOutput(true);
//		con.setRequestProperty("Content-Type", "application/json");
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
		out.flush();
		out.close();

		int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		System.out.println("response : " + content);
		in.close();
		
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
