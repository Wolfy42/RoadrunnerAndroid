package at.roadrunner.android.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

public class HttpHelper {

	public static String contentToString(HttpResponse response)  {
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
			    builder.append(line);
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
