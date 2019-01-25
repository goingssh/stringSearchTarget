package time;

import java.util.*;
import java.io.*;
import java.lang.*;
import method.*;

/**
 * @author Sherri Goings
 */
public class TimeTest {

	ArrayList<String> realWords;
	
	public TimeTest() {
		realWords = new ArrayList<String>();
	}
	
	public void loadWords(String filename) {
		try{
      	    Scanner scan = new Scanner(new File(filename));
			while (scan.hasNext()) {
				realWords.add(scan.next());
			}
			scan.close();
		} catch (IOException e){
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void timeTest(Search searchMethod) {
		Collections.shuffle(realWords);
        long t1 = System.currentTimeMillis();
		for (int i=0; i<2000; i++) {
			searchMethod.getCount(realWords.get(i));
		}
        long t2 = System.currentTimeMillis();
		for (int j=0; j<2000; j++) {
			searchMethod.getCounts(realWords.get(j));
		}
		long t3 = System.currentTimeMillis();
		
		System.out.println(String.format("%-10.3f %-10.3f", (double)(t2-t1)/1000, (double)(t3-t2)/1000));
	}
	
	/**
	 * @param  args  not used
	 */
	public static void main(String[] args) {
		
		TimeTest tt = new TimeTest();
		String basedir = "../resources/efficiency_tests";
		tt.loadWords(basedir+"/words230000.txt");

		Search test = null;
		
		String[] testdirs = {"FewShortFile", "singleLongFile", "All"};
		for (String dir : testdirs) {
			System.out.println("running tests on dir: " + dir);
			
			System.out.println("\ntesting index search");
			test = new IndexSearch(basedir+"/"+dir);
			tt.timeTest(test);
			
			System.out.println("\ntesting simple search");
			test = new SimpleSearch(basedir+"/"+dir);
			tt.timeTest(test);

			System.out.println("\ntesting regex search");
			test = new RegExpSearch(basedir+"/"+dir);
			tt.timeTest(test);
		}
			





	}
}
