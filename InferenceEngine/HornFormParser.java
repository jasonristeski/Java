package InferenceEngine;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HornFormParser 
{
	private String fFile;
	private ArrayList<PropositionID> fPID;
	private PropositionID fQuery;
	private ArrayList<PropositionSentence> fSymbol;
	
	/**
	 * Parser constructor
	 * @param aFileInput String File
	 */
	public HornFormParser(String aFileInput)
	{
		fFile = aFileInput;
		fPID = new ArrayList<PropositionID>();
		fSymbol = new ArrayList<PropositionSentence>();
	}
	
	/**
	 * Reads file and parses appropriate to HornForm 
	 * @param aKB KnowledgeBase
	 */
	public void parseFile(KnowledgeBase aKB)
	{
		try
		{
			FileReader lFile = new FileReader(fFile);
			BufferedReader lReader = new BufferedReader(lFile);
			lReader.readLine(); // TELL;
			String lTell = lReader.readLine().toLowerCase().trim();
			lReader.readLine(); // ASK
			
			String lQuery = lReader.readLine().toLowerCase();
			fQuery = new PropositionID(lQuery);
			String[] lPropositionSymbols = lTell.split(";");
			// Horn form symbols, can add more for different symbols.
			String regex = "&|([=>])";
			
			for(String p : lPropositionSymbols)
			{
				String[] lID = p.split(regex);
				parseSymbol(extractWhiteSpace(lID));
			}
			for(int i = 0; i < fSymbol.size();i++)
			{
				aKB.tell(fSymbol.get(i));
			}
			lReader.close();
		
		}
		
		catch(FileNotFoundException ex)
		{
			System.out.println("File" + fFile + "cannot be found..");
			System.exit(1);
		}
		catch(IOException e)
		{
			System.out.println("Error reading file" +fFile);
			System.exit(1);
		}	
	}
	
	
	private void parseSymbol(String[] aProp)
	{
		// Literal
		if(aProp.length == 1)
		{
			fPID.add(new PropositionID(aProp[0]));
			fSymbol.add(new Literal(fPID.get(fPID.size() -1)));
		}
		// Simple implication
		else if (aProp.length == 2)
		{
			fPID.add(new PropositionID (aProp[1]));
			fPID.add(new PropositionID(aProp[0]));
			fSymbol.add(new Implication(fPID.get(fPID.size() -1),fPID.get(fPID.size() -2)));
			
		}
		else
		{
			// TODO: n conjunction to implication
			fPID.add(new PropositionID(aProp[2]));
			fPID.add(new PropositionID(aProp[1]));
			fPID.add(new PropositionID(aProp[0]));
			
			fSymbol.add(new Implication(new Conjunction(fPID.get(fPID.size() -1),fPID.get(fPID.size() -2)),fPID.get(fPID.size() -3)));		
			
		}
	}
	
	/**
	 * Returns ASK proposition
	 * @return PropositionID Query
	 */
	public PropositionID getQuery()
	{
		return fQuery;
	}
	
	private String[] extractWhiteSpace(String[] aString)
	{
		 List<String> lResult = new ArrayList<String>();
		 for(int i = 0; i < aString.length;i++)
		 {
			 if(!(aString[i].equals("") || aString[i].equals(" ")))
				 lResult.add(aString[i].trim());
		 }
		 return lResult.toArray(new String[lResult.size()]);
	}
}
