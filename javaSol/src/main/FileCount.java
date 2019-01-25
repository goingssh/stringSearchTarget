
public class FileCount implements Comparable<FileCount> {
    public String filename;
    public int count;

    public FileCount(String filename, int count) {
		this.filename = filename;
		this.count = count;
    }
    
    public String toString() {
		return filename + " -- " + count;
    }

    public int compareTo(FileCount other) {
        return ((Integer)count).compareTo((Integer)other.count);
    }

}
