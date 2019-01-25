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
public class SimpleSearch extends Search {

	public SimpleSearch(String path) {
		super(path);
	}
	
   	/**
	 * Finds the number of times a search string appears in a text file.
	 * Simple unoptimized method loops through string of entire contents of file to
	 * check if search term appears as a substring using indexOf method. Each time
	 * instance of search term is found, begins checking again at character after
	 * 1st character of found substring. Note that this means if searching for the 
	 * string "aa" in the string "aaa" it would be found twice.
	 * @param  searchTerm  the string to search for
	 * @param  contents    the file contents string searching through
	 * @return             total number of matches found 
	 */
	public int getCount(String searchTerm, String contents) {
		int count=0;
		int index = contents.indexOf(searchTerm, 0);
		while (index != -1) {
			count++;
			index = contents.indexOf(searchTerm, index+1);
		}
		return count;
    }

	// * for functional tests, always will only be one file to search
	public int getCount(String searchTerm) {
		return getCount(searchTerm, getFileStrings().get(0).getContents());
	}
   
}
