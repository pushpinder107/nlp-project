// File read: mypath/ner-tagged/book_name-ner-tagged-txt
// File written: mypath/names/book_name-names-nospace.txt
//

import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;


public class EntityExtractor 
{
	String mypath, book_name;
	String pattern = "<PERSON>.*?</PERSON>";
	String folder = "ner-tagged/";
	String post_fix = "-ner-tagged.txt";
	public int num;
	public ArrayList<String> all_first_names = new ArrayList<String>(); //will hold all first names in the sequence in which they appear
	public ArrayList<String> all_last_names = new ArrayList<String>();
	
	public EntityExtractor(String mpth, String b_name)
	{
		mypath = mpth;
		book_name = b_name;
		
	}
	
	void extract () throws IOException
	{
		num=1; // to count number of lines extracted from ner-tagged book
		String name;
		Pattern p = Pattern.compile(pattern);
		BufferedReader r = new BufferedReader(new FileReader(mypath + folder + book_name + post_fix)); // r reads ner-tagged files
		String line;
		PrintWriter out = new PrintWriter(new FileWriter(mypath + "names/" + book_name + "-names-nospace.txt"), true);
		while ((line = r.readLine()) != null) 
		{	
			Matcher m = p.matcher(line);
			while (m.find()) 
			{
				
				name = (m.group(0).replaceAll("<PERSON>","")).replaceAll("</PERSON>","|#"); //end tag is replaced by |# for later use
				name = (name.replace('.','+')).replaceAll("\\+ ","+ "); // . is replaced by + to signify middle names or abbreviations
				name = name.replaceAll(" ","|"); // space replaced by | to split name into first name and last name
				String[] split = name.split("[|]",2);
				all_first_names.add(split[0]);
				all_last_names.add(split[1]);
				out.write(name.trim() + "\r\n");
				num++;
			}	
		}
		
		
	  
	 
		out.flush();
		out.close();
		r.close();
		System.out.println("Total entity mentions extracted (with tags): " + num);
	}
	
	
	String[] getAllFirstNames()
	{
		String[] arr = all_first_names.toArray( new String[all_first_names.size()]);// converting ArrayList to array
		//System.out.println("Size of array after list= " + arr.length);
		return arr;
	}
	
	String[] getAllLastNames()
	{
		String[] arr = all_last_names.toArray( new String[all_last_names.size()]);
		return arr;
	}
	
	
	int getEntityNum() //return total number of lines extracted 
	{
		return num;
	}
	
	
	
	
	
}