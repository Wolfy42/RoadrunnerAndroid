package at.roadrunner.android.couchdb;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import at.roadrunner.android.Config;
import at.roadrunner.android.couchdb.CouchDBException.CouchDBNotReachableException;
import at.roadrunner.android.model.Log;
import at.roadrunner.android.model.Log.LogType;

public class RequestWorker {
	
	public static void saveLog(String itemId, LogType logType)  {
		
		JSONObject log = new JSONObject();
		try {
			log.put(Log.TYPE_KEY, Log.TYPE_VALUE);
			log.put(Log.LOG_TYPE_KEY, logType.name());
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
	
	public static void replicate() {

		JSONObject repl = new JSONObject();
		try {
			repl.put("source", Config.DATABASE);
			repl.put("target", "http://172.16.102.224:5984/"+Config.DATABASE);
			repl.put("filter", "roadrunnermobile/logfilter");
			
			HttpPost post = RequestFactory.createHttpPost("_replicate");
			StringEntity body = new StringEntity(repl.toString());
			post.setEntity(body);
			
	        String result = HttpExecutor.getInstance().executeForResponse(post);
	        result.toString();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CouchDBNotReachableException e) {
			// TODO Auto-generated catch block
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
