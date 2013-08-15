package si.chen.votingapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class DisplayActivity extends Activity {
	
	private List<Candidate> candidates_list;
	private int[] candidate_id;
	private String[] candidate_names;
	private String[] candidate_office;
	private String[] candidate_photo_url;
	private String[] candidate_promises;
	private String[] candidate_statement;
	private ArrayAdapter<String> adapter;
	private ListView lv;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		lv = (ListView) findViewById(R.id.listView_list_candidates);
		
		DatabaseHandler db = new DatabaseHandler(this);
		
		
		/* Get all candidate information from database */
		candidates_list = db.getAllCandidates();
	
		/* Initialise arrays for storing different candidate information */
		candidate_id = new int[candidates_list.size()];
		candidate_names = new String[candidates_list.size()];
		candidate_office = new String[candidates_list.size()];
		candidate_photo_url = new String[candidates_list.size()];
		candidate_promises = new String[candidates_list.size()];
		candidate_statement = new String[candidates_list.size()];
		
		
		/* Storing all candidate information in arrays */
		for (int i = 0; i < candidates_list.size(); i++) {
			candidate_id[i] = candidates_list.get(i).getID();
			candidate_names[i] = candidates_list.get(i).getName();
			candidate_office[i] = candidates_list.get(i).getOffice();
			candidate_photo_url[i] = candidates_list.get(i).getPhotograph();
			candidate_promises[i] = candidates_list.get(i).getPromises();
			candidate_statement[i] = candidates_list.get(i).getStatement();
		}
		
		
		/* Display names of all candidates in the database in a List View */
		Log.i("key_info_add_candidates_to_listview", "Adding candidate names to list view...");
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, candidate_names);
		lv.setAdapter(adapter);
		
		
		/* Display specific candidate information when their name is selected */
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
				/* Stores selected candidate information in an intent */
				Intent intent = new Intent(getApplicationContext(), CandidateInfoActivity.class);
				intent.putExtra("key_candidate_id", candidate_id[position]);
				intent.putExtra("key_candidate_name", candidate_names[position]);
				intent.putExtra("key_candidate_office", candidate_office[position]);
				intent.putExtra("key_candidate_photo_url", candidate_photo_url[position]);
				intent.putExtra("key_candidate_promises", candidate_promises[position]);
				intent.putExtra("key_candidate_statement", candidate_statement[position]);	
				startActivity(intent);
				finish();
			}
			
		});
			
		/* Close database when finished */
		db.close();
	
	}

    @Override
    public void onBackPressed() {
    	
    	Intent intent = new Intent(this, MainMenuActivity.class);
    	startActivity(intent);
    	finish();
    }
}
