package method;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.lang.*;

/**
 * Count number instances of search term in files where searcg term is regular expression
 * @author Sherri Goings
 */
public class RegExpSearch {
	/**
	 * Finds the number of times a search string appears in a text file.
	 * Simple unoptimized method reads file (buffered so can handle still if large),
	 * line by line and counts number of times search string appears as substring in 
	 * each line, accumulated across all lines. Cannot handle search strings that span
	 * multiple lines. 
	 * @param  filename    the text file to search
	 * @param  searchTerm  the string to search for
	 * @return             total number of matches found 
	 */
	public static int getCount(String filename, String searchTerm) {
		int count=0;
        try{
			String sline;
			BufferedReader fin = new BufferedReader(new FileReader(filename));
			Pattern pattern =  Pattern.compile(searchTerm);
			Matcher matcher = null;
			while ((sline = fin.readLine()) != null) {
				matcher = pattern.matcher(sline);
				while (matcher.find()) {
					count++;
				}
			}
			fin.close();
		} catch (IOException e){
			System.err.println("Error: " + e.getMessage());
			return -1;
		}
		return count;
    }
   
}
