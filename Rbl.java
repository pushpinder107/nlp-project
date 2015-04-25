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
		String[] all_first_names = e.getAllFirstNames();// still contain special characters
		String[] all_last_names = e.getAllLastNames();
		//System.out.println("all_last_names:"+ all_last_names[4]);
		String[] indexes = e.getIndexes();//indexes of all entities, with repeats
		
		WordCount w = new WordCount(mypath, "names", book_name, "count", "-name-count-unsorted");
		w.count();
		int[] freq = w.getFrequency();
		//System.out.println("freq contains: "+freq.length);
		String[] unique_names = w.getUniqueNames();// contains no space ( "|" instead)
		//System.out.println("unique_names contains: "+unique_names.length);
		int total_refs = w.getTotalRefs();
		//System.out.println("total references: "+total_refs);
		int num_unique = w.getUniqueNum();
		//System.out.println("num_unique: "+num_unique);
		w.uniqueNamesSplit();
		String[] unique_first_names = w.getUniqueFirstNames();
		//System.out.println("unique_first_names contains: "+unique_first_names.length);
		String[] unique_last_names = w.getUniqueLastNames();
		
		CorefExtractor c = new CorefExtractor(mypath, book_name);
		c.extractCorefs();
		String[][] corefarray = c.corefToArray();
		String[][] reduced_corefs = c.reduceCorefs(corefarray, unique_first_names, unique_last_names, freq);
		
		ResolveCorefs rc = new ResolveCorefs(mypath, book_name, reduced_corefs, unique_first_names, unique_last_names, freq);
		rc.resolve();
		//System.out.println("corefarray[1][9]: " + corefarray[1][9]);
		//System.out.println("unique_first_name:"+unique_first_names.length+"\nuniquelastname: "+unique_last_names.length);
		//System.out.println("Rows in corefarray: " + corefarray.length + "\nColumns: " +corefarray[0].length);
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
		
		
		
		NaiveCoref naive = new NaiveCoref(mypath, book_name, all_names, first_names, last_names);
		naive.resolveCorefNaive();
		
	*/
	
	
	
	
	}

	
	
	
	
	
	
	
	
	
}