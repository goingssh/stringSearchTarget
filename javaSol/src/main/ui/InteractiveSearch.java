package ui;

import method.*;
import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * @author Sherri Goings
 */
public class InteractiveSearch {

	Search[] searches;

	public InteractiveSearch(String path) {
		searches = new Search[] { new SimpleSearch(path), new RegExpSearch(path), new IndexSearch(path) };
	}

	public void search(String searchTerm, int searchType) {
		Search search = searches[searchType-1];
		ArrayList<FileCount> filecounts = search.getCounts(searchTerm);
		Collections.sort(filecounts, Collections.reverseOrder());
		System.out.println("\nSearch results: \n");
		for (FileCount fc : filecounts) {
			System.out.println("      " + fc + " matches\n");
		}

	}

	public static void main(String[] args) {
		String path = "../resources/ui";
		if (args.length > 0) {
			System.out.println("setting searchFile directory to " + args[0]);
			path = args[0];
		}

		// would ideally put the search instance initializations in separate thread and not in class
		// constructor so could be going on in background if many/large files to load
		InteractiveSearch is = new InteractiveSearch(path);
		Scanner scanner = new Scanner(System.in);

		// * need to change to nextline so can test multi-word phrases
		System.out.println("Enter the search term or 'q' to quit: ");
		String searchTerm = scanner.next();
		int method = -1;
		while (!searchTerm.equals("q")) {
			boolean goOn=false;
			while (!goOn) {
				try{
					System.out.println("Enter the search method (1 - string match, 2 - regular expression, or 3 - indexed): ");
					method = scanner.nextInt();
					if (method <= 3 && method >= 1) { goOn=true; }
					else { System.out.println("Incorrect input: please enter an integer 1, 2, or 3. "); }

				}
				catch (InputMismatchException ex) {
					System.out.println("Incorrect input: please enter an integer! ");
					scanner.nextLine();
				}
			}
			is.search(searchTerm, method);
			System.out.println("Enter the search term or 'q' to quit: ");
			searchTerm = scanner.next();
		}   

	}
}
