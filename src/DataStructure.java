import java.util.Arrays;

public class DataStructure implements DT {

	// >>> Fields <<<
	
	private Container firstX;
	private Container lastX;
	private Container firstY;
	private Container lastY;
	private int numContainer;
	
	
	// >>> Constructors <<<
	
	public DataStructure(){
		this.firstX = null;
		this.lastX = null;
		this.firstY = null;
		this.lastY = null;
		this.numContainer = 0;
	}
	
	
	// >>> Method <<<
	
	@Override
	public void addPoint(Point point) {
		Container newCont = new Container (point); //The object added to the list
		Container helpcont; // helper pointer
		
		if (firstX == null){ // Checks whether the list is empty
			addPointfirst(newCont,true);
			addPointfirst(newCont,false);
			
			
		}else { //the list not empty
			
			// Update pointers by x
			helpcont = LocationFront(firstX, point.getX(),true);
			if (helpcont!=null){
				newCont.setnext(helpcont.getnext(true),true);
				helpcont.setnext(newCont,true);
				newCont.setPrev(helpcont,true);
				helpcont = newCont.getnext(true);
				
				if (helpcont!= null){ // Added at the middle of the list
					(helpcont).setPrev(newCont,true);
				}else { // Added at the end of the list
					lastX = newCont;
				}
			}else { //Add to the top of the list
				addPointfirst(newCont,true);	
			}
			
			
			// Update pointers by y
			helpcont = LocationFront(firstY, point.getY(),false);
			if (helpcont!=null){
				newCont.setnext(helpcont.getnext(false),false);
				helpcont.setnext(newCont,false);
				newCont.setPrev(helpcont,false);
				helpcont = newCont.getnext(false);
				
				if (helpcont!=null){ // Added at the middle of the list
					(helpcont).setPrev(newCont,false);
				}else { // Added at the end of the list
					lastY = newCont;
				}
			}else { //Add to the top of the list
				addPointfirst(newCont,false);	
			}	
		}
		numContainer++;
	}
	

	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		
	
		//Find min 
		Container minCont = LocationFront(getFirst(axis), min , axis); 
		
		if (minCont == null){
			minCont = getFirst(axis);
		}else{ 
			minCont = minCont.getnext(axis);
		}
		
		if (minCont == null){ // the list is empty or There are no points in range
			return (new Point[0]);
		}
		
		
		// Find max
		Container maxCont = LocationBackwards(getLast(axis), max, axis);
		
		
		if (maxCont == null){
			maxCont = getLast(axis);
		}else{ 
			maxCont= maxCont.getPrev(axis);
		}
		
		if (maxCont == null){ // the list is empty or There are no points in range
			return (new Point[0]);
		}
		
		
		if ( axis && ( maxCont.getData().getX()< minCont.getData().getX() ) ||  
			!axis && ( maxCont.getData().getY()< minCont.getData().getY() )  ){
				return (new Point[0]);
		}

				
		//Finds the array size
		Container minCont2 = minCont;
		int lanthLink = 1 ; 
				
		while (!(minCont2.equals(maxCont)) && minCont2.getnext(axis)!= null){
			minCont2 = minCont2.getnext(axis);
			lanthLink ++;
		}
		Point arryPoint [] = new Point[lanthLink];
		
		//Copies the points into the array
		for (int i = 0 ; i < lanthLink ; i++){
			arryPoint[i] = minCont.getData();
			minCont = minCont.getnext(axis);
		}
		
