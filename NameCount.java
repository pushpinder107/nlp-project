import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class NameCount
{
	String book_name;
	String mypath;
	public static int num;
	public int[] freq;
	public String[] name;
	public String[] first_name;
	public String[] last_name;
	
	public NameCount(String mpth, String b_name)
	{
		book_name = b_name;
		mypath = mpth;
	}
	
	void count() throws IOException
	{
		Scanner input = new Scanner(new File(mypath+"names/"+book_name+"-names-nospace.txt"));
		FileWriter f = new FileWriter(mypath+"count/"+book_name+"-name-count-unsorted.txt"); 
		
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
		
		
        System.out.println("Total unique names mentioned in book = " + wordCounts.size());
        
		this.num=wordCounts.size();
		freq = new int[this.num];
		name = new String[this.num];
		int i=0;
		int e_refs=0;
        
		for (String word : wordCounts.keySet())
		{
            int count = wordCounts.get(word);
            
			freq[i] = count;
			name[i] = word;
			e_refs=e_refs+freq[i];
			i++;
			
			f.write(count + "\t" + word + "\r\n");// writing to name-count-unsorted.txt
        }
		
		System.out.println("Total mentions in book = " + (e_refs+1));
		sort(name, freq, num);
		f.flush();
		f.close();
	}
	
	void sort(String[] name, int[] freq, int num) throws IOException
	{
		int i,j,k;
		Integer tempfreq;
		String tempname;
		
		//Sorting names according to frequencies 
		// name array here contains the entire name (first and last name) with no spaces.
		
		for(i=0;i<num;i++)
		{
			for(j=i+1;j<num;j++)
			{
				if(freq[i]<freq[j])
				{
					tempfreq=freq[j];
					tempname=name[j];
					freq[j]=freq[i];
					name[j]=name[i];
					freq[i]=tempfreq;
					name[i]=tempname;
				}
			}
		}
		FileWriter f = new FileWriter(mypath+"count/"+book_name+"-name-count-sorted"+".txt");
		for(i=0;i<num;i++)
		{
			
			f.write(freq[i]+"\t"+name[i]+"\r\n");// writing to entity-count-sorted
		}
		f.flush();
		f.close();
		
	}
	
	
	void splitNames() throws IOException
	{
		int i;
		first_name = new String[this.num];
		last_name = new String[this.num];
		FileWriter f = new FileWriter(mypath+"test/"+book_name+"-name-count-clean.txt");
		for(i=0;i<this.num;i++)
		{
			
			String split[] = name[i].split("[|]",2);
			
			first_name[i] = split[0].replaceAll("[|]"," ");
			last_name[i] = split[1].replaceAll("[#|]","");
			f.write(first_name[i]+"|"+last_name[i]+"|"+freq[i]+"\r\n");
		}
		f.flush();
		f.close();
		
		
	}
	
	String[] getFirstNames()
	{
		return first_name;
	}
	
	String[] getLastNames()
	{
		return last_name;
	}
	
	
}