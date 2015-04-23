// creates one output file, a list of all names mentioned in the book, with space between first and 
//  last name removed 

import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;


public class EntityExtractor 
{
	String mypath;
	String book_name;
	String pattern = "<PERSON>.*?</PERSON>";
	String folder = "ner-tagged/";
	String post_fix = "-ner-tagged.txt";
	public int num;
	public ArrayList<String> all_first_names = new ArrayList<String>();
	public ArrayList<String> all_last_names = new ArrayList<String>();
	
	public EntityExtractor(String mpth, String b_name)
	{
		mypath = mpth;
		book_name = b_name;
		
	}
	
	void extract () throws IOException
	{
		num=0;
		String name;
		Pattern p = Pattern.compile(pattern);
		BufferedReader r = new BufferedReader(new FileReader(mypath + folder +book_name + post_fix)); // r reads ner-tagged files
		String line;
		PrintWriter out = new PrintWriter(new FileWriter(mypath+"names/"+book_name+"-names-nospace.txt"), true);
		while ((line = r.readLine()) != null) 
		{	
			Matcher m = p.matcher(line);
			while (m.find()) 
			{
				num++;
				name = (m.group(0).replaceAll("<PERSON>","")).replaceAll("</PERSON>","|#");
				name = (name.replace('.','+')).replaceAll("\\+ ","+ ");
				name = name.replaceAll(" ","|");
				String[] split = name.split("[|]",2);
				all_first_names.add(split[0]);
				all_last_names.add(split[1]);
				out.write(name.trim() + "\r\n");
			}	
		}
		PrintWriter out2 = new PrintWriter(new FileWriter(mypath+book_name+"-arraylist-nospace.txt"), true);
		int i;
		for(i=0;i<all_first_names.size();i++)
		 out2.write(all_first_names.get(i)+"\t"+ all_last_names.get(i)+ "\r\n");
		 
		 out2.flush();
		 out2.close();
	  
	 
		out.flush();
		out.close();
		r.close();
		System.out.println("Total entity mentions extracted (with tags): " + (num+1));
	}
	
	int getEntityNum() //return total number of entity mentions
	{
		return num+1;
	}
	
	
	
	
	
}