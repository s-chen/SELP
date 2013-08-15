package si.chen.votingapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CandidateInfoActivity extends Activity implements OnRatingBarChangeListener {

	private Intent intent;
	private String name;
	private String office;
	private int id;
	private String photo_url;
	private String promises;
	private String statement;
	private UserSessionManager session;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_info);
        
        Button send_tweet_button = (Button) findViewById(R.id.button_send_tweet);
        
        session = new UserSessionManager(getApplicationContext());
           	
        
        TextView textView_name = (TextView) findViewById(R.id.textView_candidate_name);
        TextView textView_office = (TextView) findViewById(R.id.textView_candidate_office);
        TextView textView_promises = (TextView) findViewById(R.id.textView_candidate_promises);
        TextView textView_statement = (TextView) findViewById(R.id.textView_candidate_statement);
        RatingBar candidate_ratings = (RatingBar) findViewById(R.id.ratingBar_candidate_rating);

        
        /* Gets all the data from DisplayActivity */
        Log.i("key_info_get_intent_data", "Getting selected candidate data...");
        intent = getIntent();
        id = intent.getIntExtra("key_candidate_id", 0);
        name = intent.getStringExtra("key_candidate_name");
        office = intent.getStringExtra("key_candidate_office");
        promises = intent.getStringExtra("key_candidate_promises");
        statement = intent.getStringExtra("key_candidate_statement");
        
        
        /* Display candidate name */
        textView_name.setText(name);
        
        /* Display candidate office */
        textView_office.setText(office);
        
        /* Display candidate promises */
        textView_promises.setText(promises);
        
        /* Display candidate statement */
        textView_statement.setText(statement);
        
        /* Gets rating for a candidate stored in shared preferences */
        candidate_ratings.setRating(session.getRatings(id));
        
       
        
        /* Display candidate photo */
        photo_url = intent.getStringExtra("key_candidate_photo_url");
        try {
        	ImageView imgView_photo = (ImageView) findViewById(R.id.imageView_candidate_photo);
        	Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(photo_url).getContent());
        	imgView_photo.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } 
        
        
       
        /* Only allow user to tweet for a candidate after user has voted for them */
        if (!session.checkHasVoted(id)) {
        	send_tweet_button.setEnabled(false);
        }
        
        
 
        /* Calls the listener when ratings are changed by the user */
        candidate_ratings.setOnRatingBarChangeListener(this);
       
    }
    
    /* Display message and stores new ratings */
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
    		
    	session.storeRatings(id, rating);
    	
    	Toast.makeText(getApplicationContext(), "New rating: " + rating, Toast.LENGTH_SHORT).show();
    	
    }
    
    
    /* This is called when 'Delete' button is clicked */
    public void deleteCandidateButton(View view) {
    	
    	final DatabaseHandler db = new DatabaseHandler(this);
    	
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(CandidateInfoActivity.this);
    	
    	/* Set Dialog title */
    	alertDialog.setTitle("Confirm Delete");
    	
    	/* Set Dialog message */
    	alertDialog.setMessage("Are you sure you want to delete this candidate?");
    	
    	
    	/* Set positive "Yes" button */
    	alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				/* Delete candidate from database */
				Log.i("key_info_delete_candidate", "Deleting candidate from database...");
				db.deleteCandidate(id);
			
				Toast.makeText(getApplicationContext(), "Candidate deleted", Toast.LENGTH_SHORT).show();
				
				/* Refresh DisplayActivity to update List View */
				Intent refresh = new Intent(getApplicationContext(), DisplayActivity.class);
				startActivity(refresh);
				
				/* Destroy current activity */
				finish();
			
			}
		});
    	
    	/* Set negative "No" button */
    	alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getApplicationContext(), "Candidate NOT deleted", Toast.LENGTH_SHORT).show();
				dialog.cancel();
			}
		});
    	
    	/* Show alert message */
    	alertDialog.show();
    }
     
    
    /* This is called when 'Tweet' button is clicked */
    public void sendTweet(View view) {
    	
    	/* Sends a private tweet with hashtag #becounted to @InformaticsAndr after user has voted (%23 is the escape code for #) 
    	 * (%40 is the escape code for @) */
    	Log.i("key_info_tweet", "Sending tweet...");
    	String tweet = "https://twitter.com/intent/tweet?text=I have voted!! Have you? %23becounted %40InformaticsAndr";
    	Uri uri = Uri.parse(tweet);
    	startActivity(new Intent(Intent.ACTION_VIEW, uri));
    	
    	finish();
    	
    }
    
    
   /* This is called when 'Vote (Email)' button is clicked */
    public void sendEmail(View view) {
    	
    	
    	Log.i("key_info_email", "Sending email...");
    	
    	
    	/* A vote is submitted for a particular candidate */
    	session.hasVoted(id, true);
    	
    	
    	String[] email_address = {"informatics.android@gmail.com"};
    	String[] email_address_cc = {"s" + session.getStudentNumber() + "@sms.ed.ac.uk"};
    	
    	Intent email_intent = new Intent(Intent.ACTION_SEND);
    	email_intent.setType("plain/text");
    	email_intent.putExtra(Intent.EXTRA_EMAIL, email_address);
    	email_intent.putExtra(Intent.EXTRA_CC, email_address_cc);
    	email_intent.putExtra(Intent.EXTRA_SUBJECT, "Confirm vote");
    	email_intent.putExtra(Intent.EXTRA_TEXT, "I have voted for " + name + " who is running for " + office);
    	
    	try {
    		startActivity(Intent.createChooser(email_intent, "Send email..."));
    	} catch (android.content.ActivityNotFoundException e) {
    		Toast.makeText(this, "There are no email clients installed", Toast.LENGTH_SHORT).show();
    	}
    	
    }
    
    
    @Override
    public void onBackPressed() {
    	
    	Intent intent = new Intent(this, DisplayActivity.class);
    	startActivity(intent);
    	finish();
    }
    
    
    /* Refresh activity on restart */
    public void onRestart() {
    	
    	super.onRestart();
    	intent = getIntent();
    	finish();
    	startActivity(intent);
    } 
}
