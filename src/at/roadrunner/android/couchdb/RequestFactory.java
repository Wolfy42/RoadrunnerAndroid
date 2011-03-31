package at.roadrunner.android.couchdb;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

import at.roadrunner.android.Config;

public class RequestFactory {

	public static HttpGet createHttpGet(String path)  {
		StringBuilder sb = new StringBuilder(Config.HOST);
		if (path != null)  {
			sb.append(path);
		}
		HttpGet get = new HttpGet(sb.toString());
		return get;
	}
		
	public static HttpPut createHttpPut(String docId)  {
		HttpPut put = new HttpPut(Config.DB_HOST + docId);
		setHeader(put);
		
		return put;
	}
	
	private static void setHeader(HttpRequestBase request)  {
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
	}
	
}
