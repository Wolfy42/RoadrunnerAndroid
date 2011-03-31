package at.roadrunner.android.couchdb;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import at.roadrunner.android.couchdb.CouchDBException.CouchDBNotReachableException;
import at.roadrunner.android.model.Log;

public class RequestWorker {
	
	public static void saveLogForLoad(String itemId)  {
		
		JSONObject log = new JSONObject();
		try {
			log.put(Log.TYPE_KEY, Log.TYPE_VALUE);
			log.put(Log.LOG_TYPE_KEY, Log.LogType.LOAD.name());
			log.put(Log.ITEM_KEY, itemId);
			
			HttpPut put = RequestFactory.createHttpPut(getNextId());
	        StringEntity body = new StringEntity(log.toString());
	        put.setEntity(body);
			
	        HttpExecutor.getInstance().executeForResponse(put);
				        
		} catch (CouchDBNotReachableException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static String getNextId()  {
		try {
			HttpGet get = RequestFactory.createHttpGet("_uuids");		
			JSONObject content = new JSONObject(HttpExecutor.getInstance().executeForResponse(get)); 
			JSONArray array = new JSONArray(content.getString("uuids"));
			return array.getString(0);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (CouchDBNotReachableException e) {
			e.printStackTrace();
		}
		return null;
	}
}
