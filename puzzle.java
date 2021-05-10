
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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

			List<Node> nodeSuccessors = Node_Operator.getOperators(n,goalState);
			for (Node g : nodeSuccessors) {
				Num++;
				g.setParent(n);                   
				g.setVisited(true);

				if(!open.containsKey(g.toString()) && !close.containsKey(g.toString())) {
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
			List<Node> nodeSuccessors = null;
			H.put(n.toString(),n);
			boolean isCutoff=false; 
			nodeSuccessors = Node_Operator.getOperators(n,goalState,H);
			for (Node g : nodeSuccessors) {
				
				
				numvertex++;
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

		currentNode.setTotalCost(0 ,heuristicFunction(currentNode.getState(), goalState));

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

			List<Node> nodeSuccessors = Node_Operator.getOperators(n, goalState,close);

			for (Node x : nodeSuccessors) {
				NumVertex++;

				x.setParent(n);
				x.setVisited(true);
				if(!close.containsKey(x.toString()) && !nodePriorityQueue.contains(x) ) {
					x.setTotalCost(n.getTotalCost() ,heuristicFunction(x.getState(), goalState));
					open.put(x.toString(), x);
					nodePriorityQueue.add(x);

				}
				else if(nodePriorityQueue.contains(x)) {
					Node z= open.remove(x.toString());
					if(z.getTotalCost()>x.getTotalCost()) {
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





	public String IDA_Star() {
		int NumVertex=1;
		String result="";

		HashMap<String, Node> open=new HashMap<>();
		Stack<Node> L = new Stack<Node>();

		int t=root.getTotalCost()+heuristicFunction(root.getState(),goalState);

		while(t!=Integer.MAX_VALUE) {
			int minF=Integer.MAX_VALUE;

			L.push(root);
			open.put(root.toString(),root);

			while(!L.isEmpty()) {

				if(this.WithOpen==true) {
					PrintOpen(open);
				}

				Node n= L.pop();
				if(n.isVisited()) {//mark as out
					open.remove(n.toString());
				}
				else {
					n.setVisited(true);//mark as "out"
					L.push(n);
					List<Node> nodeSuccessors = Node_Operator.getOperators(n, goalState);
					for (Node g : nodeSuccessors) {
						NumVertex++;

						g.setParent(n);                   
						int f_g=g.getTotalCost()+heuristicFunction(g.getState(),goalState);

						if(f_g>t) {
							minF=Math.min(minF,f_g);
							continue;
						}
						Node take=open.get(g.toString());
						if(open.containsKey(g.toString()) && take.isVisited()) {
							continue;
						}
						if(open.containsKey(g.toString()) && take.isVisited()==false) {
							if(take.getTotalCost()+heuristicFunction(take.getState(),goalState)>f_g){
								L.remove(take);
								open.remove(take.toString());
							}
							else {
								continue;
							}

						}
						if(check_equal(g.getState(),goalState)) {
							result=Node_Operator.getPath(g,root);
							result+="\nNum:"+NumVertex;
							int Cost=Node_Operator.getCost(g, root);
							result+="\nCost: "+Cost;
							return result;

						}
						L.push(g);
						open.put(g.toString(),g);

					}
				}


			}
			t=minF;
			root.setVisited(false);//initialize the root




		}

		String res="no path";
		res+="\nNum:"+NumVertex;
		return res;


	}






	public String DFBnB() {
		int NumVertex=1;
		String result="";

		HashMap<String, Node> H=new HashMap<>();
		Stack<Node> L = new Stack<Node>();

		H.put(root.toString(), root);
		L.push(root);

		int t=Integer.MAX_VALUE;

		while(!L.isEmpty()) {
			Node n=L.pop();

			if(this.WithOpen==true) {
				PrintOpen(H);
			}

			if(n.isVisited()) {//mark as "out"
				H.remove(n.toString());
			}
			else {
				n.setVisited(true);//make as "out"
				L.push(n);

				List<Node> nodeSuccessors = Node_Operator.getOperators(n, goalState);
				Node [] N=new Node[nodeSuccessors.size()];

				//apply all of the allowed operators on n
				int i=0;
				for (Node g : nodeSuccessors) {
					NumVertex++;
					g.setParent(n);
					g.setTotalCost(g.getTotalCost(), heuristicFunction(g.getState(), goalState));
					N[i]=g;
					i++;

				}
				//sort N
				Arrays.asList(N);
				NodePriorityComparator nodePriorityComparator = new NodePriorityComparator();
				Arrays.sort(N,nodePriorityComparator);
				

				for (i=0;i<N.length;i++) {
					if(N[i].getTotalCost()>=t) {
						//remove g and all the nodes after it 
						
						N[i]=null;
						while(i<N.length) {
							N[i]=null;
							i++;
						}
						
						
						
					}
					else if(H.containsKey(N[i].toString()) && N[i].isVisited()) {
						//remove g from N
						N[i]=null;
					}
					else if(H.containsKey(N[i].toString()) && !N[i].isVisited()) {
						Node g_tag=H.get(N[i].toString());
						if(g_tag.getTotalCost()<=N[i].getTotalCost()) {
							//remove g from N
							N[i]=null;

						}
						else {
							L.remove(g_tag);
							H.remove(g_tag.toString());
						}
					}
					else if(N[i]!=null && check_equal(N[i].getState(), goalState) ) {

						t=N[i].getTotalCost()+ heuristicFunction(N[i].getState(), goalState);
						result=Node_Operator.getPath(N[i],root);
						result+="\nNum:"+NumVertex;
						int Cost=Node_Operator.getCost(N[i], root);
						result+="\nCost: "+Cost;

						if(Cost<t) {
							return result;
						}


						//remove g and all the nodes after it 
						N[i]=null;
						while(i<N.length) {
							N[i]=null;
							i++;
						}
					}
				}

				//insert N into L and H
				for(int j=N.length-1;j>=0;j--) {
					if(N[j]!=null) {
						L.push(N[j]);
						H.put(N[j].toString(),N[j]);
					}					


				}

			}
		}

		String res="no path";
		res+="\nNum:"+numvertex;
		return res;

	}
}
