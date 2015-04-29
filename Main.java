import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

	static DataMap fullDataSet = new DataMap();
	static String[] companyList = { "MMM", "AXP", "AAPL", "BA", "CAT",
									"CVX", "CSCO", "KO", "DD", "XOM",
									"GE", "GS", "HD", "INTC", "IBM",
									"JNJ", "JPM", "MCD", "MRK", "MSFT",
									"NKE", "PFE", "PG", "TRV", "UNH",
									"UTX", "VZ", "V", "WMT", "DIS" };
	
	
	
	/**
	 * Builds a new document that can then be placed into the fullDataSet
	 * 
	 * @param user - author of the tweet as parsed from the data file
	 * @param date - date the tweet was authored, as parsed from the data file
	 * @param company - company the tweet concerns, as parsed from the data file
	 * @param text - the actual text of the tweet, as parsed from the data file
	 * @param tweetID - the unique tweetID, as parsed from the data file
	 * @return - the Document created by the method call
	 */
	public Document createDocument(String user, Calendar date, String company, String text, String tweetID){
		return new Document(user, date, company, text, tweetID);
	}
	
	
	/**
	 * Checks if there is already a document list associated with the company and date MapPair
	 *		- if there is a current list, the method simply appends the document to the list
	 *		- if there isn't a current list, the method creates the appropriate map pair
	 *			and a new document list, adds the document to the new document list, and puts
	 *			the MapPair, LinkedList key-value set into the fullDataSet HashMap
	 *
	 * @param d - the document that is being added to the fullDataSet
	 * @throws ParseException 
	 * 
	 */
	static public void addToFullDataSet(Document doc) throws ParseException{
		MapPair test = new MapPair(doc.dateCreated, doc.company);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar testDate;
		testDate = new GregorianCalendar(2015, Calendar.MARCH, 25);
		MapPair test2 = new MapPair(testDate, doc.company);
		//System.out.println(test.equals(test2));
		if(fullDataSet.containsKey(test)){
			//System.out.println("contained key");
			LinkedList<Document> newList = fullDataSet.get(test);
			newList.add(doc);

		} else{
			//System.out.println("did NOT contain key");
			LinkedList<Document> newList = new LinkedList<Document>();
			newList.add(doc);
			fullDataSet.put(test, newList);
		}
	}
	
	static public int monthTextToNumber(String s){
		
		switch(s){
		case "Mar": return 2;
		case "Apr": return 3;
		}
		
		return 0;
		
	}
	
	
	public static void main(String [] args) throws ParseException, InterruptedException{

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final Calendar theDate = new GregorianCalendar(2015, Calendar.MARCH, 25);
		final Lock locker = new ReentrantLock();
		locker.lock();
		int positive = 0;
		int negative = 0;
		
		File inputFileFolder = new File("data");
		File[] fileList = inputFileFolder.listFiles();
		for(int i = 0; i < fileList.length; i++){
			System.out.println(fileList[i]);
		}
		ExecutorService formatDataThreadPool = new ThreadPoolExecutor(10, 20, 10*60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(40));
		

			
			for( final File singleFile : fileList){
				formatDataThreadPool.execute(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Lock lock = new ReentrantLock();
						String result = null;
						String textField;
						String textParse[];
						String company;
						String companyParse[] = null;
						String tweetId;
						String tweetIdParse[];
						String user;
						String userParse[];
						String date;
						String dateParse[];
						String advDateParse[];
						String unit[];
						//Date theDate = new Date();
						try{
								FileReader dataIn = new FileReader(singleFile);
								BufferedReader bReader = new BufferedReader(dataIn);
								String theWholeStringManLikeWhoa;
								while( (theWholeStringManLikeWhoa = bReader.readLine()) != null){
									unit = theWholeStringManLikeWhoa.split("::::");
									//now iterate over all units - each unit has 5 elements, so increment by 5
									for(int i = 0; i < 225; i = i+5){
										//grab and parse each element
										System.out.println("running " + (i/5 + 1));
										company = unit[i+0];
										companyParse = company.split(":", 2);
										tweetId = unit[i+1];
										tweetIdParse = tweetId.split(":", 2);
										date = unit[i+2];
										dateParse = date.split(":", 2);
										user = unit[i+3];
										userParse = user.split(":", 2);
										textField = unit[i+4];
										textParse = textField.split(":", 2);
										
										advDateParse = dateParse[1].split(" ");
										
										//System.out.println(advDateParse[2]);
										
					
					
										
										//System.out.println("date is " + theDate);
										
										
										//user, date, company, text, tweetID
										
										
										Document d = new Document(userParse[1], theDate, companyParse[1].replaceAll("\\s+", ""), textParse[1], tweetIdParse[1]);
										//d.buildWordList();
										
										synchronized(locker){
											addToFullDataSet(d);
										}
										
									}
								}
						}catch(Exception e){
							e.printStackTrace();
						}
							
						
					}
				});
				
						
			}


			
			formatDataThreadPool.shutdown();
			formatDataThreadPool.awaitTermination(2, TimeUnit.MINUTES);
		
		// a testing section
		Calendar finalTestDate = new GregorianCalendar(2015, Calendar.MARCH, 25);

		//System.out.println("date checked is " + mp.dateCreated + "  company is " + mp.company);
		/**
		for(String com : companyList){
			MapPair mp = new MapPair(finalTestDate, com);
			if(fullDataSet.containsKey(mp)){
				LinkedList<Document> l = fullDataSet.get(mp);
				Iterator<Document> it = l.iterator();
				while(it.hasNext()){
					Document d = it.next();
					System.out.println(d.company);
					System.out.println(d.tweetID);
					System.out.println(d.dateCreated);
					System.out.println(d.user);
					System.out.println(d.text);
					System.out.println("-----------------");
				}
			}else {
				System.out.println("key not contained");
			}
		
		}
		**/
		Iterator it = fullDataSet.iterator();
		while(it.hasNext()){
			
			MapPair tempMapPair = (MapPair) it.next();
			LinkedList iterTest = fullDataSet.get(tempMapPair);
			if(iterTest != null){
				//System.out.println("Iterator returned something");
				Iterator listIter = iterTest.iterator();
				while(listIter.hasNext()){
					Document temp = (Document) listIter.next();
					//System.out.println(temp.text + "\n" + temp.sentiment);
					tempMapPair.totalSentiment += temp.sentiment;
				}
				System.out.println("For company " + tempMapPair.company + "\tOn day " + tempMapPair.dateCreated);
				System.out.println("Sentiment is " + tempMapPair.totalSentiment);
				System.out.println("---------------------------------------");
			}
		}
		
	}
	
}
