import java.util.regex.*;
import java.util.*;
import java.io.*;

public class ResolveCorefs
{
	public String[][] reduced_corefs;
	public String[] unique_first_names, unique_last_names;
	public int[] freq, new_freq;
	public String mypath, book_name;

	public ResolveCorefs(String mpth, String b_n, String[][]redcrfs, String[] ufns, String[] ulns, int[] frq)
	{
		mypath = mpth;
		book_name = b_n;
		reduced_corefs = redcrfs;
		unique_first_names = ufns;
		unique_last_names = ulns;
		freq = frq;
		int i;
		new_freq = new int[freq.length];
		for(i=0;i<freq.length;i++)
			new_freq[i] = freq[i];
	}
	
	void resolve() throws IOException
	//possible problem with last name with spaces eg Van Daan
	{
		FileWriter f = new FileWriter(mypath+"test/"+book_name+"-freq-changes.txt");
		
		int hl=0, hf=0, x=0, y=0, i, j, k=0, l, hlf, z=0;
		
		for(i=0;i<unique_first_names.length;i++)
		{
			if(unique_last_names[i].equals("#")) //incomplete name in list
			{
				hf=0;// to check matching first names
				hl=0;// to check matching last names
				x=0;
				for(j=0;j<unique_first_names.length;j++)
				{
					if(unique_last_names[j].equalsIgnoreCase(unique_first_names[i]))// if incomplete name is part of other last name
					{
						hl = hl+1;
						x = j;
					}
				}
				
				if(hl == 1)//incomplete name is part of only one last name
				{
					y=0;
					
					f.write(unique_first_names[i]+"["+freq[i]+"]"+" is last name of :"+unique_first_names[x]+" "+unique_last_names[x]+"["+freq[x]+"]\r\n");
					//System.out.println(unique_first_names[i]+"["+freq[i]+"]"+" is last name of :"+unique_first_names[x]+" "+unique_last_names[x]+"["+freq[x]+"]");
					for(k=0;k<unique_first_names.length;k++)
					{
						if(k != i)// to avoid self match
						{
							if(unique_first_names[i].equalsIgnoreCase(unique_first_names[k]))//incomplete name is also someone's first name
							{	
								f.write(unique_first_names[i]+"["+freq[i]+"]"+" is also the first name of: "+unique_first_names[k] +" " +unique_last_names[k]+"["+freq[k]+"]\r\n");
								//System.out.println(unique_first_names[i]+" is also the first name of: "+unique_first_names[k] +" " +unique_last_names[k]);
								hf = hf + 1;
								y = k;
							}
							
						}
					}
					if(hf == 0)// incomplete name is not a first name, just a last name
					{
						f.write(unique_first_names[i]+"["+freq[i]+"]"+" and "+unique_first_names[x]+" "+unique_last_names[x]+"["+freq[x]+"]"+" are interchangeable\r\n");
						//System.out.println(unique_first_names[i]+" and "+unique_first_names[x]+" "+unique_last_names[x]+" are interchangeable");
						new_freq[x] = new_freq[x] + new_freq[i];
						new_freq[i] = 0;
					}
					else
					{
						f.write(unique_first_names[i]+"["+freq[i]+"]"+" and "+unique_first_names[x]+" "+unique_last_names[x]+"["+freq[x]+"]"+" are not interchangeable\r\n");
						//System.out.println("*"+unique_first_names[i]+" and "+unique_first_names[x]+" "+unique_last_names[x]+" are not interchangeable");
					}


					
				}//match found for unique last name
				else if(hl == 0)//incomplete name is not anyone's last name
				{
					hlf = 0;
					z = 0;
					f.write("#"+unique_first_names[i]+"["+freq[i]+"]"+" is not anyone's last name\r\n");
					//System.out.println("#"+unique_first_names[i]+" is not anyones last name");
					for(l=0;l<unique_first_names.length;l++)
					{
						if(i != l)
						{
							if(unique_first_names[i].equalsIgnoreCase(unique_first_names[l]))
							{
								hlf = hlf +1;
								z = l;
								f.write("@ "+unique_first_names[i]+"["+freq[i]+"]"+" is the first name of :"+unique_first_names[l]+" "+unique_last_names[l]+"\r\n");
							}
						}
					}
					if(hlf == 1)
					{
						f.write("$ "+unique_first_names[i]+"["+freq[i]+"]"+" is interchangeable with: "+unique_first_names[z]+" "+unique_last_names[z]+"["+freq[z]+"]\r\n");
						//System.out.println("$"+unique_first_names[i]+" is interchangeable with: "+unique_first_names[z]+" "+unique_last_names[z]);
						new_freq[z] = new_freq[z] + new_freq[i];
						new_freq[i] = 0;
					}
					else
					{
						f.write("$ "+unique_first_names[i]+"["+freq[i]+"]"+" is not interchangeable\r\n");
					}
				}
				
				
				
						
			
			
					
			}
	
		}
		FileWriter f1 = new FileWriter(mypath+"test/"+book_name+"-updated-freq.txt");
		for(i=0;i<new_freq.length;i++)
		{
			f1.write(unique_first_names[i]+" "+unique_last_names[i]+": old freq = "+freq[i]+"  new freq = "+new_freq[i]+"\r\n");
		}
		f1.flush();
		f1.close();
		
		
		f.flush();
		f.close();
		
	
	}
	
	void printFreqChanges() throws IOException 
	{
		int i,count=0;
		FileWriter f = new FileWriter(mypath+"test/"+book_name+"-new-freq.txt");
		for(i=0;i<freq.length;i++)
		{
			if(new_freq[i]!=0)
			{
				f.write(unique_first_names[i]+" "+unique_last_names[i]+" new_freq= "+new_freq[i]+" old freq= "+freq[i]+"\r\n");
				count++;
			}
		}
		f.write("Non trivial entitites left = "+count+"\r\n");
		System.out.println(freq.length-count+" trivial mentions resolved, entities left = "+count);
		f.flush();
		f.close();
	}
	
	void resolve_final()
	{
		int i,j,k;
		ArrayList<String> complete_names = new ArrayList<String>();
		ArrayList<String> incomplete_names = new ArrayList<String>();
		String incomplete, complete;
		for(i=0;i<freq.length;i++)
		{
			if(unique_last_names[i].equalsIgnoreCase("#") && new_freq[i]!=0)
			{
				incomplete_names.add(unique_first_names[i]);
				//System.out.println(incomplete+" "+unique_last_names[i]+" is an incomplete mention");
				
			}
			else if(!(unique_last_names[i].equalsIgnoreCase("#")) && new_freq[i]!=0)
			{
				complete_names.add(unique_first_names[i]+" "+unique_last_names[i]);
				//complete = unique_first_names[i]+" "+unique_last_names[j];
			}
		}
		String[] inc_names = incomplete_names.toArray( new String[incomplete_names.size()]);
		String[] comp_names = complete_names.toArray( new String[complete_names.size()]);
		
		for(i=0;i<inc_names.length;i++)
		{
			for(j=0;j<comp_names.length;j++)
			{
				for(k=0;k<reduced_corefs.length;k++)
				{
					if(reduced_corefs[k][8].equalsIgnoreCase(inc_names[i]) && reduced_corefs[k][9].equalsIgnoreCase(comp_names[j]))
					{
						System.out.println(inc_names[i]+" matches: "+comp_names[j]+" in: "+reduced_corefs[k][8]+" "+reduced_corefs[k][9]+" "+k);
					}
				}
			}
		}
			
		
		
		
	}
	
	int[] getNewFreq()
	{
		return new_freq;
	}
	
	
		
	
	
	
	
	
}