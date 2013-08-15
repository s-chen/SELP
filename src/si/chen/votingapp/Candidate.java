package si.chen.votingapp;

/* This class provides getter and setter methods for storing and retrieving candidate information */

public class Candidate {

	private int id;
	private String name;
	private String office;
	private String photograph;
	private String promises;
	private String statement;
	
	// Empty constructor
	public Candidate() {
		
	}
	
		
	public Candidate(int id, String name, String office, String photograph, String promises, String statement) {
		
		this.id = id;
		this.name = name;
		this.office = office;
		this.photograph = photograph;
		this.promises = promises;
		this.statement = statement;
		
	}
	
	public Candidate(String name, String office, String photograph, String promises, String statement) {
		
		this.name = name;
		this.office = office;
		this.photograph = photograph;
		this.promises = promises;
		this.statement = statement;
		
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOffice() {
		return this.office;
	}
	
	public void setOffice(String office) {
		this.office = office;
	}
	
	public String getPhotograph() {
		return this.photograph;
	}
	
	
	public void setPhotograph(String photograph) {
		this.photograph = photograph;
	}
	
	public String getPromises() {
		return this.promises;
	}
	
	public void setPromises(String promises) {
		this.promises = promises;
	}
	
	public String getStatement() {
		return this.statement;
	}
	
	public void setStatement(String statement) {
		this.statement = statement;
	}
	
}
