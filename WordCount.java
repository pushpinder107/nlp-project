import java.util.regex.*;
import java.util.*;
import java.io.*;

public class WordCount
{
	public String mypath, input_file, inFolder, outFolder, output_file;
	public int num, total_refs;
	public String[] unique_names;
	public int[] freq; 
	
	
	public WordCount( String mpth, String i_fldr, String i_f, String o_fldr, String o_f)
	{
		mypath = mpth;
		input_file = i_f;
		inFolder = i_fldr + "/";
		outFolder = o_fldr + "/";
		output_file = o_f;
			 
	}

	void count() throws IOException
	{
		Scanner input = new Scanner(new File(mypath + inFolder + input_file +"-names-nospace.txt"));
		FileWriter f = new FileWriter(mypath + outFolder + input_file + output_file + ".txt"); 
		
		Map<String, Integer> wordCounts = new TreeMap<String, Integer>();
        
		while (input.hasNext())
		{
            String next = input.next().toLowerCase();
            if (!wordCounts.containsKey(next)) 
			{
                wordCounts.put(next, 1);
            } 
			else
			{
                wordCounts.put(next, wordCounts.get(next) + 1);
            }
        }
		
		
        System.out.println("Total unique names mentioned in file = " + wordCounts.size());
        
		num=wordCounts.size();
		freq = new int[num];
		unique_names = new String[num];
		int i=0;
		total_refs=0;
        
		for (String word : wordCounts.keySet())
		{
            int count = wordCounts.get(word);
            
			freq[i] = count;
			unique_names[i] = word;
			total_refs = total_refs + freq[i];
			i++;
			
			f.write(count + "\t" + word + "\r\n");// writing to name-count-unsorted.txt
			//System.out.println("i= "+i);
        }
		
		f.flush();
		f.close();
		
		
	}	
	
		
	int[] getFrequency()
	{
		return freq;
	}
		
	String[] getUniqueNames()
	{
		return unique_names;
	}
		
	int getUniqueNum()
	{
		return num;
	}
		
	int getTotalRefs()
	{
		return total_refs;
	}
		
}
	
	






