package test;

public class main {
  
	public static void main(String[] args) {
		int K [] = {2,4,5,7 ,10};
		
		MinMaxMod m =new MinMaxMod();
		String randomState="0020000#0000000#0000000#0000000#0000000#0000000";
		for(int i=0; i<K.length; i++) {
			System.out.println("********************************");
			System.out.println("At k= "+K[i]);
			System.out.println("********");
			System.out.println("alpha and Beta prunning :");
			long start = System.currentTimeMillis();

		     m.getNextMove(randomState, K[i], true);
		    


			long end = System.currentTimeMillis();
			
			System.out.println("Running Time(ms) :" + (end-start) );
            System.out.println(" ");
			System.out.println("Without alpha and Beta prunning :");

			long start2 = System.currentTimeMillis();


			m.getNextMove(randomState, K[i], false);


			long end2 = System.currentTimeMillis();
			System.out.println("Without alpha and Beta prunning Running Time(ms) :" + (end2-start2) );
	
		System.out.println("********************************");
			
		}

	}
	
	
}
