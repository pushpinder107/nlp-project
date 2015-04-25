import java.util.regex.*;
import java.util.*;
import java.io.*;

public class ResolveCorefs
{
	public String[][] reduced_corefs;
	public String[] unique_first_names, unique_last_names;
	public int[] freq;
	public String mypath, book_name;

	public ResolveCorefs(String mpth, String b_n, String[][]redcrfs, String[] ufns, String[] ulns, int[] frq)
	{
		mypath = mpth;
		book_name = b_n;
		reduced_corefs = redcrfs;
		unique_first_names = ufns;
		unique_last_names = ulns;
		freq = frq;
	}
	
	void resolve()
	{
		int[] hitl = new int[unique_first_names.length];
		int[] hitf = new int[unique_first_names.length];
		int i,j,k=0;
		for(i=0;i<unique_first_names.length;i++)
		{
			if(unique_last_names[i].equals("#"))
			{
				for(j=0;j<unique_first_names.length;j++)
				{
					if(unique_last_names[j].equalsIgnoreCase(unique_first_names[i]))
					{
						hitl[i]++;
						k++;
						System.out.println(unique_first_names[i]+" is in :"+unique_first_names[j]+" "+unique_last_names[j]);
						for(j=0;j<unique_first_names.length;j++)
						{
							if(i!=j)
							{
								if(unique_first_names[j].equalsIgnoreCase(unique_first_names[i]))
								{
									hitf[i]++;
									k++;
									System.out.println(unique_first_names[i]+" is also first name of "+unique_first_names[j]+" "+unique_last_names[j]);
								}
							}	
					
						
						}
					
					
					}
				
				}
						
			
			
					
			}
	
		}
	
	}
	
	
		
	
	
	
	
	
}