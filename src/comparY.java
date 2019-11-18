import java.util.Comparator;

public class comparY implements Comparator<Object> {

	public int compare(Object p1, Object p2) {
		
		return (((Point) p1).getY())-(((Point) p2).getY());
	}
}

