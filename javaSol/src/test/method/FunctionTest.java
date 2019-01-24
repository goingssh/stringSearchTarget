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
	public static int SearchTest(String dirname, String searchType) {
		String countFile = dirname+"/result_substr.txt";
		try{
      	    Scanner scanTerms = new Scanner(new File(dirname+"/search_terms.txt"));
			Scanner scanCounts = new Scanner(new File(countFile));
			int failCount=0;
			while (scanTerms.hasNext()) {
                String s = scanTerms.next();
				int count = -1;
				if (searchType.equals("simple")) {
					count = SimpleSearch.getCount(dirname+"/testfile.txt", s);
				}
				else if (searchType.equals("regex")) {
					count = RegExpSearch.getCount(dirname+"/testfile.txt", s);
				}
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

	//	enum SearchType
    //{ 
    //    SIMPLE, REGEX, INDEX, ALL; 
    //} 


	/**
	 * Runs quick function checking tests.
	 * @param  args  not used
	 */
	public static void main(String[] args) {
		// SearchType toTest = SearchType.ALL;
		// if (args.length > 0) {
		// 	toTest = SearchType.valueOf(args[0]);
		// }
		// else {
		// 	System.out.print("defaulting to testing all search types, to test specific");
		// 	System.out.println("search type run with argument (SIMPLE, REGEX, INDEX or ALL)");
		// }
		String basedir = "../resources/function_tests/";
		String[] dirs = {"test1", "test2", "test3"};

		int err = 0;
		String dirname = "";
		for (int i=0; i<2; i++) {
			dirname = basedir + dirs[i];
			System.out.printf("\ntesting %s search on dir %s\n", "simple", dirname);
			err = SearchTest(dirname, "simple");
			if (err < 0) break;
			System.out.printf("\ntesting %s search on dir %s\n", "regex", dirname);
			err = SearchTest(dirname, "regex");
			if (err < 0) break;
		}
		if (err >= 0) {
			dirname = basedir + dirs[2];
			System.out.printf("\ntesting %s search on dir %s\n", "regex", dirname);
			err = SearchTest(dirname, "regex");
		}	
	}
}
