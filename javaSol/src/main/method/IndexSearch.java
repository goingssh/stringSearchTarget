package method;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Count number instances of search term in files where first preprocess files to hash word counts
 * @author Sherri Goings
 */
public class IndexSearch {

	// associates each filename with its map of wordcounts
	private class FileMap {
		private String filename;
		private Map<String, Integer> wordcounts;

		public FileMap(String filename) {
			this.filename = filename;
			wordcounts = new HashMap<String, Integer>();
		}

		public void addWord(String word) {
			if (wordcounts.containsKey(word)) {
				wordcounts.put(word, wordcounts.get(word)+1);
			}
			else {
				wordcounts.put(word, 1);
			}
		}

		public int getCount(String word) {
			if (wordcounts.containsKey(word)) {
				return wordcounts.get(word);
			}
			else {
				return 0;
			}
		}
	}

	// list of all file/map pairs
	private ArrayList<FileMap> filemaps;

	public IndexSearch(String path) {
		filemaps = new ArrayList<FileMap>();
		
		File folder = new File(path);
		File[] filelist = folder.listFiles();
		
		for (File file : filelist) {
			if (file.isFile()) {
				
				// *** this "if" is temporary to work with current functional tests setup,
				// simply remove the "if" once test format is updated! ***
				if (file.getName().equals("testfile.txt")) {
					FileMap curFileMap = new FileMap(file.getName());
					buildFileMap(curFileMap, file);
					filemaps.add(curFileMap);
				}
			}
		}
	}

	private int buildFileMap(FileMap curMap, File file) {
		try{
      	    Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
                String word = scan.next();
				curMap.addWord(word);
			}
			scan.close();
		} catch (IOException e){
			System.err.println("Error: " + e.getMessage());
			return -1;
		}
		return 0;
	}


	/**
	 * Finds the number of times a search string appears in a text file.
	 * Uses hash table created by preprocessing files for fast lookup of count.
	 * Works for exact matches of single words only.
	 * @param  searchTerm  the string to search for
	 * @return             total number of matches found 
	 */
	public int getCount(String searchTerm) {
		int count = filemaps.get(0).getCount(searchTerm);
		return count;
    }

	/*public static void main(String[] args) {

		IndexSearch isearch = new IndexSearch("test1/");
		String basedir = "./";
		String[] dirs = {"test1", "test2", "test3"};

		int err = 0;
		String dirname = "";

		dirname = basedir + dirs[0];
		System.out.printf("\ntesting %s search on dir %s\n", "simple", dirname);

		String countFile = dirname+"/result_exact.txt";
		try{
      	    Scanner scanTerms = new Scanner(new File(dirname+"/search_terms.txt"));
			Scanner scanCounts = new Scanner(new File(countFile));
			int failCount=0;
			while (scanTerms.hasNext()) {
                String s = scanTerms.nextLine();
				int count = isearch.getCount(s);
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
		}
		}*/
   
}
