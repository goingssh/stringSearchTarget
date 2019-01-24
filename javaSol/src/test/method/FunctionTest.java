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
	 * @param  dirname   path to test directory with appropriate files
	 * @param  test      Search class instance for search method testing.
	 * @param  countFile file with expected counts for this search method.
	 * @return           -1 if IO error, 0 otherwise.
	 * @see              SimpleSearch.java
	 */
	public static int SearchTest(String dirname, Search test, String countFile) {
		try{
      	    Scanner scanTerms = new Scanner(new File(dirname+"/search_terms.txt"));
			Scanner scanCounts = new Scanner(new File(countFile));
			int failCount=0;
			while (scanTerms.hasNext()) {
                String s = scanTerms.nextLine();
				int count = -1;
				count = test.getCount(s);
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
	 * @param  args  not used
	 */
	public static void main(String[] args) {

		String basedir = "../resources/function_tests/";
		String[] dirs = {"test1", "test2", "test3"};

		int err = 0;
		String dirname = "";
		Search test = null;
		for (int i=0; i<2; i++) {
			dirname = basedir + dirs[i];
			
			System.out.printf("\ntesting %s search on dir %s\n", "simple", dirname);
			test = new SimpleSearch(dirname+"/textfile");
			if ( SearchTest(dirname, test, dirname+"/result_substr.txt") < 0 ) break;
			
			System.out.printf("\ntesting %s search on dir %s\n", "regex", dirname);
			test = new RegExpSearch(dirname+"/textfile");
			if ( SearchTest(dirname, test, dirname+"/result_substr.txt") < 0 ) break;

			System.out.printf("\ntesting %s search on dir %s\n", "index", dirname);
			test = new IndexSearch(dirname+"/textfile");
			if ( SearchTest(dirname, test, dirname+"/result_exact.txt") < 0 ) break;

		}
		dirname = basedir + dirs[2];
		System.out.printf("\ntesting %s search on dir %s\n", "regex", dirname);
		test = new RegExpSearch(dirname+"/textfile");
		SearchTest(dirname, test, dirname+"/result_substr.txt");
	}
}
