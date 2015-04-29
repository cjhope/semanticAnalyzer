import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class Document {

	String user;
	Calendar dateCreated;
	String text;
	HashMap<String, WordInformation> wordList;
	String tweetID;
	int sentiment;
	int totalWordCount;
	String company;
	
	static public void calculateSentiment(){
		//Stub method -- implement TODO here
	}
	
	public Document(String theUser, Calendar theDate, String theCompany, String theText, String theTweetID){
		this.user = theUser;
		this.dateCreated = theDate;
		this.text = theText;
		this.tweetID = theTweetID;
		this.sentiment = 0;
		this.buildWordList();
		this.totalWordCount = 0;
		this.company = theCompany;
		this.calculateSentiment();
	}
	
	public void buildWordList(){
		this.wordList = new HashMap<String, WordInformation>();
		//stub method -- implement TODO here
		try{
			Process p = new ProcessBuilder("/bin/bash", "src/redirectToJava.sh", this.text).start();
			p.waitFor();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while( (line = in.readLine()) != null ){
				//this.totalWordCount++;
				//System.out.println(line);
				String parseLine[] = line.split("\\s+");
				WordInformation wordInfo;
				if(wordList.containsKey(parseLine[2])){
					//if the key is already in the word set, increment the count
					wordInfo = wordList.get(parseLine[2]);
					wordInfo.count++;
					wordList.put(parseLine[2], wordInfo);
					//System.out.println("IF worked for " + parseLine[2]);
				}//if the current key isn't in the create a new word info object and put it in
				else{
					wordInfo = new WordInformation(parseLine[1]);
					wordList.put(parseLine[2], wordInfo);
					//System.out.println("ELSE worked for " + parseLine[2]);
				}
				
			}
		}catch (Exception e){
			System.err.println("failed again");
		}
		return;
	}
	
	public String toString(){
		return this.company + "\n" + this.user + "\n" + this.tweetID + "\n" + this.dateCreated + "\n" + this.text;
	}
	
	public void setProbability(){
		//stub method -- implement TODO here
	}
	
}
