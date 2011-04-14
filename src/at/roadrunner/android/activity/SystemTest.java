package at.roadrunner.android.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import at.roadrunner.android.R;
import at.roadrunner.android.setup.SystemTestCases;
import at.roadrunner.android.setup.SystemTestCases.TestCase;
public class SystemTest extends Activity {
	
	private final String RESULT = "RESULT";
	private final String TESTCASE = "TESTCASE";
	
	private ArrayList<HashMap<String, String>> _testList;
	private SimpleAdapter _mSchedule;
	
	private GridView _testCaseList;
	private ProgressBar _progressBar;
	private Context _context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_systemtest);
		_context = this;
		_testCaseList = (GridView) findViewById(R.id.TestCaseList);
		_progressBar = (ProgressBar) findViewById(R.id.systemstate_progressbar);
		
        _testList = new ArrayList<HashMap<String, String>>();
        _mSchedule = new SimpleAdapter(this, _testList, R.layout.row_item_systemtest,
	                    new String[] {RESULT, TESTCASE}, new int[] {R.id.RESULT, R.id.TESTCASE});
        _testCaseList.setAdapter(_mSchedule);
        
        new UpdateTask().execute();
	}
	
	class UpdateTask extends AsyncTask<Void, TestCase, Void>  {

		@Override
		protected Void doInBackground(Void... params) {
			SystemTestCases testCases = new SystemTestCases(_context);
			
			publishProgress(testCases.localCouchDBInstalled());
			publishProgress(testCases.localIniFileExists());
			publishProgress(testCases.localCouchDBRunning());
			publishProgress(testCases.localCouchDBReachable());
			publishProgress(testCases.localAdminUserExists());
			publishProgress(testCases.localDatabaseExists());
			publishProgress(testCases.localInitialReplicationExists());
			publishProgress(testCases.remoteCouchDBReachable());
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			_progressBar.setVisibility(View.INVISIBLE);
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			_progressBar.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected void onProgressUpdate(TestCase... testCase) {
			
			HashMap<String, String> testListItem = new HashMap<String, String>();
			testListItem.put(RESULT, testCase[0].getResult());
	        testListItem.put(TESTCASE, testCase[0].getTestCase());
	        _testList.add(testListItem);
	        
	        _testCaseList.setAdapter(_mSchedule);
		};
	}
	
	/*
	 * fix up the problems
	 */
	private void fixUpProblems() {
		boolean isValid = true;
		
//		// is CouchDB installed and running?
//		if (AppInfo.isAppInstalled(_context, COUCHDB_PACKAGE) ) {
//			if (AppInfo.isAppRunning(_context, COUCHDB_SERVICE) ) {
//				CouchDB couch = new CouchDB();
//				// check if admin user "roadrunner" exists or create it
//				if (couch.existsRoadrunnerUser()) {
//					// create database and replicate initial documents
//					couch.createRoadrunnerDB();
//					couch.replicateInitialDocuments(_context);
//					Log.v("user", "database created and documents replicated");
//				} else {
//					couch.insertRoadrunnerUser();
//					AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//					alertBuilder.setMessage(R.string.roadrunner_dialog_restartCouchDB);
//					AlertDialog alert = alertBuilder.create();
//					
//					alert.show();
//					Log.v("user", "not existsing");
//				}
//			} else {
//				//TODO: start the activity
//				AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//				alertBuilder.setMessage(R.string.roadrunner_dialog_closedCouchDB);
//				AlertDialog alert = alertBuilder.create();
//				
//				alert.show();
//			}
//		} else {
//			// show dialog
//			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//			alertBuilder.setMessage(R.string.roadrunner_dialog_missingCouchDB);
//			alertBuilder.setCancelable(false);
//			alertBuilder.setPositiveButton(R.string.app_dialog_yes, new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int id) {
//					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + COUCHDB_PACKAGE));
//					startActivity(intent);
//				}
//			});
//			alertBuilder.setNegativeButton(R.string.app_dialog_no, new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int id) {
//					dialog.cancel();
//				}
//			});
//			AlertDialog alert = alertBuilder.create();
//			
//			alert.show();
//			Log.v("test", "not installed");
//		}
	}
	
	/*
	 * inflate menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.systemtest_menu, menu);
		return true;
	}
	
	/*
     * Event OptionsMenuItemSelected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.systemtest_menu_fixitup:
        	fixUpProblems();
        	Log.v("test", "clicked");
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
