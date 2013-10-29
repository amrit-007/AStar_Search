
public class Coordinate {
	int x,y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Coordinate(Coordinate s){
		this.x = s.getX();
		this.y = s.getY();
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Coordinate){
			if(((Coordinate)o).getX() == x && ((Coordinate)o).getY() == y){
				return true;
			}
		}
		return false;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}
	
	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}
}
