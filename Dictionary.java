import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Dictionary should hold words of a similar sentiment. Create a dictionary from a .txt file 
 * of words that express that sentiment. Use the probability that the word appears in that 
 * Dictionary, to determine the likelihood of that sentiment, given the use of that word.
 * 
 * @author Laura Stalker
 */
public class Dictionary extends HashMap< String, Integer>{
	
	
	private static final long serialVersionUID = 1L;
	private int totalWords = 0;  //Total number of non-distinct words
	
	
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
		
		/*Read through the text file, parse out individual words, and save them in the list
		*/
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
		 * Dictionary with a frequency of 1.
		 * */
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
	}
	
	/**
	 * Calculates the probability that a certain word appears in the dictionary.
	 * =[Total times that word appears DIVIDED BY total number of non-distinct words in the dictionary]
	 * @param word - String to check the dictionary for
	 * @return probability of a word occurring
	 */
	public double getProbability( String word){
		
		double prob = 0.0;
		
		if( this.containsKey( word)){
			prob = ( (double) ((int)this.get( word)) / this.totalWords);
		}
		return prob;
	}
	

	public static void main( String a[]){
		
		Dictionary negDict = new Dictionary();  //"more negative reviews tagged"
		negDict.addAllToDictionary( "/home/laura/Dropbox/Data Mgmt Sys/SentimentAnalysisProject/Words/NegativeDictionary.txt");
		
		Dictionary posDict = new Dictionary();  //"more positive reviews tagged" //80 words total (counting duplicates as separate)
		posDict.addAllToDictionary( "/home/laura/Dropbox/Data Mgmt Sys/SentimentAnalysisProject/Words/PositiveDictionary.txt");
		
		
		/* TESTING */
		System.out.println( "num diff POS words = " + posDict.getNumDiffWords());
		System.out.println( "num diff NEG words = " + negDict.getNumDiffWords());
		
		System.out.println( "prob new POS = " + posDict.getProbability( "new"));
		System.out.println( "prob new NEG = " + negDict.getProbability( "new"));
		
		System.out.println( "num POS new = " + posDict.get("new"));
		
		System.out.println( "prob steal POS = " + posDict.getProbability( "steal"));
		System.out.println( "prob steal NEG = " + negDict.getProbability( "steal"));
		
		System.out.println( "num NEG steal = " + negDict.get("steal"));
		
		System.out.println( "prob experience POS = " + posDict.getProbability( "experience"));
		System.out.println( "prob experience NEG = " + negDict.getProbability( "experience"));
		
		System.out.println( "num POS experience = " + posDict.get("experience"));
		
		System.out.println( "prob news POS = " + posDict.getProbability( "news"));
		System.out.println( "prob news NEG = " + negDict.getProbability( "news"));
		
		System.out.println( "num POS news = " + posDict.get("news"));
		System.out.println( "num NEG news = " + negDict.get("news"));
		
		System.out.println( "prob collaborate POS = " + posDict.getProbability( "collaborate"));
		System.out.println( "prob collaborate NEG = " + negDict.getProbability( "collaborate"));
		System.out.println( "prob alleged POS = " + posDict.getProbability( "alleged"));
		System.out.println( "prob alleged NEG = " + negDict.getProbability( "alleged"));
		
		System.out.println( "prob earning POS = " + posDict.getProbability( "earning"));
		System.out.println( "prob earning NEG = " + negDict.getProbability( "earning"));
		System.out.println( "num POS earning = " + posDict.get("earning"));
		
		
		
		
//		String[] negWords = {"recall", "die", "angry", "spit", "idiotic", "fuck", "puppet", "vulture", "dead", "steal", "contradict",
//		"debt", "malware", "lie", "fraud", "dispute", "xenophobia", "layoff", "hurt", "leave", "alleged", "closure", "Nazi", "racist",
//		"low", "fuck", "bad", "warn", "illegal", "settlement", "bad", "sue", "alleged", "death", "weapon", "war", "hate", "sue", "sexual",
//		"discrimination", "delay", "lawyer", "suddenly", "closed", "without", "news", "never", "stop", "claim", "crunch", "testimony"};
		
//		String[] posWords = {"love", "too", "experience", "liberate", "easy", "fast", "predictable", "fast", "experience", "learn", "teach",
//		"deliver", "experience", "excellent", "experience", "collaborate", "capable", "develop", "assist", "developer", "accessibility",
//		"accessible", "hire", "apply", "good", "right", "startup", "lovely", "brighten", "shine", "new", "create", "Internet", "reduce",
//		"help", "productivity", "momentum", "go", "news", "optimistic", "future", "extend", "research", "funding", "research", "collaboration",
//		"channel", "love", "wonderful", "expression", "new", "good", "energy", "right", "play", "idea", "now", "report", "earning", "gain",
//		"revenue", "report", "earning", "rise", "delicious", "big", "ahead", "scientist", "save", "life", "give", "new", "apply", "work",
//		"mobile", "technology", "expect", "performance", "build", "momentum"};  //80 words total (counting duplicates as separate)
		
		
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
}
