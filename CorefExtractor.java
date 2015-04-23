import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class CorefExtractor
{
	String mypath, book_name;
	public int count_coref=1;
	public String[][] corefarray;
	
	public CorefExtractor(String mpth, String b_name)
	{
		mypath = mpth;
		book_name = b_name;
	}
	
	void extractCorefs() throws IOException
	{
		BufferedReader r = new BufferedReader(new FileReader(mypath+"annotated-books/"+book_name+"-annotated.txt"));
		String line;
		PrintWriter out = new PrintWriter(new FileWriter(mypath+"coreferences/"+book_name+"-corefs.txt"), true);
		Pattern p = Pattern.compile("\\([0-9]+,[0-9]+,\\[[0-9]+,[0-9]+\\]\\) -> \\([0-9]+,[0-9]+,\\[[0-9]+,[0-9]+\\]\\), that.*");
		
		String str;
		while ((line = r.readLine()) != null) 
		{
			Matcher m = p.matcher(line);
			if(m.find()) 
			{
				str=m.group(0).replaceAll("[(|\\[|\\]|)]",""); //removing ( [ ] )
				str=str.replaceAll("[,]", "#"); // replacing , with # for later splitting
				str=str.replaceAll("that is:",""); // removing that is:
				out.write(str.replace("->","#")+"\r\n");// replacing -> with #
				count_coref++;
			}
			
				
		}
		System.out.println("Total Co-reference chains: " + count_coref);
		out.flush();
		out.close();
		r.close();
	}
	
	void corefToArray() throws IOException
	{
		corefarray = new String[count_coref][11];
		BufferedReader r = new BufferedReader(new FileReader(mypath+"coreferences/"+book_name+"-corefs.txt"));
		FileWriter f = new FileWriter(mypath+"test/"+book_name+"-coref-array.txt");
		String line;
		int sents=0;
		int z=0;
		int x;
		
		while ((line = r.readLine()) != null) 
		{
			String split[]=line.split("#",10); // first 10 hashes are important in book-coref.txt
			sents=split.length; // minimum length will be 10 
			
			for(x=0;x<sents;x++) // filling only 10 columns of array
			{
			
			corefarray[z][x]=(split[x].trim()).replaceAll("\"","");
			//corefer2[z][x]=(split5[x].trim()).replaceAll("\"","");
			}
			
			f.write(corefarray[z][0]+" "+corefarray[z][1]+" "+corefarray[z][2]+" "+corefarray[z][3]+" "+corefarray[z][4]+" "+corefarray[z][5]+" "+corefarray[z][6]+" "+corefarray[z][7]+" "+corefarray[z][8]+" "+corefarray[z][9]+"\r\n");
			//f6.write(corefer2[z][0]+" "+corefer2[z][1]+" "+corefer2[z][2]+" "+corefer2[z][3]+" "+corefer2[z][4]+" "+corefer2[z][5]+" "+corefer2[z][6]+" "+corefer2[z][7]+" "+corefer2[z][8]+" "+corefer2[z][9]+"\r\n");
			z++;
		}
	
	
	}
	
	
	void reduceCorefs(String[] first_name, String[] last_name)
	{
	
	
	}
	
}