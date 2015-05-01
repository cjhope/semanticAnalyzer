import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Dictionary should hold words of a similar sentiment. The HashMap maps the word (String)
 * to it's frequency (int). Create a dictionary from a .txt file of words that express that sentiment. 
 * Use the probability that the word appears in that Dictionary, to determine the likelihood of that 
 * sentiment, given the use of that word.
 * 
 * @author Laura Stalker
 */
public class Dictionary extends HashMap< String, Integer>{
	
	
	private static final long serialVersionUID = 1L;
	private int totalWords;  //Total number of non-distinct words
	

	public Dictionary( String textFileName){
		totalWords = 0;
		this.addAllToDictionary( textFileName);
	}
	
	
	/**
	 * @return - the number of distinct words in the dictionary
	 */
	public int getNumDiffWords(){
		
		return this.size();
	}
	
	
	/**
	 * Fills a dictionary with all the words from a text file.
	 * If a repeat is found, then it's frequency (mapped value) is incremented by 1.
	 * Assumes space between words.. I'm separating with '\n'.
	 * 
	 * @param fileName - path to text file of words to add to dictionary
	 */
	public void addAllToDictionary( String fileName){
		
		int oneWord = 1;  //Frequency, for adding 1 word at a time
		
		String allWords;  //String of entire text file of dictionary words
		List<String> words = new ArrayList<String>();  //Parsed dictionary word String
		BufferedReader br = null;
		
		/*Read through the text file, parse out individual words, and save them in the list */
		try{
			br = new BufferedReader( new FileReader( fileName));
			while( (allWords = br.readLine()) != null){
				String[] token = allWords.split("\\s+");
				words.add( token[0]);
			}
		} catch ( Exception e) {
			e.printStackTrace();
		} finally{
			if( br != null){
				try {
					br.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		
		/*For each word in the list, check if it is in the Dictionary already.
		 * If so, increment its frequency (mapped value). If not, add it to the
		 * Dictionary with a frequency of 1. */
		for( int i = 0; i < words.size(); i++){
			if( this.containsKey( words.get(i))){
				int hasNum = (int)this.get( words.get(i));
				this.put( words.get(i), ++hasNum);
				this.totalWords++;
			}
			else{
				this.put( words.get(i), oneWord);
				this.totalWords+=oneWord;
			}
		}
	}// End addAllToDictionary method
	
	
	/**
	 * Calculates the probability that a certain word appears in the dictionary.
	 * =[Total times that word appears DIVIDED BY total number of non-distinct words in the dictionary]
	 * 
	 * @param word - String to check the dictionary for
	 * @return probability of a word occurring
	 */
	public double getProbability( String word){
		
		double prob = 0.0;  //Double.MIN_VALUE??
		
		word = word.toLowerCase();
		
		word = word.toLowerCase();
		
		if( this.containsKey( word)){
			prob = ( (double) ((int)this.get( word)) / this.totalWords);
		}
		return prob;
	}
	
//<<<<<<< HEAD

//	/**
//	 * Sentiment = 1/3 * probWord1InDictionary * probWord2InDictionary * ... * probWordnInDictionary
//	 * 
//	 * @return - Sentiment value: +1 for positive, 0 for neutral, -1 for negative
//	 */
//	public int calculateSentiment(){
//		
//		HashMap<String, Integer> wordList = new HashMap<String, Integer>();
//		String[] posTweet = { "I", "love", "my", "beautiful", "new", "phone"};
//		String[] negTweet = { "fuck", "this", "thing", "Never", "buy", "one"};
//		
//		double probSent = (1.0/3.0);  //Posititve, negative, or neutral sentiments possible
//		double posProb, negProb;  //Probabilities to compare
//		
//		for( int i=0; i < posTweet.length; i++){  //Populate wordList
//			wordList.put( posTweet[i], 1);
//		}
//		negProb = 0.0; posProb = probSent;
//		
////		for( int i=0; i < negTweet.length; i++){
////			wordList.put( negTweet[i], 1);
////		}
////		negProb = probSent; posProb = 0.0;
//		
//		/*For every word in the tweet (wordList), get it's probability of appearing in each of the two 
//		 * dictionaries. Multiply all probabilities for each dictionary.*/
//		for( String key : wordList.keySet()){
//			System.out.println( key);
//			if( this.getProbability( key) != 0.0){
//				posProb = posProb * (this.getProbability( key));
////				System.out.println( this.getProbability( key));
////				System.out.printf( "PosProb = %.8f\n",posProb);
//			}
//		}
//		/*Choose the higher probability*/
//		if( posProb > negProb){
//			return 1;
//		}
//		else if( negProb > posProb){
//			return -1;
//		}
//		else{
//			return 0;
//		}
//	}
	
	
	public static void main( String a[]){
		
		
		Dictionary negDict = new Dictionary( "src/NegativeDictionary.txt");  //"more negative reviews tagged"//		
		Dictionary posDict = new Dictionary( "src/PositiveDictionary.txt");  //"more positive reviews tagged" //80 words total (counting duplicates as separate)
		
//		System.out.println( "Sentiment = " + posDict.calculateSentiment());
//		System.out.println( "Sentiment = " + negDict.calculateSentiment());
		
	
		
//		/**
//		 * Adds an array of words to the dictionary.
//		 * @param words - Array of Strings to add to the dictionary
//		 */
//		public void addAllToDictionary( String[] words){
//			
//			int oneWord = 1;
//			
//			for( int i=0; i<words.length; i++){
//				if( this.containsKey( words[i])){
//					int hasNum = (int)this.get( words[i]);
//					this.put( words[i], ++hasNum);
//					this.totalWords++;
//				}
//				else{
//					this.put( words[i], oneWord);
//					this.totalWords+=oneWord;
//				}
//			}
//		}

		
//		/**
//		 * Adds a certain number of a given word to the dictionary.
//		 * @param word - String to add to dictionary
//		 * @param freq - int number of occurrences of this word
//		 */
//		public void addToDictionary( String word, int freq){
//			
//			if( this.containsKey( word)){
//				int hasNum = (int)this.get( word);
//				this.put( word, hasNum+=freq);
//				this.totalWords+=freq;
//			}
//			else{
//				this.put( word, freq);
//				this.totalWords+=freq;
//			}
//		}
	}
//=======
//>>>>>>> da692e3142d0a1f6820a3517bea56e8fc7c6a554
}
