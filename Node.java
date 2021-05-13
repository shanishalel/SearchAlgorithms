

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
