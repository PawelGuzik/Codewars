package mostFreqWords;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopWords {

	public static void main(String[] args) {
		/*top3("In a village of La Mancha, the name of which I have no desire to call to"+
                        "mind, there lived not long since one of those gentlemen that keep a lance"+
                        "in the lance-rack, an old buckler, a lean hack, and a greyhound for"+
                        "coursing. An olla of rather more beef than mutton, a salad on most"+
                        "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra"+
                        "on Sundays, made away with three-quarters of his income.");
		
		top3(" //wont won't won't ");
		top3("a a a  b  c c  d d d d  e e e e e");
		TopWords.top3("e e e e DDD ddd DdD: ddd ddd aa aA Aa, bb cc cC e e e");*/
		
		top3("  '  ");

	}
	
	 public static List<String> top3(String s) {
		 //Removes ' with non alphabetical neighbors
		 s =  s.replaceAll("[^a-zA-Z]{1,}'[^a-zA-Z]{1,}", "");
	     
		 //Splits text in places where is something else than alphabet character or '
		List<String> list = Arrays.asList(s.split("[^a-zA-Z']"));
		
		//Makes stream from list of words
		List<String> result = list.stream()
				//removes from stream zero length words and single '
			.filter(x-> x.length()>0 && !x.equalsIgnoreCase("'"))
				//Collects stream to map where key is word in lower case and value is number of occurrences
			.collect(Collectors.toMap(v -> v.toLowerCase(), w -> 1, Integer::sum))
				//Changes map to stream
			.entrySet().stream()
				//Sorts map by value in reversed order
			.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				//Collects only three most frequently words as List
			.limit(3L).map(e -> e.getKey()).collect(Collectors.toList());
			
	
			System.out.println(result);
		
	        return result;
	    }

}
