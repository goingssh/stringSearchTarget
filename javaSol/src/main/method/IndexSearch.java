package method;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Count number instances of search term in files where first preprocess files to hash word counts
 * @author Sherri Goings
 */
public class IndexSearch extends Search {

	// list of all file/map pairs
	private ArrayList<FileMap> filemaps;
	
	public IndexSearch(String path) {
		super(path);
		filemaps = new ArrayList<FileMap>();
		readFiles(path);
	}

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
			else { wordcounts.put(word, 1); }
		}
		public int getCount(String word) {
			if (wordcounts.containsKey(word)) {
				return wordcounts.get(word);
			}
			else { return 0; }
		}
		public String getName() {
			return filename;
		}
	}

	private void readFiles(String path) {
		File folder = new File(path);
		File[] filelist = folder.listFiles();
		
		for (File file : filelist) {
			if (file.isFile()) {
				FileMap curFileMap = new FileMap(file.getName());
				buildFileMap(curFileMap, file);
				filemaps.add(curFileMap);
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

	public ArrayList<FileCount> getCounts(String searchTerm) {
		ArrayList<FileCount> counts = new ArrayList<FileCount>();
		for (FileMap fm : filemaps) {
			int curCount = fm.getCount(searchTerm);
			FileCount fc = new FileCount(fm.getName(), curCount);
			counts.add(fc);
		}
		return counts;
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
}
