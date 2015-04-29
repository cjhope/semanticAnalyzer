import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;


public class DataMap extends HashMap<MapPair, LinkedList<Document>>{

	static String[] companyList = { "MMM", "AXP", "AAPL", "BA", "CAT",
		"CVX", "CSCO", "KO", "DD", "XOM",
		"GE", "GS", "HD", "INTC", "IBM",
		"JNJ", "JPM", "MCD", "MRK", "MSFT",
		"NKE", "PFE", "PG", "TRV", "UNH",
		"UTX", "VZ", "V", "WMT", "DIS" };
	
	

	
	
	public Iterator iterator() throws ParseException{
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar startDate = new GregorianCalendar(2015, Calendar.MARCH, 24);
		Calendar endDate = new GregorianCalendar();
		return new Iterator(){
			
			int i = 0;


			@Override
			public boolean hasNext() {
				//Check if the date is valid
				if( startDate.before(endDate) ){
					//date range is correct
					//now check if the company is is at end
					i++;
					if( i < companyList.length ){
						
						return true;
					}
					else {
						i = 0;
						startDate.add(Calendar.DATE, 1);
						if( startDate.before(endDate) )
							return true;
						else
							return false;
					}
					
				}
				return false;
			}

			@Override
			public LinkedList next() {
				// TODO Auto-generated method stub
				MapPair mp = new MapPair(startDate, companyList[i]);
				if(Main.fullDataSet.containsKey(mp)){
					LinkedList l = Main.fullDataSet.get(mp);
					return l;
				}else
					return null;
				
				
			}
			
		};
		
	}
	
}
