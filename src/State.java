import java.util.ArrayList;


public class State {
	Coordinate A,B;
	double cost;
	int nodeExpanded;
	ArrayList<Coordinate> steps;
	
	public State(Coordinate A, Coordinate B, double cost, ArrayList<Coordinate> steps, int nodeExpanded){
		this.A = A;
		this.B = B;
		this.cost = cost;
		this.steps = steps;
		this.nodeExpanded = nodeExpanded;
		steps.add(new Coordinate(A.getX(),A.getY()));
	}
	
	public State(State s){
		this.A = new Coordinate(s.getA());
		this.B = new Coordinate(s.getB());
		this.cost = s.getCost();
		this.steps = new ArrayList<Coordinate>(s.getSteps());
	}
	
	public boolean goalCheck(){
		if(A.getX() == B.getX() && A.getY() == B.getY())
			return true;
		else 
			return false;
	}
	
	public Coordinate getA(){
		return A;
	}

	public void setA(Coordinate A){
		this.A = A;
	}
	
	public Coordinate getB(){
		return B;
	}
	
	public void setB(Coordinate B){
		this.B = B;
	}
	
	public double getCost(){
		return cost;
	}
	
	public void setCost(double cost){
		this.cost = cost;
	}

	public double getNodeExpanded(){
		return nodeExpanded;
	}
	
	public void setNodeExpanded(int node){
		this.nodeExpanded= node;
	}

	
	public ArrayList<Coordinate> getSteps(){
		return steps;
	}

}
