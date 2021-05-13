
import java.util.Comparator;

/**
 * This is a comparator used by the priorityQueue to store nodes sorted by the cost value
 */
public class NodePriorityComparator implements Comparator<Node> {

	@Override
	public int compare (Node x, Node y) {

		if (x.getF() < y.getF()) {
			return -1;
		}
		if (x.getF() > y.getF()) {
			return 1;
		}

		else if (x.getDepth()==y.getDepth()) {
			if (x.getMovement().length()==4 && x.getMovement().contains("L") ) return -1;//7&8L
			if (y.getMovement().length()==4 && y.getMovement().contains("L")) return 1;
			if (x.getMovement().length()==4 && x.getMovement().contains("U")) return -1;
			if (y.getMovement().length()==4 && y.getMovement().contains("U")) return 1;
			if (x.getMovement().length()==4 && x.getMovement().contains("R")) return -1;
			if (y.getMovement().length()==4 && y.getMovement().contains("R")) return 1;
			if (x.getMovement().length()==4 && x.getMovement().contains("D")) return -1;
			if (y.getMovement().length()==4 && y.getMovement().contains("D")) return 1;

			if (x.getMovement().contains("L")) return -1;//7L
			if (y.getMovement().contains("L")) return 1;
			if (x.getMovement().contains("U")) return -1;
			if (y.getMovement().contains("U")) return 1;
			if (x.getMovement().contains("R")) return -1;
			if (y.getMovement().contains("R")) return 1;
			if (x.getMovement().contains("D")) return -1;
			if (y.getMovement().contains("D")) return 1;

		}
		return 0;
	}
}

