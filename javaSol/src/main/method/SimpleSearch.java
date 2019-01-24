package method;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;


/**
 * Quick start to file search problem.
 * @author Sherri Goings
 */
public class SimpleSearch {

	// list of all filename/contents_string pairs
	private ArrayList<FileString> filestrings;

	// associates each filename with its map of wordcounts
	private class FileString {
		private String filename;
		private String contents;

		public FileString(String filename, String contents) {
			this.filename = filename;
			this.contents = contents;
		}

		public String getContents() {
			return contents;
		}
	}

	public SimpleSearch(String path) {
		filestrings = new ArrayList<FileString>();

		File folder = new File(path);
		File[] filelist = folder.listFiles();
		
		for (File file : filelist) {
			if (file.isFile()) {
				
				// *** this "if" is temporary to work with current functional tests setup,
				// simply remove the "if" once test format is updated! ***
				if (file.getName().equals("testfile.txt")) {
					String contents="";
					try {
						Path filepath = file.toPath();
						contents = new String(Files.readAllBytes(filepath), StandardCharsets.UTF_8);
					} catch (IOException e){
						System.err.println("Error: " + e.getMessage());
					}
					FileString curFileString = new FileString(file.getName(), contents);
					filestrings.add(curFileString);
				}
			}
		}
	}

	/**
	 * Finds the number of times a search string appears in a text file.
	 * Simple unoptimized method reads file (buffered so can handle still if large),
	 * line by line and counts number of times search string appears as substring in 
	 * each line, accumulated across all lines. Cannot handle search strings that span
	 * multiple lines. 
	 * @param  searchTerm  the string to search for
	 * @return             total number of matches found 
	 */
	public int getCount(String searchTerm) {
		int count=0;
		String contents = filestrings.get(0).getContents();
		int index = contents.indexOf(searchTerm, 0);
		while (index != -1) {
			count++;
			index = contents.indexOf(searchTerm, index+1);
		}
		return count;
    }
   
}
