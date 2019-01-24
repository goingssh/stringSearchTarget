import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Quick start to file search problem.
 * @author Sherri Goings
 */
public class SimpleSearch {
	/**
	 * Finds the number of times a search term appears in a text file.
	 * Simple unoptimized method uses Scanner to compare term to each whitespace-separated token.
	 * Optionally may count substring matches.
	 * @param  filename    the text file to search
	 * @param  searchTerm  the string to search for
	 * @param  substr      if true match also counts if entire searchTerm is a substring of given token.
	 * @return             total number of matches found 
	 */
	public static int getCount(String filename, String searchTerm, boolean substr) {
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

	/**
	 * Runs simple search on file for string matches (does not handle multi-line search terms)
	 * @param  args  requires 2 command line arguments, name of file and search term
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("usage: java SimpleSearch filename searchword");
           return;
        }

        String filename = args[0];
		String searchTerm = args[1];
		int count = getCount(filename, searchTerm, true);
		if (count >= 0) {
			System.out.printf("%s appears %d times in file %s\n", searchTerm, count, filename);
		}
	}
   
}
