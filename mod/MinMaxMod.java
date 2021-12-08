package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinMaxMod {
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
			   return new Pair(null , MODeval(x));
		   }
		   List <String> moves = getMovesMod(x, '2');
		   
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
			   return new Pair(null , MODeval(x));
		   }
		   List <String> moves = getMovesMod(x, '1');
		   
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
	private String[] getRows(String x) {
		String [] a = x.split("#");
		String[] b = new String[a.length];
		for(int i = 0; i < a.length; i++) {
			b[i] = a[a.length - i - 1];
		}
		return b;
	}
 	public List<String> getMovesMod(String x, char c){
		List  <String> moves = new ArrayList<>();
		String[] a = getRows(x);
		
		int n = a.length, m = a[0].length();
		for(int j = 0; j < m; j++) {
			int i = n - 1;
			while(i >= 0 && a[i].charAt(j) != '0') i--;
			if (i < 0) continue;
			char[] temp = a[i].toCharArray();
			temp[j] = c;
			String newline = new String(temp);
			String newMove = "";
			for(int k = n - 1; k > 0; k--) {
				if (k == i) newMove+= newline;
				else newMove += a[k];
				newMove += "#";
			}
			if (i == 0) newMove += newline;
			else newMove += a[0];
			moves.add(newMove);
		}
		return moves;
	}
	private String[] getdiagonals(String[] a) { 
				int n = a.length, m = a[0].length();
				String [] diag = new String[(n + m - 1) * 2];
		       int index =0;
		       for(int k = 0; k < m; k++) {
		       	int i = 0, j = k;
		       	diag[index] = "";
		       	while(i < n && j < m) {
		       		diag[index] += a[i].charAt(j);
		       		i++; j++;
		       	}
		       	index++;
		       }
		       for(int k = 1; k < n; k++) {
		       	int i = k, j = 0;
		       	diag[index] = "";
		       	while(i < n && j < m) {
		       		diag[index] += a[i].charAt(j);
		       		i++; j++;
		       	}
		       	index++;
		       }
		       for(int k = 0; k < n; k++) {
		       	int i = k, j = 0;
		       	diag[index] = "";
		       	while(i >=0 && j < m) {
		       		diag[index] += a[i].charAt(j);
		       		i--; j++;
		       	}
		       	index++;
		       }
		       for(int k = 1; k < m; k++) {
		       	int i = n - 1, j = k;
		       	diag[index] = "";
		       	while(i >= 0 && j < m) {
		       		diag[index] += a[i].charAt(j);
		       		i--; j++;
		       	}
		       	index++;
		       }
		       return diag;
	}
	public String[] getcolumn(String [] a) {
		String[] col = new String[a[0].length()];
	       
	       for(int j=0; j<col.length; j++) {
	       	for(int i=0; i<a.length; i++) {
	       		col[j]+=a[i].charAt(j);
	       	}
	       }
	       return col;
	}
	private double MODeval(String x) {
		String [] a = getRows(x);
		
		int threes =0 ,fours = 0;
		
       String[] diag = getdiagonals(a);
        // rows
       for(int i=0; i< a.length; i++) {
        threes+=calcThrees(a[i],'1');
        fours+=calcFours(a[i],'1');    

        threes-=calcThrees(a[i],'2');
        fours-=calcFours(a[i],'2');    

       }
       
       // cols
       String[] col = getcolumn(a);
       for(int i = 0; i < col.length; i++) {
    	   threes+=calcThrees(col[i],'1');
           fours+=calcFours(col[i],'1');    

          
           threes-=calcThrees(col[i],'2');
           fours-=calcFours(col[i],'2');
       }
       
       
    // diag
       
       for(int i=0; i<diag.length; i++) {
           threes+=calcThrees(diag[i],'1');    	
           fours+=calcFours(diag[i],'1');    

           threes-=calcThrees(diag[i],'2');    
           fours-=calcFours(diag[i],'2');    
       
       }
               
		return fours + threes / 2;
	}
	int scoreCalc(String x , char c) {
		String [] a = getRows(x);
		
		int fours = 0;
		
       String[] diag = getdiagonals(a);
        // rows
       for(int i=0; i< a.length; i++) {
        fours+=calcFours(a[i],c);

       }
       
       // cols
       String[] col = getcolumn(a);
       for(int i = 0; i < col.length; i++) {
           fours+=calcFours(col[i],c);
       }
       
       
    // diag
       
       for(int i=0; i<diag.length; i++) {
           fours+=calcFours(diag[i],c);
       }
               
		return fours;
	}
	private int calcThrees(String x ,char c) {
		int threes =0;
       threes += MOdcountOcuurence( x ,"0"+c+c+c);
       threes += MOdcountOcuurence( x ,""+c+"0"+c+c);
       threes += MOdcountOcuurence( x ,""+c+c+"0"+c); 
       threes += MOdcountOcuurence( x ,""+c+c+c+"0");
		return threes;
	}	
	
	private int calcFours(String x , char c) {
		return MOdcountOcuurence(x,""+c+c+c+c);
	}
	public int MOdcountOcuurence(String str , String findStr ) {

		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){

		    lastIndex = str.indexOf(findStr,lastIndex);

		    if(lastIndex != -1){
		        count ++;
		        lastIndex++;
		    }
		}
		return count;
		
	}
	
}
