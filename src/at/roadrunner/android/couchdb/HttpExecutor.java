package at.roadrunner.android.couchdb;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import at.roadrunner.android.couchdb.CouchDBException.CouchDBNotReachableException;
import at.roadrunner.android.util.HttpHelper;

public class HttpExecutor {

	private HttpExecutor(){
		_client = new DefaultHttpClient();
	};
	
	private static HttpExecutor _instance;
	private HttpClient _client;
	
	public static HttpExecutor getInstance()  {
		if (_instance == null)  {
			_instance = new HttpExecutor();
		}
		return _instance;
	}
	
	public String executeForResponse(HttpRequestBase request) throws CouchDBNotReachableException, JSONException  {
		synchronized (this) {
			try {
				HttpResponse response = _client.execute(request);
				return HttpHelper.contentToString(response); 
			} catch (IOException e) {
				throw new CouchDBException.CouchDBNotReachableException();
			}
		}
	}
}
