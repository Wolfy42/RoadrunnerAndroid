package at.roadrunner.android.couchdb;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;

import at.roadrunner.android.Config;

public class RequestFactory {

	public static HttpGet createHttpGet()  {
		HttpGet put = new HttpGet(Config.HOST);
        put.setHeader("Accept", "application/json");
        put.setHeader("Content-type", "application/json");
		
		return put;
	}
	
	public static HttpGet createHttpGetForId()  {
		HttpGet put = new HttpGet(Config.HOST + "_uuids");
        put.setHeader("Accept", "application/json");
        put.setHeader("Content-type", "application/json");
		
		return put;
	}
	
	public static HttpPut createHttpPut(String docId)  {
		HttpPut put = new HttpPut(Config.DB_HOST + docId);
        put.setHeader("Accept", "application/json");
        put.setHeader("Content-type", "application/json");
		
		return put;
	}
	
}
