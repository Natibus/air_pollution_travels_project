import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

public class Message
{
	private String messageId;
	private String carId;
	private Date timestamp;
	private int accuracy;
	private double lat;
	private double lon;
	DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
	
	public Message()
	{
		
	}
	
	public Message(String messageId, String carId, Date timestamp, int accuracy, double lat, double lon)
	{
		this.messageId = messageId;
		this.carId = carId;
		this.accuracy = accuracy;
		this.lat = lat;
		this.lon = lon;
	}
	
	public Message(JsonObject jsonObject) throws Exception
	{
		try
		{
			System.out.println(jsonObject.toString());
			this.messageId = jsonObject.get("messageId").getAsString();
			this.carId = jsonObject.get("carId").getAsString();
			this.timestamp = format.parse(jsonObject.get("timestamp").getAsString());
			this.accuracy = jsonObject.get("accuracy").getAsInt();
			this.lat = jsonObject.get("lat").getAsDouble();
			this.lon = jsonObject.get("lon").getAsDouble();
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public String getMessageId()
	{
		return this.messageId;
	}
	
	public String getCardId()
	{
		return this.carId;
	}
	
	public Date getDate()
	{
		return this.timestamp;
	}
	
	public int getAccuracy()
	{
		return this.accuracy;
	}
	
	public String toString()
	{
		return (this.messageId + " "
				+ this.carId + " "
				+ format.format(this.timestamp) + " "
				+ this.accuracy + " "
				+ this.lat + " "
				+ this.lon);
	}
}
