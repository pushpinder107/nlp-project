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
	
	public EntityExtractor(String mpth, String b_name)
	{
		mypath = mpth;
		book_name = b_name;
		
	}
	
	void extract () throws IOException
	{
		int num=0;
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
				out.write(name.trim() + "\r\n");
			}	
		}
		out.flush();
		out.close();
		r.close();
		System.out.println("Total entity mentions extracted (with tags): " + (num+1));
	}
}