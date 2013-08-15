package si.chen.votingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/* This class manages user interaction with the voting app */

public class UserSessionManager {

	SharedPreferences pref;
	Editor editor;
	Context context;
	int PRIVATE_MODE = 0;
	
	private static final String PREF_NAME = "UserPref";
	
	public static final String KEY_STUDENT_NUMBER = "student_number";
		

	/* Constructor */
	public UserSessionManager(Context context) {
		
		this.context = context;
		pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	
	/* Store student number in pref */
	public void storeStudentNumber(String student_number) {
		
		editor.putString(KEY_STUDENT_NUMBER, student_number);
		
		/* Commit changes */
		editor.commit();
		
	}
	
	
	/* Get student number */
	public String getStudentNumber() {
		
		return pref.getString(KEY_STUDENT_NUMBER, null);
	}
	
	
	/* Checks whether user has entered a student number */
	public boolean existStudentNumber() {
		
		if (pref.contains(KEY_STUDENT_NUMBER)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/* Store candidate ratings in pref */
	public void storeRatings(int id, float rating) {
		
		editor.putFloat(String.valueOf(id), rating);
		editor.commit();
	}
	
	
	/* Get candidate ratings */
	public float getRatings(int id) {
		
		return pref.getFloat(String.valueOf(id), 0);
	} 
		
	
	/* This is called when user has submitted a vote for a particular candidate */
	public void hasVoted(int id, boolean voted) {
		
		String key_id = "voted" + id;
		
		editor.putBoolean(key_id, voted);
		editor.commit();
		
	}
	
	/* Checks whether user has submitted a vote for a candidate */
	public boolean checkHasVoted(int id) {
		
		String key_id = "voted" + id;
		
		return pref.getBoolean(key_id, false);
	}
}
