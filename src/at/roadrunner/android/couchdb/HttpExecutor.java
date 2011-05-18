package at.roadrunner.android.couchdb;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import at.roadrunner.android.util.HttpHelper;

public class HttpExecutor {

	private HttpExecutor() {
		_client = new DefaultHttpClient();
	};

	private static HttpExecutor _instance;
	private final HttpClient _client;

	public static HttpExecutor getInstance() {
		if (_instance == null) {
			_instance = new HttpExecutor();
		}
		return _instance;
	}
	
	public synchronized String executeForResponse(HttpRequestBase request) throws CouchDBException  {
		synchronized (this) {
			try {
				HttpResponse response = _client.execute(request);
				switch (response.getStatusLine().getStatusCode()) {
					case 200:
					case 201:
					case 202:
						return HttpHelper.contentToString(response);
					case 304:
						return HttpHelper.contentToString(response);
					case 400:
					case 401:
					case 402:
					case 403:
					case 404:
					case 405:
					case 406:
					case 409:
					case 412:
					case 415:
					case 416:
					case 417:
						throw CouchDBException.badRequest();
					case 500:
						throw CouchDBException.internalServerError();

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";

	}
}
