

public class Container {
	
	// >>> Fields <<<
	
	private Point data;
	private Container nextX;
	private Container PrevX;
	private Container nextY;
	private Container PrevY;
	
	
	// >>> Constructors <<<

	public Container (Point data){
		this.data=data;
		this.nextX = null;
		this.nextY = null;
		this.PrevX = null;
		this.PrevY = null;
		
	}
	
	public Container (Container other){  //copy Constructors 
		this.data = new Point (other.getData());
		this.nextX = other.getnext(true);
		this.PrevX = other.getPrev(true);
		this.nextY = other.getnext(false);
		this.PrevY = other.getPrev(false);
	}
	
	public Container (Point data,Container nextX,Container PrevX ,Container nextY ,Container PrevY ){
		this.data = data;
		this.nextX = nextX;
		this.PrevX = PrevX;
		this.nextY = nextY;
		this.PrevY= PrevY;
	}
	
	
	// >>> Method <<<
	
	
	public Point getData(){
		return data;
	}
	
	
	public Container getnext(Boolean axis){
		if (axis){
		return nextX;
		}else {
		return nextY;
		}
	}
	
	
	public Container getPrev(Boolean axis){
		if (axis){
			return PrevX;
		}else {
		return PrevY; 
		}	
	}
		
	
	public void setnext(Container other , Boolean axis){
		if (axis){ 
			nextX = other;
		}else {
		nextY = other;
		}
	}
	
	public void setPrev(Container other , Boolean axis){
		if (axis){
			PrevX = other;
		}else {
			PrevY = other;
		}
	}
	
		
	public String toString() {
		return data.toString();
	}
	
	public boolean equals (Container other){
		if (other==null){
			return false;
		}
		return (data.equals(other.getData()));
	}
	
	// Returns a distance Between coordinates According to axis
	public int delta (Container other, Boolean axis){
		int ans;
		
		if (axis){
			ans = Math.abs((this.data).getX() - (other.getData()).getX());
		}else {
			ans = Math.abs((this.data).getY()- (other.getData()).getY());
		}
		
		return ans;
	}
	
}
