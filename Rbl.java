import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;



public class Rbl
{

public static void main(String args[]) throws IOException
	{
		if(args.length < 1) //file name not given
		{
			System.out.println("Usage: java Rbl <FILENAME>");
			System.exit(0);
		}
		String book_name = args[0];
		String mypath = "C:/Users/Pushpinder/Desktop/nlp-project/";
		
		EntityExtractor e = new EntityExtractor(mypath,book_name);
		e.extract();
		int entity_count = e.getEntityNum();
		String[] all_first_names = e.getAllFirstNames();
		String[] all_last_names = e.getAllLastNames();
		
		WordCount w = new WordCount(mypath, "names", book_name, "count", "-name-count-unsorted");
		w.count();
		int[] freq = w.getFrequency();
		String[] unique_names = w.getUniqueNames();
		int total_refs = w.getTotalRefs();
		int num_unique = w.getUniqueNum();
		
		//System.out.println("Entities extracted =" +entity_count+ "\nTotal unique names ="+num_unique);
		
		/*NameCount n = new NameCount(mypath, "names", "count", book_name);
		n.count();
		n.splitNames();
		n.seqNameArray(entity_count);
		String[] first_names = n.getFirstNames();
		String[] last_names = n.getLastNames();
		int[] freq = n.getFreq();
		String[] all_names = n.getAllNames();
		//System.out.println("length of name array: " + AllNames.length);
		
		CorefExtractor c = new CorefExtractor(mypath, book_name);
		c.extractCorefs();
		c.corefToArray();
		
		NaiveCoref naive = new NaiveCoref(mypath, book_name, all_names, first_names, last_names);
		naive.resolveCorefNaive();
		
	*/
	
	
	
	
	}

	
	
	
	
	
	
	
	
	
}