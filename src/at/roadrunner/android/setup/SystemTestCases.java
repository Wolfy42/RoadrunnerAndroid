package at.roadrunner.android.setup;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import at.roadrunner.android.Config;
import at.roadrunner.android.R;
import at.roadrunner.android.couchdb.HttpExecutor;
import at.roadrunner.android.couchdb.RequestFactory;
import at.roadrunner.android.util.AppInfo;

public class SystemTestCases {

	private CouchDB _couchDB;
	private Context _context;
	private String _ok;
	private String _fail;
	
	private static final String TAG = "SystemTestCases";
	
	public SystemTestCases(Context context) {
		_context = context;
		_couchDB = new CouchDB();
		_ok = _context.getString(R.string.systemtest_ok);
		_fail = _context.getString(R.string.systemtest_fail);
	}
	
	/*
	 * local CouchDB installed?
	 */
	public TestCase localCouchDBInstalled() {
		String msg = _context.getString(R.string.systemtest_local_couch_db_installed);
		
		if ( AppInfo.isAppInstalled(_context, Config.COUCHDB_PACKAGE) ) {
			return new TestCase(_ok, msg);
		} else {
			return new TestCase(_fail, msg);
		}
	}
	
	/*
	 * local.ini configuration file exists?
	 */
	public TestCase localIniFileExists()  {
		String msg = _context.getString(R.string.systemtest_local_ini_file_exists);
		
		if ( new File(Config.LOCAL_INI).exists() )  {
			return new TestCase(_ok, msg);
		}  else  {
			return new TestCase(_fail, msg);
		}
	}
	
	/*
	 * local CouchDB running?
	 */
	public TestCase localCouchDBRunning() {
		String msg = _context.getString(R.string.systemtest_local_couch_db_running);
		
		if ( AppInfo.isAppRunning(_context, Config.COUCHDB_SERVICE) ) {
			return new TestCase(_ok, msg);
		} else {
			return new TestCase(_fail, msg);
		}
	}
	
	/*
	 * local CouchDB reachable?
	 */
	public TestCase localCouchDBReachable()  {
		String msg = _context.getString(R.string.systemtest_local_couch_db_reachable);
			
		try {
			JSONObject response = new JSONObject(HttpExecutor.getInstance().executeForResponse(RequestFactory.createLocalHttpGet(null)));
			if (response.getString("couchdb") != null)  {
				return new TestCase(_ok, msg);
			}
		} catch (Exception e) {  }
		return new TestCase(_fail, msg);
	}
	
	/*
	 * local Admin User exists?
	 */
	public TestCase localAdminUserExists() {
		String msg = _context.getString(R.string.systemtest_local_admin_exists);
		
		if (_couchDB.existsRoadrunnerUser()) {
			return new TestCase(_ok, msg);
		} else {
			return new TestCase(_fail, msg);
		}
	}
	
	/*
	 * local Database exists?
	 */
	public TestCase localDatabaseExists()  {
		String msg = _context.getString(R.string.systemtest_local_db_exists);
			
		try {
			JSONArray content = new JSONArray(HttpExecutor.getInstance().executeForResponse(RequestFactory.createLocalHttpGet("_all_dbs"))); 
			for (int i = 0; i < content.length(); i++)  {
				if (content.getString(i).equals(Config.DATABASE))  {
					return new TestCase(_ok, msg);
				}
			}
		} catch (Exception e) {  }
		return new TestCase(_fail, msg);
	}
	
	/*
	 * remote CouchDB reachable?
	 */
	public TestCase remoteCouchDBReachable() {
		String msg = _context.getString(R.string.systemtest_remote_couch_db_reachable);
		
		try {
			JSONObject response = new JSONObject(HttpExecutor.getInstance().executeForResponse(RequestFactory.createRemoteHttpGet(null)));
			if (response.getString("couchdb") != null)  {
				return new TestCase(_ok, msg);
			}
		} catch (Exception e) {  }
		return new TestCase(_fail, msg);
	}
	
	/*
	 * initial Replication exists?
	 */
	public TestCase localInitialReplicationExists() {
		String msg = _context.getString(R.string.systemtest_local_initial_replication);
		String document = "roadrunner/_design/roadrunnermobile";
		
		try {
			JSONObject response = new JSONObject(HttpExecutor.getInstance().executeForResponse(RequestFactory.createLocalHttpGet(document)));
			Log.v(TAG, response.toString());
			if (response.getString("_rev") != null)  {
				return new TestCase(_ok, msg);
			}
		} catch (Exception e) {  }
		return new TestCase(_fail, msg);
	}
	
	/*
	 * TestCase Class
	 */
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
