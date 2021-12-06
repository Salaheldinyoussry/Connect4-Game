package test;

import java.util.ArrayList;
import java.util.List;

public class Minmax {
     private class Pair {
    	 public String state;
    	 public double val;
    	 Pair(String state, double val){
    		 this.state=state;
    		 this.val=val;
    	 }
     }
	public String getNextMove(String state, int depth,Boolean alphaBeta) {
		return max(state,-999999999,999999999,depth,  alphaBeta).state;
	}
	
	private Pair min(String x , double a , double b, int depth ,Boolean alphaBeta) {
		   if(depth ==0) {
			   return new Pair(null , eval(x));
		   }
		   List <String> moves = getMoves(x);
		   
		    Pair curr = new Pair(null ,999999999);
		   for( String move : moves) {
			   Pair p = max(move , a ,b , depth-1 , alphaBeta);
			   if(p.val<curr.val) {
				   curr.state=move;
				   curr.val=p.val;
			   }
			   if(alphaBeta && curr.val<= a)
				   break;
			   if(curr.val < b)
				   b=curr.val;
		   }
	return curr;
	}
	
	private Pair max(String x , double a , double b, int depth , Boolean alphaBeta) {
		   if(depth ==0) {
			   return new Pair(null , eval(x));
		   }
		   List <String> moves = getMoves(x);
		   
		    Pair curr = new Pair(null ,-999999999);
		   for( String move : moves) {
			   Pair p = min(move , a ,b , depth-1, alphaBeta);
			   if(p.val>curr.val) {
				   curr.state=move;
				   curr.val=p.val;
			   }
			   if(alphaBeta && curr.val>= b)
				   break;
			   if(curr.val >a)
				   a=curr.val;
		   }
	return curr;
	}
	
	///
	// 2 1 0
	// 1 1 1
	public List<String> getMoves(String x){
		List  <String> moves = new ArrayList<>();
		String [] a = x.split("#");
		System.out.println(x);
		for(int i=0; i<a.length; i++) {
			System.out.println(a[i] +"  "+a.length);
		}
		for(int i=0; i< a[0].length(); i++) {
			int j=a.length-1;
			while(j>=0 && a[j].charAt(i)=='0')
				j--;
	
			j++;
			if(j >=6 || a[j].charAt(i)!='0')
				continue;
			
		    String r1 = a[0];
		    String r2 = a[1];
		    String r3 = a[2];
		    String r4 = a[3];
		    String r5 = a[4];
		    String r6 = a[5];
		    
		    String[] b = {r1,r2,r3,r4,r5,r6};
		    char []  temp = b[j].toCharArray();
		    temp[i]='1';
		    b[j]=new String(temp);
		    
		    moves.add(""+b[0]+"#" + b[1]+"#"+b[2]+"#"+b[3]+"#"+b[4]+"#"+b[5]);
			
		}
		System.out.println("moves: ");
		for(int i=0; i<moves.size(); i++) {
			System.out.println(moves.get(i) +"  "+a.length);
		}
		
		return moves;
	}
	
	
	
	private double eval(String x) {
		String [] a = x.split("#");
		
		int ones =0 , twoes=0 , threes =0 ,fours = 0;
		
        // diagonals
        String d1,d2,d3,d4,d5,d6,d7;
		d1=""+a[2].charAt(0)+ a[3].charAt(1) +a[4].charAt(2)+a[5].charAt(3);
        
        d2=""+a[1].charAt(0)+ a[2].charAt(1) +a[3].charAt(2)+ a[4].charAt(3)+  a[5].charAt(4);

        d3=""+a[0].charAt(0)+ a[1].charAt(1) +a[2].charAt(2)+ a[3].charAt(3)+  a[4].charAt(4) + a[5].charAt(5);
        
        d4=""+a[0].charAt(0)+ a[1].charAt(1) +a[2].charAt(2)+ a[3].charAt(3)+  a[4].charAt(4) + a[5].charAt(5);

        d5=""+a[0].charAt(1)+ a[1].charAt(2) +a[2].charAt(3)+ a[3].charAt(4)+  a[4].charAt(5) + a[5].charAt(6) ;
        
        d6=""+a[0].charAt(2)+ a[1].charAt(3) +a[2].charAt(4)+ a[3].charAt(5)+  a[4].charAt(6)  ;

        d7 =""+a[0].charAt(3)+ a[1].charAt(4) +a[2].charAt(5)+ a[3].charAt(6) ;
        
        String [] diag = {d1,d2,d3,d4,d5,d6,d7};
        
         // rows
        for(int i=0; i< a.length; i++) {
        ones+=calcOnes(a[i] ,'1');          
         twoes += calcTwoes(a[i],'1');
         threes+=calcThrees(a[i],'1');
         fours+=calcFours(diag[i],'1');    


         ones-=calcOnes(a[i] ,'2');          
         twoes -= calcTwoes(a[i],'2');
         threes-=calcThrees(a[i],'2');
         fours-=calcFours(diag[i],'2');    

        }
        
        // cols

        for(int i=0; i<a[0].length(); i++) {
        	String col ="";
        	for(int j=0; j<a.length; j++) {
        		col+=a[j].charAt(i);
        	}
            ones+=calcOnes(col,'1');          
            twoes += calcTwoes(col,'1');
            threes+=calcThrees(col,'1');
            fours+=calcFours(diag[i],'1');    

           
            ones-=calcOnes(col,'2');          
            twoes -= calcTwoes(col,'2');
            threes-=calcThrees(col,'2');
            fours-=calcFours(diag[i],'2');    

        }
        
     // diag
        
        for(int i=0; i<diag.length; i++) {
            ones+=calcOnes(diag[i],'1');          
            twoes += calcTwoes(diag[i],'1');
            threes+=calcThrees(diag[i],'1');    	
            fours+=calcFours(diag[i],'1');    

            ones-=calcOnes(diag[i],'2');          
            twoes -= calcTwoes(diag[i],'2');
            threes-=calcThrees(diag[i],'2');    
            fours-=calcFours(diag[i],'2');    
        
        }
                
		return ones+ twoes*5 + threes*10;
	}
	
