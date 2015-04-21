import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
//import java.nio.charset.StandardCharsets;
import java.nio.charset.*;
//import java.nio.*;


public class Rbl
{

public static void main(String args[]) throws IOException
	{
		if(args.length < 1) //file name not given
		{
			System.out.println("Usage: java Rbl <FILENAME>");
			System.exit(0);
		}
		
		String mypath = "C:/Users/Pushpinder/Desktop/nlp-project/";
		
		EntityExtractor e = new EntityExtractor(mypath,args[0]);
		e.extract();
		
		NameCount n = new NameCount(mypath, args[0]);
		n.count();
		n.splitNames();
		String[] first_name = n.getFirstNames();
		String[] last_name = n.getLastNames();
		
		CorefExtractor c = new CorefExtractor(mypath, args[0]);
		c.extractCorefs();
		c.corefToArray();
		
	
	
	
	
	
	}

	
	
	
	
	
	
	
	
	
}