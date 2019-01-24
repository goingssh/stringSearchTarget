package method;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * @author Sherri Goings
 */
public class FunctionTest {
	/**
	 * Runs test specified in testdir on getCount method in SimpleSearch class.
	 * testdir contains at least 3 files:
	 *   search_terms.txt with a list of strings one per line
	 *   testfile.txt with the text to be searched
	 *   result_*.txt with a list of correct counts one per line for the search terms.
	 * there may be multiple result files depending on if including substrings as matches.
	 * note that there must be the same number of lines in the search term and result files.
	 * 
	 * Test compares count from getCount method to expected result for each search term, 
	 * outputs individual count values for any mismatch, and total incorrect counts at end.
	 *
	 * @param  dirname  path to test directory with appropriate files
	 * @param  substr   if true include substring matches as described in getCount
	 * @return          -1 if IO error, 0 otherwise.
	 * @see             SimpleSearch.java
	 */
	public static int SimpleSearchTest(String dirname) {
		String countFile = dirname+"/result_substr.txt";
		try{
      	    Scanner scanTerms = new Scanner(new File(dirname+"/search_terms.txt"));
			Scanner scanCounts = new Scanner(new File(countFile));
			int failCount=0;
			while (scanTerms.hasNext()) {
                String s = scanTerms.next();
				int count = SimpleSearch.getCount(dirname+"/testfile.txt", s);
				int testcount = scanCounts.nextInt();
				if (count != testcount) {
					System.out.print("test fail on term -" + s + "- ");
					System.out.printf("found %d, expected %d\n", count, testcount);
					failCount++;
				}
			}
			System.out.printf("%d count errors\n", failCount);
        }catch (IOException e){
			System.err.println("Error: " + e.getMessage());
			return -1;
		}
		return 0;
	}

	/**
	 * Runs quick function checking tests.
	 * @param  args  requires 1 path to test directory on command line, more are optional
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("usage: java Test testdir");
           return;
        }
		for (int i=0; i<args.length; i++) {
			String dirname = args[i];
			System.out.println("\ntesting dir " + dirname);
			int err = SimpleSearchTest(dirname);
			if (err < 0) break;
		}
	}
}
