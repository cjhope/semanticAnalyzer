import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Document {

	String user;
	Calendar dateCreated;
	String text;
	HashMap<String, WordInformation> wordList;
	String tweetID;
	int sentiment;
	int totalWordCount;
	String company;
	
	/**
	 * SentimentGivenWordsInDocument = 1/3 * probWord1InDictionary * probWord2InDictionary * ... * probWordnInDictionary
	 * @return - Sentiment value: +1 for positive, 0 for neutral, -1 for negative
	 */
	public int calculateSentiment(){
	
		double probSent = (1.0/3.0);  //Positive, negative, or neutral sentiments possible
		double posProb = probSent, negProb = probSent;  //Probabilities to compare
		Boolean posFlag = false, negFlag = false;
		
		/*Create Dictionaries*/
		Dictionary posDictionary = new Dictionary( "PositiveDictionary.txt");
		Dictionary negDictionary = new Dictionary( "NegativeDictionary.txt");
		
		/*For every word in the tweet (wordList), get it's probability of appearing in each of the two 
		 * dictionaries. Multiply all probabilities for each dictionary. If a word appears more than once
		 * in a tweet, make sure to include it's probability that many times.*/
		for( Map.Entry<String, WordInformation> entry : wordList.entrySet()){
			int count = entry.getValue().getCount();
			while( count >= 1){
				if( posDictionary.getProbability( entry.getKey()) != 0.0){  /////////////////DON'T USE A PROB=0???????????
					posProb *= posDictionary.getProbability( entry.getKey()); 
					posFlag = true;
				}
				if( negDictionary.getProbability( entry.getKey()) != 0.0){
					negProb *= negDictionary.getProbability( entry.getKey());
					negFlag = true;
				}						
				count--;
			}	
		}
		/*Since we initialized them to 1/3, if NO words were found in that dictionary, 
		 * we want the probability to be 0, not 1/3.*/
		if( posFlag == false){
			posProb = 0.0;
		}
		if( negFlag == false){
			negProb = 0.0;
		}
		/*Choose the higher probability*/
		if( posProb > negProb){
			return 1;
		}
		else if( negProb > posProb){
			return -1;
		}
		else{
			return 0;
		}
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
