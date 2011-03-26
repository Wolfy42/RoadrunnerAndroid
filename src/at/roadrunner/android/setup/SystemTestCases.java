package at.roadrunner.android.setup;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import at.roadrunner.android.Config;
import at.roadrunner.android.R;
import at.roadrunner.android.util.HttpHelper;

public class SystemTestCases {

	private Context _context;
	private String _ok;
	private String _fail;
	public SystemTestCases(Context context) {
		_context = context;
		_ok = _context.getString(R.string.systemtest_test_ok);
		_fail = _context.getString(R.string.systemtest_test_fail);
	}
	
	public TestCase iniFileExists()  {
		String test = _context.getString(R.string.systemtest_test_ini_file_exists);
		
		File file = CouchDB.getIniFile();
		if (file.exists())  {
			return new TestCase(_ok, test);
		}  else  {
			return new TestCase(_fail, test);
		}
	}
	
	public TestCase localCouchDBReachable()  {
		String test = _context.getString(R.string.systemtest_test_local_couch_db_reachable);
			
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(Config.HOST);
		try {
			HttpResponse response = client.execute(request);
			JSONObject content = new JSONObject(HttpHelper.contentToString(response)); 
			if (content.getString("couchdb") != null)  {
				return new TestCase(_ok,test);
			}
		} catch (Exception e) {  }
		return new TestCase(_fail,test);
	}
	
	public TestCase localDatabaseExists()  {
		String test = _context.getString(R.string.systemtest_test_local_db_exists);
			
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(Config.HOST+"_all_dbs");
		try {
			HttpResponse response = client.execute(request);
			JSONArray content = new JSONArray(HttpHelper.contentToString(response)); 
			for (int i=0; i<content.length(); i++)  {
				if (content.getString(i).equals(Config.DATABASE))  {
					return new TestCase(_ok,test);
				}
			}
		} catch (Exception e) {  }
		return new TestCase(_fail,test);
	}
	
	public static class TestCase  {
		private String _result;
		private String _testCase;
		
		public TestCase(String result, String testCase)  {
			_result = result;
			_testCase = testCase;
		}
		public String getResult()  {
			return _result;
		}
		public String getTestCase()  {
			return _testCase;
		}
	}
}
