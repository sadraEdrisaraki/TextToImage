
public class Coordinate {
	public int x;
	public int y;
	Coordinate(int x, int y){
		this.x=x;
		this.y=y;
	}
	Coordinate(){
		this.x=0;
		this.y=0;
	}
	@Override
	public String toString() {
		return "x: "+x + " ; y; "+y;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this) { 
            return true; 
        } 

        if (!(o instanceof Coordinate)) { 
            return false; 
        } 
        Coordinate coo = (Coordinate) o;
        
        if(coo.x == this.x && coo.y == this.y) {
        	return true;
        }else return false;
	}
	
}
