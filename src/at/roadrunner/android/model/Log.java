package at.roadrunner.android.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Log {

	public static String TYPE_KEY = "type";
	public static String LOG_TYPE_KEY = "logType";
	public static String ITEMS_KEY = "items";
	public static String TIMESTAMP_KEY = "timestamp";
	public static String VALUE_KEY = "value";
	public static String DOCTRINE_METADATA_KEY = "doctrine_metadata";
	
	public static String ERROR = "error";
	public static String TYPE_VALUE = "log";
	
	public enum LogType {		
		REGISTER,
		UNREGISTER,
		LOAD,
		UNLOAD,
		TRACK,
		TEMPSENSOR,
		POSSENSOR,
		SENSOR_NOT_ACCESSIBLE;
	}
	
	public static void addDoctrineMetadata(JSONObject log)  {
		try {
			JSONObject value = new JSONObject().put("type", "Roadrunner.Model.Log"); 
			log.put("doctrine_metadata", value.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
