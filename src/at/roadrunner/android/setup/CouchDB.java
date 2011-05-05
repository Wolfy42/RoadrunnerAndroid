package at.roadrunner.android.setup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import at.roadrunner.android.Config;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.HttpExecutor;
import at.roadrunner.android.couchdb.RequestFactory;
import at.roadrunner.android.couchdb.RequestWorker;

public class CouchDB {
	private File _iniFile = null;

	public CouchDB() {
		_iniFile = new File(Config.LOCAL_INI);
	}

	/**
	 * creates the user Roadrunner in the database
	 */
	public void insertRoadrunnerUser() {

		URLConnection conn;
		BufferedReader reader = null;
		OutputStreamWriter writer = null;

		try {
			// Read ini.File
			conn = _iniFile.toURL().openConnection();
			conn.setDoInput(true);
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			boolean inAdmins = false;

			boolean foundRoadrunner = false;

			while (line != null) {
				if (line.equals("[admins]")) {
					inAdmins = true;
				}
				if (inAdmins && line.contains("roadrunner")) {
					foundRoadrunner = true;
				}
				if (inAdmins && !foundRoadrunner && !line.equals("[admins]")
						&& line.startsWith("[")) {
					sb.append("roadrunner=roadrunner");
					sb.append("\n");
					inAdmins = false;
					foundRoadrunner = true;
				}

				sb.append(line);
				sb.append("\n");
				line = reader.readLine();
			}

			if (!foundRoadrunner) {
				sb.append("roadrunner=roadrunner");
				sb.append("\n");
			}

			reader.close();

			if (sb.length() > 0) {

				writer = new OutputStreamWriter(new FileOutputStream(_iniFile));
				writer.write(sb.toString());
				writer.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * @return boolean true if Roadrunner user exists
	 */
	public boolean existsRoadrunnerUser() {
		BufferedReader bufReader;
		URLConnection conn;
		String line;
		String pattern = "roadrunner = -hashed";

		try {
			// Read ini.File
			conn = _iniFile.toURL().openConnection();
			bufReader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			while ((line = bufReader.readLine()) != null) {
				if (line.contains(pattern)) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * creates the Roadrunner Database Syntax of the HTTP request:
	 * 
	 * curl -X PUT http://127.0.0.1:5984/my_database
	 * 
	 * you have to use header authentication of http
	 */
	public boolean createRoadrunnerDB() {
		String url = Config.PROTOCOL + Config.URL + ":" + Config.PORT + "/"
				+ Config.DATABASE;
		String response;
		Log.v("url", url);

		try {
			response = HttpExecutor.getInstance().executeForResponse(
					RequestFactory.createHttpPutForDB(url));
			Log.v("url", response);
			JSONObject objResponse = new JSONObject(response);

			return (!objResponse.isNull("ok"));

		} catch (CouchDBException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * replicates initial documents from the server
	 */
	public boolean replicateInitialDocuments(Context context) {
		return new RequestWorker(context).replicateInitialDocuments();
	}
}
