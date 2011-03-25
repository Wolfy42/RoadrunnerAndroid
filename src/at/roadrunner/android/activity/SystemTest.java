package at.roadrunner.android.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import at.roadrunner.android.R;
public class SystemTest extends Activity {
	
	private final String RESULT = "RESULT";
	private final String TESTCASE = "TESTCASE";
	
	private GridView _testCaseList;
	private ArrayList<HashMap<String, String>> _testList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_systemtest);
		
		_testCaseList = (GridView) findViewById(R.id.TestCaseList);
		
        _testList = new ArrayList<HashMap<String, String>>();
        
        
        SimpleAdapter mSchedule = new SimpleAdapter(this, _testList, R.layout.item_systemtest,
	                    new String[] {RESULT, TESTCASE}, new int[] {R.id.RESULT, R.id.TESTCASE});
        _testCaseList.setAdapter(mSchedule);
        
        new UpdateTask().execute();
        
	}
	
	class UpdateTask extends AsyncTask<Void, HashMap<String,String>, Void>  {

		@Override
		protected Void doInBackground(Void... params) {
			HashMap<String, String> testListItem = new HashMap<String, String>();
	        testListItem.put(RESULT, "OK");
	        testListItem.put(TESTCASE, "RUNNING");
	        publishProgress(testListItem);
			return null;
		}
		
		protected void onProgressUpdate(HashMap<String,String>... testItem) {
						
	        _testList.add(testItem[0]);
			
		};
		
		
		/*@Override
		protected Integer doInBackground(URL... params) {
			URL url = params[0];
			publishProgress(PROGRESS_STATE.START.state);
			try {
				//delete all entries in DB
				getContentResolver().delete(StauProvider.CONTENT_URI, null, null);
				//read from server
				URLConnection ucon = url.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
				String line = reader.readLine();
				String[] split;
				while (line != null)  {
					split = line.split(";");
					if (split.length >= 5)  {
						ContentValues editedValues = new ContentValues();
				        editedValues.put(StauProvider.STRASSE, split[0].trim());
				        editedValues.put(StauProvider.RICHTUNG, split[1].trim());
				        editedValues.put(StauProvider.VON, split[2].trim());
				        editedValues.put(StauProvider.BIS, split[3].trim());
				        editedValues.put(StauProvider.TIMESTAMP, split[4].trim());
				        //insert into DB
				        getContentResolver().insert(StauProvider.CONTENT_URI, editedValues);
					}
					line = reader.readLine();
				}
			} catch (IOException e)  {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
//			publishProgress(PROGRESS_STATE.DONE.state);
//			displayLastStaumeldung();
//			return;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
//			PROGRESS_STATE state = PROGRESS_STATE.stateForState(values[0]);
//			if (state == PROGRESS_STATE.START)  {
//				_progressBar.setVisibility(View.VISIBLE);
//			}  else if (state == PROGRESS_STATE.DONE)  {
//				_progressBar.setVisibility(View.INVISIBLE);
//			}
		}*/
	}
	
}
