
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


	public String BFS() {
		HashMap<String,Node> open = new HashMap<>();//open list
		HashMap<String,Node> close = new HashMap<>();//close list
		Queue<Node> L = new LinkedList<Node>(); 

		L.add(root);
		int Num=1;//number of nodes that created

		while (!L.isEmpty()) {
			Node n=L.poll();

			if(this.WithOpen==true) {
				PrintOpen(open);
			}
			close.put(n.toString(),root);
			open.remove(n.toString());

			List<Node> nodeSuccessors = Node_Operator.getOperators(n,goalState,open);
			for (Node g : nodeSuccessors) {

				g.setParent(n);                   
				g.setVisited(true);

				if(!open.containsKey(g.toString()) && !close.containsKey(g.toString())) {
					Num++;
					if(check_equal(g.getState(),goalState)) {
						String res=Node_Operator.getPath(g,root);
						res+="\nNum:"+Num;
						int Cost=Node_Operator.getCost(g, root);
						res+="\nCost: "+Cost;
						return res;
					}

					L.add(g);
					open.put(g.toString(),g);
				}

			}

		}
		String res="no path";
		res+="\nNum:"+Num;
		return res;
	}


	//print the open list to the screen
	private void PrintOpen(HashMap<String, Node> open) {
		System.out.println(open.values().toString());

	}

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





	static int numvertex;

	public String DFID()  {
		numvertex=1;
		String result="";
		for(int depth=0;depth<Integer.MAX_VALUE;depth++) {
			HashMap<String,Node> stateSets = new HashMap<>();
			result=limited_dfs(root,goalState, depth,stateSets);
			if(this.WithOpen==true) {
				PrintOpen(stateSets);
			}

			if(!result.equals("cutoff")) {
				return result;
			}


		}
		String res="no path";
		res+="\nNum:"+numvertex;
		return res;


	}

	public String limited_dfs(Node n , Integer [][] goalState, int limit, HashMap <String,Node> H) {
		String result="";

		if(check_equal(goalState, n.getState())) {
			result=Node_Operator.getPath(n,root);
			result+="\nNum:"+numvertex;
			int Cost=Node_Operator.getCost(n, root);
			result+="\nCost: "+Cost;
			return result;
		}
		else if(limit==0) {
			return "cutoff";
		}
		else {

			H.put(n.toString(),n);
			numvertex++;
			boolean isCutoff=false; 
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


			H.remove(n.toString());
			if(isCutoff==true) {
				return "cutoff";
			}
			else {
				return "fail";
			}
		}

	}

	public String A_Star() {
		String result="";

		HashMap<String,Node> open = new HashMap<>();
		HashMap<String,Node> close = new HashMap<>();
		NodePriorityComparator nodePriorityComparator = new NodePriorityComparator();
		PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(5, nodePriorityComparator);

		int NumVertex=1;
		Node currentNode = new Node(root.getState());


		currentNode.setF(currentNode.getTotalCost()+heuristicFunction(currentNode.getState(), goalState));
		
		nodePriorityQueue.add(currentNode);
		open.put(currentNode.toString(),currentNode);


		while (!nodePriorityQueue.isEmpty()) {

			if(this.WithOpen==true) {
				PrintOpen(open);
			}

			Node n=nodePriorityQueue.poll();

			open.remove(n.toString());

			if(check_equal(goalState,n.getState())) {
				result=Node_Operator.getPath(n,root);
				result+="\nNum:"+NumVertex;
				int Cost=Node_Operator.getCost(n, root);
				result+="\nCost: "+Cost;
				return result;

			}
			close.put(n.toString(), n);

			List<Node> nodeSuccessors = Node_Operator.getOperators(n, goalState,open);

			for (Node x : nodeSuccessors) {

				if(!close.containsKey(x.toString()) && !nodePriorityQueue.contains(x) ) {
					x.setParent(n);
					NumVertex++;	
					x.setF(x.getTotalCost()+heuristicFunction(x.getState(), goalState));
					open.put(x.toString(), x);
					nodePriorityQueue.add(x);

				}
				else if(nodePriorityQueue.contains(x)) {
					Node z= open.remove(x.toString());
					if(z.getF()>x.getF()) {
						open.put(x.toString(), x);
						nodePriorityQueue.remove(z);//remove the one with higher cost
						nodePriorityQueue.add(x);

					}

				}

			}
		}

		String res="no path";
		res+="\nNum:"+NumVertex;
		return res;
	}






	/*
	 * this function compute the 
	 * */

	private int heuristicFunction(Integer [][] currentState, Integer[][] goalState) {
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

		return 3*manhattanDistance;



	} 

	private int heuristicFunction_IDA(Integer [][] currentState, Integer[][] goalState) {
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

		return 3*manhattanDistance;



	} 





	public String IDA_Star(){
		int NumVertex=0;
		String result="";

		Node start=root;
		HashMap<String, Node> H = new HashMap<String, Node>(); 
		Stack<Node> ST = new Stack<>(); 


		start.setF(start.getTotalCost()+heuristicFunction_IDA(start.getState(),goalState));

		int t=start.getF();
		while (t!=Integer.MAX_VALUE){
			int minF = Integer.MAX_VALUE;
			start.setVisited(false);
			ST.push(start);
			H.put(start.toString(), start);
			while (!ST.isEmpty()){
				Node n = ST.pop();
				if (n.isVisited()) {
					H.remove(n.toString());
				}
				else{
					n.setVisited(true);
					ST.push(n);

					List<Node> nodeSuccessors = Node_Operator.getOperators(n, goalState);
					for (Node g : nodeSuccessors){
						NumVertex++;
						g.setParent(n);                   
						g.setF(n.getTotalCost()+heuristicFunction(g.getState(),goalState));
												

						if(this.WithOpen==true) {
							PrintOpen(H);
						}

						if (g.getF()>t){
							minF = Math.min(minF,g.getF());
							continue;
						}
						Node re = H.get(g.toString());
						if (H.containsKey(g.toString()) && re.isVisited())
							continue;
						if (H.containsKey(g.toString()) && !re.isVisited()) {
							if (re.getF() > g.getF()) {
								H.remove(re.toString());
								ST.remove(g);
							}
							else
								continue;
						}

						if (check_equal(g.getState(),goalState)) {
							result=Node_Operator.getPath(g,root);
							result+="\nNum:"+NumVertex;
							int Cost=Node_Operator.getCost(g, root);
							result+="\nCost: "+Cost;
							return result;
						}
						
						ST.push(g);
						H.put(g.toString(),g);
					}
				}
			}
			
			t= minF;
			start.setVisited(false);
			
		}
		String res="no path";
		res+="\nNum:"+NumVertex;
		return res;


	}






	//	public String IDA_Star() {
	//		int NumVertex=1;
	//		String result="";
	//
	//		HashMap<String, Node> open=new HashMap<>();
	//		Stack<Node> L = new Stack<Node>();
	//		
	//
	//		int t=root.getTotalCost()+heuristicFunction(root.getState(),goalState);
	//
	//		while(t!=Integer.MAX_VALUE) {
	//			int minF=Integer.MAX_VALUE;
	//
	//			L.push(root);
	//			open.put(root.toString(),root);
	//
	//			while(!L.isEmpty()) {
	//
	//				if(this.WithOpen==true) {
	//					PrintOpen(open);
	//				}
	//
	//				Node n= L.pop();
	//				if(n.isVisited()) {//mark as out
	//					open.remove(n.toString());
	//				}
	//				else {
	//					n.setVisited(true);//mark as "out"
	//					L.push(n);
	//					List<Node> nodeSuccessors = Node_Operator.getOperators(n, goalState);
	//					for (Node g : nodeSuccessors) {
	//						NumVertex++;
	//						g.setParent(n);                   
	//						g.setF(n.getTotalCost()+heuristicFunction(g.getState(),goalState));
	//						
	//						if(g.getF()>t) {
	//							minF=Math.min(minF,g.getF());
	//							continue;
	//						}
	//						Node take=open.get(g.toString());
	//						if(open.containsKey(g.toString()) && take.isVisited()) {
	//							continue;
	//						}
	//						if(open.containsKey(g.toString()) && take.isVisited()==false) {
	//							if(take.getF()>g.getF()){
	//								L.remove(take);
	//								open.remove(take.toString());
	//							}
	//							else {
	//								continue;
	//							}
	//
	//						}
	//						if(check_equal(g.getState(),goalState)) {
	//							result=Node_Operator.getPath(g,root);
	//							result+="\nNum:"+NumVertex;
	//							int Cost=Node_Operator.getCost(g, root);
	//							result+="\nCost: "+Cost;
	//							return result;
	//
	//						}
	//						L.push(g);
	//						open.put(g.toString(),g);
	//
	//					}
	//				}
	//
	//			}
	//			t=minF;
	//			root.setVisited(false);//initialize the root
	//
	//
	//
	//
	//		}
	//
	//		String res="no path";
	//		res+="\nNum:"+NumVertex;
	//		return res;
	//
	//	}
	//


	public String DFBnB() {
		int num=0;
		String result = ""; 
		//create openList
		HashMap<String,Node> H = new HashMap<String,Node>();  

		Stack<Node> my_stack = new Stack<Node>();

		Stack<Node> save_order = new Stack<Node>();

		Node parent  = new Node(root.getState());
		int t = Integer.MAX_VALUE;
		my_stack.add(parent);
		H.put(parent.toString(), parent);

		while(!my_stack.empty()) 
		{
			if(this.WithOpen==true) {
				PrintOpen(H);
			}

			parent = my_stack.pop();
			if(parent.isVisited()) {
				H.remove(parent.toString());
			}else {
				parent.setVisited(true); 
				my_stack.add(parent);
				num++;

				
				PriorityQueue<Node> priority_queue = new PriorityQueue<Node>(new NodePriorityComparator());				
				List<Node> new_ = Node_Operator.getOperators(parent, goalState);

				for(Node a : new_) {	

					a.setParent(parent);
					a.setF((parent.getTotalCost())+ heuristicFunction(a.getState(), goalState) );
					priority_queue.add(a);
				}
				Iterator<Node> it = priority_queue.iterator();
				while(it.hasNext()) {
					Node curr = it.next();
					if(curr.getF() >= t)
					{
						//remove g all the nodes after it
						while(!priority_queue.isEmpty()) {
							//savig the order-cause we cant delete all the priority queue
							if(!priority_queue.peek().toString().equals(curr.toString())) {
								save_order.add(priority_queue.poll());
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
					else if( H.get(curr.toString()) != null &&  H.get(curr.toString()).isVisited()){
						priority_queue.remove(curr);
						it = priority_queue.iterator();
					}else if(H.get(curr.toString()) !=null &&  !H.get(curr.toString()).isVisited()) {
						if(H.get(curr.toString()).getF() <= curr.getF()) {
							priority_queue.remove(curr);
							it = priority_queue.iterator();
						}else {
							my_stack.remove(H.get(curr.toString()));
							H.remove(curr.toString());
						}
					}else if(check_equal(curr.getState(), goalState)) {						
						t = curr.getF();
						result=Node_Operator.getPath(curr,root);
						result+="\nNum:"+num;
						int Cost=Node_Operator.getCost(curr, root);
						result+="\nCost: "+Cost;

						while(!priority_queue.isEmpty()) {
							if(!priority_queue.peek().toString().equals(curr.toString())) {
								save_order.add(priority_queue.poll());
							}else {
								priority_queue.clear();
							}
						}
						while(!save_order.isEmpty()) {
							priority_queue.add(save_order.pop());
						}	
						it = priority_queue.iterator();	
					}
				}
				//insert to priority queue in reverse
				Stack<Node> stack_temp = new Stack<Node>();
				while(!priority_queue.isEmpty()) {
					stack_temp.add(priority_queue.poll());
				}
				while(!stack_temp.isEmpty()) 
				{
					Node temp = stack_temp.pop();
					my_stack.add(temp);
					H.put(temp.toString(), temp);
				}
			}
		}
		return result;

	}
}