	private int calcOnes(String x , char c) {
		int ones =0;
        ones+= countOcuurence( x ,""+c+"000");
        ones+= countOcuurence(x ,"0"+c+"00");
        ones+= countOcuurence(x,"00"+c+"0");
        ones+= countOcuurence(x,"000"+c);
		return ones;
	}
	
	private int calcTwoes(String x,char c) {
		int twoes =0;
        twoes += countOcuurence( x ,""+c+"00"+c);
        twoes += countOcuurence( x ,""+c+"0"+c+"0");
        twoes += countOcuurence( x ,c+c+"00");
        twoes += countOcuurence( x ,"00"+c+c);
        twoes += countOcuurence( x ,"0"+c+"0"+c);
        twoes += countOcuurence( x ,"0"+c+c+"0");
		return twoes;
	}
	private int calcThrees(String x ,char c) {
		int threes =0;
        threes += countOcuurence( x ,""+c+c+c+"0");
        threes += countOcuurence( x ,"0"+c+c+c);
        threes += countOcuurence( x ,""+c+"0"+c+c);
        threes += countOcuurence( x ,""+c+"0"+c+c); 
		return threes;
	}	
	
	private int calcFours(String x , char c) {
		return countOcuurence(x,""+c+c+c+c);
	}
	
	int scoreCalc(String x , char c) {
		String [] a = x.split("#");
        int score = 0;
        
        // rows
        score+=lineScore(a[0],c);
        score+=lineScore(a[1],c);
        score+=lineScore(a[2],c);
        score+=lineScore(a[3],c);
        score+=lineScore(a[4],c);
        score+=lineScore(a[5],c);
        
        // columns 
        
        for(int i=0; i<a[0].length(); i++) {
        	String col ="";
        	for(int j=0; j<a.length; j++) {
        		col+=a[j].charAt(i);
        	}
        	score+=lineScore(col,c);
        }
        
        // diagonals
        
        score+=lineScore(""+a[2].charAt(0)+ a[3].charAt(1) +a[4].charAt(2)+a[5].charAt(3)   ,c);
      
        score+=lineScore(""+a[1].charAt(0)+ a[2].charAt(1) +a[3].charAt(2)+ a[4].charAt(3)+  a[5].charAt(4) ,c);

        score+=lineScore(""+a[0].charAt(0)+ a[1].charAt(1) +a[2].charAt(2)+ a[3].charAt(3)+  a[4].charAt(4) + a[5].charAt(5) ,c);
        
        score+=lineScore(""+a[0].charAt(0)+ a[1].charAt(1) +a[2].charAt(2)+ a[3].charAt(3)+  a[4].charAt(4) + a[5].charAt(5) ,c);

        score+=lineScore(""+a[0].charAt(1)+ a[1].charAt(2) +a[2].charAt(3)+ a[3].charAt(4)+  a[4].charAt(5) + a[5].charAt(6) ,c);
        
        score+=lineScore(""+a[0].charAt(2)+ a[1].charAt(3) +a[2].charAt(4)+ a[3].charAt(5)+  a[4].charAt(6)  ,c);

        score+=lineScore(""+a[0].charAt(3)+ a[1].charAt(4) +a[2].charAt(5)+ a[3].charAt(6)  ,c);


        return score;
		
	}
	private int lineScore(String x, char c) {
		
		int ans=0;
		if(x.contains(""+c+c+c+c+c+c+c)) 
			ans=4;
		else if (x.contains(""+c+c+c+c+c+c))
			ans+=3;
		else if (x.contains(""+c+c+c+c+c))
			ans+=2;
		else if (x.contains(""+c+c+c+c))
			ans+=1;
	
	   return ans;
	}
	
	public int countOcuurence(String str , String findStr ) {

	int lastIndex = 0;
	int count = 0;

	while(lastIndex != -1){

	    lastIndex = str.indexOf(findStr,lastIndex);

	    if(lastIndex != -1){
	        count ++;
	        lastIndex += findStr.length();
	    }
	}
	return count;
	
}
	
}
