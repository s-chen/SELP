package si.chen.votingapp;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {
	
	private UserSessionManager session;
	
	static final String URL = "http://www.inf.ed.ac.uk/teaching/courses/selp/elections/election.xml";
	
	// XML nodes
	public static final String KEY_CANDIDATE = "candidate"; // parent node
	public static final String KEY_NAME = "name";
	public static final String KEY_OFFICE = "office";
	public static final String KEY_PHOTOGRAPH = "photograph";
	public static final String KEY_PROMISES = "promises";
	public static final String KEY_STATEMENT = "statement";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        Button display_candidates_button = (Button) findViewById(R.id.button_display_candidates);
        
        session = new UserSessionManager(getApplicationContext());
        
        /* Only allow user to view candidates once they have registered their student number
         * by initially disabling the 'Candidates' button when app is first started */
        if (!session.existStudentNumber()) {
        	display_candidates_button.setEnabled(false);
        }
        
        
        File dbExists = new File("data/data/si.chen.votingapp/databases/candidateDatabase");
        
        // Checks whether database already exists, if not then create the database
        if (!dbExists.exists()) {
        	Log.i("key_info_db", "Creating database of candidates...");
       
        	DatabaseHandler db = new DatabaseHandler(this);
            
            XMLParser parser = new XMLParser();
        	String xml = parser.getXMLFromURL(URL); // getting XML
        	Document document = parser.getDOMElement(xml); // getting DOM element
            	
        	Element element_doc = document.getDocumentElement();
        	NodeList nodeList = element_doc.getElementsByTagName(KEY_CANDIDATE);
    
        	
        	for (int i = 0; i < nodeList.getLength(); i++) {
        		  			
       			Element element_info = (Element) nodeList.item(i);
       			
        		db.addCandidate(new Candidate(parser.getValue(element_info, KEY_NAME), parser.getValue(element_info, KEY_OFFICE), parser.getValue(element_info, KEY_PHOTOGRAPH), parser.getCandidatePromises(element_info, KEY_PROMISES), parser.getValue(element_info, KEY_STATEMENT) ));	
        	}
        }
        
    }

    
    /* This is called when 'Register' button is clicked*/
    public void register_student_number(View view) {
    	
    	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);
    	finish();
    }
    
    /* This is called when 'Candidates' button is clicked */
    public void displayCandidates(View view) {
    	
    	Intent intent = new Intent(this, DisplayActivity.class);
    	startActivity(intent);
    	finish();
    }
    
    /* This is called when 'About' button is clicked */
    public void aboutApp(View view) {
    	
    	Intent intent = new Intent(this, AboutActivity.class);
    	startActivity(intent);
    }
     
}
