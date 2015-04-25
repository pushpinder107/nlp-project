//File read: mypath/annotated/book_name-annotated.txt
//File written: mypath/coreferences/book_name-corefs.txt

import java.util.regex.*;
import java.util.*;
import java.io.*;


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
				str = str.replace("'s","");
				out.write(str.replace("->","#")+"\r\n");// replacing -> with #
				count_coref++;
			}
			
			
		}
		System.out.println("Total Co-reference chains: " + count_coref);
		out.flush();
		out.close();
		r.close();
	}
	
	String[][] corefToArray() throws IOException
	{
		corefarray = new String[count_coref][10];
		BufferedReader r = new BufferedReader(new FileReader(mypath+"coreferences/"+book_name+"-corefs.txt"));
		FileWriter f = new FileWriter(mypath+"test/"+book_name+"-coref-array.txt");
		String line;
		int sents=0, z=0, x;
		
		
		while ((line = r.readLine()) != null) 
		{
			String split[]=line.split("#",10); // first 10 hashes are important in book-coref.txt
			sents=split.length; // minimum length will be 10 
			
			for(x=0;x<sents;x++) // filling only 10 columns of array
			{
				split[x] = split[x].replaceAll("\"","");//removing quotes
				corefarray[z][x] = split[x].trim(); //possibility of improvement in running time**
			
			}
			
			f.write(corefarray[z][0]+"|"+corefarray[z][1]+"|"+corefarray[z][2]+"|"+corefarray[z][3]+"||"+corefarray[z][4]+"|"+corefarray[z][5]+"|"+corefarray[z][6]+"|"+corefarray[z][7]+"||"+corefarray[z][8]+"|"+corefarray[z][9]+"\r\n");
			
			z++;
		}
		f.flush();
		f.close();
		return corefarray;
	
	
	}
	
	
	String[][] reduceCorefs(String[][] corefs, String[] first_names, String[] last_names, int[] freq) throws IOException
	{
		int i, j, k, l=0, hit=0;
		String[][] reduced_corefs = new String[corefs.length][10];
		FileWriter f = new FileWriter(mypath+"test/"+book_name+"-reduced-corefs.txt");
		
		//System.out.println(corefs[1][8].equalsIgnoreCase(first_names[1]));
		System.out.println("corefs length: "+corefs.length);
		
		for(i=0; i<first_names.length;i++)
		{
			if(last_names[i].equals("#"))
			{
			    for(j=0;j<corefs.length;j++)
				{
					if(first_names[i].equalsIgnoreCase(corefs[j][8]) || first_names[i].equalsIgnoreCase(corefs[j][9]) && !(corefs[j][1].equals("$$@@##")) )
					{
						for(k=0;k<10;k++)
						{
							reduced_corefs[l][k] = corefs[j][k];
							corefs[j][k] = "$$@@##";
							
						
						}
						f.write(reduced_corefs[l][8]+" "+reduced_corefs[l][9]+"\r\n");
						l++;
					}
				}
			}
			
			
		}
		String[][] new_corefs = new String[l][10];
		for(i=0;i<l;i++)
		{
			for(j=0;j<10;j++)
			{
				new_corefs[i][j]=reduced_corefs[i][j];
			}
		}
		
		Pattern p = Pattern.compile("\\b[A-Z][a-z\\p{L}\\-']+\\s(\\b[A-Z][a-z\\p{L}\\-']+\\s*)+");
		FileWriter f2 = new FileWriter(mypath+book_name+"coref-tst.txt");
		//beware of special characters, supported: ü, ñ
		//String str = "Eva Köppen hello what Chadé Severin";
		for(i=0;i<l;i++)
		{
		
			Matcher m = p.matcher(new_corefs[i][9]);
			int matches=0;
			if((!new_corefs[i][8].equalsIgnoreCase(new_corefs[i][9])) &&  !(new_corefs[i][8].equalsIgnoreCase("he")) && !(new_corefs[i][8].equalsIgnoreCase("she")) )
			{
			
			while(m.find())
			{
				new_corefs[i][9] = "";
				new_corefs[i][9] = new_corefs[i][9].trim() +" "+ (m.group(0)).trim(); //for multiple names in one coref
				//System.out.println(new_corefs[i][9]+" matched: |"+m.group(0)+"|");
				//f2.write(new_corefs[i][9]+" matched: |"+m.group(0)+"|\r\n");
				matches++;
			}
			new_corefs[i][9] = new_corefs[i][9].trim();
			if(matches==0)
			{
				new_corefs[i][9] = new_corefs[i][9].replaceAll("[#|$]","");
				new_corefs[i][9] = (new_corefs[i][9].replaceAll("\\b[a-z]+","")).trim();
				new_corefs[i][9] = new_corefs[i][9].replaceAll("\\s\\s\\s*"," ");
				
			}
			}
		}
		for(i=0;i<l;i++)
		{
			f2.write(new_corefs[i][8]+"|"+new_corefs[i][9]+"\r\n");
		}
		//System.out.println("no match");
		f.flush();
		f.close();
		f2.flush();
		f2.close();
		return new_corefs;
	}
		
		


}

