Code for Target case study on string search. See readme files in each project folder.

Projects:

  bashTest - first quick tests using bash scripts to start thinking about problem.

  javaSol - Java solution as relatively efficient and highly portable option.

Efficiency: 
  
  Some time test data - getting match counts for all files in a directory of 2000 random words taken from a file with 23000 unique english words (reshuffled for each change of test directory). Times do not include initial reading of files (saved to String objects for simple and regular expression search) or building of hash map for indexed search. For the tests done here with <10 files the largest of which was ~4mb, these times were inconsequential compared to thousands of searches, however in a situation where the files to be searched are numerous, large, or frequently changing compared to the number of searches, the initial setup could become a factor.

Runtimes (in seconds) for 3 search methods on 3 slightly different sets of files
- searching all files for counts of 2000 random words: 

             	      	index	      simple 	 regular_expression

3 short (<4KB)		.003	      .022	 .035

1 "long" (<4MB)		.001	      4.33	 7.89

3 short, 2 long		.001	      8.74	 14.01

The indexed hashmap is as expected orders of magnitude faster than the other 2 searches, and unaffected by file size. After the initial setup, given a decent hash calculation (trivial for strings) and sufficient space (not always so trivial in today's world of big data) we expect each search operation to be O(1), or constant time. Therefore time will scale linearly with # of searches (# search terms and # of files to search), but will remain constant with respect to file size.  The significant disadvantage of the indexed method as I have implemented it is that it can only match exact words delimited by whitespace before and after. There are many algorithms/methods to improve the generality of indexed search, such as hashing multi-word phrases, or including in the hashmap a list of all indexes in the file where a search term is found to be used in intersection methods for longer phrases. These algorithms come with the tradeoff of usually significant increase in runtime, code complexity, memory space, or often a combination of all 3, so indexing is not as magic as it can seem to be given runtimes like those above. 

The simple and regular expression methods both do scale linearly with file length (both approximately double in time when 2 about equally long files are included instead of 1), again in line with expectation. These methods both have to iterate through the entire file, so each search count takes O(N) time per search term per file where N is some measure of the file length. They will also scale linearly with the number of search terms. The total number of files is not a factor in the performance of these methods, only the total length of all of the files. Both of these methods handle matching phrases of any length as well as variants on search terms such as the plural or any combo of prefixes/suffixes. The regular expression search is clearly a bad choice for a test like this where we are not actually searching for any regular expressions, only standard words/phrases. The regular expression code can only add unnecessary overhead in this case. Searching for actual regular expressions is even slower;  given unlimited string length and allowed complexity of the regular expression the increase in time complexity is unbounded, and this search can easily become impractically slow, but it is also often useful and in many situations no other search could get the desired results. It is just not meant to be used in general situations where simpler methods will suffice.

There are a plethora of ways to optimize any and all of these search methods, hundreds of computer scientists have made search optimization their entire career's work. 
Some general/broad ideas:
- searches in the real world are almost never random. Just keeping a cache of the top few thousand or even few hundred searches in any given context will often yield orders of magnitude increases in speed. In a dynamic application it may make sense to maintain a sliding window of the most frequent searches that updates over time.
- use heuristic methods. Almost every problem in AI/ML/EC is best solved in the end in its own unique way by combining two or more methods to create the perfect fit for this problem. It's often not worth creating a perfectly fitted solution for just any problem, but if you know you need serious optimization on something in particular, considering how to combine a few "pretty good" generalized solutions is a good place to start.
 

Some specific ideas from my background and knowledge domains:
- if running actual search on local servers, I would code at a lower level for optimal speed. Java is a reasonable tradeoff for portability, but there is no comparison to C or even C++ with a good compiler tuned to a particular OS and hardware. It is rare in a typical use case for this level of optimization to be important, but for example in complex real-time simulations getting a memory-mapped bytestream of the optimal buffer size for a given hard-drive's I/O interface can mean the difference between full functionality and unusability.

- parallel & distributed computation. Employing multiple threads to split up the work of especially initial file reading/setup and later dividing searches too is a clear option for speedup on this kind of search. Most every chip now has multiple cores and hyperthreading on each core so using as many threads as there are cores*2 assuming hyperthreading causes very little overhead and can yield a several times increase in speed. Given an even larger number of requests, farming tasks out in a distributed manner could be an effective solution, either on an in-house cluster or more likely today on cloud services. For this type of search problem you have to deal with the movement of file data to distributed hardware, so there is a tradeoff in sending searches off to many cpus that are not co-located.

- from an algorithms perspective. Something I would really like to try but didn't have time is to use a binary search tree instead of a hash map to index the counts of each word in each file. Given that the text file is known completely at the start (and assuming that file changes are rare), a very efficient binary search tree could be implemented - perfectly balanced using a fixed length standard array (though it would be of references to objects, as the hashmap is). For each file you would create a sorted list of words with their associated counts in a similar manner as the hashmap was created, and then it's simple and efficient to convert that into an array that represents a BST. A standard search for a single exact word would be O(logN) instead of the O(1) that the hashmap provides, but except in the case of very large files that would be a negligable difference, and the lack of any wasted space may make the BST array implementation worthwhile even in the large file case.  The potential real benefit though would be to get back at least many of the partial string matches that are lost with the hashmap. Because the BST is "sorted" alphabetically on the strings, finding strings in a range around a given search string is time efficient. So while this still couldn't handle prefixes, you could quickly find all similar strings to the search string that simply had different endings, such as plurals, suffixes, verb ending variations, etc. It seems that theoretically this could be only slightly slower even in the worst case and give a large benefit in the generality of the search.  

 


