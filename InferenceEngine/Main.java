package InferenceEngine;


public class Main {

	public static void main(String[] args)
	{
		if(args.length != 2 )
		{
			System.out.println("Invalid number of arguments...Aborting...");
			System.exit(-1);
		}
		
		KnowledgeBase kb = new KnowledgeBase();
		HornFormParser parser = new HornFormParser(args[1]);
		parser.parseFile(kb);
		PropositionID lQuery = parser.getQuery();
		
		
		InferenceMethod lMethod;
		switch(args[0].toUpperCase())
		{
		
			case "FC":
			{
				lMethod = new FowardChain(kb,lQuery);
				System.out.println(lMethod.entails() ? lMethod.getResult() : "NO");
				break;
			}
			case "BC":
			{
				lMethod = new BackwardChain(kb,lQuery);
				System.out.println(lMethod.entails() ? lMethod.getResult() : "NO");
				break;
			}
			case "TT":
			{
				lMethod = new TruthTable(kb,lQuery);
				System.out.println(lMethod.entails() ? lMethod.getResult() : "NO");
				break;
			}
			default:
			{
				System.out.println("Method not implemented " + args[0].toUpperCase() + " ...Aborting" );
				System.exit(1);
			}
		}
	}
}
