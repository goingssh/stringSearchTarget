package method;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;


public abstract class Search {

	// list of all filename/contents_string pairs
	private ArrayList<FileString> filestrings;

	public Search(String path) {
		filestrings = new ArrayList<FileString>();
		readFiles(path);
	}

	protected class FileString {
		private String filename;
		private String contents;

		public FileString(String filename, String contents) {
			this.filename = filename;
			this.contents = contents;
		}
		public String getContents() {
			return contents;
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

	public ArrayList<FileString> getFileStrings() {
		return filestrings;
	}

	public abstract ArrayList<FileCount> getCounts(String searchTerm);
	
	public abstract int getCount(String searchTerm);
}
