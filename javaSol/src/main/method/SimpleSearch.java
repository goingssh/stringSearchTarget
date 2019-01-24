package method;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Quick start to file search problem.
 * @author Sherri Goings
 */
public class SimpleSearch {
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
			while ((sline = fin.readLine()) != null) {
				int index = sline.indexOf(searchTerm, 0);
				while (index != -1) {
					count++;
					index = sline.indexOf(searchTerm, index+1);
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
