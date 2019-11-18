import java.util.Comparator;

public class comparX implements Comparator<Object> {

	public int compare(Object p1, Object p2) {
		
		return (((Point) p1).getX())-(((Point) p2).getX());
	}
}

