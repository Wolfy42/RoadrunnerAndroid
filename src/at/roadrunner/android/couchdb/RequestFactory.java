package at.roadrunner.android.couchdb;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

import android.util.Base64;
import at.roadrunner.android.Config;

public class RequestFactory {

	public static HttpGet createLocalHttpGet(String path)  {
		StringBuilder sb = new StringBuilder(Config.HOST);
		if (path != null)  {
			sb.append(path);
		}
		HttpGet get = new HttpGet(sb.toString());
		return get;
	}
	
	public static HttpGet createRemoteDbHttpGet(String path, String username, String password)  {
		StringBuilder sb = new StringBuilder(Config.REMOTE_DB_HOST);
		if (path != null)  {
			sb.append(path);
		}
		HttpGet get = new HttpGet(sb.toString());
		if (username != null && password != null)  {
			setRemoteAuthentication(get, username, password);
		}
		return get;
	}
	
	public static HttpGet createRemoteTimeHttpGet()  {
		HttpGet get = new HttpGet(Config.TIME_HOST);
		return get;
	}
	
	public static HttpGet createRemoteHttpGet(String path)  {
		StringBuilder sb = new StringBuilder(Config.REMOTE_HOST);
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
	
	public static HttpPost createLocalHttpPostAuth(String path)  {
		StringBuilder sb = new StringBuilder(Config.HOST);
		if (path != null)  {
			sb.append(path);
		}
		HttpPost post = new HttpPost(sb.toString());
		setHeader(post);
		setAdminAuthentication(post);
		
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
	
	private static void setRemoteAuthentication(HttpRequestBase request, String username, String password)  {
		byte[] bytes = new StringBuilder(username).append(':').append(password).toString().getBytes();
		String base64Encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
		request.setHeader("Authorization", "Basic "+base64Encoded);
	}
}
