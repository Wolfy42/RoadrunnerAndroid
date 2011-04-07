package at.roadrunner.android.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
			
			publishProgress(testCases.iniFileExists());
			publishProgress(testCases.localCouchDBReachable());
			publishProgress(testCases.localDatabaseExists());
			
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
}
