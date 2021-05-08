

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class checking_things {

	public static Integer [][] replace_up (Integer [][] temp,int row,int col){
		Integer [][] replace =new Integer [temp.length][temp[0].length];
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[0].length;j++) {
				if (i==row && j==col) {
					replace[i][j]=temp[i+1][j];

				}else if(i==row+1 && j==col) {
					replace[i][j]=null;
				}
				else {
					replace[i][j]=temp[i][j];

				}
			}
		}
		return replace;
	}

	public static Integer [][] replace_down (Integer [][] temp,int row,int col){
		Integer [][] replace =new Integer [temp.length][temp[0].length];
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[0].length;j++) {
				if (i==row && j==col) {
					replace[i][j]=temp[i-1][j];
				}
				else if(i==row-1 && j==col) {
				replace[i][j]=null;
				}
				else {
				replace[i][j]=temp[i][j];
				}
				
			}
		}
		return replace;
	}

	public static Integer [][] replace_right (Integer [][] temp,int row,int col){
		Integer [][] replace =new Integer [temp.length][temp[0].length];
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[0].length;j++) {
				if (i==row && j==col) {
					replace[i][j]=temp[i][j-1];
				}
				else if(i==row && j==col-1) {
				replace[i][j]=null;
				}
				else {
				replace[i][j]=temp[i][j];
				}
			}
		}
		return replace;
	}


	public static Integer [][] replace_left (Integer [][] temp,int row,int col){
		int tmp=temp[row][col+1];
		temp[row][col+1]=null;
		temp[row][col]=tmp;
		
		
//		Integer [][] replace =new Integer [temp.length][temp[0].length];
//		for(int i=0;i<temp.length;i++) {
//			for(int j=0;j<temp[0].length;j++) {
//					if (j+1<temp[0].length && i==row && j==col) {
//						replace[i][j]=temp[i][j+1];
//					}
//					else if(i==row && j==col+1) {
//					replace[i][j]=null;
//					}
//					else {
//					replace[i][j]=temp[i][j];
//					}
//				}
//			}
		
		return temp;
	}

	 static int heuristicFunction(Integer [][] currentState, Integer[][] goalState) {
		int manhattanDistance = 0;
		for (int c = 0; c < goalState.length; c++) {
			for (int d = 0; d < goalState[0].length; d++) {
				for (int a = 0; a < currentState.length; a++) {
					for (int b = 0; b < currentState[0].length; b++) {
						if (currentState[a][b] == goalState[c][d] && currentState[a][b]!=null ) {
							manhattanDistance+=Math.abs(a-c)+Math.abs(b-d);
						}
					}

				}
			}
		}
		return manhattanDistance;  
	
	 }
	
	
	
	public static void main(String[] args) {
		Integer []N= {1,2,3,4};
		
		int j=0;
		while(!(N[j]==2)) {
			j++;
		}
		while(j<N.length) {
			N[j]=null;
			j++;
		}
		
		
		// TODO Auto-generated method stub

		//HashMap<String, Node> ans=new HashMap<>();
//		  Integer[][]ans6={{1,2,3,4},{5,null,6,10},{9,null,7,8}};
//		  
//		  Integer[][]ans7=replace_up_double(ans6,1,1,1,2);
//		  
		for(int i=0;i<N.length;i++) {
			System.out.println(N[i]);
		}
		System.out.println();
		
		
		  HashMap<Integer,String> map = new HashMap<>();
		  map.put(1, "a");
		  map.put(1,"b");
//		  Node currentNode = new Node(ans6);
//		  Integer[][]ans7={{1,2,9},{4,5,6},{7,8,9}};
//		  Node currentNode2 = new Node(ans7);
//		Stack<Node>st=new Stack<>();
//		st.add(currentNode);
//		st.remove(currentNode);
//		ans.put(currentNode.toString(), currentNode);
//		Node currentNode3=ans.get(currentNode.toString());
//		System.out.println(currentNode3.toString());
//		System.out.println(ans.containsKey(currentNode.toString()));
//		System.out.println(ans.containsKey(currentNode2.toString()));

//		  Set<String> stateSets = new HashSet<>();
//		  
//		  Integer[][]ans6={{1,2,3},{4,5,6},{7,8,9}};
//		  
//		  Node currentNode = new Node(ans6);
//		  Node curr2 = new Node(ans6);
//		  stateSets.add(currentNode.toString());
//		  System.out.println(stateSets.contains(curr2.toString()));

//		String check="shani";
//		System.out.println(check.contains("y"));

//	Integer [][]arr1= {{8,1,3},{4,null,2},{7,6,5}};
//	Integer [][]arr2= {{1,2,3},{4,5,6},{7,8,null}};
//	System.out.println(heuristicFunction(arr1,arr2));
		
		
//		Integer [][] state= {{1,2,3,4},{5,null,11,10},{9,null,7,8}};
//		Integer [][] ans=new Integer [state.length][state[0].length];;
//		for(int i=0;i<state.length;i++) {
//			for ( int j=0;j<state[i].length;j++) {
//				if (state[i][j] == (Integer) null ) {
//					
//					if(i+1<state.length && state[i+1][j]==null ) {
//						ans=replace_left_double(state,i,j,i+1,j);
//						
//						String move = state[i][j+1] + "&"+ state[i+1][j+1];
//						move+="L";
//						System.out.println(move);
//
//					}
//		

//					
//					
//
//				}
//			}
//		}
//		for(int i=0;i<ans7.length;i++) {
//			for ( int j=0;j<ans7[i].length;j++) {
//				System.out.print(ans6[i][j]+" ");
//			}
//			System.out.println();
//		}
	}



	
	private static Integer [][] replace_up_double(Integer[][] temp, int row_1, int col_1, int row_2, int col_2) {
		int tmp=temp[row_1+1][col_1];
		temp[row_1][col_1]=tmp;
		temp[row_2][col_2]=temp[row_2+1][col_2];
		temp[row_1+1][col_1]=null;
		temp[row_2+1][col_2]=null;
		
		return temp;
		
//		Integer [][] replace =new Integer [temp.length][temp[0].length];
//		for(int i=0;i<temp.length;i++) {
//			for(int j=0;j<temp[0].length;j++) {
//				if(i==row_1 && j==col_1) {
//					replace[i][j]=temp[row_1+1][col_1];
//				}
//				else if(i==row_2 && j==col_2) {
//					replace[i][j]=temp[row_2+1][col_2];
//				}
//				else if((i==row_1+1 && j==col_1) || (i==row_2+1 && j==col_2)) {
//					replace[i][j]=null;
//				}
//				else {
//					replace[i][j]=temp[i][j];
//				}
//				
//			}
//		}
//		return replace;
		
	}
	
	
	private static Integer [][] replace_down_double(Integer[][] temp, int row_1, int col_1, int row_2, int col_2) {
		Integer [][] replace =new Integer [temp.length][temp[0].length];
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[0].length;j++) {
				if(i==row_1 && j==col_1) {
					replace[i][j]=temp[row_1-1][col_1];
				}
				else if(i==row_2 && j==col_2) {
					replace[i][j]=temp[row_2-1][col_2];
				}
				else if((i==row_1-1 && j==col_1) || (i==row_2-1 && j==col_2)) {
					replace[i][j]=null;
				}
				else {
					replace[i][j]=temp[i][j];
				}
				
			}
		}
		return replace;
		
	}
	
	
	private static Integer [][] replace_right_double(Integer[][] temp, int row_1, int col_1, int row_2, int col_2) {
		Integer [][] replace =new Integer [temp.length][temp[0].length];
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[0].length;j++) {
				if(i==row_1 && j==col_1) {
					replace[i][j]=temp[row_1][col_1-1];
				}
				else if(i==row_2 && j==col_2) {
					replace[i][j]=temp[row_2][col_2-1];
				}
				else if((i==row_1 && j==col_1-1) || (i==row_2 && j==col_2-1)) {
					replace[i][j]=null;
				}
				else {
					replace[i][j]=temp[i][j];
				}
				
			}
		}
		return replace;
		
	}

	private static Integer [][] replace_left_double(Integer[][] temp, int row_1, int col_1, int row_2, int col_2) {
		Integer [][] replace =new Integer [temp.length][temp[0].length];
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[0].length;j++) {
				if(i==row_1 && j==col_1) {
					replace[i][j]=temp[row_1][col_1+1];
				}
				else if(i==row_2 && j==col_2) {
					replace[i][j]=temp[row_2][col_2+1];
				}
				else if((i==row_1 && j==col_1+1) || (i==row_2 && j==col_2+1)) {
					replace[i][j]=null;
				}
				else {
					replace[i][j]=temp[i][j];
				}

			}
		}
		return replace;
	}


}