		return arryPoint;
	}
	

	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		
		Container helpcont = getFirst(!axis);
				
		if (helpcont==null){ // the list is empty
			return (new Point[0]);
		
		}else{
			
			//Finds the array size
			int key;
			int lanthLink = 0;
			
			while (helpcont != null){
				if (axis){
					key =(helpcont.getData()).getX();
				}else { 
					key =(helpcont.getData()).getY();
				}
				
				if (key >= min && key <= max){
					lanthLink ++;
				}
				
				helpcont = helpcont.getnext(!axis);
			}
		
			Point [] arryPoint = new Point[lanthLink];
			
			//Copies the points into the array
			helpcont = getFirst(!axis);
			int i = 0;
			while (i < arryPoint.length && helpcont!=null ){
				
				if (axis){
					key =(helpcont.getData()).getX();
				}else { 
					key =(helpcont.getData()).getY();
				}
				
				if (key >= min && key <= max){
					arryPoint [i] = helpcont.getData();
					i ++;
				}
				
				helpcont = helpcont.getnext(!axis);
			}
			return arryPoint;
		}
	}
	

	@Override
	public double getDensity() {
		return  (double)numContainer /(firstX.delta(lastX,true)*firstY.delta(lastY,false));
	}
	

	@Override
	public void narrowRange(int min, int max, Boolean axis) {
		
		Container helpcont2 = getFirst(axis);
		Container helpcont1;
		Container helpcont3;
		if (helpcont2!=null){ // the list is empty

			int key;
			Boolean Continued = true;
			
			//Delete the points into the list
			while (Continued && helpcont2 != null ){
				
				if (axis){
					key =(helpcont2.getData()).getX();
				}else { 
					key =(helpcont2.getData()).getY();
				}
				
				if (key < min){
					
					//Update Links of (axis)
					helpcont1 = helpcont2.getPrev(axis);
					helpcont3 = helpcont2.getnext(axis);
					
					if (helpcont3 == null){
						setFirst(null, axis);
						setLast(null, axis);	
					}else{
						helpcont3.setPrev(null, axis);
						setFirst(helpcont3, axis);
					}

					//Update Links of (!axis)
					helpcont1 = helpcont2.getPrev(!axis);
					helpcont3 = helpcont2.getnext(!axis);
					
					if (helpcont1==null){
						
						if (helpcont3== null){
							setFirst(null, !axis);
							setLast(null, !axis);
						}else{
							helpcont3.setPrev(null, !axis);
							setFirst(helpcont3,!axis);
						}
					}else{
						
						if (helpcont3==null){
							helpcont1.setnext(null, !axis);
							setLast(helpcont1, !axis);
						}else{
							helpcont1.setnext(helpcont3, !axis);
							helpcont3.setPrev(helpcont1, !axis);
						}					
					}
					
					// next Container
					numContainer--;
					helpcont2 = helpcont2.getnext(axis);
				
				}else{
					Continued = false;
				}
			}
			
			
			helpcont2 = getLast(axis);
			Continued = true;
			
			while (Continued && helpcont2 != null ){
				
				if (axis){
					key =(helpcont2.getData()).getX();
				}else { 
					key =(helpcont2.getData()).getY();
				}
				
				if (key > max){
					
					//Update Links of (axis)
					helpcont3 = helpcont2.getPrev(axis);
					helpcont1 = helpcont2.getnext(axis);
					
					if (helpcont3 == null){
						setFirst(null, axis);
						setLast(null, axis);	
					}else{
						helpcont3.setnext(null, axis);
						setLast(helpcont3, axis);
					}

					//Update Links of (!axis)
					helpcont3 = helpcont2.getPrev(!axis);
					helpcont1 = helpcont2.getnext(!axis);
					
					if (helpcont1==null){
						
						if (helpcont3== null){
							setFirst(null, !axis);
							setLast(null, !axis);
						}else{
							helpcont3.setnext(null, !axis);
							setLast(helpcont1,!axis);
						}
					}else{
						
						if (helpcont3==null){
							helpcont1.setPrev(null, !axis);
							setFirst(helpcont1, !axis);
						}else{
							helpcont3.setnext(helpcont1, !axis);
							helpcont1.setPrev(helpcont3, !axis);
						}					
					}
					
					// next Container
					numContainer--;
					helpcont2 = helpcont2.getPrev(axis);
				
				}else{
					Continued = false;				
				}
			}
		}
	}
	

	@Override
	public Boolean getLargestAxis() {
		if (firstX == null){ 
			return false;
		}
		return ( firstX.delta(lastX,true) > firstY.delta(lastY,false) );
	}
	
	@Override
	public Container getMedian (Boolean axis) {
		
		Container mincont = getFirst(axis);
		Container maxcont = getLast(axis);
		
		
		if (mincont == null || maxcont == null){
			return null;
		
		}else {
			return	getMedianCont ( mincont, maxcont, axis);
		}
	}
	
	
	@Override
	public Point[] nearestPairInStrip(Container container, double width, Boolean axis) {
		
		if (getFirst(axis)==null){
			return (new Point [0]);
		}
				
		if (getFirst(axis).equals(getLast(axis))){
			return (new Point [0]);
		}
		
		Point [] ans = new Point [2];
		
		if (numContainer==2){
			ans[0]= getFirst(axis).getData();
			ans[1]= getLast(axis).getData();
			return ans;
		}
		int middle;
		
		
		if (axis){
			middle = container.getData().getX();
		}else{
			middle = container.getData().getY();
		}
		
		//Find max 
		int max = (int) (middle + width/2);
		Container maxcont = LocationFront (container,max,axis);
		if (maxcont == null){
			maxcont = getFirst(axis);
		}else{ 
			maxcont= maxcont.getnext(axis);
		}
		
		//Find min 
		int min = (int) (middle - width/2);
		Container mincont = LocationBackwards (container,min,axis);
		if (mincont == null){
			mincont = getLast(axis);
		}else{ 
			mincont= mincont.getPrev(axis);
		}
		
		
		//Finds the array size
		Container mincont2 = mincont;
		int lanthLink = 1 ; 
		
		while (mincont2!=null && !(mincont2.equals(maxcont)) && mincont2.getnext(axis)!= null){
			mincont2 = mincont2.getnext(axis);
			lanthLink ++;
		}
		
		Point arryPoint [] = new Point[lanthLink];
		
		//Copies the points into the array
		for (int i = 0 ; i < lanthLink ; i++){
			arryPoint[i] = mincont.getData();
			mincont = mincont.getnext(axis);
		}
		
		
		if (!axis){		
			Arrays.sort(arryPoint,new comparX());
		}else{
			Arrays.sort(arryPoint,new comparY());	
		}
				
		ans [0] = arryPoint[0];
		ans [1] = arryPoint[1];
		
		
		double minDistance = distance (ans [0],ans [1]);
		
		for (int i = 1 ; i < arryPoint.length; i++) {
			for (int j = i+1 ; j < i + 7 && j < arryPoint.length ; j++) {
				if (distance (arryPoint[i],arryPoint[j]) < minDistance ){
					ans [0] = arryPoint[i];
					ans [1] = arryPoint[j];
					minDistance = distance (ans [0],ans [1]);
				}
			}
		}
		
		return ans;
	}
	

	@Override
	public Point[] nearestPair() {
		
		
		
		//1a
		if (getFirst(true)==null || getFirst(true).equals(getLast(true))){
			return null;
		}
		
		Point [] ans;
		
		//2
		Boolean axis = getLargestAxis(); //The bigger Axis
		
		//1b
		if ( (getFirst(axis).getnext(axis)).equals(getLast(axis)) ){
			ans = new Point [2];
			ans [0] = getFirst(axis).getData();
			ans [1] = getLast(axis).getData();
			return ans;
		}
		
		//3
		Container median = getMedian (axis); 
				
		//4
		Point [] ansL = nearestPair(getFirst(axis), median.getPrev(axis) ,axis);
		Point [] ansR = nearestPair(median, getLast(axis),axis);
		
		double minDist;
		
		//5
		if (ansR==null || ansL==null){
			if (ansL==null){
				ans= ansR;
				minDist = distance(ansR [0],ansR [1]);
			}else{
				ans= ansL;
				minDist = distance(ansL [0], ansL [1]);
			}
		}else{
			if (distance(ansL [0],ansL [1])  >  distance(ansR [0],ansR [1])){
				ans =  ansR;
				minDist = distance(ansR [0],ansR [1]);
				
			}else {
				ans =  ansL;
				minDist = distance(ansL [0], ansL [1]);
			}
		}
		
		
		//6
		Point [] ansH = nearestPairInStrip(median ,2*minDist,axis);
		
		if (distance(ansH [0], ansH [1])  >  distance(ans [0],ans [1])){
			return ans;
		}else {
			return ansH;
		}
	}

	
	
	// >>> MY HELP Method <<<
	
	
	private Container getFirst(Boolean axis){
		if (axis){
			return firstX;
		}else {
			return firstY;
		}
	}
	
	private Container getLast(Boolean axis){
		if (axis){
			return lastX;
		}else {
			return lastY; 
		}	
	}
		
	private void setFirst(Container other , Boolean axis){
		if (axis){ 
			firstX = other;
		}else {
			firstY = other;
		}
	}
	
	private void setLast(Container other , Boolean axis){
		if (axis){
			lastX = other;
		}else {
			lastY = other;
		}
	}
	
		
	
	//add Container To the top of the list
	private void addPointfirst(Container newCont,Boolean axis) {
		
		newCont.setnext(getFirst(axis),axis);
		setFirst(newCont,axis);
		if ((newCont.getnext(axis))!= null){ //If the list is not empty before
			(newCont.getnext(axis)).setPrev(newCont,axis);
		}else {
			setLast(newCont,axis);
		}
	}	
	
	//Returns a pointer to the element which is before the key - Advanced to first!!! 
	private Container LocationFront (Container newCont, int m ,Boolean axis){
		
		
		while (newCont != null){	
			
			if ( ( axis && ((newCont.getData()).getX()>= m) ) ||
					( !axis && ((newCont.getData()).getY()>= m ) )){
				return newCont.getPrev(axis);
				
			}else { 
				if (newCont.getnext(axis)!= null){
					newCont = newCont.getnext(axis);
				}
				else {
					return newCont;
				}
			}
		}
		return newCont;		
	}

	
	//Returns a pointer to the element which is next the key - Advanced to last!!!
	private Container LocationBackwards (Container newCont, int m ,Boolean axis){
		
		while (newCont != null){
				if ( ( axis && ((newCont.getData()).getX()<= m) ) ||
						( !axis && ((newCont.getData()).getY()<= m ) )){
					return newCont.getnext(axis);
					
				}else { 
					if (newCont.getPrev(axis)!= null){
						newCont = newCont.getPrev(axis);
					}
					else {
						return newCont;
					}
				}
			}
			
		return newCont;		
	}
	
	
	
	private double distance (Point p1, Point p2){
		return Math.hypot(p1.getX()- p2.getX(),p1.getY()- p2.getY());
	}
	
	
	public Point[] nearestPair(Container mincont,Container maxcont,Boolean axis) {
		
		
		if (mincont == null || maxcont == null || maxcont.equals(mincont)){
			return null;
		}
		
		Point[] ans;
		if ((mincont.getnext(axis)).equals(maxcont)){
			ans = new Point [2];
			ans [0] = maxcont.getData();
			ans [1] = mincont.getData();
			return ans;
		}
		
			
		Container midCont = getMedianCont ( mincont, maxcont, axis);
		
			
		
		
		Point [] ansL = nearestPair(mincont,midCont.getPrev(axis),axis);
		
		Point [] ansR = nearestPair(midCont,maxcont,axis);
		
		if ( ansR == null && ansL == null){
			return null;
		}
		if ( ansL == null && ansR != null){
			return ansR;
		}
		if ( ansR == null && ansL != null){
			return ansL;
		}
		
			
		
		
		if (distance(ansL [0], ansL [1])  >  distance(ansR [0],ansR [1])){
			return ansR;
						
		}else {
			return ansL;
		}
	}
	
	
	
	public Container getMedianCont (Container mincont,Container maxcont,Boolean axis) {
		
		if (mincont == null || maxcont == null){
			return	null;
		}else {
			while (!(mincont.equals(maxcont))){
				
				if ( mincont.equals(maxcont) ){
					return mincont;
				}else {
					mincont = mincont.getnext(axis);
				}
				
				if ( mincont.equals(maxcont) ){
					return mincont;
				}else {
					maxcont = maxcont.getPrev(axis);
				}
			}
			return mincont;
		}
	}
	
	
	
	public void toString(boolean axis) {		
		Container min =getFirst(axis);
		Container max = getLast(axis);
		while (min!=null && !(min.equals(max))){
			System.out.println(min.getData().toString());	
			min=min.getnext(axis);
		}
	}
	
}

	

