

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Node_Operator {

	//function that print the state
	public static String State_print(Integer [][] state) {
		String ans="";
		for(int i=0;i<state.length;i++) {
			for(int j=0;j<state[0].length;j++) {
				ans+=state[i][j]+" ";
			}
			ans+="  ";
		}
		return ans;
	}


	/*all this function gets matrix, row, col that it is null and replace that 
	 * location with up/down/left/right position */
	public static Integer [][] replace_up (Integer [][] temp,int row,int col){
		int tmp=temp[row+1][col];
		temp[row+1][col]=null;
		temp[row][col]=tmp;
		return temp;


	}


	public static Integer [][] replace_down (Integer [][] temp,int row,int col){
		int tmp=temp[row-1][col];
		temp[row-1][col]=null;
		temp[row][col]=tmp;
		return temp;

	}

	public static Integer [][] replace_right (Integer [][] temp,int row,int col){
		int tmp=temp[row][col-1];
		temp[row][col-1]=null;
		temp[row][col]=tmp;
		return temp;
	}


	public static Integer [][] replace_left (Integer [][] temp,int row,int col){
		int tmp=temp[row][col+1];
		temp[row][col+1]=null;
		temp[row][col]=tmp;
		return temp;

	}




	/* this function move double up or down or left or right in horizontal*/
	private static Integer [][] replace_up_double(Integer[][] temp, int row_1, int col_1, int row_2, int col_2) {
		int tmp=temp[row_1+1][col_1];
		temp[row_1][col_1]=tmp;
		temp[row_2][col_2]=temp[row_2+1][col_2];
		temp[row_1+1][col_1]=null;
		temp[row_2+1][col_2]=null;

		return temp;

	

	}



	private static Integer [][] replace_down_double(Integer[][] temp, int row_1, int col_1, int row_2, int col_2) {

		int tmp=temp[row_1-1][col_1];
		temp[row_1][col_1]=tmp;
		temp[row_2][col_2]=temp[row_2-1][col_2];
		temp[row_1-1][col_1]=null;
		temp[row_2-1][col_2]=null;

		return temp;



		
	}


	private static Integer [][] replace_left_double(Integer[][] temp, int row_1, int col_1, int row_2, int col_2) {


		int tmp=temp[row_1][col_1+1];
		temp[row_1][col_1]=tmp;
		temp[row_2][col_2]=temp[row_2][col_2+1];
		temp[row_1][col_1+1]=null;
		temp[row_2][col_2+1]=null;

		return temp;

		
	}


	private static Integer [][] replace_right_double(Integer[][] temp, int row_1, int col_1, int row_2, int col_2) {
		int tmp=temp[row_1][col_1-1];
		temp[row_1][col_1]=tmp;
		temp[row_2][col_2]=temp[row_2][col_2-1];
		temp[row_1][col_1-1]=null;
		temp[row_2][col_2-1]=null;

		return temp;

		
	}


	/*this function will work on vertical left, right, up, down*/



	/* this function without Hashset !
	 * this function will return list of operators according to this order:
	 * 1. 2 blocks left
	 * 2. 2 blocks up 
	 * 3. 2 blocks right
	 * 4. 2 blocks down
	 * if we can't move 2 blocks 
	 * 5.left
	 * 6.up
	 * 7.right
	 * 8.down
	 * */
	public static List<Node> getOperators(Node curr, Integer[][] goal) {
		List<Node> successors = new ArrayList<Node>();

		Integer [][] curr_state =new Integer [curr.getState().length][curr.getState()[0].length];
		for(int i=0;i<curr.getState().length;i++) {
			for(int j=0;j<curr.getState()[0].length;j++) {
				curr_state[i][j]=curr.getState()[i][j];
			}
		}

		for (int i=0;i<curr.getState().length;i++) {
			for (int j=0;j<curr.getState()[i].length;j++) {
				if (curr.getState()[i][j]==null) {

					/*1. is if we can do 2 blocks left so they should be vertical*/

					/*vertical:
					 * null
					 * null*/

					if(i+1<curr.getState().length && curr.getState()[i+1][j]==null && j+1<curr.getState()[0].length) {
						replace_left_double(curr_state,i,j,i+1,j);

							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+ "&"+ curr_state[i+1][j]+"R")) {

								Node t=new Node(curr_state);
								t.setCost(6);
								//add movement 
								String move = t.getState()[i][j].toString() + "&"+ t.getState()[i+1][j].toString();
								move+="L";
								t.setMovement(move);
								t.setTotalCost(curr.getTotalCost()+t.getCost());
								successors.add(t);
							
						}

						replace_right_double(curr_state,i,j+1,i+1,j+1);
					}


					/* 2. is if we can do 2 blocks up so horizinal*/

					/*up:
					 * null null
					 * 5      6*/

					if(j+1<curr.getState()[i].length && curr.getState()[i][j+1]==null && i+1<curr.getState().length) {
						replace_up_double(curr_state,i,j,i,j+1);

							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+ "&"+ curr_state[i][j+1]+"D")) {

								Node t=new Node(curr_state);
								t.setCost(7);
								//add movement 
								String move = t.getState()[i][j].toString() + "&"+ t.getState()[i][j+1].toString();
								move+="U";
								t.setMovement(move);
								t.setTotalCost(curr.getTotalCost()+t.getCost());

								successors.add(t);
							
						}
						replace_down_double(curr_state,i+1,j,i+1,j+1);
					}


					/* 3. is if we can do 2 blocks right so vertical  */
					/*right:
					 * 6 null
					 * 5 null*/

					if(i+1<curr.getState().length && curr.getState()[i+1][j]==null && j>0 ) {
						replace_right_double(curr_state,i,j,i+1,j);

							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+ "&"+ curr_state[i+1][j]+"L")) {

								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() + "&"+ t.getState()[i+1][j].toString();
								move+="R";
								t.setMovement(move);

								t.setCost(6);
								t.setTotalCost(curr.getTotalCost()+t.getCost());

								successors.add(t);
							}
						
						replace_left_double(curr_state,i,j-1,i+1,j-1);
					}

					/* 4. 2 blocks down so horizonal */


					/*down:
					 * null null
					 * 6      6 */
					if(j+1<curr.getState()[i].length && curr.getState()[i][j+1]==null && i>0) {
						replace_down_double(curr_state,i,j,i,j+1);
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+ "&"+ curr_state[i][j+1]+"U")) {

								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() + "&"+ t.getState()[i][j+1].toString();
								move+="D";
								t.setMovement(move);

								t.setCost(7);
								t.setTotalCost(curr.getTotalCost()+t.getCost());

								successors.add(t);
							
						}
						replace_up_double(curr_state,i-1,j,i-1,j+1);
						
					}

					/* 5. Single left */
					//the locations that are in order already we won't move
					if(j+1<curr.getState()[0].length && curr.getState()[i][j+1]!=null) {
						replace_left(curr_state,i,j);
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+"R")) {

								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() ;
								move+="L";
								t.setMovement(move);

								t.setCost(5);
								t.setTotalCost(curr.getTotalCost()+t.getCost());

								successors.add(t);
							

						}
						replace_right(curr_state,i,j+1);//return to the location of current

					}

					/* 6. single up */
					if(i+1<curr.getState().length && curr.getState()[i+1][j]!=null) {
						replace_up(curr_state,i,j);
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+"D")) {
								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() ;
								move+="U";
								t.setMovement(move);
								
								t.setCost(5);
								t.setTotalCost(curr.getTotalCost()+t.getCost());
								successors.add(t);
							
						}
						replace_down(curr_state,i+1,j);

					}
					/* 7. single right*/

					if(j>0 && curr.getState()[i][j-1]!=null) {
						replace_right(curr_state,i,j);
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+"L")) {
								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() ;
								move+="R";
								t.setMovement(move);

								t.setCost(5);
								t.setTotalCost(curr.getTotalCost()+t.getCost());
								successors.add(t);
								
						}
						replace_left(curr_state,i,j-1);
					}

					/* 8. single down*/
					if(i>0 && curr.getState()[i-1][j]!=null) {
						replace_down(curr_state,i,j);
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+"U")) {
								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() ;
								move+="D";
								t.setMovement(move);

								t.setCost(5);
								t.setTotalCost(curr.getTotalCost()+t.getCost());
								successors.add(t);
							}
						
						replace_up(curr_state,i-1,j);
					}
				}
			}
		}

		return successors;
	}
	/*gets the total cost */
	public static int getCost(Node goal, Node root) {
		int totalCost=0;
		while (!check_equal(goal.getState(),root.getState())) {
			totalCost+=goal.getCost();
			goal=goal.getParent();
		}
		return totalCost;
	}


	/* gets the path */
	public static String getPath(Node goal, Node root) {
		Stack<Node> solutionStack = new Stack<Node>();
		//solutionStack.push(currentNode);
		String path="";
		solutionStack.push(goal);
		while (!check_equal(goal.getState(),root.getState())) {
			solutionStack.push(goal.getParent());
			goal=goal.getParent();

		}
		while(!solutionStack.isEmpty()) {
			Node curr1=solutionStack.pop();
			path+=curr1.getMovement();
			path+="-";
		}
		path=path.substring(1,path.length()-1);
		return path;

	}


	/* this function with Hashset !
	 * this function will return list of operators according to this order:
	 * 1. 2 blocks left
	 * 2. 2 blocks up 
	 * 3. 2 blocks right
	 * 4. 2 blocks down
	 * if we can't move 2 blocks 
	 * 5.left
	 * 6.up
	 * 7.right
	 * 8.down
	 * */
	public static List<Node> getOperators(Node curr, Integer[][] goal,HashMap <String,Node> H) {
		List<Node> successors = new ArrayList<Node>();

		Integer [][] curr_state =new Integer [curr.getState().length][curr.getState()[0].length];
		for(int i=0;i<curr.getState().length;i++) {
			for(int j=0;j<curr.getState()[0].length;j++) {
				curr_state[i][j]=curr.getState()[i][j];
			}
		}


		for (int i=0;i<curr.getState().length;i++) {
			for (int j=0;j<curr.getState()[i].length;j++) {
				if (curr.getState()[i][j]==null) {

					/*1. is if we can do 2 blocks left so they should be vertical*/

					/*vertical:
					 * null
					 * null*/

					if(i+1<curr.getState().length && curr.getState()[i+1][j]==null && j+1<curr.getState()[0].length) {
						replace_left_double(curr_state,i,j,i+1,j);
						if(!H.containsKey(State_print(curr_state))) {//if the hashset isn't contain the string

							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+ "&"+ curr_state[i+1][j]+"R")) {

								Node t=new Node(curr_state);
								t.setCost(6);
								//add movement 
								String move = t.getState()[i][j].toString() + "&"+ t.getState()[i+1][j].toString();
								move+="L";
								t.setMovement(move);
								t.setTotalCost(curr.getTotalCost()+t.getCost());
								successors.add(t);
							}
						}

						replace_right_double(curr_state,i,j+1,i+1,j+1);
					}


					/* 2. is if we can do 2 blocks up so horizinal*/

					/*up:
					 * null null
					 * 5      6*/

					if(j+1<curr.getState()[i].length && curr.getState()[i][j+1]==null && i+1<curr.getState().length) {
						replace_up_double(curr_state,i,j,i,j+1);
						if(!H.containsKey(State_print(curr_state))) {//if the hashset isn't contain the string

							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+ "&"+ curr_state[i][j+1]+"D")) {

								Node t=new Node(curr_state);
								t.setCost(7);
								//add movement 
								String move = t.getState()[i][j].toString() + "&"+ t.getState()[i][j+1].toString();
								move+="U";
								t.setMovement(move);
								t.setTotalCost(curr.getTotalCost()+t.getCost());

								successors.add(t);
							}
						}
						replace_down_double(curr_state,i+1,j,i+1,j+1);
					}


					/* 3. is if we can do 2 blocks right so vertical  */
					/*right:
					 * 6 null
					 * 5 null*/

					if(i+1<curr.getState().length && curr.getState()[i+1][j]==null && j>0 ) {
						replace_right_double(curr_state,i,j,i+1,j);

						if(!H.containsKey(State_print(curr_state))) {
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+ "&"+ curr_state[i+1][j]+"L")) {

								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() + "&"+ t.getState()[i+1][j].toString();
								move+="R";
								t.setMovement(move);

								t.setCost(6);
								t.setTotalCost(curr.getTotalCost()+t.getCost());
								successors.add(t);
							}
						}
						replace_left_double(curr_state,i,j-1,i+1,j-1);
					}

					/* 4. 2 blocks down so horizonal */


					/*down:
					 * null null
					 * 6      6 */
					if(j+1<curr.getState()[i].length && curr.getState()[i][j+1]==null && i>0) {
						replace_down_double(curr_state,i,j,i,j+1);
						if(!H.containsKey(State_print(curr_state))) {//if the hashset isn't contain the string
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+ "&"+ curr_state[i][j+1]+"U")) {

								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() + "&"+ t.getState()[i][j+1].toString();
								move+="D";
								t.setMovement(move);

								t.setCost(7);
								t.setTotalCost(curr.getTotalCost()+t.getCost());
								successors.add(t);
							}
						}
						replace_up_double(curr_state,i-1,j,i-1,j+1);
						
					}

					/* 5. Single left */
					//the locations that are in order already we won't move
					if(j+1<curr.getState()[0].length && curr.getState()[i][j+1]!=null) {
						replace_left(curr_state,i,j);
						if(!H.containsKey(State_print(curr_state))) {//if the hashset isn't contain the string
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+"R")) {

								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() ;
								move+="L";
								t.setMovement(move);

								t.setCost(5);
								t.setTotalCost(curr.getTotalCost()+t.getCost());
								successors.add(t);
							}

						}
						replace_right(curr_state,i,j+1);//return to the location of current

					}

					/* 6. single up */
					if(i+1<curr.getState().length && curr.getState()[i+1][j]!=null) {
						replace_up(curr_state,i,j);
						if(!H.containsKey(State_print(curr_state))) {//if the hashset isn't contain the string
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+"D")) {
								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() ;
								move+="U";
								t.setMovement(move);

								t.setCost(5);
								successors.add(t);
								t.setTotalCost(curr.getTotalCost()+t.getCost());

							}
						}
						replace_down(curr_state,i+1,j);

					}
					/* 7. single right*/

					if(j>0 && curr.getState()[i][j-1]!=null) {
						replace_right(curr_state,i,j);
						if(!H.containsKey(State_print(curr_state))) {//if the hashset isn't contain the string
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+"L")) {
								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() ;
								move+="R";
								t.setMovement(move);

								t.setCost(5);
								t.setTotalCost(curr.getTotalCost()+t.getCost());

								successors.add(t);
							}	
						}
						replace_left(curr_state,i,j-1);
					}

					/* 8. single down*/
					if(i>0 && curr.getState()[i-1][j]!=null) {
						replace_down(curr_state,i,j);
						if(!H.containsKey(State_print(curr_state))) {//if the hashset isn't contain the string
							String curr_move=curr.getMovement();
							//is the opposite operation just done
							if(!curr_move.equals(curr_state[i][j]+"U")) {
								Node t=new Node(curr_state);
								//add movement 
								String move = t.getState()[i][j].toString() ;
								move+="D";
								t.setMovement(move);

								t.setCost(5);
								t.setTotalCost(curr.getTotalCost()+t.getCost());

								successors.add(t);
							}
						}
						replace_up(curr_state,i-1,j);
					}
				}
			}
		}

		return successors;


	}


	private static boolean check_equal(Integer[][] state, Integer[][] goalState2) {
		for(int i=0; i<state.length;i++) {
			for(int j=0;j<state[i].length;j++) {
				if(state[i][j]!=goalState2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}







}
