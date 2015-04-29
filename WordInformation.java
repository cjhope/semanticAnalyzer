
public class WordInformation {

	int count;
	String POS;
	double probability;
	
	public WordInformation(String pos){
		this.count = 1;
		this.probability = 0;
		this.POS = pos;
	}
	
	public int getCount(){
		return count;
	}
	
}
