

import java.util.ArrayList;

public class Node {

	private boolean visited;

	private Integer[][] state;
	private int column;
	private int row;

	private ArrayList<Node> children;
	private Node parent;

	private String Movements="";

	private int cost;
	private int estimatedCostToGoal;
	private int totalCost;
	private int F;
	private int depth;

	static int id_number;
	public int id;


	public int getID() {
		return this.id;
	}
	public void setF(int g) {
		this.F=g;
	}

	public int getF() {
		return this.F;
	}

	public void setColumn(int column ) {
		this.column=column ;
	}

	public int getColumn() {
		return this.column;
	}

	public void setRow(int row ) {
		this.row=row ;
	}

	public void setMovement(String move) {
		this.Movements+=move;
	}

	public String getMovement() {
		return this.Movements;
	}

	public int getRow() {
		return this.row;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	public void setTotalCost(int cost, int estimatedCost) {
		this.totalCost = cost + estimatedCost;
	}

	public int getEstimatedCostToGoal() {
		return estimatedCostToGoal;
	}

	public void setEstimatedCostToGoal(int estimatedCostToGoal) {
		this.estimatedCostToGoal = estimatedCostToGoal;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setState(int [][] state) {
		for (int i=0;i<state.length;i++) {
			for(int j=0;j<state[i].length;j++) {
				this.state[i][j]=state[i][j];
			}
		}
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}


	// Constructor
	public Node(Integer[][] state) {
		this.state=new Integer[state.length][state[0].length];
		id_number++;
		this.id=id_number;
		for (int i=0;i<state.length;i++) {
			for(int j=0;j<state[i].length;j++) {
				this.state[i][j]=state[i][j];
			}
		}
		children = new ArrayList<Node>();
	}

	/*
	 * compute manhattan distance 
	 * */
	
	private int manhattan(int a,int b,Integer [][] goal) {

		int manhattan=0;
		for(int i=0;i<goal.length;i++) {
			for(int j=0;j<goal[0].length;j++) {
				if (this.getState()[a][b] == goal[i][j] && this.getState()[a][b]!=null ) {
					manhattan+=Math.abs(a-i)+Math.abs(b-j);
				}
			}
		}
		return manhattan;
	}




/*
 * the heurstic function gets the algo name and the goal state
 * if it is a A* algo we will check the cost and add by thats 
 * else we will just multiply by 3 */
	public int h(String algo, Integer [][] goal) {
			int result = 0;
			int dist = 0;
			for(int i = 0; i < getState().length; i++) {
				for(int j = 0; j < getState()[0].length; j++) {
					dist = (manhattan(i,j, goal));
					if(this.getCost() == 5) result += dist*4;
					else result += dist*3;

				}
			}
			return result;
		}







	

	// Properties
	public Integer[][] getState() {
		return state;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	// Public interface
	public void addChild(Node child) {
		children.add(child);
	}

	public String toString() {
		String ans="";
		for(int i=0;i<this.state.length;i++) {
			for(int j=0;j<this.state[0].length;j++) {
				ans+=this.state[i][j]+" ";
			}
			ans+="  ";
		}
		return ans;
	}




}
