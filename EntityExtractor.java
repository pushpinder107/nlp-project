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
	public ArrayList<String> indexes = new ArrayList<String>();
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
		int len =0;
		PrintWriter out = new PrintWriter(new FileWriter(mypath + "names/" + book_name + "-names-nospace.txt"), true);
		while ((line = r.readLine()) != null) 
		{	
			//System.out.println("line length: "+line.length());
			len = len + line.length();
			Matcher m = p.matcher(line);
			while (m.find()) 
			{
				
				name = (m.group(0).replaceAll("<PERSON>","")).replaceAll("</PERSON>","|#"); //end tag is replaced by |# for later use
				name = (name.replace('.','+')).replaceAll("\\+ ","+ "); // . is replaced by + to signify middle names or abbreviations
				name = name.replaceAll(" ","|"); // space replaced by | to split name into first name and last name
				String[] split = name.split("[|]",2);
				all_first_names.add(split[0]);
				all_last_names.add(split[1]);
				int in = m.start()+ len;
				String ind = Integer.toString(in);
				indexes.add(ind );
				out.write(name.trim() + "\r\n");
				num++;
			}	
		}
		System.out.println("Total length: "+len);
		
	  
	 
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
	
	String[] getIndexes() throws IOException
	{
		String[] indxs = indexes.toArray( new String[indexes.size()]);
		FileWriter f = new FileWriter(mypath+"test/"+book_name+"-index.txt");
		int i;
		for(i=0;i<indxs.length;i++)
			f.write(indxs[i]+"\r\n");
			
		f.flush();
		f.close();
		
		return indxs;
	}
	
	
	
	
	
}