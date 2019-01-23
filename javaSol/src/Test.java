import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * @author Sherri Goings
 */
public class Test {
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
	public static int SimpleSearchTest(String dirname, boolean substr) {
		String countFile = dirname+"/result_sep_ws.txt";
		if (substr) {
			countFile=dirname+"/result_all_substr.txt";
		}
		try{
      	    Scanner scanTerms = new Scanner(new File(dirname+"/search_terms.txt"));
			Scanner scanCounts = new Scanner(new File(countFile));
			int failCount=0;
			while (scanTerms.hasNext()) {
                String s = scanTerms.next();
				int count = SimpleSearch.getCount(dirname+"/testfile.txt", s, substr);
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
	 * Runs test twice, once with exact matches and once including substrings.
	 * Assumes given subdirectory will contain result files for both methods.
	 * @param  args  requires 1 command line argument - path to test directory
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("usage: java Test testdir");
           return;
        }
        String dirname = args[0];
		System.out.println("\ntesting substr matches");
		int err = SimpleSearchTest(dirname, true);
		if (err >= 0) {
			System.out.println("\ntesting exact matches");
			err = SimpleSearchTest(dirname, false);
		}
	}
}
