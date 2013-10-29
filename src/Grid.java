
public class Grid {
	State state;
	double cost;

	public Grid(State s, double c){
		state = s;
		cost = c;
	}
	
	public void setState(State s){
		state = s;
	}
	
	public State getState(){
		return state;
	}

	public void setCost(double c){
		cost = c;
	}
	
	public double getCost(){
		return cost ;
	}
}
