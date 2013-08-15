package si.chen.votingapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	
	// Database Version
	private static final int DATABASE_VERSION = 1;
	
	// Database Name
	private static final String DATABASE_NAME = "candidateDatabase";
		
	// Candidates table name
	public static final String TABLE_CANDIDATES = "candidates";
	
		
	// Candidates table column names
	private static final String KEY_ID = "_id";
	private static final String KEY_NAME = "name";
	private static final String KEY_OFFICE = "office";
	private static final String KEY_PHOTOGRAPH = "photograph";
	private static final String KEY_PROMISES = "promises";
	private static final String KEY_STATEMENT = "statement";
		
		
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
		
	// Create table
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CANDIDATES_TABLE = "CREATE TABLE " + TABLE_CANDIDATES + "(" 
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_OFFICE + " TEXT," 
				+ KEY_PHOTOGRAPH + " TEXT," + KEY_PROMISES + " TEXT," + KEY_STATEMENT + " TEXT" + ")";
		db.execSQL(CREATE_CANDIDATES_TABLE);
	}
		
	// Upgrade database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		Log.w(DatabaseHandler.class.getName(), "Upgrading database from version " 
				+ oldVersion + " to " + newVersion + ", which will destroy all old data");
		// Drop older tables if already exists
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATES);
			
		// Create new table
		onCreate(db);
	}
		
	// Add new candidate
	void addCandidate(Candidate candidate) {
			
		SQLiteDatabase db = this.getWritableDatabase();
			
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, candidate.getName()); // Get candidate name
		values.put(KEY_OFFICE, candidate.getOffice()); // Get office
		values.put(KEY_PHOTOGRAPH, candidate.getPhotograph()); // Get photograph
		values.put(KEY_PROMISES, candidate.getPromises()); // Get promises
		values.put(KEY_STATEMENT, candidate.getStatement()); // Get statement
			
		// Insert row
		db.insert(TABLE_CANDIDATES, null, values);
		db.close(); // Close database connection
	}
		
		
	// Get single candidate
	public Candidate getCandidate(int id) {
			
		SQLiteDatabase db = this.getReadableDatabase();
			
		Cursor cursor = db.query(TABLE_CANDIDATES, new String[] {KEY_ID, KEY_NAME, KEY_OFFICE, 
				KEY_PHOTOGRAPH, KEY_PROMISES, KEY_STATEMENT}, KEY_ID + "=?", 
				new String[] {String.valueOf(id)}, null, null, null, null);
			
		if (cursor != null) {
			cursor.moveToFirst();
		}
		
		Candidate candidate = new Candidate(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
			
		return candidate;
	}
		
	// Get all candidates stored in database 
	public List<Candidate> getAllCandidates() {
			
		List<Candidate> candidateList = new ArrayList<Candidate>();
			
		// Select All query
		String selectQuery = "SELECT * FROM " + TABLE_CANDIDATES;
			
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
			
		// Loop through all rows and add to list
		if (cursor.moveToFirst()) {
			do {
				Candidate candidate = new Candidate();
				candidate.setID(Integer.parseInt(cursor.getString(0)));
				candidate.setName(cursor.getString(1));
				candidate.setOffice(cursor.getString(2));
				candidate.setPhotograph(cursor.getString(3));
				candidate.setPromises(cursor.getString(4));
				candidate.setStatement(cursor.getString(5));
					
				// Add candidate to list
				candidateList.add(candidate);
			} while (cursor.moveToNext());
		}
		
		return candidateList;	
	}
		
	// Update single candidate
	public int updateCandidate(Candidate candidate) {
			
		SQLiteDatabase db = this.getWritableDatabase();
			
		ContentValues values = new ContentValues();
			
		values.put(KEY_NAME, candidate.getName());
		values.put(KEY_OFFICE, candidate.getOffice());
		values.put(KEY_PHOTOGRAPH, candidate.getPhotograph());
		values.put(KEY_PROMISES, candidate.getPromises());
		values.put(KEY_STATEMENT, candidate.getStatement());
			
		// Update each row
		return db.update(TABLE_CANDIDATES, values, KEY_ID + " = ?", 
				new String[] {String.valueOf(candidate.getID())});
			
	}
		
	// Delete single candidate
	public void deleteCandidate(int id) {
			
		SQLiteDatabase db = this.getWritableDatabase();
			
		db.delete(TABLE_CANDIDATES, "_id = " + id, null);
		
		db.close();
	}
		
	// Get candidate count
	public int getCandidateCount() {
		
		String countQuery = "SELECT * FROM " + TABLE_CANDIDATES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		
		return cursor.getCount();
	}
}
