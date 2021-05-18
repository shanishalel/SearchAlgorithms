
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;



public class puzzle {
	private Node root;
	private Integer[][] goalState;
	public boolean WithOpen=false;

	public Node getRoot() {
		return root;
	}

	public void setWithOpen(boolean a) {
		this.WithOpen=a;
	}

	public boolean getWithOpen() {
		return WithOpen;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public Integer[][] getGoalSate() {
		return goalState;
	}

	public void setGoalSate(int[][] goalState) {
		for(int i=0;i<goalState.length;i++) {
			for(int j=0;j<goalState[i].length;j++) {
				this.goalState[i][j] = goalState[i][j];
			}
		}

	}

	public puzzle(Node root, Integer[][] goalState) {
		this.root = root;
		this.goalState=new Integer[goalState.length][goalState[0].length];
		for(int i=0;i<goalState.length;i++) {
			for(int j=0;j<goalState[i].length;j++) {
				this.goalState[i][j] = goalState[i][j];
			}
		}
	}



	//*******************************Algorithms*******************************

/*
 * the BFS algo */
	public String BFS() {
		HashMap<String,Node> open = new HashMap<>();//open list
		HashMap<String,Node> close = new HashMap<>();//close list
		Queue<Node> L = new LinkedList<Node>(); 

		L.add(root);
		int Num=1;//number of nodes that created

		while (!L.isEmpty()) {
			Node n=L.poll();//poll the front from L stack

			if(this.WithOpen==true) {
				PrintOpen(open);
			}
			
			close.put(n.toString(),root);
			open.remove(n.toString());//remove from open 

			//gets operators and create them only if the open list is't contains them  
			List<Node> nodeSuccessors = Node_Operator.getOperators(n,goalState,open);
			
			for (Node g : nodeSuccessors) {

				g.setParent(n);//set the n as g parent                    
				g.setVisited(true);//set as visited node
				
				
				if(!open.containsKey(g.toString()) && !close.containsKey(g.toString())) {
					Num++;
					//if we are in the goal state
					if(check_equal(g.getState(),goalState)) {
						String res=Node_Operator.getPath(g,root);
						res+="\nNum: "+Num;
						int Cost=Node_Operator.getCost(g, root);
						res+="\nCost: "+Cost;
						return res;
					}

					L.add(g);//add it to the stack 
					open.put(g.toString(),g);//put in the open list 
				}

			}

		}
		String res="no path";
		res+="\nNum: "+Num;
		return res;
	}


	// function that print the open list to the screen
	private void PrintOpen(HashMap<String, Node> open) {
		System.out.println(open.values().toString());

	}

	/*
	 * the function checking if the state and the goalState2 is equal*/
	private boolean check_equal(Integer[][] state, Integer[][] goalState2) {
		for(int i=0; i<state.length;i++) {
			for(int j=0;j<state[i].length;j++) {
				if(state[i][j]!=goalState2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}


	static int numvertex;//static var

	/*
	 * dfid that is the function that is the "matefet" function */
	public String DFID()  {
		numvertex=1;
		String result="";
		for(int depth=0;depth<Integer.MAX_VALUE;depth++) {
			HashMap<String,Node> stateSets = new HashMap<>();
			//limited dfs till the depth we at 
			result=limited_dfs(root,goalState, depth,stateSets);
			if(this.WithOpen==true) {
				PrintOpen(stateSets);
			}

			if(!result.equals("cutoff")) {
				return result;
			}


		}
		String res="no path";
		res+="\nNum: "+numvertex;
		return res;

	}

	public String limited_dfs(Node n , Integer [][] goalState, int limit, HashMap <String,Node> H) {
		String result="";
		//if we got the goal state
		if(check_equal(goalState, n.getState())) {
			result=Node_Operator.getPath(n,root);
			result+="\nNum: "+numvertex;
			int Cost=Node_Operator.getCost(n, root);
			result+="\nCost: "+Cost;
			return result;
		}
		//if we got limit ==0 so we done in this iteration
		else if(limit==0) {
			return "cutoff";
		}
		else {

			H.put(n.toString(),n);
			numvertex++;
			boolean isCutoff=false; 
			//got the operators and checking is the H contains this node before adding it
			List<Node> nodeSuccessors = Node_Operator.getOperators(n,goalState,H);
			
			for (Node g : nodeSuccessors) {
				
				g.setParent(n);    
				result=limited_dfs(g,goalState,limit-1,H);

				if(result.equals("cutoff")) {
					isCutoff=true;
				}
				else if(!result.equals("fail")){
					return result;
				}

			}

			//remove n fron H set
			H.remove(n.toString());
			
			if(isCutoff==true) {
				return "cutoff";
			}
			else {
				return "fail";
			}
		}

	}

	/*
	 * the function of A* algo */
	
	public String A_Star() {
		String result="";

		HashMap<String,Node> open = new HashMap<>();//open list
		HashMap<String,Node> close = new HashMap<>();//close list 
		
		NodePriorityComparator nodePriorityComparator = new NodePriorityComparator();
		PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(5, nodePriorityComparator);

		int NumVertex=1;
		Node currentNode = new Node(root.getState());

		//herustic function
		currentNode.setF(currentNode.getTotalCost()+(int)currentNode.h("A*",goalState));

		nodePriorityQueue.add(currentNode);
		open.put(currentNode.toString(),currentNode);


		while (!nodePriorityQueue.isEmpty()) {

			if(this.WithOpen==true) {
				PrintOpen(open);
			}

			Node n=nodePriorityQueue.poll();//remove the first in the priority queue

			open.remove(n.toString());//remove the same node the we remove from the priority queue

			//if we gets to goal 
			if(check_equal(goalState,n.getState())) {
				result=Node_Operator.getPath(n,root);
				result+="\nNum: "+NumVertex;
				int Cost=Node_Operator.getCost(n, root);
				result+="\nCost: "+Cost;
				return result;

			}
			close.put(n.toString(), n);
			
			//gets the operators, this function gets the open list and don't initialized node that 
			//the open list contains
			List<Node> nodeSuccessors = Node_Operator.getOperators(n, goalState,open);

			for (Node x : nodeSuccessors) {

				if(!close.containsKey(x.toString()) && !nodePriorityQueue.contains(x) ) {
					x.setParent(n);
					NumVertex++;	
					//heuristic function
					x.setF(n.getTotalCost()+x.h("A*",goalState));
					
					open.put(x.toString(), x);//add to the open list
					nodePriorityQueue.add(x);//add to priority queue

				}
				
				else if(nodePriorityQueue.contains(x)) {
					Node z= open.remove(x.toString());
					//if the open list contains var that his f is smaller
					if(z.getF()>x.getF()) {
						open.put(x.toString(), x);
						nodePriorityQueue.remove(z);//remove the one with higher cost
						nodePriorityQueue.add(x);//add the one with the smallest cost

					}

				}

			}
		}

		String res="no path";
		res+="\nNum: "+NumVertex;
		return res;
	}





/*the IDA* function 
 * 
 * */
	
	public String IDA_Star(){
		int NumVertex=0;
		String result="";

		Node start=root;
		HashMap<String, Node> H = new HashMap<String, Node>(); 
		Stack<Node> L = new Stack<>(); 

		//the heurstic function 
		start.setF((start.getTotalCost()+start.h("IDA*",goalState)));

		int t=start.getF();
		while (t!=Integer.MAX_VALUE){

			int minF = Integer.MAX_VALUE;
			start.setVisited(false);

			L.push(start);
			H.put(start.toString(), start);

			while (!L.isEmpty()){
				if(this.WithOpen==true) {
					PrintOpen(H);

				}
				Node n = L.pop();
				
				if (n.isVisited()) {
					H.remove(n.toString());
				}
				else{
					n.setVisited(true);
					L.push(n);
					
					//got all the operators
					List<Node> nodeSuccessors = Node_Operator.getOperators(n, goalState);
					for (Node g : nodeSuccessors){
						NumVertex++;
						//we initialized n as g parent
						g.setParent(n); 

						//the herustic function 
						g.setF(g.getTotalCost()+g.h("IDA*",goalState));

						if (g.getF()>t){
							minF = Math.min(minF,g.getF());
							continue;
						}
						//we visit in it 
						if (H.containsKey(g.toString()) && H.get(g.toString()).isVisited())
							continue;
						//we won't visit it 
						if (H.containsKey(g.toString()) && !H.get(g.toString()).isVisited()) {
							if (H.get(g.toString()).getF() > g.getF()) {
								H.remove(g.toString());
								L.remove(g);
							}
							else {
								continue;
							}
						}
						//got to goal node!
						if (check_equal(g.getState(),goalState)) {
							result=Node_Operator.getPath(g,root);
							result+="\nNum: "+NumVertex;
							int Cost=Node_Operator.getCost(g, root);
							result+="\nCost: "+Cost;
							return result;
						}

						L.push(g);
						H.put(g.toString(),g);
					}
				}
			}

			t= minF;

		}
		String res="no path";
		res+="\nNum: "+NumVertex;
		return res;


	}





	public String DFBnB() {
		int num=0;
		String result = ""; 
		//create openList
		HashMap<String,Node> open = new HashMap<String,Node>();  

		Stack<Node> stack_L = new Stack<Node>();
		
		Stack<Node> reverse=new Stack<Node>();
		
		//I will create a stack that we save the order as needed
		Stack<Node> save_order = new Stack<Node>();
		
		Node parent  = new Node(root.getState());
		int t = Integer.MAX_VALUE;
		stack_L.push(parent);
		open.put(parent.toString(), parent);

		while(!stack_L.empty()) 
		{
			if(this.WithOpen==true) {
				PrintOpen(open);
			}

			parent = stack_L.pop();//gets the front in the stack
			//we visit this parent
			if(parent.isVisited()) {
				open.remove(parent.toString());
			}else {
				
				parent.setVisited(true); 
				stack_L.push(parent);
				num++;
				
				//priority queue as the N that hold all the operators by order 
				PriorityQueue<Node> priority_queue = new PriorityQueue<Node>(new NodePriorityComparator());				
				List<Node> successors = Node_Operator.getOperators(parent, goalState);

				for(Node child : successors) {	
					//set parent as a parent 
					child.setParent(parent);
					//heuristic function
					child.setF(child.getTotalCost()+child.h("DFBnB",goalState));
					//add to the priority queue
					priority_queue.add(child);
				}
				Iterator<Node> it = priority_queue.iterator();
				//for each operator in N
				while(it.hasNext()) {
					Node curr = it.next();
					if(curr.getF() >= t)
					{
						//remove g all the nodes after it
						while(!priority_queue.isEmpty()) {
							//saving the order-cause we cant delete all the priority queue
							if(!priority_queue.peek().toString().equals(curr.toString())) {
								save_order.push(priority_queue.poll());
							}else {
								priority_queue.clear();
							}
						}
						//add to priority queue again
						while(!save_order.isEmpty()) {
							priority_queue.add(save_order.pop());
						}

						it = priority_queue.iterator();
					}
					else if( open.get(curr.toString()) != null &&  open.get(curr.toString()).isVisited()){

						priority_queue.remove(curr);
						it = priority_queue.iterator();
						
					}else if(open.get(curr.toString()) !=null &&  !open.get(curr.toString()).isVisited()) {
						//open contains the same node but with smaller f
						if(open.get(curr.toString()).getF() <= curr.getF()) {
							priority_queue.remove(curr);
							it = priority_queue.iterator();
						}else {
							
							stack_L.remove(open.get(curr.toString()));
							open.remove(curr.toString());
						}
						//we got the goal node
					}else if(check_equal(curr.getState(), goalState)) {						
						t = curr.getF();
						result=Node_Operator.getPath(curr,root);
						result+="\nNum: "+num;
						int Cost=Node_Operator.getCost(curr, root);
						result+="\nCost: "+Cost;
						
						while(!priority_queue.isEmpty()) {
							//we not in the curr node
							if(!priority_queue.peek().toString().equals(curr.toString())) {
								//we will save the order as it was
								save_order.push(priority_queue.poll());
							}else {
								//delete it 
								priority_queue.clear();
							}
						}
						//after we clear the queue we will add it int the order we saved
						while(!save_order.isEmpty()) {
							priority_queue.add(save_order.pop());
						}	
						//initialize the iterator again
						it = priority_queue.iterator();	
					}
				}
				//insert to priority queue in reverse
				reverse = new Stack<Node>();
				
				while(!priority_queue.isEmpty()) {
					reverse.push(priority_queue.poll());
				}
				while(!reverse.isEmpty()) 
				{
					Node temp = reverse.pop();
					stack_L.push(temp);
					open.put(temp.toString(), temp);
				}
			}
		}
		String res="no path";
		res+="\nNum: "+num;
		return res;
	}
	
	
		
	
}

















