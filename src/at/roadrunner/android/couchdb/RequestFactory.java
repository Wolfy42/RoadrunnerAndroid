package at.roadrunner.android.couchdb;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
	
	public static HttpPut createHttpPutForDB(String url)  {
		HttpPut put = new HttpPut(url);
		setHeader(put);
		setAdminAuthentication(put);
		
		return put;
	}
	
	public static HttpPost createLocalHttpPost(String path)  {
		StringBuilder sb = new StringBuilder(Config.HOST);
		if (path != null)  {
			sb.append(path);
		}
		HttpPost post = new HttpPost(sb.toString());
		setHeader(post);
		
		return post;
	}
	
	private static void setHeader(HttpRequestBase request)  {
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
	}
	
	private static void setAdminAuthentication(HttpRequestBase request)  {
		// cm9hZHJ1bm5lcjpyb2FkcnVubmVy => BASE64 encoded (roadrunner:roadrunner);
		request.setHeader("Authorization", "Basic cm9hZHJ1bm5lcjpyb2FkcnVubmVy");
	}
}
