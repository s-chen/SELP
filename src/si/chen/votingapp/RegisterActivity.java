package si.chen.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	UserSessionManager session;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        session = new UserSessionManager(getApplicationContext());
    }
    
    
    /* This is called when 'Confirm' button is clicked */
    public void confirmRegister(View view) {
    	
    	Intent intent = new Intent(this, MainMenuActivity.class);
    	EditText editText = (EditText) findViewById(R.id.editText_student_number);
    	String student_number = editText.getText().toString();
    	
    	/* Validate and store student number entered by user in shared preferences */
    	if (student_number.length() == 7 && student_number.matches("[0-9]+")) {
    	
    		Log.i("key_info_student_number", "Storing student number in shared preferences..");
    		session.storeStudentNumber(student_number);
    		startActivity(intent);
    		finish();
  
    	} else {
    		editText.setError("Please enter a 7 digit student number");
    	}
    }


    @Override
    public void onBackPressed() {
    	
    	Intent intent = new Intent(this, MainMenuActivity.class);
    	startActivity(intent);
    	finish();
    }
}
